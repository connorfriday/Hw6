package edu.cs3500.spreadsheets.sexp;

import static org.junit.Assert.assertEquals;

import edu.cs3500.spreadsheets.model.Coord;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * A test suite for the visitor that gets coordinate references out of an SExpressions.
 */
public class GetCoordReferencesTest {


  @Test
  public void visitBoolean() {
    GetCoordReferences g = new GetCoordReferences();
    assertEquals(new ArrayList<>(), g.visitBoolean(true));
  }


  @Test
  public void visitNumber() {
    GetCoordReferences g = new GetCoordReferences();
    assertEquals(new ArrayList<>(), g.visitNumber(10));
  }

  @Test
  public void visitSList() {
    GetCoordReferences g = new GetCoordReferences();
    List<Coord> l = new ArrayList<>();
    l.add(new Coord(1, 1));
    List<Sexp> l2 = new ArrayList<>();
    SSymbol s = new SSymbol("A1");
    l2.add(s);
    assertEquals(l, g.visitSList(l2));
  }

  @Test
  public void visitSList2() {
    GetCoordReferences g = new GetCoordReferences();
    List<Coord> l = new ArrayList<>();
    l.add(new Coord(1, 1));
    l.add(new Coord(1, 2));
    List<Sexp> l2 = new ArrayList<>();
    SSymbol s = new SSymbol("A1");
    l2.add(s);
    SSymbol s2 = new SSymbol("A2");
    l2.add(s2);
    assertEquals(l, g.visitSList(l2));
  }


  @Test
  public void visitSymbol() {
    GetCoordReferences g = new GetCoordReferences();
    List<Coord> l = new ArrayList<>();
    l.add(new Coord(1, 1));
    assertEquals(l, g.visitSymbol("A1"));
  }

  @Test
  public void visitSymbol2() {
    GetCoordReferences g = new GetCoordReferences();
    List<Coord> l = new ArrayList<>();
    l.add(new Coord(1, 1));
    l.add(new Coord(1,2));
    assertEquals(l, g.visitSymbol("A1:A2"));
  }


  @Test
  public void visitString() {
    GetCoordReferences g = new GetCoordReferences();
    assertEquals(new ArrayList<>(), g.visitString("true"));
  }
}