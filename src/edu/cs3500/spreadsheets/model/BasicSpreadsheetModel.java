package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.EvaluateSexp;
import edu.cs3500.spreadsheets.sexp.GetCoordReferences;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * A class representing a basic spreadsheet implementing the SpreadsheetModel interface.
 */
public class BasicSpreadsheetModel implements SpreadsheetModel {

  private final HashMap<Coord, Cell> cells;
  private final Map<String, SpreadsheetFunction> functions = new HashMap<>();
  private final Map<Coord, String> evaluated = new HashMap<>();

  /**
   * This constructs an instance of BasicSpreadsheetModel with all cells empty.
   */
  public BasicSpreadsheetModel() {
    this.cells = new HashMap<>();
    this.setFunctions();
  }

  @Override
  public void clearCell(Coord coord) {
    cells.remove(coord);
  }

  // throws illegal argument from parser
  @Override
  public void setCell(String s, Coord coord) {
    if (s.isEmpty()) {
      return;
    }
    boolean hasEquals = false;
    if (s.charAt(0) == '=') {
      s = s.substring(1);
      hasEquals = true;
    }

    if (getAllReferences(s).contains(coord)) {
      throw new IllegalArgumentException("This update will create a cycle");
    }

    if (hasEquals) {
      s = "=" + s;
    }

    if (!cells.containsKey(coord)) {
      cells.put(coord, new Cell(s));
    } else {
      Cell c = this.cells.get(coord);
      c.updateContents(s);
    }
    this.evaluated.clear();
  }

  private Set<Coord> getAllReferences(String s) {
    Sexp exp = Parser.parse(s);
    Set<Coord> refs = new HashSet<>();
    Stack<Coord> stack = new Stack<>();
    stack.addAll(exp.accept(new GetCoordReferences()));
    while (!stack.isEmpty()) {
      Coord curr = stack.pop();
      refs.add(curr);

      if (cells.containsKey(curr)) {
        Cell cell = cells.get(curr);
        for (Coord coord : Parser.parse(cell.getContents()).accept(new GetCoordReferences())) {
          if (!refs.contains(coord)) {
            stack.push(coord);
          }
        }
      }
    }
    return refs;
  }

  @Override
  public String getRawValue(Coord coord) {
    if (!cells.containsKey(coord)) {
      return "";
    }
    return cells.get(coord).getContents();
  }

  @Override
  public String getComputedValue(Coord coord) {
    if (evaluated.containsKey(coord)) {
      return evaluated.get(coord);
    } else {
      if (!cells.containsKey(coord)) {
        evaluated.put(coord, "");
        return "";
      }
      try {
        String res = parseAndEvaluate(getRawValue(coord));
        evaluated.put(coord, res);
        return res;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Error computing value in cell "
            + coord.toString() + ": "
            + e.getMessage());
      }
    }
  }

  private String parseAndEvaluate(String s) {
    if (s.substring(0, 1).equals("=")) {
      s = s.substring(1);
    }
    return Parser.parse(s).accept(
        new EvaluateSexp(functions, this, null));
  }

  @Override
  public Map<String, SpreadsheetFunction> getFunctions() {
    HashMap<String, SpreadsheetFunction> map = new HashMap<>();
    for (SpreadsheetFunction func : functions.values()) {
      map.put(func.getName(), func);
    }
    return map;
  }

  @Override
  public Set<Coord> getNonEmptyCoordinates() {
    return cells.keySet();
  }

  private void setFunctions() {
    functions.put("SUM", new Sum(functions, this));
    functions.put("PRODUCT", new Product(functions, this));
    functions.put("CONCAT", new Concat(functions, this));
    functions.put("<", new LessThan(functions, this));
  }
}
