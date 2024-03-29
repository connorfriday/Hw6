package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import java.io.StringReader;
import java.util.Scanner;

/**
 * A worksheet reader that adds graphs to a model when loading from a file.
 */
public final class GraphEnabledWorksheetReader {

  /**
   * Creates a model using a worksheet builder and a readable with graphs.
   *
   * @param builder  the builder to construct the sheet
   * @param readable the readable containing information
   * @return the model that the method builds
   */
  public static SpreadsheetModel
  read(BasicWorksheetBuilder builder, Readable readable) {

    Scanner scan = new Scanner(readable);
    scan.useDelimiter("/GRAPHS/");

    if (!scan.hasNext()) {
      throw new IllegalArgumentException("Scan does not have next");
    }

    SpreadsheetModel model = WorksheetReader.read(builder, new StringReader(scan.next()));

    if (scan.hasNext()) {
      return GraphEnabledWorksheetReader.addGraphs(model, new StringReader(scan.next()));
    } else {
      return model;
    }
  }

  private static SpreadsheetModel addGraphs(SpreadsheetModel model, Readable readable) {

    Scanner scan = new Scanner(readable);
    scan.useDelimiter("\n");

    while (scan.hasNext()) {

      Scanner line = new Scanner(scan.next());

      String type = "";
      String name = "";
      String refs = "";

      if (line.hasNext()) {
        type = line.next();
      }
      if (line.hasNext()) {
        name = line.next();
      }
      if (line.hasNext()) {
        refs = line.next();
      }

      if (type.isEmpty() || name.isEmpty() || refs.isEmpty()) {
        throw new IllegalArgumentException("One of type, name, or references are empty");
      }

      //this may throw an exception if any of the parameters are malformed.
      model.addGraph(type, name, refs);

    }
    return model;
  }
}
