package edu.cs3500.spreadsheets.sexp;

import static org.junit.Assert.assertEquals;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Sum;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * A test suit for the evaluation visitor for SExpressions.
 */
public class EvaluateSexpTest {

  @Test
  public void visitBoolean() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals(Boolean.toString(true), e.visitBoolean(true));
  }

  @Test
  public void visitBoolean2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals(Boolean.toString(false), e.visitBoolean(false));
  }

  @Test
  public void visitNumber() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("1.0", e.visitNumber(1));
  }

  @Test
  public void visitNumber2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("2.5", e.visitNumber(2.5));
  }

  @Test
  public void visitSList() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("3.0", e.visitSList(list));
  }


  @Test
  public void visitSList2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    list.add(new SSymbol("A1"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("6.0", e.visitSList(list));
  }

  @Test
  public void visitSList3() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("9.0", e.visitSList(list));
  }

  @Test (expected = IllegalArgumentException.class)
  public void visitSList4() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("9.0", e.visitSList(new ArrayList<>()));
  }

  @Test (expected = IllegalArgumentException.class)
  public void visitSList5() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("PRO"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("9.0", e.visitSList(new ArrayList<>()));
  }

  @Test
  public void visitSymbol() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("SUM", e.visitSymbol(list.get(0).toString()));
  }

  @Test
  public void visitSymbol2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("1", e.visitSymbol(list.get(1).toString()));
  }

  @Test
  public void visitSymbol3() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1"));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("3.0", e.visitSymbol(list.get(3).toString()));
  }

  @Test
  public void visitSymbol4() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1"));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, new Sum(m.getFunctions(), m));
    assertEquals("6.0", e.visitSymbol(list.get(4).toString()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void visitSymbol5() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    List<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("SUM"));
    list.add(new SSymbol("1"));
    list.add(new SSymbol("2"));
    m.setCell("3", new Coord(1, 1));
    m.setCell("3", new Coord(1, 2));
    list.add(new SSymbol("A1"));
    list.add(new SSymbol("A1:A2"));
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("6.0", e.visitSymbol(list.get(4).toString()));
  }


  @Test
  public void visitString() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    EvaluateSexp e = new EvaluateSexp(m.getFunctions(), m, null);
    assertEquals("JO", e.visitString("JO"));
  }


}