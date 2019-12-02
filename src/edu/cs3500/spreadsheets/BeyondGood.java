package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.controller.SpreadsheetController;
import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyBasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import edu.cs3500.spreadsheets.provider.model.SpreadsheetToWorksheetAdapter;
import edu.cs3500.spreadsheets.provider.model.Worksheet;
import edu.cs3500.spreadsheets.provider.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.controller.SpreadsheetControllerEditable;
import edu.cs3500.spreadsheets.view.SpreadsheetGUI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The main class for our program.
 */
public class BeyondGood {

  /**
   * Our main method that runs our spreadsheet app.
   * @param args that represent the file to be read from if any, the type of view to use,
   *             and the destination file, if any
   */
  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        throw new InvalidCommandException("Expected command-line args to be non-empty.");
      }

      SpreadsheetModel model;
      String primaryCommand = args[0];

      if (primaryCommand.equals("-in")) {
        model = buildModel(args);
      }
      else if (primaryCommand.equals("-gui")) {
        model = new BasicSpreadsheetModel();
        gui(model);
        return;
      }
      else if (primaryCommand.equals("-provider")) {
        model = new BasicSpreadsheetModel();
        provider(model);
        return;
      }
      else if (primaryCommand.equals("-edit")) {
        model = new BasicSpreadsheetModel();
        edit(model);
        return;
      }
      else {
        throw new InvalidCommandException("Invalid first command.");
      }

      if (args.length >= 3) {
        switch (args[2]) {
          case "-eval":
            System.out.print(evaluate(model, args));
            return;
          case "-save":
            write(model, args);
            return;
          case "-gui":
            gui(model);
            return;
          case "-edit":
            edit(model);
            return;
          case "-provider":
            provider(model);
            return;
          default:
            throw new InvalidCommandException("Unrecognized command. Supports -eval, -save, -gui");
        }
      } else {
        throw new InvalidCommandException("Invalid command-line arguments. "
            + "If first is not -gui, expected at least 3 arguments.");
      }
    }
    catch (InvalidCommandException e) {
      System.out.print(e.getMessage());
    }
  }

  private static void provider(SpreadsheetModel model) {
    Worksheet w = new SpreadsheetToWorksheetAdapter(model);
    BasicWorksheetReadOnlyModel row = new BasicWorksheetReadOnlyModel(w);
    BasicWorksheetView view = new BasicWorksheetEditorView(row);

  }

  // given a model, creates an editable GUI view and renders the veiw
  private static void edit(SpreadsheetModel model) {
    SpreadsheetController c = new SpreadsheetControllerEditable();
    c.start(model);
  }

  // given a model, creates a GUI view and renders the view
  // throws an exception if unable to render
  private static void gui(SpreadsheetModel model) throws InvalidCommandException {
    try {
      SpreadsheetView view = new SpreadsheetGUI(new ReadOnlyBasicSpreadsheetModel(model));
      view.render();
    }
    catch (IOException e) {
      throw new InvalidCommandException("Issue to read from input or write to output");
    }
  }

  // writes the model to a given file using the textual view
  // throws an exception with a detailed message if unable to do so
  private static void write(SpreadsheetModel model, String[] args) throws InvalidCommandException {
    if (args.length != 4) {
      throw new InvalidCommandException("Need 4 arguments to save to another file.");
    }
    try {
      PrintWriter writer = new PrintWriter(args[3]);
      SpreadsheetView view = new SpreadsheetTextualView(new ReadOnlyBasicSpreadsheetModel(model),
          writer);
      view.render();
      writer.close();
    }
    catch (FileNotFoundException e) {
      throw new InvalidCommandException("Unable to save to file. File not found.");
    }
    catch (IOException e) {
      throw new InvalidCommandException("Unable to write to file.");
    }
  }

  // gets the computed value of a specified cell from the given model
  // throws an exception with a detailed message if unable to do so
  private static String evaluate(SpreadsheetModel model, String[] args)
      throws InvalidCommandException {
    if (args.length != 4) {
      throw new InvalidCommandException("Need 4 arguments to evaluate a cell");
    }
    try {
      Coord c = new Coord(args[3]);
      return model.getComputedValue(c);
    }
    catch (IllegalArgumentException e) {
      throw new InvalidCommandException(e.getMessage());
    }
  }

  // builds a model using the given arguments array by reading from a given file
  // throws an exception with a detailed message if unable to do so
  private static SpreadsheetModel buildModel(String[] args) throws InvalidCommandException {
    try {
      if (args.length < 2) {
        throw new InvalidCommandException("Expected at least 2 arguments.");
      }
      Readable file = new FileReader(args[1]);
      BasicWorksheetBuilder builder = new BasicWorksheetBuilder();
      String errors = builder.errorMessages.toString();
      if (!errors.equals("")) {
        throw new InvalidCommandException(errors);
      }
      return WorksheetReader.read(builder, file);
    }
    catch (FileNotFoundException e) {
      throw new InvalidCommandException("Unable to read file.");
    }
    catch (IllegalStateException e) {
      throw new InvalidCommandException("Given file was malformed.");
    }
  }

  // a custom exception to handle any errors related to issuing commands to the application
  private static class InvalidCommandException extends Exception {
    InvalidCommandException(String message) {
      super(message);
    }
  }
}
