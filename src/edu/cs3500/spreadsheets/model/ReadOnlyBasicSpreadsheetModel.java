package edu.cs3500.spreadsheets.model;

import java.util.Map;
import java.util.Set;

public class ReadOnlyBasicSpreadsheetModel implements SpreadsheetReadOnlyModel {
    private SpreadsheetModel model;

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
