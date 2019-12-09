package edu.cs3500.spreadsheets.sexp;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyBasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetFunction;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A visitor that allows the evaluation of an SExpression.
 */
public class EvaluateSexp implements SexpVisitor<String> {
  private final Map<String, SpreadsheetFunction> functionMap;
  private final SpreadsheetModel sheet;
  private final SpreadsheetFunction function;
  private final List<Coord> visited;

  /**
   * Constructs an instance of a visitor to evaluate an SExpression.
   * @param functionMap the supported functions of the sheet
   * @param sheet the sheet
   * @param function the function this visitor was called in (null if none)
   */
  public EvaluateSexp(
      Map<String, SpreadsheetFunction> functionMap,
      SpreadsheetModel sheet,
      SpreadsheetFunction function,
      List<Coord> visited) {
    if (functionMap == null) {
      throw new IllegalArgumentException("Function map cannot be null");
    }
    if (sheet == null) {
      throw new IllegalArgumentException("Spreadsheet cannot be null");
    }
    if (visited == null) {
      throw new IllegalArgumentException("Visited cannot be null");
    }

    this.functionMap = functionMap;
    this.sheet = sheet;
    this.function = function;
    this.visited = visited;
  }

  @Override
  public String visitBoolean(boolean b) {
    return Boolean.toString(b);
  }

  @Override
  public String visitNumber(double d) {
    return String.format("%f", d);
  }

  @Override
  public String visitSList(List<Sexp> l) {
    if (l.isEmpty()) {
      throw new IllegalArgumentException("Cannot evaluate empty parentheses");
    }
    String funcName = l.get(0).accept(new EvaluateSexp(functionMap, sheet, null, this.visited));
    if (functionMap.containsKey(funcName)) {
      SpreadsheetFunction func = functionMap.get(funcName);
      return func.evaluate(l.subList(1, l.size()), visited);
    }
    else {
      throw new IllegalArgumentException("Expected supported function call");
    }
  }

  @Override
  public String visitSymbol(String s) {

    if (functionMap.containsKey(s)) {
      return s;
    }
    else {
      List<Coord> refs = Parser.parse(s).accept(new GetCoordReferences());
      refs.addAll(Parser.parse(s).accept(
          new GetColumnReferences(new ReadOnlyBasicSpreadsheetModel(sheet))));
      if (refs.size() == 0) {
        return s;
      }
      if (refs.size() == 1) {
        Coord ref = refs.get(0);
        return sheet.getComputedValue(visited, refs.get(0));
      }
      else if (function != null && function.supportMultiArg()) {
        List<Sexp> res = new ArrayList<>();
        for (Coord coord : refs) {
          res.add(new SSymbol(coord.toString()));
        }
        return function.evaluate(res, visited);
      }
      throw new IllegalArgumentException("Cannot have multi cell reference out of a "
          + "multi cell reference supported function");
    }
  }

  @Override
  public String visitString(String s) {
    return s;
  }
}
