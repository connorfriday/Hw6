package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import java.util.HashMap;

import edu.cs3500.spreadsheets.cell.CellFormula;

/**
 * Represents a worksheet with cells.
 */
public interface Worksheet {

  /**
   * Replaces a cell at a given location in {@Code Worksheet} with a given String.
   *
   * @param input    the String value to replace the cell.
   * @param location the location of the cell being edited.
   */
  void editCell(String input, Coord location);

  /**
   * Returns a cell at a given location in {@Code Worksheet}.
   *
   * @param location the coordinates of the cell being returned.
   * @return a cell at a specific location.
   */
  CellFormula getCellAt(Coord location);

  /**
   * Returns all the cells in this model.
   * @return the cells of the model.
   */
  HashMap<Coord, CellFormula> getCells();

  /**
   * Determines the row number of the farthest down cell with contents in this Worksheet.
   * @return the number of rows needed to display the full model.
   */
  int getNumRows();

  /**
   * Determines the column index of the farthest over cell with contents in this Worksheet.
   * @return the number of columns needed to display the full model.
   */
  int getNumCols();

  /**
   * Removes the cell from the given location.
   * @param location the coordinate location of the cell the user wants to remove
   */
  void removeCell(Coord location);

}
