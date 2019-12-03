package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.provider.cell.CellBoolean;
import edu.cs3500.spreadsheets.provider.cell.CellDouble;
import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.cell.CellReference;
import edu.cs3500.spreadsheets.provider.cell.CellString;
import edu.cs3500.spreadsheets.sexp.SBoolean;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;
import java.util.List;

public class SexpToCellFormulaVisitor implements SexpVisitor<CellFormula> {

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
    return null;
  }

  @Override
  public CellFormula visitSymbol(String s) {
    //unsure, todo
    return null;
  }

  @Override
  public CellFormula visitString(String s) {
    return new CellString(s);
  }
}
