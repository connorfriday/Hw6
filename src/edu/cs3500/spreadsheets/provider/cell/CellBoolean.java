package edu.cs3500.spreadsheets.provider.cell;

import edu.cs3500.spreadsheets.provider.function.CellVisitor;

/**
 * A class that represents a cell in a spreadsheet that contains a boolean.
 */
public class CellBoolean implements CellFormula {
  private boolean value;

  /**
   * Constructs a {@code CellBoolean} object. A constructor for this CellBoolean that takes in the
   * value as a boolean.
   *
   * @param value the boolean value in the cell.
   */
  public CellBoolean(Boolean value) {
    this.value = value;
  }

  @Override
  public Boolean evaluateCell() {
    return this.value;
  }

  @Override
  public String getRawContents() {
    return Boolean.toString(this.value);
  }

  @Override
  public Object accept(CellVisitor visit) {
    return visit.visitBoolean(this);
  }
}
