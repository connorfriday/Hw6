package edu.cs3500.spreadsheets.provider;

import edu.cs3500.spreadsheets.provider.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.provider.view.SpreadsheetPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Represents the act of highlighting a cell in a worksheet.
 */
public class HighlightCell implements MouseListener {
  private SpreadsheetPanel spreadsheetPanel;
  private BasicWorksheetEditorView view;

  /**
   * Constructs a {@code HighlightCell} object, which takes in a model and a view and
   * determines/displays the highlighted cell.
   *
   * @param spreadsheetPanel The spreadsheet panel with all the cells, one of which is highlighted.
   * @param view             The given view which will display the highlighted cell.
   */
  public HighlightCell(SpreadsheetPanel spreadsheetPanel, BasicWorksheetEditorView view) {
    this.spreadsheetPanel = spreadsheetPanel;
    this.view = view;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // gets the position of the mouse click
    int xPos = e.getX();
    int yPos = e.getY();

    // determines the coordinate of the cell that the mouse was clicked in
    int xCoord = xPos / SpreadsheetPanel.CELL_WIDTH;
    int yCoord = yPos / SpreadsheetPanel.CELL_HEIGHT;

    // sets the highlighted location of the spreadsheet panel, so that the highlighted cell
    // will be clearly displayed; then repaints the view and sets the text field to be the
    // cell contents
    this.spreadsheetPanel.setHighlightLocation(xCoord, yCoord);
    this.spreadsheetPanel.revalidate();
    this.spreadsheetPanel.repaint();
    this.view.setTextbox();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // no action taken for a mouse press
  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
    // no action taken for a mouse release
  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {
    // no action taken for a mouse enter
  }

  @Override
  public void mouseExited(MouseEvent mouseEvent) {
    // no action taken for a mouse exit
  }

}
