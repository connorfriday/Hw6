package edu.cs3500.spreadsheets.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import edu.cs3500.spreadsheets.sexp.SBoolean;
import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Test suite for the less than function.
 */
public class LessThanTest {

  @Test (expected = IllegalArgumentException.class)
  public void evaluateRegion() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new LessThan(sheet.getFunctions(), sheet);
    sheet.setCell("1", new Coord("A1"));
    sheet.setCell("54", new Coord("A2"));
    sheet.setCell("2", new Coord("A3"));
    sheet.setCell("=B1", new Coord("A4"));
    sheet.setCell("3", new Coord("B1"));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1:B4"));
    func.evaluate(list, new ArrayList<>());
  }

  @Test (expected = IllegalArgumentException.class)
  public void evaluateOneArg() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    List<Sexp> l = new ArrayList<>();
    l.add(new SSymbol("1"));
    f.evaluate(l, new ArrayList<>());
  }

  @Test (expected = IllegalArgumentException.class)
  public void evaluateNoArg() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    List<Sexp> l = new ArrayList<>();
    f.evaluate(l, new ArrayList<>());
  }

  @Test
  public void evaluateBasicTrue() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    List<Sexp> l = new ArrayList<>();
    l.add(new SSymbol("1"));
    l.add(new SSymbol("2"));
    assertEquals("true", f.evaluate(l, new ArrayList<>()));
  }

  @Test
  public void evaluateBasicFalse() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    List<Sexp> l = new ArrayList<>();
    l.add(new SSymbol("11"));
    l.add(new SSymbol("2"));
    assertEquals("false", f.evaluate(l, new ArrayList<>()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateTooManyArgs() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    List<Sexp> l = new ArrayList<>();
    l.add(new SSymbol("11"));
    l.add(new SSymbol("2"));
    l.add(new SSymbol("2"));
    f.evaluate(l, new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateImproperArgType1() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    List<Sexp> l = new ArrayList<>();
    l.add(new SSymbol("hi"));
    l.add(new SSymbol("2"));
    f.evaluate(l, new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateImproperArgType2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    List<Sexp> l = new ArrayList<>();
    l.add(new SBoolean(true));
    l.add(new SSymbol("2"));
    f.evaluate(l, new ArrayList<>());
  }


  @Test
  public void getName() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    assertEquals("<", f.getName());
  }


  @Test
  public void supportMultiArg() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    LessThan f = new LessThan(m.getFunctions(), m);
    assertFalse(f.supportMultiArg());
  }
}