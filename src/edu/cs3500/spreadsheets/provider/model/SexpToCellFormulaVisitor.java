package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.cell.CellBoolean;
import edu.cs3500.spreadsheets.provider.cell.CellDouble;
import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.cell.CellFunction;
import edu.cs3500.spreadsheets.provider.cell.CellReference;
import edu.cs3500.spreadsheets.provider.cell.CellString;
import edu.cs3500.spreadsheets.provider.function.Add;
import edu.cs3500.spreadsheets.provider.function.IFunction;
import edu.cs3500.spreadsheets.provider.function.LessThan;
import edu.cs3500.spreadsheets.provider.function.Multiply;
import edu.cs3500.spreadsheets.provider.function.Repeat;
import edu.cs3500.spreadsheets.sexp.GetCoordReferences;
import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SexpToCellFormulaVisitor implements SexpVisitor<CellFormula> {
  private final Coord coord;
  private final Worksheet model;

  SexpToCellFormulaVisitor(Coord coord, Worksheet model) {
    if (coord == null || model == null) {
      throw new IllegalArgumentException("Can't be null");
    }
    this.coord = coord;
    this.model = model;
  }

  @Override
  public CellFormula visitBoolean(boolean b) {
    return new CellBoolean(b);
  }

  @Override
  public CellFormula visitNumber(double d) {
    return new CellDouble(d);
  }

  @Override
  public CellFormula visitSList(List<Sexp> l) {
    //unsure, todo
    if (l == null) {
      throw new IllegalArgumentException("Can't be null.");
    }
    if (l.isEmpty()) {
      throw new IllegalArgumentException("Can't be empty.");
    }

    String s = l.get(0).toString();

    List<CellFormula> nl = new ArrayList<>();
    for (int i = 1; i < l.size(); i++) {
      nl.add(l.get(i).accept(this));
    }

    IFunction f;
    switch (s) {
      case "SUM":
        f = new Add();
        break;
      case "<":
        f = new LessThan();
        break;
      case "REPT":
        f = new Repeat();
        break;
      case "Product":
        f = new Multiply();
        break;
      default:
        throw new IllegalArgumentException("Supported function expected");
    }

    return new CellFunction(s, nl, this.coord);
  }

  @Override
  public CellFormula visitSymbol(String s) {
    List<Coord> l = new SSymbol(s).accept(new GetCoordReferences());
    if (l.isEmpty()) {
      throw new IllegalArgumentException("Expected a reference.");
    }
    HashMap<Coord, CellFormula> map = new HashMap<>();
    for (Coord c : l) {
      map.put(c, model.getCellAt(c));
    }
    return new CellReference(s, map, this.coord, false);
  }

  @Override
  public CellFormula visitString(String s) {
    return new CellString(s);
  }
}
