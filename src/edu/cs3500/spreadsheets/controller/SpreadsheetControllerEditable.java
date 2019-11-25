package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.BasicWorksheetBuilder;
import edu.cs3500.spreadsheets.view.SpreadsheetEditableGUI;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SpreadsheetControllerEditable implements SpreadsheetController {
  private SpreadsheetModel model;
  private SpreadsheetView view;

  @Override
  public void start(SpreadsheetModel model) {
    this.model = model;
    this.view = new SpreadsheetEditableGUI(model);
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
      PrintWriter writer = new PrintWriter(destinationFile);
      SpreadsheetView view = new SpreadsheetTextualView(model, writer);
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
  public void clearContents(Coord coord) {
    this.model.clearCell(coord);
  }
}
