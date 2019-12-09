package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.EvaluateSexp;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.List;
import java.util.Map;

/**
 * A class representing a function for comparing(less than) numbers in a spreadsheet.
 */
public class LessThan implements SpreadsheetFunction {
  private final Map<String, SpreadsheetFunction> functions;
  private final SpreadsheetModel sheet;

  /**
   * Constructs an instance of a less than function.
   * @param functions the map of function objects from the spreadsheet
   * @param sheet the spreadsheet this object is working in
   * @throws IllegalArgumentException if any arguments are null
   */
  public LessThan(
      Map<String, SpreadsheetFunction> functions,
      SpreadsheetModel sheet) {
    if (functions == null) {
      throw new IllegalArgumentException("Function map cannot be null");
    }
    if (sheet == null) {
      throw new IllegalArgumentException("Spreadsheet cannot be null");
    }
    this.functions = functions;
    this.sheet = sheet;
  }

  @Override
  public String evaluate(List<Sexp> list, List<Coord> visited) {
    if (list.size() != 2) {
      throw new IllegalArgumentException("2 arguments expected for less than");
    }

    String firstS = list.get(0).accept(new EvaluateSexp(functions, sheet, this, visited));
    String secondS = list.get(1).accept(new EvaluateSexp(functions, sheet, this, visited));
    try {
      double first = Double.parseDouble(firstS);
      double second = Double.parseDouble(secondS);
      return Boolean.toString(first < second);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("< only accepts numerical arguments");
    }
  }

  @Override
  public String getName() {
    return "<";
  }

  @Override
  public boolean supportMultiArg() {
    return false;
  }
}
