package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.io.IOException;

/**
 * This is a textual view for a spreadsheet model.
 * Represents non-empty cells in the format (cell name) (contents) \n
 */
//todo test whole class
public class SpreadsheetTextualView implements SpreadsheetView {
  SpreadsheetModel model;
  Appendable out;

  /**
   * Constructs an instance of this textual view given a model and an output
   * @param model The spreadsheet we are trying to view
   * @param out The place to output the representation of the model
   */
  public SpreadsheetTextualView(SpreadsheetModel model, Appendable out) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (out == null) {
      throw new IllegalArgumentException("Output cannot be null");
    }
    this.model = model;
    this.out = out;
  }

  @Override
  public void render() throws IOException {
    for (Coord c: model.getNonEmptyCoordinates()) {
      out.append(c.toString() + " " + model.getRawValue(c) + "\n");
    }
  }
}
