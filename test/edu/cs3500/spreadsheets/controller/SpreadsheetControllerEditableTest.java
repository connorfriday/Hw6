package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.MockSpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test suite to check functionality of a SpreadsheetControllerEditable.
 */
public class SpreadsheetControllerEditableTest {

  @Test
  public void testUpdateCellValue() {
    SpreadsheetControllerEditable cont = new SpreadsheetControllerEditable();
    SpreadsheetModel model = new BasicSpreadsheetModel();
    cont.start(model);
    cont.updateCellValue(new Coord(1, 1), "Hello");
    assertEquals(model.getRawValue(new Coord(1, 1)), "Hello");
    assertEquals(model.getComputedValue(new Coord(1, 1)), "Hello");
    cont.updateCellValue(new Coord(2, 2), "=(SUM 1 1)");
    assertEquals(model.getRawValue(new Coord(2, 2)), "=(SUM 1 1)");
    assertEquals(model.getComputedValue(new Coord(2, 2)), "2.000000");
    cont.updateCellValue(new Coord(2, 2), "");
    assertEquals(model.getRawValue(new Coord(2, 2)), "");
    assertEquals(model.getComputedValue(new Coord(2, 2)), "");
  }

  @Test
  public void testLoadFile() throws FileNotFoundException {
    SpreadsheetControllerEditable cont = new SpreadsheetControllerEditable();
    SpreadsheetModel model = new BasicSpreadsheetModel();
    cont.start(model);
    SpreadsheetModel model2 = cont.loadFile("sample.txt");
    SpreadsheetModel model3 = WorksheetReader.read(new WorksheetReader.BasicWorksheetBuilder(),
        new FileReader("sample.txt"));
    assertEquals(model2.getRawValue(new Coord(1, 1)),
        model3.getRawValue(new Coord(1, 1)));
    assertEquals(model2.getRawValue(new Coord(2, 1)),
        model3.getRawValue(new Coord(2, 1)));
    assertEquals(model2.getRawValue(new Coord(3, 1)),
        model3.getRawValue(new Coord(3, 1)));
    assertEquals(model2.getRawValue(new Coord(4, 1)),
        model3.getRawValue(new Coord(4, 1)));
  }

  @Test
  public void testSaveFile() throws FileNotFoundException {
    SpreadsheetControllerEditable cont = new SpreadsheetControllerEditable();
    SpreadsheetModel model = WorksheetReader.read(new WorksheetReader.BasicWorksheetBuilder(),
        new FileReader("sample.txt"));
    cont.start(model);
    cont.saveFile("blank.txt");
    SpreadsheetModel model2 = WorksheetReader.read(new WorksheetReader.BasicWorksheetBuilder(),
        new FileReader("blank.txt"));
    assertEquals(model.getRawValue(new Coord(1, 1)),
        model2.getRawValue(new Coord(1, 1)));
    assertEquals(model.getRawValue(new Coord(2, 1)),
        model2.getRawValue(new Coord(2, 1)));
    assertEquals(model.getRawValue(new Coord(3, 1)),
        model2.getRawValue(new Coord(3, 1)));
    assertEquals(model.getRawValue(new Coord(4, 1)),
        model2.getRawValue(new Coord(4, 1)));
  }

  @Test
  public void testClearCell() {
    SpreadsheetControllerEditable cont = new SpreadsheetControllerEditable();
    SpreadsheetModel model = new BasicSpreadsheetModel();
    cont.start(model);
    cont.updateCellValue(new Coord(1, 1), "Hello");
    assertEquals(model.getRawValue(new Coord(1, 1)), "Hello");
    assertEquals(model.getComputedValue(new Coord(1, 1)), "Hello");
    cont.clearCell(new Coord(1, 1));
    assertEquals(model.getRawValue(new Coord(1, 1)), "");
    assertEquals(model.getComputedValue(new Coord(1, 1)), "");
  }

  @Test
  public void testWithMock() {
    SpreadsheetControllerEditable cont = new SpreadsheetControllerEditable();
    SpreadsheetModel model = new BasicSpreadsheetModel();
    StringBuilder actualLog = new StringBuilder();
    StringBuilder expectedLog = new StringBuilder();
    SpreadsheetModel mock = new MockSpreadsheetModel(model, actualLog);
    cont.start(mock);
    cont.updateCellValue(new Coord(1, 1), "cat");
    expectedLog.append("Called set cell at A1 with contents cat\n");
    cont.clearCell(new Coord(1, 1));
    expectedLog.append("Called clear cell at A1\n");
    cont.saveFile("blank.txt");
    cont.loadFile("sample.txt");
    assertEquals(expectedLog.toString(), actualLog.toString());
  }
}
