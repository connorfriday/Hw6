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
 * A test suite for the product function object.
 */
public class ProductTest {

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNullMap() {
    SpreadsheetFunction func = new Product(null, new BasicSpreadsheetModel());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNullSheet() {
    SpreadsheetFunction func = new Product(new HashMap<>(), null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void evaluateAllStrings() {
    SpreadsheetFunction func = new Product(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SString("Hello"));
    list.add(new SString(" "));
    list.add(new SString("World"));
    list.add(new SString("!"));
    func.evaluate(list);
  }

  @Test
  public void evaluateAllNumbers() {
    SpreadsheetFunction func = new Product(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SNumber(1.0));
    list.add(new SNumber(2.0));
    list.add(new SNumber(3.0));
    list.add(new SNumber(4.0));
    assertEquals(func.evaluate(list), "24.000000");
  }

  @Test (expected = IllegalArgumentException.class)
  public void evaluateAllBoolean() {
    SpreadsheetFunction func = new Product(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SBoolean(true));
    list.add(new SBoolean(true));
    list.add(new SBoolean(false));
    list.add(new SBoolean(true));
    func.evaluate(list);
  }

  @Test (expected = IllegalArgumentException.class)
  public void evaluateMixed1() {
    SpreadsheetFunction func = new Product(new HashMap<>(), new BasicSpreadsheetModel());
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SString("Hello"));
    list.add(new SNumber(3.0));
    list.add(new SBoolean(true));
    list.add(new SString("World"));
    list.add(new SNumber(3.0));
    list.add(new SBoolean(true));
    func.evaluate(list);
  }

  @Test
  public void evaluateAllReferencesNums() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    sheet.setCell("2", new Coord("A1"));
    sheet.setCell("3", new Coord("A2"));
    sheet.setCell("4", new Coord("A3"));
    sheet.setCell("=B1", new Coord("A4"));
    sheet.setCell("4", new Coord("B1"));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1"));
    list.add(new SSymbol("A2"));
    list.add(new SSymbol("A3"));
    list.add(new SSymbol("A4"));
    assertEquals(func.evaluate(list), "96.000000");
  }

  @Test (expected = IllegalArgumentException.class)
  public void evaluateAllReferencesNonNum() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    sheet.setCell("2", new Coord("A1"));
    sheet.setCell("true", new Coord("A2"));
    sheet.setCell("4", new Coord("A3"));
    sheet.setCell("=B1", new Coord("A4"));
    sheet.setCell("4", new Coord("B1"));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1"));
    list.add(new SSymbol("A2"));
    list.add(new SSymbol("A3"));
    list.add(new SSymbol("A4"));
    func.evaluate(list);
  }

  @Test (expected = IllegalArgumentException.class)
  public void evaluateMixedWithRefs() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
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
    func.evaluate(list);
  }

  @Test
  public void evaluateEmptyCells() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1"));
    list.add(new SSymbol("A2"));
    list.add(new SSymbol("A3"));
    list.add(new SNumber(4.0));
    list.add(new SSymbol("A4"));
    list.add(new SNumber(9.0));
    assertEquals(func.evaluate(list), "36.000000");
  }

  @Test
  public void evaluateSingleEmptyCells() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1"));
    assertEquals(func.evaluate(list), "0.000000");
  }

  @Test
  public void evaluateNoArgs() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    assertEquals(func.evaluate(list), "0.000000");
  }

  @Test
  public void evaluateRegion() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    sheet.setCell("1", new Coord("A1"));
    sheet.setCell("54", new Coord("A2"));
    sheet.setCell("3", new Coord("A3"));
    sheet.setCell("=B1", new Coord("A4"));
    sheet.setCell("2", new Coord("B1"));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SSymbol("A1:B4"));
    assertEquals(func.evaluate(list), "648.000000");
  }

  @Test
  public void evaluateWithInnerFunction() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    ArrayList<Sexp> innerList = new ArrayList<>();
    innerList.add(new SSymbol("SUM"));
    innerList.add(new SNumber(1.0));
    innerList.add(new SNumber(12.0));
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SList(innerList));
    list.add(new SNumber(4.0));
    list.add(new SNumber(16.0));
    assertEquals(func.evaluate(list), "832.000000");
  }

  @Test
  public void evaluateOneArg() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SNumber(4.0));
    assertEquals(func.evaluate(list), "4.000000");
  }

  @Test
  public void evaluateTwoArg() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SNumber(4.0));
    list.add(new SNumber(16.0));
    assertEquals(func.evaluate(list), "64.000000");
  }

  @Test
  public void evaluateThreeArg() {
    SpreadsheetModel sheet = new BasicSpreadsheetModel();
    SpreadsheetFunction func = new Product(sheet.getFunctions(), sheet);
    ArrayList<Sexp> list = new ArrayList<>();
    list.add(new SNumber(4.0));
    list.add(new SNumber(16.0));
    list.add(new SNumber(6.0));
    assertEquals(func.evaluate(list), "384.000000");
  }

  @Test
  public void getName() {
    SpreadsheetFunction func = new Product(new HashMap<>(), new BasicSpreadsheetModel());
    assertEquals(func.getName(), "PRODUCT");
  }

  @Test
  public void supportMultiArg() {
    SpreadsheetFunction func = new Product(new HashMap<>(), new BasicSpreadsheetModel());
    assertTrue(func.supportMultiArg());
  }
}