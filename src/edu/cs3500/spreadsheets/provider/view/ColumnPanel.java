package edu.cs3500.spreadsheets.provider.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the column header panel in a worksheet.
 */
public class ColumnPanel extends javax.swing.JPanel {
  private int numCols;

  /**
   * Constructs a {@code ColumnPanel} object, which is the column header for the spreadsheet.
   *
   * @param numberOfColumns The total number of columns that are non-empty in this spreadsheet.
   */
  ColumnPanel(int numberOfColumns) {
    this.numCols = numberOfColumns;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int x = 0; x < this.numCols; x++) {
      // draws a number of rectangles equal to the specified number of column cells side by side
      // along the x-axis
      g2d.drawRect(x * SpreadsheetPanel.CELL_WIDTH, -1, SpreadsheetPanel.CELL_WIDTH,
              SpreadsheetPanel.CELL_HEIGHT);
      // sets the font size to be 14 and bolds the header text
      g2d.setFont(new Font("default", Font.BOLD, 14));
      // draws the text displayed in the column header (column letter) and aligns it based on
      // the width of the text
      if (x < 26) {
        g2d.drawString(Coord.colIndexToName(x + 1), x * 64 + 28,
                SpreadsheetPanel.CELL_HEIGHT / 2 + 6);
      } else if (x < 26 * 26) {
        g2d.drawString(Coord.colIndexToName(x + 1), x * 64 + 24,
                SpreadsheetPanel.CELL_HEIGHT / 2 + 6);
      } else if (x < 26 * 26 * 26) {
        g2d.drawString(Coord.colIndexToName(x + 1), x * 64 + 20,
                SpreadsheetPanel.CELL_HEIGHT / 2 + 6);
      }
    }
  }

  /**
   * Adds a column the spreadsheet and adjusts the size of the spreadsheet panel accordingly.
   */
  void addColAndChangePanelSize() {
    this.numCols += 1;
    this.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * this.numCols,
            SpreadsheetPanel.CELL_HEIGHT));
    this.revalidate();
  }
}
