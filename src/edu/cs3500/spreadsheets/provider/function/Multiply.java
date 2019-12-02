package edu.cs3500.spreadsheets.provider.function;

import java.util.Map;

import edu.cs3500.spreadsheets.provider.cell.CellBlank;
import edu.cs3500.spreadsheets.provider.cell.CellBoolean;
import edu.cs3500.spreadsheets.provider.cell.CellDouble;
import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.cell.CellFunction;
import edu.cs3500.spreadsheets.provider.cell.CellReference;
import edu.cs3500.spreadsheets.provider.cell.CellString;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * A function object for the Product function of a spreadsheet, which multiplies an arbitrary number
 * of values together.
 */
public class Multiply implements CellVisitor<CellFormula, Double> {

  // evaluates the given CellDouble for use in the product function
  @Override
  public Double visitDouble(CellDouble d) {
    return d.evaluateCell();
  }

  // since a boolean is a non-numeric value, the Product function ignores it by treating it as an
  //  // unlikely user input such that it can be identified as a non-numeric cell
  @Override
  public Double visitBoolean(CellBoolean b) {
    return 0.0;
  }

  // evaluates the function and applies the multiply function object to it to handle what
  // return type it is
  @Override
  public Double visitFunction(CellFunction f) {
    return this.apply(f.cellFunctionEvaluated);
  }

  // applies the visitor to each cell in the reference for use by the Product function
  @Override
  public Double visitReference(CellReference r) {
    Double result = 1.0;
    // for each cell in r.cells
    for (Map.Entry<Coord, CellFormula> cell : r.referencedCells.entrySet()) {
      // if it is a double, multiply it by the result
      result *= this.apply(cell.getValue());
    }
    return result;
  }

  // since a string is a non-numeric value, the Product function ignores it by treating it as an
  // unlikely user input such that it can be identified as a non-numeric cell
  @Override
  public Double visitString(CellString s) {
    return 0.0;
  }

  // ignores the blank value by multiplying it by 1.0
  @Override
  public Double visitBlank(CellBlank b) {
    return 1.0;
  }

  @Override
  public Double apply(CellFormula formula) {
    return (Double) formula.accept(this);
  }
}
