package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.model.BasicWorksheetReadOnlyModel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the GUI view of a basic spreadsheet, so the user can view files and inputs on a
 * spreadsheet.
 */
public class BasicWorksheetGraphicalView extends JFrame implements BasicWorksheetView {
  SpreadsheetPanel spreadsheetPanel;
  private RowPanel rowPanel;
  private ColumnPanel columnPanel;

  // sets the total number of cells to be 100
  private int numRows = 100;
  private int numCols = 50;

  /**
   * A constructor for the GUI view of a spreadsheet that creates a new blank spreadsheet.
   */
  public BasicWorksheetGraphicalView() {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet"); // sets window title
    this.setSize(1350, 750); // sets window size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets window close button action

    // draws the row panel with the index of each row displayed in a grey-ed out cell
    this.rowPanel = new RowPanel(numRows);
    this.rowPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            numRows * SpreadsheetPanel.CELL_WIDTH));
    this.rowPanel.setBackground(Color.GRAY);

    // draws the column panel with the index of each row displayed as a letter or series of
    // letters in a grey-ed out cell
    this.columnPanel = new ColumnPanel(numCols);
    this.columnPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * numCols,
            SpreadsheetPanel.CELL_HEIGHT));
    this.columnPanel.setBackground(Color.GRAY);

    // draws all of the cells along with their components
    this.spreadsheetPanel = new SpreadsheetPanel(numRows, numCols,
            new BasicWorksheetReadOnlyModel(new BasicWorksheetModel()));
    this.spreadsheetPanel.setPreferredSize(new Dimension(
            numCols * SpreadsheetPanel.CELL_WIDTH,
            numRows * SpreadsheetPanel.CELL_HEIGHT));

    this.addScrollPane();
  }

  /**
   * A constructor for the GUI view of a spreadsheet which reads an existing model and displays that
   * in the view.
   *
   * @param model The given spreadsheet model/file to be displayed
   */
  public BasicWorksheetGraphicalView(BasicWorksheetReadOnlyModel model) {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet"); // sets window title
    this.setSize(1350, 750); // sets window size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets window close button action

    // establishes the number of rows and columns of cells to draw, based on the model requirements
    int numRowsToDraw;
    int numColsToDraw;
    // the default number of rows to draw is 100, unless the model requires more
    if (model.getNumRows() > numRows) {
      numRowsToDraw = model.getNumRows() + 1;
    } else {
      numRowsToDraw = numRows;
    }
    // the default number of columns to draw is 50, unless the model requires more
    if (model.getNumCols() > numCols) {
      numColsToDraw = model.getNumCols() + 1;
    } else {
      numColsToDraw = numCols;
    }

    // draws the row panel with the index of each row displayed in a grey-ed out cell
    this.rowPanel = new RowPanel(numRowsToDraw);
    this.rowPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            numRows * SpreadsheetPanel.CELL_WIDTH));
    this.rowPanel.setBackground(Color.GRAY);

    // draws the column panel with the index of each row displayed as a letter or series of
    // letters in a grey-ed out cell
    this.columnPanel = new ColumnPanel(numColsToDraw);
    this.columnPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * numColsToDraw,
            SpreadsheetPanel.CELL_HEIGHT));
    this.columnPanel.setBackground(Color.GRAY);

    // draws all of the cells along with their components
    this.spreadsheetPanel = new SpreadsheetPanel(numRowsToDraw, numColsToDraw, model);
    this.spreadsheetPanel.setPreferredSize(new Dimension(
            numColsToDraw * SpreadsheetPanel.CELL_WIDTH,
            numRowsToDraw * SpreadsheetPanel.CELL_HEIGHT));

    this.addScrollPane();

  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.revalidate();
    this.repaint();
  }

  /**
   * Gets the location of the cell that is highlighted in this view.
   *
   * @return the location (Coord) of the highlighted cell
   */
  Coord getHighlightedCell() {
    return this.spreadsheetPanel.highlightCellLocation();
  }

  /**
   * Moves the location of the highlighted cell by given row and column factors.
   *
   * @param columnFactor how many cells to move left or right (back or through columns); negative
   *                     means move left, positive means move right
   * @param rowFactor    how many cells up or down to move (up or down rows); negative means move
   *                     up, positive means move down
   */
  void moveHighlightedCell(int columnFactor, int rowFactor) {
    Coord currentLocation = this.spreadsheetPanel.highlightCellLocation();
    int xLoc = currentLocation.col;
    int yLoc = currentLocation.row;
    if (xLoc == 0 || yLoc == 0) {
      throw new IllegalArgumentException("At the edge of the speadsheet");
    } else {
      this.spreadsheetPanel.setHighlightLocation(xLoc + columnFactor, yLoc + rowFactor);
    }
  }


  /**
   * A method that adds a horizontal and vertical scroll pane to our worksheet view. Allows a user
   * to scroll through the entire spreadsheet of cells.
   */
  private void addScrollPane() {
    final JScrollPane scroller = new JScrollPane();
    // sets up the scroller panels
    scroller.setViewportView(this.spreadsheetPanel);
    scroller.setRowHeaderView(this.rowPanel);
    scroller.setColumnHeaderView(this.columnPanel);
    // increases the speed
    scroller.getVerticalScrollBar().setUnitIncrement(16);
    scroller.getHorizontalScrollBar().setUnitIncrement(16);
    // adds a listener for infinite scrolling
    scroller.getVerticalScrollBar().addAdjustmentListener(ae -> {
      // checks if the scroll bars has been moved
      inifinteScroll(ae, true);
    });
    scroller.getHorizontalScrollBar().addAdjustmentListener(ae -> {
      // checks if the scroll bars has been moved
      inifinteScroll(ae, false);
    });
    this.add(scroller, BorderLayout.CENTER);
  }

  /**
   * Uses the position of the scroll bars within the frame to determine when to add more rows and or
   * columns, thus handling infinite scrolling functionality.
   *
   * @param ae     an event that takes place when either scrollbar is moved.
   * @param isVert signifies if the vertical scrollbar was moved (true if vertical, false if
   *               horizontal)
   */
  private void inifinteScroll(AdjustmentEvent ae, boolean isVert) {
    if (!ae.getValueIsAdjusting()) {
      JScrollBar scrollBar = (JScrollBar) ae.getAdjustable();
      int extent = scrollBar.getModel().getExtent();
      int max = scrollBar.getModel().getMaximum();
      int value = ae.getValue();
      if (extent + value == max) {
        if (isVert) {
          this.spreadsheetPanel.addRowAndChangeSize();
          this.rowPanel.addRowAndChangePanelSize();
        } else {
          this.spreadsheetPanel.addColAndChangeSize();
          this.columnPanel.addColAndChangePanelSize();
        }
      }
    }
  }
}
