package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import java.io.StringReader;
import java.util.Scanner;

public final class GraphEnabledWorksheetReader {

  public static SpreadsheetModel
  read(BasicWorksheetBuilder builder, Readable readable) {

    Scanner scan = new Scanner(readable);
    scan.useDelimiter("/GRAPHS/");

    if (!scan.hasNext()) {
      throw new IllegalArgumentException("Scan does not have next");
    }

    SpreadsheetModel model = WorksheetReader.read(builder, new StringReader(scan.next()));

    if(scan.hasNext()) {
      return GraphEnabledWorksheetReader.addGraphs(model, new StringReader(scan.next()));
    }
    else {
      return model;
    }
  }

  private static SpreadsheetModel addGraphs(SpreadsheetModel model, Readable readable) {

    Scanner scan = new Scanner(readable);
    scan.useDelimiter("\n");

    while(scan.hasNext()) {

      Scanner line = new Scanner(scan.next());

      String type = "";
      String name = "";
      String refs = "";

      if(line.hasNext()) {
        type = line.next();
      }
      if(line.hasNext()) {
        name = line.next();
      }
      if(line.hasNext()) {
        refs = line.next();
      }

      if(type.isEmpty() || name.isEmpty() || refs.isEmpty()) {
        throw new IllegalArgumentException("One of type, name, or references are empty");
      }

      //this may throw an exception if any of the parameters are malformed.
      model.addGraph(type, name, refs);

    }
    return model;
  }
}
