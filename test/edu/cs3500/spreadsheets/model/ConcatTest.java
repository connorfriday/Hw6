package edu.cs3500.spreadsheets.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.cs3500.spreadsheets.sexp.SBoolean;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;

/**
 * A test suite for the Concatenation function object.
 */
public class ConcatTest {

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNullMap() {
    SpreadsheetFunction func = new Concat(null, new BasicSpreadsheetModel());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNullSheet() {
    SpreadsheetFunction func = new Concat(new HashMap<>(), null);
  }

  @Test
  public void evaluateAllStrings() {
    SpreadsheetFunction func = new Concat(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SString("Hello"));
    list.add(new SString(" "));
    list.add(new SString("World"));
    list.add(new SString("!"));
    assertEquals(func.evaluate(list, new ArrayList<>()), "Hello World!");
  }

  @Test
  public void evaluateAllNumbers() {
    SpreadsheetFunction func = new Concat(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SNumber(1.0));
    list.add(new SNumber(2.0));
    list.add(new SNumber(3.0));
    list.add(new SNumber(4.0));
    assertEquals(func.evaluate(list, new ArrayList<>()), "1.0000002.0000003.0000004.000000");
  }

  @Test
  public void evaluateAllBoolean() {
    SpreadsheetFunction func = new Concat(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SBoolean(true));
    list.add(new SBoolean(true));
    list.add(new SBoolean(false));
    list.add(new SBoolean(true));
    assertEquals(func.evaluate(list, new ArrayList<>()), "truetruefalsetrue");
  }

  @Test
  public void evaluateMixed1() {
    SpreadsheetFunction func = new Concat(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SString("Hello"));
    list.add(new SNumber(3.0));
    list.add(new SBoolean(true));
    list.add(new SString("World"));
    list.add(new SNumber(3.0));
    list.add(new SBoolean(true));
    assertEquals(func.evaluate(list, new ArrayList<>()), "Hello3.000000trueWorld3.000000true");
  }

  @Test
  public void evaluateAllReferences() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Concat(sheet.getFunctions(), sheet);
    sheet.setCell("\"Hi\"", new Coord("A1"));
    sheet.setCell("54", new Coord("A2"));
    sheet.setCell("true", new Coord("A3"));
    sheet.setCell("=B1", new Coord("A4"));
    sheet.setCell("\"car\"", new Coord("B1"));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1"));
    list.add(new SSymbol("A2"));
    list.add(new SSymbol("A3"));
    list.add(new SSymbol("A4"));
    assertEquals(func.evaluate(list, new ArrayList<>()), "Hi54.000000truecar");
  }

  @Test
  public void evaluateMixedWithRefs() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Concat(sheet.getFunctions(), sheet);
    sheet.setCell("\"Hi\"", new Coord("A1"));
    sheet.setCell("54", new Coord("A2"));
    sheet.setCell("true", new Coord("A3"));
    sheet.setCell("=B1", new Coord("A4"));
    sheet.setCell("\"car\"", new Coord("B1"));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1"));
    list.add(new SString("Hello"));
    list.add(new SSymbol("A2"));
    list.add(new SBoolean(true));
    list.add(new SSymbol("A3"));
    list.add(new SNumber(4.0));
    list.add(new SSymbol("A4"));
    assertEquals(func.evaluate(list, new ArrayList<>()), "HiHello54.000000truetrue4.000000car");
  }

  @Test
  public void evaluateEmptyCells() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Concat(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1"));
    list.add(new SString("Hello"));
    list.add(new SSymbol("A2"));
    list.add(new SBoolean(true));
    list.add(new SSymbol("A3"));
    list.add(new SNumber(4.0));
    list.add(new SSymbol("A4"));
    assertEquals(func.evaluate(list, new ArrayList<>()), "Hellotrue4.000000");
  }

  @Test
  public void evaluateWithInnerFunction() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Concat(sheet.getFunctions(), sheet);
    ArrayList<Sexp> innerList = new ArrayList<>();
    innerList.add(new SSymbol("<"));
    innerList.add(new SNumber(1.0));
    innerList.add(new SNumber(12.0));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SList(innerList));
    list.add(new SString("Hello"));
    list.add(new SBoolean(true));
    list.add(new SNumber(4.0));
    assertEquals(func.evaluate(list, new ArrayList<>()), "trueHellotrue4.000000");
  }

  @Test
  public void evaluateRegion() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Concat(sheet.getFunctions(), sheet);
    sheet.setCell("\"Hi\"", new Coord("A1"));
    sheet.setCell("54", new Coord("A2"));
    sheet.setCell("true", new Coord("A3"));
    sheet.setCell("=B1", new Coord("A4"));
    sheet.setCell("\"car\"", new Coord("B1"));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1:B4"));
    assertEquals(func.evaluate(list, new ArrayList<>()), "Hicar54.000000truecar");
  }

  @Test
  public void getName() {
    SpreadsheetFunction func = new Concat(new HashMap<>(), new BasicSpreadsheetModel());
    assertEquals(func.getName(), "CONCAT");
  }

  @Test
  public void evaluateNoArgs() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Concat(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    assertEquals(func.evaluate(list, new ArrayList<>()), "");
  }

  @Test
  public void supportMultiArg() {
    SpreadsheetFunction func = new Concat(new HashMap<>(), new BasicSpreadsheetModel());
    assertTrue(func.supportMultiArg());
  }
}