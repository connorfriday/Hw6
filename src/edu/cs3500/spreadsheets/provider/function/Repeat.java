package edu.cs3500.spreadsheets.provider.function;

import edu.cs3500.spreadsheets.provider.cell.CellBlank;
import edu.cs3500.spreadsheets.provider.cell.CellBoolean;
import edu.cs3500.spreadsheets.provider.cell.CellDouble;
import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.cell.CellFunction;
import edu.cs3500.spreadsheets.provider.cell.CellReference;
import edu.cs3500.spreadsheets.provider.cell.CellString;

/**
 * A function object for the Repeat function of a spreadsheet, which repeats a value a set number of
 * times.
 */
public class Repeat implements CellVisitor<CellFormula, String> {

  // gets the raw contents (a.k.a the string representation of the double) because our
  // functionality of repeat allows doubles to be repeated (just like in Excel)
  @Override
  public String visitDouble(CellDouble d) {
    return d.getRawContents();
  }

  // gets the raw contents (a.k.a the string representation of the boolean) because our
  // functionality of repeat allows booleans to be repeated (just like in Excel)
  @Override
  public String visitBoolean(CellBoolean b) {
    return b.getRawContents();
  }

  // evaluates the function and applies the repeat function object to it to handle what
  // return type it is
  @Override
  public String visitFunction(CellFunction f) {
    return this.apply(f.cellFunctionEvaluated);
  }

  // evaluate the reference, convert it to a string, and this will be the value that is repeated
  @Override
  public String visitReference(CellReference r) {
    return r.evaluateCell().toString();
  }

  // gets the raw contents (a.k.a the string representation of the double) because our
  // functionality of repeat allows strings to be repeated (just like in Excel)
  @Override
  public String visitString(CellString s) {
    return s.evaluateCell();
  }

  // ignores the blank cell by returning an empty string
  @Override
  public String visitBlank(CellBlank b) {
    return "";
  }

  @Override
  public String apply(CellFormula formula) {
    return (String) formula.accept(this);
  }

}
