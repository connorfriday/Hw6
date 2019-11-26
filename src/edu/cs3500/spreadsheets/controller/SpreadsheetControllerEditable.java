package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyBasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import edu.cs3500.spreadsheets.view.SpreadsheetEditableGUI;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Represents a controller for an editable spreadsheet implementation.
 */
public class SpreadsheetControllerEditable implements SpreadsheetController, Features {
  private SpreadsheetModel model;
  private SpreadsheetReadOnlyModel rom;
  private SpreadsheetView view;

  @Override
  public void start(SpreadsheetModel model) {
    this.model = model;
    this.rom = new ReadOnlyBasicSpreadsheetModel(model);
    this.view = new SpreadsheetEditableGUI(rom);
    view.setFeatures(this);
    try {
      view.render();
    }
    catch (IOException e) {
      System.out.print("Unable to render view. IO issue.");
    }
  }

  @Override
  public void updateCellValue(Coord coord, String contents) {
    try {
      if(contents.trim().isEmpty()) {
        this.model.clearCell(coord);
      }
      this.model.setCell(contents, coord);
    }
    catch (IllegalArgumentException e) {
      this.view.displayMessage(e.getMessage() );
    }
  }

  @Override
  public void loadFile(String sourceFile) {
    try {
      SpreadsheetModel model =
          WorksheetReader.read(new BasicWorksheetBuilder(), new FileReader(sourceFile));
      this.start(model);
    }
    catch (IOException e) {
      view.displayMessage("Unable to load file: " + sourceFile);
    }
  }

  @Override
  public void saveFile(String destinationFile) {
    try {
      if(!(destinationFile.length() > 4) ||
          !destinationFile.substring(destinationFile.length() - 4).equals(".txt")) {
        view.displayMessage("Destination file must be .txt with name");
        return;
      }
      PrintWriter writer = new PrintWriter(destinationFile);
      SpreadsheetView view = new SpreadsheetTextualView(rom, writer);
      view.render();
      writer.close();
    }
    catch (FileNotFoundException e) {
      view.displayMessage("Destination file not found.");
    }
    catch (IOException e) {
      view.displayMessage("Unable to save file at destination: " + destinationFile);
    }
  }

  @Override
  public void clearCell(Coord coord) {
    this.model.clearCell(coord);
  }

}
