package edu.cs3500.spreadsheets.model;

import java.util.Map;
import java.util.Set;

/**
 * Read only format of a model - only allows reading a spreadsheet, no editing.
 */
public interface SpreadsheetReadOnlyModel {

  /**
   * This method gets the raw value of a cell as a string object.
   * @param coord a Coord object representing 1-indexed coordinates of the cell
   * @return the raw string value representing the contents of that object
   */
  String getRawValue(Coord coord);

  /**
   * This method gets the computed value of a cell as a string object.
   * @param coord a Coord object representing 1-indexed coordinates of the cell
   * @return the raw string value representing the contents of that object
   */
  String getComputedValue(Coord coord);

  /**
   * This method gets a map of which functions are supported within BasicSpreadsheetModel.
   * @return the map of names to supported functions
   */
  Map<String, SpreadsheetFunction> getFunctions();

  /**
   * Gets a list of coordinates of all non-empty cells.
   * @return a list containing coordinates of all non-empty cells
   */
  Set<Coord> getNonEmptyCoordinates();

  /**
   * Get all of the graphs in this model in name, graph form.
   * @return Map of name and graphs in model
   */
  Map<String, SpreadsheetGraph> getGraphs();

  /**
   * Get all of the graph types supported in the model.
   * @return a set of the supported graph types
   */
  Set<String> getGraphTypes();
}
