package edu.cs3500.spreadsheets.provider.cell;

import edu.cs3500.spreadsheets.provider.function.CellVisitor;

/**
 * Represents a blank cell in a spreadsheet. No contents, displayed as an empty string.
 */
public class CellBlank implements CellFormula {
  private String value;

  public CellBlank(String displayValue) {
    this.value = displayValue;
  }

  @Override
  public String evaluateCell() {
    return this.value;
  }

  @Override
  public String getRawContents() {
    return "";
  }

  @Override
  public Object accept(CellVisitor visit) {
    return visit.visitBlank(this);
  }

}