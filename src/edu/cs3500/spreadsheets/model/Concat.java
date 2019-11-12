package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.EvaluateSexp;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.List;
import java.util.Map;

/**
 * A class representing a function for concatenating strings in a spreadsheet.
 */
public class Concat implements SpreadsheetFunction {

  private final Map<String, SpreadsheetFunction> functions;
  private final SpreadsheetModel sheet;

  /**
   * Constructs an instance of a concatenation function.
   *
   * @param functions the map of function objects from the spreadsheet
   * @param sheet     the spreadsheet this object is working in
   * @throws IllegalArgumentException if any arguments are null
   */
  public Concat(
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
  public String evaluate(List<Sexp> list) {
    StringBuilder result = new StringBuilder();
    for (Sexp s : list) {
      String val = s.accept(new EvaluateSexp(functions, sheet, this));
      result.append(val);
    }
    return result.toString();
  }

  @Override
  public String getName() {
    return "CONCAT";
  }

  @Override
  public boolean supportMultiArg() {
    return true;
  }
}
