package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.model.BasicWorksheetReadOnlyModel;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the view for a worksheet which saves a given model to a new text file.
 */
public class BasicWorksheetSaveView implements BasicWorksheetView {
  private final BasicWorksheetReadOnlyModel model;
  private PrintWriter appendable;

  /**
   * Constructs a new view of the given model, which saves the model in a readable text format.
   *
   * @param model      the model of the worksheet to be saved
   * @param appendable the appendable being added to
   */
  public BasicWorksheetSaveView(BasicWorksheetReadOnlyModel model, PrintWriter appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() {
    // adds the model (as a line-separated-String of spreadsheet cell raw contents) to a file
    this.appendable.append(this.toString());
    // writes the file and closes it
    this.appendable.close();
  }

  @Override
  public void refresh() {
    this.render();
  }

  @Override
  public String toString() {
    String rawContents = "";
    HashMap<Coord, CellFormula> modelCells = model.getCells();
    for (Map.Entry<Coord, CellFormula> cell : modelCells.entrySet()) {
      String coordinate = cell.getKey().toString();
      rawContents += coordinate + " =" + cell.getValue().getRawContents() + "\n";
    }
    return rawContents;
  }
}
