package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.IFeatures;
import edu.cs3500.spreadsheets.provider.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.provider.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.provider.model.Worksheet;
import edu.cs3500.spreadsheets.provider.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.provider.view.BasicWorksheetView;

/**
 * Represents a controller for an editable spreadsheet implementation.
 */
public class WorksheetControllerEditable implements IFeatures, SpreadsheetController {
  private SpreadsheetModel model;
  private Worksheet worksheetModel;
  private BasicWorksheetEditorView view;


  @Override
  public void start(SpreadsheetModel model) {
    this.model = model;
    worksheetModel = new BasicWorksheetModel(model);
    BasicWorksheetReadOnlyModel rom = new BasicWorksheetReadOnlyModel(worksheetModel);
    view = new BasicWorksheetEditorView(rom);
    view.addIFeatures(this);
    view.render();
  }

  @Override
  public void run() {
    //redundant with SpreadsheetController start() method
    this.start(this.model);
  }

  @Override
  public void acceptCellEdit(Coord location, String rawContents) {
    if (rawContents.trim().isEmpty()) {
      this.worksheetModel.removeCell(location);
    }
    else {
      this.worksheetModel.editCell(rawContents, location);
    }
    view.refresh();
  }

  @Override
  public void rejectCellEdit() {
    view.setTextbox();
  }

  @Override
  public void moveHighlightedCell(String arrowKey) {
    // extra credit feature. Not implemented.
  }

  @Override
  public void deleteCellContents(Coord location) {
    this.worksheetModel.removeCell(location);
    view.refresh();
  }
}
