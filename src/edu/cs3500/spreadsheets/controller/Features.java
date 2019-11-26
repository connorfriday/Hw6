package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * Features that a spreadsheet can use.
 */
public interface Features {

  /**
   * Updates a cell value.
   * @param coord the coordinate of the cell to be updated
   * @param contents the contents with which the cell will be updated
   */
  void updateCellValue(Coord coord, String contents);

  /**
   * Loads a file into a GUI.
   * @param sourceFile the file which will be loaded
   */
  void loadFile(String sourceFile);

  /**
   * Saves the current GUI into a given destination.
   * @param destinationFile the file which GUI will be saved into.
   */
  void saveFile(String destinationFile);

}
