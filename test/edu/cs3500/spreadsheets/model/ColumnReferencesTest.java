package edu.cs3500.spreadsheets.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a class that tests the addition of support for column references to a spreadsheet.
 */
public class ColumnReferencesTest {

  @Test
  public void test1ColumnSum() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("100", new Coord(1, 1));
    model.setCell("200", new Coord(1, 2));
    model.setCell("400", new Coord(1, 3));
    model.setCell("500", new Coord(1, 4));
    model.setCell("=(SUM A:A)", new Coord(2, 1));
    assertEquals("1200.000000", model.getComputedValue(new Coord(2, 1)));
  }

  @Test
  public void test2ColumnSum() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("100", new Coord(1, 1));
    model.setCell("200", new Coord(1, 2));
    model.setCell("400", new Coord(2, 3));
    model.setCell("500", new Coord(2, 4));
    model.setCell("=(SUM A:B)", new Coord(3, 1));
    assertEquals("1200.000000", model.getComputedValue(new Coord(3, 1)));
  }

  @Test
  public void test1ColumnSumSparse() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("100", new Coord(1, 1));
    model.setCell("200", new Coord(1, 50));
    model.setCell("400", new Coord(1, 14));
    model.setCell("500", new Coord(1, 10000234));
    model.setCell("=(SUM A:B)", new Coord(3, 1));
    assertEquals("1200.000000", model.getComputedValue(new Coord(3, 1)));
  }

  @Test
  public void test2ColumnSumSparse() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("100", new Coord(1, 1));
    model.setCell("200", new Coord(1, 50));
    model.setCell("400", new Coord(1, 14));
    model.setCell("500", new Coord(1, 10000234));
    model.setCell("100", new Coord(2, 1));
    model.setCell("200", new Coord(2, 50));
    model.setCell("400", new Coord(2, 14));
    model.setCell("500", new Coord(2, 10000234));
    model.setCell("=(SUM A:B)", new Coord(3, 1));
    assertEquals("2400.000000", model.getComputedValue(new Coord(3, 1)));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSumCatchCycleWithSumUpdate() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("=B1", new Coord(1, 1));
    model.setCell("200", new Coord(1, 50));
    model.setCell("400", new Coord(1, 14));
    model.setCell("500", new Coord(1, 10000234));
    model.setCell("=(SUM A:A)", new Coord(2, 1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSumCatchCycleWithValueUpdate() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("100", new Coord(1, 1));
    model.setCell("200", new Coord(1, 50));
    model.setCell("400", new Coord(1, 14));
    model.setCell("500", new Coord(1, 10000234));
    model.setCell("=(SUM A:A)", new Coord(2, 1));
    assertEquals("1200.000000", model.getComputedValue(new Coord(2, 1)));
    model.setCell("=B1", new Coord(1, 1));
  }

  @Test
  public void test1ColumnProduct() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("1", new Coord(1, 1));
    model.setCell("2", new Coord(1, 2));
    model.setCell("4", new Coord(1, 3));
    model.setCell("5", new Coord(1, 4));
    model.setCell("=(PRODUCT A:A)", new Coord(2, 1));
    assertEquals("40.000000", model.getComputedValue(new Coord(2, 1)));
  }

  @Test
  public void test2ColumnProduct() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("1", new Coord(1, 1));
    model.setCell("2", new Coord(1, 2));
    model.setCell("4", new Coord(2, 3));
    model.setCell("5", new Coord(2, 4));
    model.setCell("=(PRODUCT A:B)", new Coord(3, 1));
    assertEquals("40.000000", model.getComputedValue(new Coord(3, 1)));
  }

  @Test
  public void test1ColumnProductSparse() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("1", new Coord(1, 1));
    model.setCell("2", new Coord(1, 50));
    model.setCell("4", new Coord(1, 14));
    model.setCell("5", new Coord(1, 10000234));
    model.setCell("=(PRODUCT A:B)", new Coord(3, 1));
    assertEquals("40.000000", model.getComputedValue(new Coord(3, 1)));
  }

  @Test
  public void test2ColumnProductSparse() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("1", new Coord(1, 1));
    model.setCell("2", new Coord(1, 50));
    model.setCell("4", new Coord(1, 14));
    model.setCell("5", new Coord(1, 10000234));
    model.setCell("1", new Coord(2, 1));
    model.setCell("2", new Coord(2, 50));
    model.setCell("4", new Coord(2, 14));
    model.setCell("5", new Coord(2, 10000234));
    model.setCell("=(PRODUCT A:B)", new Coord(3, 1));
    assertEquals("1600.000000", model.getComputedValue(new Coord(3, 1)));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testProductCatchCycleWithProductUpdate() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("=B1", new Coord(1, 1));
    model.setCell("2", new Coord(1, 50));
    model.setCell("4", new Coord(1, 14));
    model.setCell("5", new Coord(1, 10000234));
    model.setCell("=(PRODUCT A:A)", new Coord(2, 1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testProductCatchCycleWithValueUpdate() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    model.setCell("1", new Coord(1, 1));
    model.setCell("2", new Coord(1, 50));
    model.setCell("4", new Coord(1, 14));
    model.setCell("5", new Coord(1, 10000234));
    model.setCell("=(PRODUCT A:A)", new Coord(2, 1));
    assertEquals("40.000000", model.getComputedValue(new Coord(2, 1)));
    model.setCell("=B1", new Coord(1, 1));
  }
}
