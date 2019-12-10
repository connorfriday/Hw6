package edu.cs3500.spreadsheets.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import org.junit.Test;

/**
 * As test suite for the BasicSpreadsheetModel class.
 */
public class BasicSpreadsheetModelTest {

  @Test
  public void testEmpty() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    assertEquals("", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void testNumber() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("3", new Coord("A1"));
    assertEquals("3", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void testString() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("hi!", new Coord("A1"));
    assertEquals("hi!", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void testBoolean() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("true", new Coord("A1"));
    assertEquals("true", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void testBlank() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("", new Coord("A1"));
    assertEquals("", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void testLess() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(< 1 2)", new Coord("A1"));
    assertEquals("true", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void testConcat() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(CONCAT hi bye)", new Coord("A1"));
    assertEquals("hibye", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void testProduct() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(PRODUCT 1 2)", new Coord("A1"));
    assertEquals("2.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void testSum() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(SUM 1 2)", new Coord("A1"));
    assertEquals("3.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void testSameCellMultipleTimes() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(SUM B1 B1)", new Coord("A1"));
    assertEquals("0.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void testRegion() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(SUM B1:Z1)", new Coord("A1"));
    m.setCell("10", new Coord("G1"));
    assertEquals("10.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFormulaIncorrect() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(SUM B1 ho)", new Coord("A1"));
    m.getComputedValue(new Coord("A1"));
  }


  @Test
  public void clearCell() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("hello!", new Coord("A1"));
    assertEquals("hello!", m.getRawValue(new Coord("A1")));
    m.clearCell(new Coord("A1"));
    assertEquals("", m.getRawValue(new Coord("A1")));

  }

  @Test
  public void setCell() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("hello!", new Coord("A1"));
    assertEquals("hello!", m.getRawValue(new Coord("A1")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void setCell2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("A1", new Coord("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void setCell3() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("A2", new Coord("A1"));
    m.setCell("A1", new Coord("A2"));
  }

  @Test
  public void setCell4() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("hello!", new Coord("A1"));
    assertEquals("hello!", m.getRawValue(new Coord("A1")));
    m.setCell("hello", new Coord("A1"));
    assertEquals("hello", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void setCell5() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("", new Coord("A1"));
    assertEquals("", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void getRawValue() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("hello!", new Coord("A1"));
    assertEquals("hello!", m.getRawValue(new Coord("A1")));
    m.setCell("hello", new Coord("A1"));
    assertEquals("hello", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void getRawValue2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    assertEquals("", m.getRawValue(new Coord("A1")));
  }

  @Test
  public void getComputedValue() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(SUM 1 2)", new Coord("A1"));
    assertEquals("3.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void getComputedValue2() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("2", new Coord("A1"));
    assertEquals("2.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void getComputedValue3() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("true", new Coord("A1"));
    assertEquals("true", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void getComputedValue4() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("\"=(SUM 1 2 3)\"", new Coord("A1"));

    assertEquals("=(SUM 1 2 3)", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void getComputedValue5() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(< 1 2)", new Coord("A1"));
    assertEquals("true", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void getComputedValue6() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(CONCAT 1 2 3)", new Coord("A1"));
    assertEquals("1.0000002.0000003.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test
  public void getComputedValue7() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(PRODUCT 1 2)", new Coord("A1"));
    assertEquals("2.000000", m.getComputedValue(new Coord("A1")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getComputedValue8() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    m.setCell("=(PRODUCT x 2)", new Coord("A1"));
    m.getComputedValue(new Coord("A1"));
  }

  @Test
  public void getFunctions() {
    BasicSpreadsheetModel m = new BasicSpreadsheetModel();
    assertTrue(m.getFunctions().containsKey("SUM"));
    assertTrue(m.getFunctions().containsKey("PRODUCT"));
    assertTrue(m.getFunctions().containsKey("CONCAT"));
    assertTrue(m.getFunctions().containsKey("<"));
  }

  @Test
  public void addGraph() {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    assertEquals(model.getGraphs(), new HashMap<>());
  }

  @Test
  public void addGraph2() {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    model.addGraph("LINE", "A", "A1:B2");
    assertTrue(model.getGraphs().containsKey("A"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void addGraph3()  {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    model.addGraph("LINE", " ", "A1:B2");
  }

  @Test (expected = IllegalArgumentException.class)
  public void addGraph4()  {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    model.addGraph("LINE", "A", "A1:B2");
    model.addGraph("LINE", "A", "A1:B2");
  }

  @Test (expected = IllegalArgumentException.class)
  public void addGraph5()  {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    model.addGraph("BAR", "A", "A1:B2");
  }

  @Test
  public void removeGraph() {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    model.addGraph("LINE", "A", "A1:B2");
    model.removeGraph("A");
    assertEquals(0, model.getGraphs().size());
  }

  @Test
  public void removeGraph2() {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    model.addGraph("LINE", "B", "A1:B2");
    model.removeGraph("A");
    assertEquals(1, model.getGraphs().size());
  }

  @Test
  public void getGraphs() {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    model.addGraph("LINE", "B", "A1:B2");
    model.addGraph("LINE", "C", "A1:B3");
    assertTrue(model.getGraphs().containsKey("B"));
    assertTrue(model.getGraphs().containsKey("C"));
    assertFalse(model.getGraphs().containsKey("A"));
  }

  @Test
  public void getGraphTypes() {
    BasicSpreadsheetModel model = new BasicSpreadsheetModel();
    assertEquals(1, model.getGraphTypes().size());
    assertEquals("LINE", model.getGraphTypes().toArray()[0]);
  }





}