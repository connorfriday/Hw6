package edu.cs3500.spreadsheets.provider.cell;

import edu.cs3500.spreadsheets.provider.function.CellVisitor;

/**
 * Represents a cell in a worksheet. Each cell can be represented as a formula, with either a value,
 * reference, or function.
 *
 * @param <K> the object class that the cell represents.
 */
public interface CellFormula<K> {

  /**
   * Evaluates the contents of a cell.
   *
   * @return a type K (boolean, string, double) based on the cell that is being evaluated.
   */
  K evaluateCell();

  /**
   * Returns the raw contents (not evaluated) that the user inputs into a cell.
   *
   * @return a String with the raw contents that the user put in the cell.
   */
  String getRawContents();


  /**
   * Accepts a cell and passes it to the visitor methods, which handle it based on thee type of cell
   * it is being called on.
   *
   * @param visit the visitor that the cell will be given to.
   * @param <K>   the type that the visitor returns (either a double or a string).
   * @return a type K (boolean, string, double) based on the cell that is being evaluated.
   */
  <K> K accept(CellVisitor<CellFormula, K> visit);

}
