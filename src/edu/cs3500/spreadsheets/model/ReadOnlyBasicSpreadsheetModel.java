package edu.cs3500.spreadsheets.model;

import java.util.Map;
import java.util.Set;

public class ReadOnlyBasicSpreadsheetModel extends BasicSpreadsheetModel
    implements SpreadsheetModel{

  @Override
  public void clearCell(Coord coord) {
    throw new UnsupportedOperationException("Read-Only!");
  }

  @Override
  public void setCell(String s, Coord coord) {
    throw new UnsupportedOperationException("Read-Only!");
  }

}
