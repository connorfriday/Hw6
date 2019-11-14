package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;;
import javax.swing.border.LineBorder;

/**
 * Represents a scrollable GUI to view a Spreadsheet
 */
public class SpreadsheetGUI extends JFrame implements SpreadsheetView {

  private SpreadsheetModel model;
  private static int CELLSTOBESHOWNX = 20;
  private static int CELLSTOBESHOWNY = 40;
  private static int furthestX = CELLSTOBESHOWNX;
  private static int furthestY = CELLSTOBESHOWNY;
  private List<List<JComponent>> visibleCells;
  private JPanel cellPanel;
  private static int HBARMAX = 500;
  private static int VBARMAX = 500;
  private static int SCROLLINCREMENT = 1;

  /**
   * Constructs a spreadsheet GUI.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetGUI(SpreadsheetModel model) {
    //construct model
    this.model = model;
    //set default size to 1000 x 1000
    setSize(2000, 1000);
    //set default location to 0 0
    setLocation(0, 0);
    //set close frame to Exit on Close
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //allow min size to be 500 x 500
    this.setMinimumSize(new Dimension(500, 500));
    //use a border layout for the overall frame
    this.setLayout(new BorderLayout());

    //set the cellPanel's layout to a GridBagLayout
    GridBagLayout layout = new GridBagLayout();
    cellPanel = new JPanel(layout);

    // add all of the components onto the JFrame
    fillCells();
    // add scroll buttons
    scrollButtons();
    // display the cells
    displayCells();
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
    //add the panel full of cells to this frame
    this.add(cellPanel);
  }


  //fill the cells with values
  private void fillCells() {
    visibleCells = new ArrayList<>();

    //add the first row, blank first square and A-Z for the rest
    List<JComponent> tempList = new ArrayList<>();
    tempList.add(new JLabel(""));
    for (int x = furthestX - CELLSTOBESHOWNX + 1; x <= furthestX; x++) {
      JLabel tempJLabel = new JLabel(Coord.colIndexToName(x), SwingConstants.CENTER);
      tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
      tempList.add(tempJLabel);
    }
    visibleCells.add(tempList);

    //add the rest of the rows, row number and then the cells
    for (int y = furthestY - CELLSTOBESHOWNY + 1; y <= furthestY; y++) {
      tempList = new ArrayList<>();

      for (int x = furthestX - this.CELLSTOBESHOWNX; x <= furthestX; x++) {
        if (x == furthestX - this.CELLSTOBESHOWNX) {
          JLabel tempJLabel = new JLabel(Integer.toString(y), SwingConstants.CENTER);
          tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
          tempList.add(tempJLabel);
        } else {
          JTextField tempTextField;
          try {
            tempTextField = new JTextField(model.getComputedValue(new Coord(x, y)));
          } catch (IllegalArgumentException e) {
            tempTextField = new JTextField("#ERROR");
          }
          tempTextField.setBorder(new LineBorder(Color.GRAY, 1));
          tempTextField.setEditable(false);
          tempTextField.setColumns(10);
          tempList.add(tempTextField);
        }
      }
      visibleCells.add(tempList);
    }
  }

  //add the scroll buttons to this frame
  private void scrollButtons() {

    JPanel horizontalScroll = new JPanel();
    JScrollBar hBar = new JScrollBar(JScrollBar.HORIZONTAL, CELLSTOBESHOWNX,
        SCROLLINCREMENT, CELLSTOBESHOWNX, HBARMAX);
    hBar.addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
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
    });
    hBar.setBackground(Color.BLACK);
    horizontalScroll.add(hBar);
    this.add(horizontalScroll, BorderLayout.PAGE_END);

    JPanel verticalScroll = new JPanel();
    JScrollBar vBar = new JScrollBar(JScrollBar.VERTICAL, CELLSTOBESHOWNY,
        SCROLLINCREMENT, CELLSTOBESHOWNY, VBARMAX);
    vBar.addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
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
    });
    vBar.setBackground(Color.BLACK);
    verticalScroll.add(vBar);
    this.add(verticalScroll, BorderLayout.LINE_END);
  }

  /**
   * Renders the GUI.
   * @throws IOException if an output issue occurs
   */
  @Override
  public void render() throws IOException {
    setVisible(true);
  }
}
