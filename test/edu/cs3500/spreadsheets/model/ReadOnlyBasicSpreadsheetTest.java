package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing suite for a read-only model.
 */
public class ReadOnlyBasicSpreadsheetTest {

  //testing for this implementation is very easy-
  //the functionality is entirely derived from a BasicSpreadsheetModel, and only contains 4 methods
  //It does not even contain the methods to edit the model


  @Test
  public void testGetRawValue() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample2.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);


    assertEquals(rom.getRawValue(new Coord(1,1)), model.getRawValue(new Coord(1,1)));

  }

  @Test
  public void testGetComputedValue() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample2.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);
    assertEquals(rom.getComputedValue(new Coord(1,1)), model.getComputedValue(new Coord(1,1)));
  }

  @Test
  public void testFunctions() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample2.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);
    assertEquals(rom.getFunctions(), model.getFunctions());
  }

  @Test
  public void testGetNonEmptyCoordinates() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample2.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);
    assertEquals(rom.getNonEmptyCoordinates(), model.getNonEmptyCoordinates());
  }

  @Test
  public void testGetGraphs() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample2.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);
    assertEquals(rom.getGraphs(), model.getGraphs());
  }

  @Test
  public void testGetGraphTypes() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample2.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);
    assertEquals(rom.getGraphTypes(), model.getGraphTypes());
  }
}
