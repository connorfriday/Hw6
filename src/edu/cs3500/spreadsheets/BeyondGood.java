package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import edu.cs3500.spreadsheets.view.SpreadsheetGUI;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main2(String[] args) {
    String expected = "Expected: -in filename -eval cellName";
    if (args.length != 4) {
      System.out.println("Expected 4 command-line arguments." + expected);
      return;



    }
    if (!args[0].equals("-in") || !args[2].equals("-eval")) {
      System.out.println("Improper command-line arguments." + expected);
      return;
    }

    try {
      Readable file = new FileReader(args[1]);

      try {
        BasicWorksheetBuilder builder = new BasicWorksheetBuilder();
        BasicSpreadsheetModel model = WorksheetReader.read(builder, file);

        String errors = builder.errorMessages.toString();
        if (!errors.equals("")) {
          System.out.print(errors);
          return;
        }
        String s = args[3];
        StringBuilder letters = new StringBuilder();
        StringBuilder numbers = new StringBuilder();
        boolean onLetters = true;

        for (int i = 0; i < s.length(); i++) {
          char c = s.charAt(i);
          if (onLetters) {
            if (Character.isLetter(c)) {
              letters.append(c);
            }
            else if (Character.isDigit(c)) {
              numbers.append(c);
              onLetters = false;
            }
            else {
              System.out.println("Malformed cellname. Cannot process");
              return;
            }
          }
          else if (Character.isDigit(c)) {
            numbers.append(c);
          }
          else {
            System.out.println("Malformed cellname. Cannot process");
          }
        }

        int col = Coord.colNameToIndex(letters.toString());
        int row = Integer.parseInt(numbers.toString());

        try {
          String result = model.getComputedValue(new Coord(col, row));
          System.out.print(result);
        }
        catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
          return;
        }
      }
      catch (IllegalStateException e) {
        System.out.println("Given file is malformatted");
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }
  }

  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        throw new InvalidCommandException("Expected command-line args to be non-empty.");
      }

      SpreadsheetModel model;
      String primaryCommand = args[0];
      if (primaryCommand.equals("-in")) {
        model = buildModel(args);
      } else if (primaryCommand.equals("-gui")) {
        model = new BasicSpreadsheetModel();
        gui(model);
        return;
      } else {
        throw new InvalidCommandException("Invalid first command.");
      }

      if (args.length >= 3) {
        switch (args[2]) {
          case "-eval":
            System.out.print(evaluate(model, args));
          case "-save":
            write(model, args);
            return;
          case "-gui":
            gui(model);
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


  private static void gui(SpreadsheetModel model) throws InvalidCommandException {
    try {
      SpreadsheetView view = new SpreadsheetGUI(model);
      view.render();
    }
    catch (IOException e) {
      throw new InvalidCommandException("Issue to read from input or write to output");
    }
  }

  private static void write(SpreadsheetModel model, String[] args) throws InvalidCommandException {
    if (args.length != 4) {
      throw new InvalidCommandException("Need 4 arguments to save to another file.");
    }
    try {
      PrintWriter writer = new PrintWriter(args[3]);
      SpreadsheetView view = new SpreadsheetTextualView(model, writer);
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

  private static SpreadsheetModel buildModel(String[] args) throws InvalidCommandException{
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

  private static class InvalidCommandException extends Exception {
    InvalidCommandException(String message) {
      super(message);
    }
  }
}
