package edu.cs3500.spreadsheets.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A mock of Spreadsheet Model to ensure controller is calling the expected methods on the model.
 */
public class MockSpreadsheetModel implements SpreadsheetModel {
  private SpreadsheetModel model;
  private StringBuilder log;

  /**
   * Constructs an instance of the mock.
   * @param model The delegate.
   * @param log A log detailing when methods that mutate the model are called.
   */
  public MockSpreadsheetModel(SpreadsheetModel model, StringBuilder log) {
    if (model == null || log == null) {
      throw new IllegalArgumentException("No null arguments");
    }
    this.model = model;
    this.log = log;
  }


  @Override
  public void clearCell(Coord coord) {
    log.append("Called clear cell at " + coord + "\n");
    model.clearCell(coord);
  }

  @Override
  public void setCell(String s, Coord coord) {
    log.append("Called set cell at " + coord + " with contents " + s + "\n");
    model.setCell(s, coord);
  }

  @Override
  public String getRawValue(Coord coord) {
    return model.getRawValue(coord);
  }

  @Override
  public String getComputedValue(Coord coord) {
    return model.getComputedValue(coord);
  }

  @Override
  public String getComputedValue(List<Coord> visited, Coord coord) {
    return model.getComputedValue(visited, coord);
  }

  @Override
  public Map<String, SpreadsheetFunction> getFunctions() {
    return model.getFunctions();
  }

  @Override
  public Set<Coord> getNonEmptyCoordinates() {
    return model.getNonEmptyCoordinates();
  }
}
