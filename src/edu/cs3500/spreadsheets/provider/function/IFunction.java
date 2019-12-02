package edu.cs3500.spreadsheets.provider.function;



/**
 * A Function interface that will apply a function object to the given formula and return an
 * arbitrary type based on what the function is.
 *
 * @param <CellFormula> the formula that the function will be applied to.
 * @param <R>           the return type of the function object.
 */
public interface IFunction<CellFormula, R> {

  /**
   * Applies this function object to a given formula and returns the result.
   *
   * @param formula the formula that the function will be applied to.
   * @return an arbitrary type that the function object returns (boolean, String, or double).
   */
  R apply(CellFormula formula);

}
