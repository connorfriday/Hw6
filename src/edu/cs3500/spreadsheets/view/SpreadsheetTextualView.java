package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetGraph;
import edu.cs3500.spreadsheets.model.SpreadsheetReadOnlyModel;
import java.io.IOException;
import java.util.Map;

/**
 * This is a textual view for a spreadsheet model. Represents non-empty cells in the format (cell
 * name) (contents) \n
 */
public class SpreadsheetTextualView implements SpreadsheetView {

  private SpreadsheetReadOnlyModel model;
  private Appendable out;

  /**
   * Constructs an instance of this textual view given a model and an output.
   *
   * @param model The spreadsheet we are trying to view
   * @param out   The place to output the representation of the model
   */
  public SpreadsheetTextualView(SpreadsheetReadOnlyModel model, Appendable out) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (out == null) {
      throw new IllegalArgumentException("Output cannot be null");
    }
    this.model = model;
    this.out = out;
  }

  /**
   * Outputs a textual representation of the model.
   *
   * @throws IOException if the view is unable to read in or output
   */
  @Override
  public void render() throws IOException {
    for (Coord c : model.getNonEmptyCoordinates()) {
      out.append(c.toString() + " " + model.getRawValue(c) + "\n");
    }
    out.append("/GRAPHS/\n");
    Map<String, SpreadsheetGraph> graphs = model.getGraphs();
    for (SpreadsheetGraph graph : graphs.values()) {
      out.append(graph.getType() + " " + graph.getName() + " " + graph.getRefs() + "\n");
    }
  }

  @Override
  public void setCurrentCell(Coord coord) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setFeatures(Features features) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void displayMessage(String message) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void refreshGraphs() {
    //this implementation of the SpreadsheetView interface does not support graphing
    // so this method does nothing as there's no graphs to refresh
  }
}
