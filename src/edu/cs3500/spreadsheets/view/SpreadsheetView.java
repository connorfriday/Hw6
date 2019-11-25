package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import java.awt.event.ActionListener;
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
   * Gets a user input from the view, if it exists.
   * @return the user's input as a String or null if it doesn't exist
   */
  String getInputString();

  /**
   * Gets the current cell that the user is viewing/editing
   * @return the Coord representing the current cell or null if there is no current cell
   */
  Coord getCurrentCell();

  /**
   * Sets the current cell of the view to the given coord.
   * @param coord the coordinates of the current cell
   */
  void setCurrentCell(Coord coord);

  /**
   * Sets the object that will handle any events that mutate the model
   * @param a the action listener that handles the events
   */
  void setActionListener(ActionListener a);
}
