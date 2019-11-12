package edu.cs3500.spreadsheets.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Represents the test suite for Cell object.
 */
public class CellTest {

  @Test (expected = IllegalArgumentException.class)
  public void testConstructor2() {
    Cell c = new Cell(null);
  }

  @Test
  public void testConstructor3() {
    Cell c = new Cell("hello");
    assertEquals("hello", c.getContents());
  }

  @Test (expected = IllegalArgumentException.class)
  public void updateContents1() {
    Cell c = new Cell("hello");
    c.updateContents(null);
  }

  @Test
  public void updateContents2() {
    Cell c = new Cell("hello");
    c.updateContents("Hello");
    assertEquals("Hello", c.getContents());
  }

  @Test
  public void updateContents3() {
    Cell c = new Cell("hello");
    c.updateContents("");
    assertEquals("", c.getContents());
  }

  @Test
  public void getContents() {
    String initial = "Cat";
    Cell c = new Cell(initial);
    assertEquals(initial, c.getContents());
  }
}