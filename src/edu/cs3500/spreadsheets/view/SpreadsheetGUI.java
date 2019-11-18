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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * Represents a scrollable GUI to view a Spreadsheet.
 */
public class SpreadsheetGUI extends JFrame implements SpreadsheetView {

  private SpreadsheetModel model;
  private static int CELLSTOBESHOWNX = 15;
  private static int CELLSTOBESHOWNY = 30;
  private static int furthestX = CELLSTOBESHOWNX;
  private static int furthestY = CELLSTOBESHOWNY;
  private List<List<JComponent>> visibleCells;
  private JPanel cellPanel;
  private static int HBARMAX = 500;
  private static int VBARMAX = 500;
  private static int SCROLLINCREMENT = 1;
  private static int WIDTH = 1500;
  private static int HEIGHT = 1000;

  /**
   * Constructs a spreadsheet GUI.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetGUI(SpreadsheetModel model) {
    //construct model
    this.model = model;
    //set default size to 1000 x 1000
    setSize(WIDTH, HEIGHT);
    //set default location to 0 0
    setLocation(0, 0);
    //set close frame to Exit on Close
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //allow min size to be 500 x 500
    this.setMinimumSize(new Dimension(WIDTH * 3 / 4, HEIGHT * 3 / 4));
    //use a border layout for the overall frame
    this.setLayout(new BorderLayout());

    cellPanel = new CellPanel(model);
    this.add(cellPanel);
  }

  /**
   * Renders the GUI.
   */
  @Override
  public void render() {
    cellPanel.setVisible(true);
    setVisible(true);
  }
}
