package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.EvaluateSexp;
import edu.cs3500.spreadsheets.sexp.Parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * A class representing a basic spreadsheet implementing the SpreadsheetModel interface.
 */
public class BasicSpreadsheetModel implements SpreadsheetModel {

  private final HashMap<Coord, Cell> cells;
  private final Map<String, Function<String[], SpreadsheetGraph>> graphBuilders;
  private final Map<String, SpreadsheetGraph> graphs;
  private final Map<String, SpreadsheetFunction> functions = new HashMap<>();
  private final Map<Coord, String> evaluated = new HashMap<>();

  /**
   * This constructs an instance of BasicSpreadsheetModel with all cells empty.
   */
  public BasicSpreadsheetModel() {
    this.cells = new HashMap<>();
    this.graphs = new HashMap<>();
    this.graphBuilders = new HashMap<>();
    this.setFunctions();
    this.setGraphs();
  }

  @Override
  public void clearCell(Coord coord) {
    cells.remove(coord);
  }

  // throws illegal argument from parser
  @Override
  public void setCell(String s, Coord coord) {
    if (!s.trim().isEmpty()) {

      boolean hasEquals = false;
      if (s.charAt(0) == '=') {
        s = s.substring(1);
        hasEquals = true;
      }

      try {
        Parser.parse(s);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Error in cell "
            + coord.toString()
            + ": " + e.getMessage());
      }

      if (hasEquals) {
        s = "=" + s;
      }
    }
    if (!cells.containsKey(coord)) {
      if (s.trim().isEmpty()) {
        this.evaluated.clear();
        return;
      } else {
        cells.put(coord, new Cell(s));
      }
    } else {
      if (s.trim().isEmpty()) {
        cells.remove(coord);
        this.evaluated.clear();
        return;
      }
      Cell c = this.cells.get(coord);
      c.updateContents(s);
    }
    this.evaluated.clear();

    try {
      this.getComputedValue(coord);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("This update will create a cycle");
    }
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
    return getComputedValue(new ArrayList<>(), coord);
  }

  @Override
  public String getComputedValue(List<Coord> visited, Coord coord) {
    if (visited.contains(coord)) {
      throw new IllegalArgumentException("A cycle exists.");
    }

    ArrayList<Coord> newVis = new ArrayList<>(visited);
    newVis.add(coord);

    String str = getRawValue(coord);
    if (str.trim().isEmpty()) {
      return str;
    }

    if (evaluated.containsKey(coord)) {
      return evaluated.get(coord);
    } else {
      if (!cells.containsKey(coord)) {
        evaluated.put(coord, "");
        return "";
      }
      try {
        String s = getRawValue(coord);
        if (s.charAt(0) == '=') {
          s = s.substring(1);
        }

        String res = parseAndEvaluate(newVis, s);
        evaluated.put(coord, res);
        return res;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Error computing value in cell "
            + coord.toString() + ": "
            + e.getMessage());
      }
    }
  }

  private String parseAndEvaluate(List<Coord> visited, String s) {
    if (s.substring(0, 1).equals("=")) {
      s = s.substring(1);
    }
    return Parser.parse(s).accept(
        new EvaluateSexp(functions, this, null, visited));
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

  private void setGraphs() {
    graphBuilders.put("LINE", (String[] info) -> {
      return new LineGraph(info[0], info[1]);
    });
  }

  @Override
  public void addGraph(String type, String name, String refs) {
    if (name.contains(" ") || name.contains("\n")) {
      throw new IllegalArgumentException("name cannot contain a space character");
    }
    if (graphs.containsKey(name)) {
      throw new IllegalArgumentException("Cannot have two graphs with the same name");
    }

    if (!graphBuilders.containsKey(type)) {
      throw new IllegalArgumentException("Unsupported graph type.");
    }
    String[] args = new String[2];
    args[0] = name;
    args[1] = refs;

    graphs.put(name, graphBuilders.get(type).apply(args));
  }

  @Override
  public void removeGraph(String name) {
    this.graphs.remove(name);
  }

  @Override
  public Map<String, SpreadsheetGraph> getGraphs() {
    return graphs;
  }

  @Override
  public Set<String> getGraphTypes() {
    return graphBuilders.keySet();
  }
}
