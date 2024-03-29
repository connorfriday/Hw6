package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * An interface for managing the interactions between a user and a spreadsheet model.
 */
public interface SpreadsheetController {

  /**
   * Starts the stream of interactions between the user and the model.
   * @param model The object representing the actual spreadsheet contents
   */
  void start(SpreadsheetModel model);
}
