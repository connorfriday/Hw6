package edu.cs3500.spreadsheets.view;

import java.io.IOException;

/**
 * An interface providing a way to view a spreadsheet model.
 */
public interface SpreadsheetView {

  /**
   * Outputs the visual representation of the model.
   * @throws IOException if unable to read or write to inputs or outputs
   */
  void render() throws IOException;
}
