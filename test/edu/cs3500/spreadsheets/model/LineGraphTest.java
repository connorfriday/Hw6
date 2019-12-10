package edu.cs3500.spreadsheets.model;

import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * A test suite for the LineGraph class.
 */
public class LineGraphTest {

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullName() {
    LineGraph g = new LineGraph(null, "A1:B2");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullRefs() {
    LineGraph g = new LineGraph("G", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionEmptyName() {
    LineGraph g = new LineGraph("", "A1:B2");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionEmptyRefs() {
    LineGraph g = new LineGraph("G", "");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNameContainsSpace1() {
    LineGraph g = new LineGraph("G ", "A1:B2");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNameContainsSpace2() {
    LineGraph g = new LineGraph(" ", "A1:B2");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNameContainsSpace3() {
    LineGraph g = new LineGraph("G G", "A1:B2");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidRefs1() {
    LineGraph g = new LineGraph("G", "A1");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidRefs2() {
    LineGraph g = new LineGraph("G", "A1:A1");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidRefs3() {
    LineGraph g = new LineGraph("G", "A1:C1");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidRefs4() {
    LineGraph g = new LineGraph("G", "A1:A3");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidRefs5() {
    LineGraph g = new LineGraph("G", "A:A");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidRefs6() {
    LineGraph g = new LineGraph("G", "A:D");
  }

  @Test
  public void testGetName() {
    LineGraph g = new LineGraph("G", "A1:D2");
    assertEquals(g.getName(), "G");
  }

  @Test
  public void testGetType() {
    LineGraph g = new LineGraph("G", "A1:D2");
    assertEquals(g.getType(), "LINE");
  }

  @Test
  public void testGetValuesRowOriented() {
    LineGraph g = new LineGraph("G", "A1:D2");
    Map<Coord, Coord> vals = g.getValues();
    assertEquals(vals.get(new Coord(1, 1)), new Coord(1, 2));
    assertEquals(vals.get(new Coord(2, 1)), new Coord(2, 2));
    assertEquals(vals.get(new Coord(3, 1)), new Coord(3, 2));
    assertEquals(vals.get(new Coord(4, 1)), new Coord(4, 2));
  }

  @Test
  public void testGetValuesColumnOriented() {
    LineGraph g = new LineGraph("G", "A1:B4");
    Map<Coord, Coord> vals = g.getValues();
    assertEquals(vals.get(new Coord(1, 1)), new Coord(2, 1));
    assertEquals(vals.get(new Coord(1, 2)), new Coord(2, 2));
    assertEquals(vals.get(new Coord(1, 3)), new Coord(2, 3));
    assertEquals(vals.get(new Coord(1, 4)), new Coord(2, 4));
  }

  @Test
  public void testGetRefs() {
    LineGraph g = new LineGraph("G", "A1:B4");
    assertEquals(g.getRefs(), "A1:B4");
  }
}
