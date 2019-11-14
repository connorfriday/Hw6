package edu.cs3500.spreadsheets.view;

import static org.junit.Assert.*;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;
import java.util.logging.FileHandler;
import org.junit.Test;

public class SpreadsheetTextualViewTest {

  @Test
  public void testOutput() {
    try {
      FileReader reader = new FileReader("sample.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model = WorksheetReader.read(build, reader);
      StringBuilder expectedOut = new StringBuilder();
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model, out);
      view.render();
      expectedOut.append("A1 =B1\n"
          + "B1 =C1\n"
          + "C1 10\n"
          + "D1 =(CONCAT 1 2 3)\n"
          + "Z4 98234\n"
          + "A20 George\n");
      assertEquals(out.toString(), expectedOut.toString());
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testBuiltModelsEqual() {
    try {
      FileReader reader = new FileReader("sample.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model1 = WorksheetReader.read(build, reader);
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model1, out);
      view.render();
      SpreadsheetModel model2 = WorksheetReader.read(build, new StringReader(out.toString()));
      Set<Coord> model1Cells = model1.getNonEmptyCoordinates();
      Set<Coord> model2Cells = model2.getNonEmptyCoordinates();
      assertEquals(model1Cells, model2Cells);
      for (Coord c : model1Cells) {
        assertEquals(model1.getRawValue(c), model2.getRawValue(c));
      }
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testOutputWithErrors() {
    try {
      FileReader reader = new FileReader("errorTest.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model = WorksheetReader.read(build, reader);
      StringBuilder expectedOut = new StringBuilder();
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model, out);
      view.render();
      expectedOut.append("A1 =(SUM SUM)\n"
          + "A41 =(SUM cat mouse)\n"
          + "AA1 =(SUM (SUM (SUM CONCAT)))\n");
      assertEquals(out.toString(), expectedOut.toString());
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testBuiltModelsEqualWithErrors() {
    try {
      FileReader reader = new FileReader("errorTest.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model1 = WorksheetReader.read(build, reader);
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model1, out);
      view.render();
      SpreadsheetModel model2 = WorksheetReader.read(build, new StringReader(out.toString()));
      Set<Coord> model1Cells = model1.getNonEmptyCoordinates();
      Set<Coord> model2Cells = model2.getNonEmptyCoordinates();
      assertEquals(model1Cells, model2Cells);
      for (Coord c : model1Cells) {
        assertEquals(model1.getRawValue(c), model2.getRawValue(c));
      }
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testOutputEveryPossibleValue() {
    try {
      FileReader reader = new FileReader("everyPossibleValueType.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model = WorksheetReader.read(build, reader);
      StringBuilder expectedOut = new StringBuilder();
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model, out);
      view.render();
      expectedOut.append("A2 false\n"
          + "A1 true\n"
          + "A4 cat\n"
          + "A3 1\n");
      assertEquals(out.toString(), expectedOut.toString());
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testBuiltModelsEveryPossibleValue() {
    try {
      FileReader reader = new FileReader("everyPossibleValueType.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model1 = WorksheetReader.read(build, reader);
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model1, out);
      view.render();
      SpreadsheetModel model2 = WorksheetReader.read(build, new StringReader(out.toString()));
      Set<Coord> model1Cells = model1.getNonEmptyCoordinates();
      Set<Coord> model2Cells = model2.getNonEmptyCoordinates();
      assertEquals(model1Cells, model2Cells);
      for (Coord c : model1Cells) {
        assertEquals(model1.getRawValue(c), model2.getRawValue(c));
      }
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testOutputFunctionsAndRefs() {
    try {
      FileReader reader = new FileReader("functionsAndRefs.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model = WorksheetReader.read(build, reader);
      StringBuilder expectedOut = new StringBuilder();
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model, out);
      view.render();
      expectedOut.append("A2 2\n"
          + "A1 1\n"
          + "B2 4\n"
          + "C3 =A1\n"
          + "B1 3\n"
          + "C2 =(SUM A1 1)\n"
          + "C1 =(SUM A1 B1)\n"
          + "D2 =(SUM A1:B2 1 A1)\n"
          + "D1 =(SUM A1:B2)\n"
          + "E2 mouse\n"
          + "E1 cat\n"
          + "F2 =(CONCAT E1:E2)\n"
          + "G3 =(< 1 43)\n"
          + "F1 =(CONCAT E1 E2 bird)\n"
          + "G2 =(< A1 43)\n"
          + "G1 =(< A1 B2)\n"
          + "H2 =(PRODUCT A1 1)\n"
          + "H1 =(PRODUCT A1 B1)\n"
          + "H33 =A1\n"
          + "I2 =(PRODUCT A1:B2 1 A1)\n"
          + "I1 =(PRODUCT A1:B2)\n");
      assertEquals(out.toString(), expectedOut.toString());
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testBuiltModelsFunctionsAndRefs() {
    try {
      FileReader reader = new FileReader("functionsAndRefs.txt");
      WorksheetBuilder<BasicSpreadsheetModel> build = new BasicWorksheetBuilder();
      SpreadsheetModel model1 = WorksheetReader.read(build, reader);
      StringBuilder out = new StringBuilder();
      SpreadsheetView view = new SpreadsheetTextualView(model1, out);
      view.render();
      SpreadsheetModel model2 = WorksheetReader.read(build, new StringReader(out.toString()));
      Set<Coord> model1Cells = model1.getNonEmptyCoordinates();
      Set<Coord> model2Cells = model2.getNonEmptyCoordinates();
      assertEquals(model1Cells, model2Cells);
      for (Coord c : model1Cells) {
        assertEquals(model1.getRawValue(c), model2.getRawValue(c));
      }
    } catch (FileNotFoundException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }

}