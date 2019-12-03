package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.provider.model.Worksheet;
import edu.cs3500.spreadsheets.provider.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.provider.view.BasicWorksheetView;

/**
 * Represents a controller for an editable spreadsheet implementation.
 */
public class WorksheetControllerEditable implements Features, WorksheetController {
  private Worksheet model;


  @Override
  public void start(Worksheet model) {
    this.model = model;
    BasicWorksheetReadOnlyModel rom = new BasicWorksheetReadOnlyModel(model);
    BasicWorksheetView view = new BasicWorksheetEditorView(rom);
    view.render();

  }

  @Override
  public void updateCellValue(Coord coord, String contents) {
      if (contents.trim().isEmpty()) {
        this.model.removeCell(coord);
      }
      this.model.editCell(contents, coord);

  }

  @Override
  public SpreadsheetModel loadFile(String sourceFile) {
   throw new UnsupportedOperationException("Cannot load file in this view.");
  }

  @Override
  public void saveFile(String destinationFile) {
    throw new UnsupportedOperationException("cannot save file in this view.");
  }

  @Override
  public void clearCell(Coord coord) {
    this.model.removeCell(coord);
  }
}
