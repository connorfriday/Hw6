package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
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

  /**
   * Sets the current cell of the view to the given coord.
   * @param coord the coordinates of the current cell
   */
  void setCurrentCell(Coord coord);

  /**
   * Sets the features for the view.
   * @param features the features implementation
   */
  void setFeatures(Features features);

  /**
   * Displays a message to the user.
   * @param message the string contents to show
   */
  void displayMessage(String message);
}
