package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ReadOnlyBasicSpreadsheetTest {

  //test getRawValue
  //test getComputedValue
  //test getFunctions
  //test getNonEmptyCoordinates

  @Test
  public void testGetRawValue() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);


    assertEquals(rom.getRawValue(new Coord(1,1)), model.getRawValue(new Coord(1,1)));

  }

  @Test
  public void testGetComputedValue() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);
    assertEquals(rom.getComputedValue(new Coord(1,1)), model.getComputedValue(new Coord(1,1)));
  }

  @Test
  public void testFunctions() throws FileNotFoundException {
    SpreadsheetModel model =
        WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader("sample.txt"));
    SpreadsheetReadOnlyModel rom = new ReadOnlyBasicSpreadsheetModel(model);
    assertEquals(rom.getFunctions(), model.getFunctions());
  }


}
