package edu.cs3500.spreadsheets.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This class contains tests for the Coord class regarding an additional constructor we added.
 */
public class CoordTest {

  @Test
  public void testStringContructor1() {
    Coord c = new Coord("A1");
    assertEquals(c.col, 1);
    assertEquals(c.row, 1);
  }

  @Test
  public void testStringContructor2() {
    Coord c = new Coord("AA12");
    assertEquals(c.col, 27);
    assertEquals(c.row, 12);
  }

  @Test
  public void testStringContructor3() {
    Coord c = new Coord("CCC127");
    assertEquals(c.col, 2109);
    assertEquals(c.row, 127);
  }

  @Test
  public void testStringContructor4() {
    Coord c = new Coord("ZZZ8787");
    assertEquals(c.col, 18278);
    assertEquals(c.row, 8787);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStringConstructorMalformattedString1() {
    Coord c = new Coord("CCC12W7");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStringConstructorMalformattedString2() {
    Coord c = new Coord("CC1C127");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStringConstructorMalformattedString3() {
    Coord c = new Coord("CCCASF");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStringConstructorMalformattedString4() {
    Coord c = new Coord("128664723");
  }
}