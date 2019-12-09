package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.List;

/**
 * An interface represnting a function that can be done in a spreadsheet.
 */
public interface SpreadsheetFunction {

  /**
   * Performs the function.
   * @param list the given arguments as Sexp
   * @param visited any coords visited in current eval job
   * @return the evaluated value the function represented as a string
   */
  String evaluate(List<Sexp> list, List<Coord> visited);

  /**
   * Gets the name of a function for use as a key in a map.
   * @return a String representing the function's name
   */
  String getName();

  /**
   * Determines whether the function can support an unbounded number of arguments.
   * @return a boolean indicating if the function can accept an unbounded number of args
   */
  boolean supportMultiArg();
}
