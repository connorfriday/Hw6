package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class CellPanel extends JPanel {

  CellPanel() {
    super();
    GridBagLayout layout = new GridBagLayout();
    JPanel grid = new JPanel(layout);
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
        horizontalScroll(e, hBar);
      }
    });
    hBar.setBackground(Color.BLACK);
    hBar.setPreferredSize(new Dimension(WIDTH * 3 / 4, 20));
    horizontalScroll.add(hBar);
    this.add(horizontalScroll, BorderLayout.PAGE_END);

    JPanel verticalScroll = new JPanel();
    JScrollBar vBar = new JScrollBar(JScrollBar.VERTICAL, CELLSTOBESHOWNY,
        SCROLLINCREMENT, CELLSTOBESHOWNY, VBARMAX);
    vBar.addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
        verticalScroll(e, vBar);
      }
    });
    vBar.setBackground(Color.BLACK);
    vBar.setPreferredSize(new Dimension(20, HEIGHT));
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

}
