package edu.cs3500.spreadsheets.view;


import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Represents a scrollable GUI to view a Spreadsheet.
 */
public class SpreadsheetGUI extends JFrame implements SpreadsheetView {
  protected SpreadsheetModel model;
  static int CELLSTOBESHOWNX = 15;
  static int CELLSTOBESHOWNY = 30;
  static int WIDTH = 1500;
  static int HEIGHT = 1000;
  CellPanel cellPanel;


  /**
   * Constructs a spreadsheet GUI.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetGUI(SpreadsheetModel model) {
    this.model = model;
    //set default size to 1000 x 1000
    setSize(WIDTH, HEIGHT);
    //set default location to 0 0
    setLocation(0, 0);
    //set close frame to Exit on Close
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //allow min size to be 500 x 500
    this.setMinimumSize(new Dimension(WIDTH * 3 / 4, HEIGHT * 3 / 4));
    //set the spreadsheet view
    cellPanel = new CellPanel(model, this, CELLSTOBESHOWNX, CELLSTOBESHOWNY, WIDTH, HEIGHT);
    //add the spreadsheet view
    this.add(cellPanel);
  }

  /**
   * Renders the GUI.
   */
  @Override
  public void render() {
    setVisible(true);
    cellPanel.grabFocus();
  }

  @Override
  public void setCurrentCell(Coord coord) {
    //doesn't need to track current cell but allows internal cell panel to do so
  }

  @Override
  public void setFeatures(Features features) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void displayMessage(String message) {
    throw new UnsupportedOperationException();
  }
}
