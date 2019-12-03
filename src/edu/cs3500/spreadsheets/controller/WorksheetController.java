package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.Worksheet;

public interface WorksheetController {

  /**
   * Starts the stream of interactions between the user and the model.
   * @param model The object representing the actual spreadsheet contents
   */
  void start(Worksheet model);
}
