package edu.cs3500.spreadsheets.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A model interface for the representation of a spreadsheet.
 */
public interface SpreadsheetModel {

  /**
   * This method clears the contents in a cell, making it empty.
   * @param coord a Coord object representing 1-indexed coordinates of the cell
   */
  void clearCell(Coord coord);

  /**
   * This method sets the contents of a cell to a given value.
   * @param s the string object representing the desired contents of the cell
   * @param coord a Coord object representing 1-indexed coordinates of the cell
   */
  void setCell(String s, Coord coord);

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
   * This method gets the computed value of a cell as a string object.
   * @param coord a Coord object representing 1-indexed coordinates of the cell
   * @param visited any coordinates that have already been visited in the current eval job
   * @return the raw string value representing the contents of that object
   */
  String getComputedValue(List<Coord> visited, Coord coord);

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
}
