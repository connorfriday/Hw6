package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import java.awt.event.ActionListener;

/**
 * An interface for managing the interactions between a user and a spreadsheet model.
 */
public interface SpreadsheetController extends Features{

  /**
   * Starts the stream of interactions between the user and the model.
   * @param model The object representing the actual spreadsheet contents
   */
  void start(SpreadsheetModel model);
}
