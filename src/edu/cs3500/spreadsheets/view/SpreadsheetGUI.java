package edu.cs3500.spreadsheets.view;


import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents a scrollable GUI to view a Spreadsheet.
 */
public class SpreadsheetGUI extends JFrame implements SpreadsheetView {

  private int CELLSTOBESHOWNX = 15;
  private int CELLSTOBESHOWNY = 30;
  private static int WIDTH = 1500;
  private static int HEIGHT = 1000;

  /**
   * Constructs a spreadsheet GUI.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetGUI(SpreadsheetModel model) {

    //set default size to 1000 x 1000
    setSize(WIDTH, HEIGHT);
    //set default location to 0 0
    setLocation(0, 0);
    //set close frame to Exit on Close
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //allow min size to be 500 x 500
    this.setMinimumSize(new Dimension(WIDTH * 3 / 4, HEIGHT * 3 / 4));
    //set the spreadsheet view
    JPanel cellPanel = new CellPanel(model, CELLSTOBESHOWNX, CELLSTOBESHOWNY, WIDTH, HEIGHT);
    //add the spreadsheet view
    this.add(cellPanel);

  }

  /**
   * Renders the GUI.
   */
  @Override
  public void render() {
    setVisible(true);
  }
}
