package edu.cs3500.spreadsheets.provider.function;


import edu.cs3500.spreadsheets.provider.cell.CellBlank;
import edu.cs3500.spreadsheets.provider.cell.CellBoolean;
import edu.cs3500.spreadsheets.provider.cell.CellDouble;
import edu.cs3500.spreadsheets.provider.cell.CellFunction;
import edu.cs3500.spreadsheets.provider.cell.CellReference;
import edu.cs3500.spreadsheets.provider.cell.CellString;

/**
 * A visitor interface to handle evaluating different types of cells with function objects.
 *
 * @param <T> the input type to the visitor, which will be a different instance of a CellFormula.
 * @param <R> the output type of the visitor, which is an arbitrary type.
 */
public interface CellVisitor<T, R> extends IFunction<T, R> {

  /**
   * A method for visiting CellDouble and determining what to do based on what function we are
   * handling.
   *
   * @param d the CellDouble that this method will be visiting to handle its value.
   * @return an arbitrary type R that will either be a Double, String, or Boolean
   */
  R visitDouble(CellDouble d);

  /**
   * A method for visiting CellBoolean and determining what to do based on what function we are
   * handling.
   *
   * @param b the CellBoolean that this method will be visiting to handle it's value.
   * @return an arbitrary type R that will either be a Double, String, or Boolean
   */
  R visitBoolean(CellBoolean b);

  /**
   * A method for visiting CellFunction and determining what to do based on what function we are
   * handling.
   *
   * @param f the CellFunction that this method will be visiting to handle it's value.
   * @return an arbitrary type R that will either be a Double, String, or Boolean
   */
  R visitFunction(CellFunction f);

  /**
   * A method for visiting CellReference and determining that to do based on what function we are
   * handling.
   *
   * @param r the CellReference that this method will be visiting to handle it's value.
   * @return an arbitrary type R that will either be a Double, String, or Boolean
   */
  R visitReference(CellReference r);

  /**
   * A method for visiting CellString and determining that to do based on what function we are
   * handling.
   *
   * @param s the CellString that this method will be visiting to handle it's value.
   * @return an arbitrary type R that will either be a Double, String, or Boolean
   */
  R visitString(CellString s);

  /**
   * A method for visitng CellBlank and determining how to handle a blank cell.
   *
   * @param b the BlankCell
   * @return an arbitrary type R that will either be a Double, String, or Boolean
   */
  R visitBlank(CellBlank b);

}
