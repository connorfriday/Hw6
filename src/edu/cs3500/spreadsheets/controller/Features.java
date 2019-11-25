package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;

public interface Features {
  void updateCellValue(Coord coord, String contents);

  void loadFile(String sourceFile);

  void saveFile(String destinationFile);

  void clearContents(Coord coord);
}
