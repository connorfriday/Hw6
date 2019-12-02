package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import java.util.HashMap;


/**
 * Represents a read-only version of a given model and is non-mutable.
 */
public class BasicWorksheetReadOnlyModel implements Worksheet {
  Worksheet mutableModel;

  /**
   * Constructs a {@code BasicWorksheetReadOnlyModel}, which is a non-mutable version of a model.
   *
   * @param model The mutable version of the model that becomes a read-only.
   */
  public BasicWorksheetReadOnlyModel(Worksheet model) {
    this.mutableModel = model;
  }

  @Override
  public void editCell(String input, Coord location) {
    throw new UnsupportedOperationException("we do not allow this model to be edited");
  }

  @Override
  public CellFormula getCellAt(Coord location) {
    return this.mutableModel.getCellAt(location);
  }

  @Override
  public HashMap<Coord, CellFormula> getCells() {
    return this.mutableModel.getCells();
  }

  @Override
  public int getNumRows() {
    return this.mutableModel.getNumRows();
  }

  @Override
  public int getNumCols() {
    return this.mutableModel.getNumCols();
  }

  @Override
  public void removeCell(Coord location) {
    throw new UnsupportedOperationException("we do not allow this model to be edited");
  }
}
