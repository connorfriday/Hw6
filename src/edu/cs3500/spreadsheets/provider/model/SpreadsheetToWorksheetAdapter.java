package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.util.HashMap;
import java.util.Set;

public class SpreadsheetToWorksheetAdapter implements Worksheet {
  private final SpreadsheetModel model;

  public SpreadsheetToWorksheetAdapter(SpreadsheetModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Can't be null.");
    }
    this.model = model;
  }

  @Override
  public void editCell(String input, Coord location) {
    model.setCell("", location);
  }

  @Override
  public CellFormula getCellAt(Coord location) {
    String contents = model.getRawValue(location);
    // convert to Cell Formula
  }

  @Override
  public HashMap<Coord, CellFormula> getCells() {
    // this method is never used by the views given so we chose not to implement it
    throw new UnsupportedOperationException("Not supported.");
  }

  @Override
  public int getNumRows() {
    Set<Coord> set = model.getNonEmptyCoordinates();
    int max = 1;
    for (Coord c : set) {
      if (c.row > max) {
        max = c.row;
      }
    }
    return max;
  }

  @Override
  public int getNumCols() {
    Set<Coord> set = model.getNonEmptyCoordinates();
    int max = 1;
    for (Coord c : set) {
      if (c.col > max) {
        max = c.col;
      }
    }
    return max;
  }

  @Override
  public void removeCell(Coord location) {
    model.clearCell(location);
  }
}
