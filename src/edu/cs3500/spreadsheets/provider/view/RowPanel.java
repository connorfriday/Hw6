package edu.cs3500.spreadsheets.provider.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

/**
 * Represents the row header panel in a worksheet.
 */
public class RowPanel extends javax.swing.JPanel {
  private int numRows;

  /**
   * Constructs a {@code RowPanel} object, which is the row header for the spreadsheet.
   *
   * @param numberOfRows The total number of rows that are non-empty in this spreadsheet.
   */
  RowPanel(int numberOfRows) {
    this.numRows = numberOfRows;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int y = 0; y < this.numRows; y++) {
      // draws a number of rectangles equal to the specified number of column cells one on top of
      // the other along the y-axis
      g2d.drawRect(-1, (y) * SpreadsheetPanel.CELL_HEIGHT, SpreadsheetPanel.CELL_WIDTH,
              SpreadsheetPanel.CELL_HEIGHT);
      // sets the font size to be 14 and bolds the text
      g2d.setFont(new Font("default", Font.BOLD, 14));
      // draws the text displayed in the row header (row number) and aligns it based on
      // the width of the text (size of the number)
      if (y < 9) {
        g2d.drawString(String.valueOf(y + 1), SpreadsheetPanel.CELL_WIDTH / 2 - 4,
                y * 21 + 16);
      } else if (y < 99) {
        g2d.drawString(String.valueOf(y + 1), SpreadsheetPanel.CELL_WIDTH / 2 - 8,
                y * 21 + 16);
      } else if (y < 999) {
        g2d.drawString(String.valueOf(y + 1), SpreadsheetPanel.CELL_WIDTH / 2 - 12,
                y * 21 + 16);
      } else {
        g2d.drawString(String.valueOf(y + 1), SpreadsheetPanel.CELL_WIDTH / 2 - 16,
                y * 21 + 16);
      }
    }
  }

  /**
   * Adds a row to the spreadsheet and adjusts the size of the spreadsheet panel accordingly.
   */
  void addRowAndChangePanelSize() {
    this.numRows += 1;
    this.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            this.numRows * SpreadsheetPanel.CELL_HEIGHT));
    this.revalidate();
  }


}
