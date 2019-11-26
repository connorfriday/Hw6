package edu.cs3500.spreadsheets.model;

import java.util.Map;
import java.util.Set;

/**
 * Read-only version of a BasicSpreadsheetModel - cannot edit the model but can read from model.
 */
public class ReadOnlyBasicSpreadsheetModel implements SpreadsheetReadOnlyModel {

  private SpreadsheetModel model;

  /**
   * Constructs an instance of a read-only basic spreadsheet model.
   * @param model the model to transform into read-only
   */
  public ReadOnlyBasicSpreadsheetModel(SpreadsheetModel model) {
    this.model = model;
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
  public Map<String, SpreadsheetFunction> getFunctions() {
    return model.getFunctions();
  }

  @Override
  public Set<Coord> getNonEmptyCoordinates() {
    return model.getNonEmptyCoordinates();
  }
}
