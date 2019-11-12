package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.EvaluateSexp;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.List;
import java.util.Map;

/**
 * A class representing a function for multiplying numbers in a spreadsheet.
 */
public class Product implements SpreadsheetFunction {
  private final Map<String, SpreadsheetFunction> functions;
  private final SpreadsheetModel sheet;

  /**
   * Constructs an instance of a product function.
   * @param functions the map of function objects from the spreadsheet
   * @param sheet the spreadsheet this object is working in
   * @throws IllegalArgumentException if any arguments are null
   */
  public Product(
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
    double result = 0;
    boolean isFirst = true;
    for (Sexp s : list) {
      String val = s.accept(new EvaluateSexp(functions, sheet, this));
      try {
        if (!val.equals("")) {
          if (isFirst) {
            result = 1;
            isFirst = false;
          }
          double num = Double.parseDouble(val);
          result = result * num;
        }
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid arguments for given function.");
      }
    }
    return String.format("%f", result);
  }

  @Override
  public String getName() {
    return "PRODUCT";
  }

  @Override
  public boolean supportMultiArg() {
    return true;
  }
}
