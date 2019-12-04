package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.cell.CellBlank;
import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.HashMap;
import java.util.Set;

/**
 * Adapter class to take a Spreadsheet model and turn it into a Worksheet.
 */
public class BasicWorksheetModel implements Worksheet {
  private final SpreadsheetModel model;

  public BasicWorksheetModel(SpreadsheetModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Can't be null.");
    }
    this.model = model;
  }

  public BasicWorksheetModel() {
    model = new BasicSpreadsheetModel();
  }

  @Override
  public void editCell(String input, Coord location) {
    model.setCell("", location);
  }

  @Override
  public CellFormula getCellAt(Coord location) {
    //need to parse this, and then use it as a visitor
    String contents = model.getRawValue(location);
    if(contents.isEmpty()) {
      return new CellBlank("");
    }

    boolean hadEquals = false;
    if (contents.charAt(0) == '=') {
      hadEquals = true;
      contents = contents.substring(1);
    }
    Sexp sexp = Parser.parse(contents);
    return sexp.accept(new SexpToCellFormulaVisitor(location, this));
  }

  @Override
  public HashMap<Coord, CellFormula> getCells() {
    // this method is never used by the views given so we chose not to implement it
    throw new UnsupportedOperationException("Not supported.");
  }

  @Override
  public int getNumRows() {
    Set<Coord> set = model.getNonEmptyCoordinates();
    int max = 1;
    for (Coord c : set) {
      if (c.row > max) {
        max = c.row;
      }
    }
    return max;
  }

  @Override
  public int getNumCols() {
    Set<Coord> set = model.getNonEmptyCoordinates();
    int max = 1;
    for (Coord c : set) {
      if (c.col > max) {
        max = c.col;
      }
    }
    return max;
  }

  @Override
  public void removeCell(Coord location) {
    model.clearCell(location);
  }
}
