package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

class CellPanel extends JPanel {

  private SpreadsheetReadOnlyModel model;
  private SpreadsheetView view;
  private int cellsToBeShownX;
  private int cellsToBeShownY;
  private int furthestX;
  private int furthestY;
  private List<List<JComponent>> visibleCells;
  private JPanel cellPanel;
  private static int HBARMAX = 500;
  private static int VBARMAX = 500;
  private static int SCROLLINCREMENT = 1;
  private int width;
  private int height;
  private Coord focus;
  private JScrollBar hBar;
  private JScrollBar vBar;

  CellPanel(SpreadsheetReadOnlyModel model, SpreadsheetView view,
      int cellsToBeShownX, int cellsToBeShownY, int width, int height) {
    super();
    this.model = model;
    this.view = view;
    this.cellsToBeShownX = cellsToBeShownX;
    this.cellsToBeShownY = cellsToBeShownY;
    furthestX = this.cellsToBeShownX;
    furthestY = this.cellsToBeShownY;
    this.width = width;
    this.height = height;
    this.setFocusable(true);

    setSize(width, height);
    setLocation(0, 0);
    this.setMinimumSize(new Dimension(width * 3 / 4, height * 3 / 4));
    //use a border layout for the overall frame
    this.setLayout(new BorderLayout());
    this.model = model;
    GridBagLayout layout = new GridBagLayout();
    JPanel grid = new JPanel(layout);
    this.cellPanel = grid;
    visibleCells = new ArrayList<>();

    fillCells();
    scrollButtons();
    displayCells();
    addKeys();
    this.focus = new Coord(1, 1);
    JComponent c = visibleCells.get(focus.row).get(focus.col);
    c.setBackground(Color.LIGHT_GRAY);
    c.updateUI();
  }

  //displays cells with specific formatting for grid bag layout
  private void displayCells() {
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1;
    c.weighty = 1;
    for (int i = 0; i < visibleCells.size(); i++) {
      for (int j = 0; j < visibleCells.get(i).size(); j++) {
        c.gridx = j;
        c.gridy = i;
        this.cellPanel.add(visibleCells.get(i).get(j), c);
      }
    }
    cellPanel.updateUI();
    this.add(cellPanel);
  }

  //fill the cells with values
  private void fillCells() {
    visibleCells = new ArrayList<>();

    //add the first row, blank first square and A-Z for the rest
    List<JComponent> tempList = new ArrayList<>();
    tempList.add(new JLabel(""));
    for (int x = furthestX - cellsToBeShownX + 1; x <= furthestX; x++) {
      JLabel tempJLabel = new JLabel(Coord.colIndexToName(x), SwingConstants.CENTER);
      tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
      tempList.add(tempJLabel);
    }
    visibleCells.add(tempList);

    //add the rest of the rows, row number and then the cells
    for (int y = furthestY - cellsToBeShownY + 1; y <= furthestY; y++) {
      tempList = new ArrayList<>();

      for (int x = furthestX - this.cellsToBeShownX; x <= furthestX; x++) {
        if (x == furthestX - this.cellsToBeShownX) {
          JLabel tempJLabel = new JLabel(Integer.toString(y), SwingConstants.CENTER);
          tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
          tempList.add(tempJLabel);
        } else {
          JTextField tempTextField;
          try {
            Coord c = new Coord(x, y);
            tempTextField = new JTextField(model.getComputedValue(c));
          } catch (IllegalArgumentException e) {
            tempTextField = new JTextField("#ERROR");
          }
          tempTextField.setBorder(new LineBorder(Color.GRAY, 1));
          tempTextField.setEditable(false);
          tempTextField.setColumns(10);
          Coord finalC = new Coord(x, y);
          tempTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              CellPanel.this.grabFocus();
              changeFocus(finalC);
            }
          });
          tempList.add(tempTextField);
        }
      }
      visibleCells.add(tempList);
    }
  }

  //add the scroll buttons to this frame
  private void scrollButtons() {

    JPanel horizontalScroll = new JPanel();
    hBar = new JScrollBar(JScrollBar.HORIZONTAL, cellsToBeShownX,
        SCROLLINCREMENT, cellsToBeShownX, HBARMAX);
    hBar.addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
        horizontalScroll(e, hBar);
      }
    });
    hBar.setBackground(Color.BLACK);
    hBar.setPreferredSize(new Dimension(width * 3 / 4, 20));
    horizontalScroll.add(hBar);
    this.add(horizontalScroll, BorderLayout.PAGE_END);

    JPanel verticalScroll = new JPanel();
    vBar = new JScrollBar(JScrollBar.VERTICAL, cellsToBeShownY,
        SCROLLINCREMENT, cellsToBeShownY, VBARMAX);
    vBar.addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
        verticalScroll(e, vBar);
      }
    });
    vBar.setBackground(Color.BLACK);
    vBar.setPreferredSize(new Dimension(20, height));
    verticalScroll.add(vBar);
    this.add(verticalScroll, BorderLayout.LINE_END);
  }

  //helper to scroll vertical bar
  private void verticalScroll(AdjustmentEvent e, JScrollBar vBar) {
    if (vBar.getValueIsAdjusting()) {
      if (vBar.getValue() == vBar.getMaximum() - 1) {
        vBar.setMaximum(VBARMAX + 100);
        VBARMAX = VBARMAX + 100;
      }
      furthestY = e.getValue();
      cellPanel.removeAll();
      fillCells();
      displayCells();
      cellPanel.updateUI();
    }
  }

  //helper to scroll horizontal bar
  private void horizontalScroll(AdjustmentEvent e, JScrollBar hBar) {
    if (hBar.getValueIsAdjusting()) {
      if (hBar.getValue() == hBar.getMaximum() - 1) {
        hBar.setMaximum(HBARMAX + 100);
        HBARMAX = HBARMAX + 100;
      }
      furthestX = e.getValue();
      cellPanel.removeAll();
      fillCells();
      displayCells();
      cellPanel.updateUI();
    }
  }

  void repaintCell(Coord coord) {
    // safe cast as we know that Coord values cannot be < 1 and all JComponents
    // in visible cells with both coordinates >= 1 are JTextfields
    JTextField field = (JTextField) visibleCells.get(coord.row + cellsToBeShownY - furthestY).get(
        coord.col + cellsToBeShownX - furthestX);
    try {
      field.setText(model.getComputedValue(coord));
    } catch (IllegalArgumentException e) {
      field.setText("#Error");
    }
    field.updateUI();
  }

  private void changeFocus(Coord finalC) {
    if (finalC.row == furthestY + 1) {
      furthestY++;
      vBar.setValue(furthestY);
      cellPanel.removeAll();
      fillCells();
      displayCells();
      cellPanel.updateUI();
    }
    else if (finalC.row == furthestY - cellsToBeShownY) {
      furthestY--;
      vBar.setValue(furthestY);
      cellPanel.removeAll();
      fillCells();
      displayCells();
      cellPanel.updateUI();
    }

    if (finalC.col == furthestX + 1) {
      furthestX++;
      hBar.setValue(furthestX);
      cellPanel.removeAll();
      fillCells();
      displayCells();
      cellPanel.updateUI();
    }
    else if (finalC.col == furthestX - cellsToBeShownX) {
      furthestX--;
      hBar.setValue(furthestX);
      cellPanel.removeAll();
      fillCells();
      displayCells();
      cellPanel.updateUI();
    }

    if (focus != null &&
        focus.row <= furthestY && focus.row > furthestY - cellsToBeShownY &&
        focus.col <= furthestX && focus.col > furthestX - cellsToBeShownX) {
      JComponent c = visibleCells.get(focus.row + cellsToBeShownY - furthestY).get(
          focus.col + cellsToBeShownX - furthestX);
      c.setBackground(Color.WHITE);
      c.updateUI();
    }
    JComponent c = visibleCells.get(finalC.row  + cellsToBeShownY - furthestY).get(
        finalC.col + cellsToBeShownX - furthestX);
    c.setBackground(Color.LIGHT_GRAY);
    c.updateUI();
    view.setCurrentCell(finalC);
    focus = finalC;
  }

  private void addKeys() {
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        Coord c;

        try {
          switch (code) {
            case KeyEvent.VK_UP:
              c = new Coord(focus.col, focus.row - 1);
              break;
            case KeyEvent.VK_DOWN:
              c = new Coord(focus.col, focus.row + 1);
              break;
            case KeyEvent.VK_RIGHT:
              c = new Coord(focus.col + 1, focus.row);
              break;
            case KeyEvent.VK_LEFT:
              c = new Coord(focus.col - 1, focus.row);
              break;
            default:
              return;
          }
          changeFocus(c);
        }
        catch (IllegalArgumentException ignored) {
        }
      }
    });
  }
}
