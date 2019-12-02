package edu.cs3500.spreadsheets.provider.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.FontMetrics;


import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the grid of cells that make up a spreadsheet.
 */
public class SpreadsheetPanel extends javax.swing.JPanel {
  public static int CELL_WIDTH = 64;
  public static int CELL_HEIGHT = 21;
  private int numRows;
  private int numCols;
  private BasicWorksheetReadOnlyModel model;
  private int xMouseCellPos;
  private int yMouseCellPos;

  /**
   * Creates a {@code SpreadsheetPanel} object, which is the grid of cells in the view of the
   * spreadsheet.
   *
   * @param numCols the number of columns in the panel
   * @param numRows the number of rows in the panel
   * @param model   The given model which will be  displayed in this spreadsheet.
   */
  public SpreadsheetPanel(int numRows, int numCols, BasicWorksheetReadOnlyModel model) {
    this.numRows = numRows;
    this.numCols = numCols;
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    for (int x = 0; x < this.numCols; x++) {
      for (int y = 0; y < this.numRows; y++) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
        if (x == this.xMouseCellPos && y == this.yMouseCellPos) {
          g2d.setColor(Color.BLUE);
          g2d.drawRect(x * CELL_WIDTH + 1, y * CELL_HEIGHT + 1, CELL_WIDTH - 2, CELL_HEIGHT - 2);
          String contentsToDraw = getAndClipContents(g, x + 1, y + 1);
          g2d.setColor(Color.BLACK);
          g2d.drawString(contentsToDraw, x * CELL_WIDTH + 3, (y + 1) * CELL_HEIGHT - 3);
        } else {
          String contentsToDraw = getAndClipContents(g, x + 1, y + 1);
          g2d.drawString(contentsToDraw, x * CELL_WIDTH + 3, (y + 1) * CELL_HEIGHT - 3);
        }
      }
    }
  }

  /**
   * Adds a row to the spreadsheet and adjusts the size of the panel accordingly.
   */
  void addRowAndChangeSize() {
    this.numRows += 1;
    this.setPreferredSize(new Dimension(CELL_WIDTH * numCols, CELL_HEIGHT * numRows));
    this.revalidate();
  }

  /**
   * Adds a column to the spreadsheet and adjusts the size of the panel accordingly.
   */
  void addColAndChangeSize() {
    this.numCols += 1;
    this.setPreferredSize(new Dimension(CELL_WIDTH * numCols, CELL_HEIGHT * numRows));
    this.revalidate();
  }

  /**
   * Clips the contents of a cell if the contents are longer than the width of the cell.
   *
   * @param g    The contents of the cell that need to be potentially clipped.
   * @param xPos The x coordinate of the cell.
   * @param yPos The y coordinate of the cell.
   * @return A string of the new clipped contents of the cell.
   */
  private String getAndClipContents(Graphics g, int xPos, int yPos) {
    String cellValueToDisplay;

    try {
      cellValueToDisplay = String.valueOf(this.model.getCellAt(
              new Coord(xPos, yPos)).evaluateCell());
    } catch (IllegalArgumentException e) {
      cellValueToDisplay = "REF!";
    }

    // get metrics from the graphics
    FontMetrics metrics = g.getFontMetrics();

    while (metrics.stringWidth(cellValueToDisplay) >= CELL_WIDTH) {
      cellValueToDisplay = cellValueToDisplay.substring(0, cellValueToDisplay.length() - 2);
    }
    return cellValueToDisplay;
  }

  /**
   * Sets the highlight location of the cell in this panel, based on row and column input.
   *
   * @param xPos the column of the cell being highlighted
   * @param yPos the row of the cell being highlighted
   */
  public void setHighlightLocation(int xPos, int yPos) {
    this.xMouseCellPos = xPos;
    this.yMouseCellPos = yPos;
  }

  /**
   * Returns the location of the cell that is being drawn with an extra box around it, to signify a
   * "highlight".
   *
   * @return the Coord location of the cell being highlighted in this panel
   */
  Coord highlightCellLocation() {
    if (this.xMouseCellPos == -1 || this.yMouseCellPos == -1) {
      throw new IllegalArgumentException();
    } else {
      return new Coord(this.xMouseCellPos + 1, this.yMouseCellPos + 1);
    }
  }
}
