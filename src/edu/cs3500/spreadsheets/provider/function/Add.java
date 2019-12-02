package edu.cs3500.spreadsheets.provider.function;

import edu.cs3500.spreadsheets.provider.cell.CellBlank;
import edu.cs3500.spreadsheets.provider.cell.CellBoolean;
import edu.cs3500.spreadsheets.provider.cell.CellDouble;
import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.cell.CellFunction;
import edu.cs3500.spreadsheets.provider.cell.CellReference;
import edu.cs3500.spreadsheets.provider.cell.CellString;
import java.util.Map;


import edu.cs3500.spreadsheets.model.Coord;

/**
 * A function object for the Sum function of a spreadsheet, which adds an arbitrary number of values
 * together.
 */
public class Add implements CellVisitor<CellFormula, Double> {

  // evaluates the given CellDouble so that it can be used in the add function
  @Override
  public Double visitDouble(CellDouble d) {
    return d.evaluateCell();
  }

  // since a boolean is a non-numeric value, the add function ignores it by treating it as 0
  @Override
  public Double visitBoolean(CellBoolean b) {
    return 0.0;
  }

  // evaluates the given CellFunction and applies the add function object to it to handle what
  // return type it is
  @Override
  public Double visitFunction(CellFunction f) {
    return this.apply(f.cellFunctionEvaluated);
  }

  // evaluates the given CellReference and applies the add function object to each cell in the
  // reference.
  @Override
  public Double visitReference(CellReference r) {
    Double result = 0.0;
    // for each cell in r.cells
    for (Map.Entry<Coord, CellFormula> cell : r.referencedCells.entrySet()) {
      // if it is a double, add it to the result
      result += this.apply(cell.getValue());
    }
    return result;
  }

  // since a string is a non-numeric value, the add function ignores it by treating it as 0
  @Override
  public Double visitString(CellString s) {
    return 0.0;
  }

  @Override
  public Double visitBlank(CellBlank b) {
    return 0.0;
  }

  @Override
  public Double apply(CellFormula formula) {
    return (Double) formula.accept(this);
  }
}
