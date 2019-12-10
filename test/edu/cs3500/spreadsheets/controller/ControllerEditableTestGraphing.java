package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetGraph;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * A test suite for the addition of graphing features to the controller.
 */
public class ControllerEditableTestGraphing {

  @Test
  public void testRemoveGraphSuccess() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    SpreadsheetControllerEditable controller = new SpreadsheetControllerEditable();
    model.addGraph("LINE", "Graph1", "A1:B2");
    controller.start(model);
    controller.removeGraph("Graph1");
    assertTrue(model.getGraphs().keySet().isEmpty());
  }

  @Test
  public void testRemoveGraphSuccess2Graphs() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    SpreadsheetControllerEditable controller = new SpreadsheetControllerEditable();
    model.addGraph("LINE", "Graph1", "A1:B2");
    model.addGraph("LINE", "Graph2", "A1:B2");
    controller.start(model);
    assertTrue(model.getGraphs().containsKey("Graph1"));
    controller.removeGraph("Graph1");
    assertEquals(model.getGraphs().keySet().size(), 1);
    assertFalse(model.getGraphs().containsKey("Graph1"));
  }

  @Test
  public void testRemoveGraphSuccess3Graphs() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    SpreadsheetControllerEditable controller = new SpreadsheetControllerEditable();
    model.addGraph("LINE", "Graph1", "A1:B2");
    model.addGraph("LINE", "Graph2", "A1:B2");
    model.addGraph("LINE", "Graph3", "A1:B2");
    controller.start(model);
    assertTrue(model.getGraphs().containsKey("Graph3"));
    controller.removeGraph("Graph3");
    assertEquals(model.getGraphs().keySet().size(), 2);
    assertFalse(model.getGraphs().containsKey("Graph3"));
  }

  @Test
  public void testRemoveGraphGraphNotThere() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    SpreadsheetControllerEditable controller = new SpreadsheetControllerEditable();
    model.addGraph("LINE", "Graph1", "A1:B2");
    model.addGraph("LINE", "Graph2", "A1:B2");
    model.addGraph("LINE", "Graph3", "A1:B2");
    controller.start(model);
    assertTrue(model.getGraphs().containsKey("Graph3"));
    controller.removeGraph("Graph4");
    assertEquals(model.getGraphs().keySet().size(), 3);
    assertTrue(model.getGraphs().containsKey("Graph3"));
  }

  @Test
  public void testAddGraphSuccess() {
    SpreadsheetModel model = new BasicSpreadsheetModel();
    SpreadsheetControllerEditable controller = new SpreadsheetControllerEditable();
    controller.start(model);
    controller.addGraph("LINE", "Graph1", "A1:B2");
    SpreadsheetGraph g = model.getGraphs().get("Graph1");
    assertEquals(g.getName(), "Graph1");
    assertEquals(g.getRefs(), "A1:B2");
  }
}
