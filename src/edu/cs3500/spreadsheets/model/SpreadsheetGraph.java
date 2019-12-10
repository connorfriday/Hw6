package edu.cs3500.spreadsheets.model;

import java.util.Map;
import org.jfree.chart.JFreeChart;

/**
 * Represents a graph to be used in a spreadsheet.
 */
public interface SpreadsheetGraph {

  /**
   * Gets the name of the graph
   * @return the name of the graph
   */
  String getName();

  /**
   * Gets the type of the graph
   * @return the type of the graph
   */
  String getType();

  /**
   * Gets the x and y value pairs as a map
   * @return map of x and y values
   */
  Map<Coord, Coord> getValues();

  /**
   * Gets the cells referenced
   * @return string representation of the cells referenced
   */
  String getRefs();

  /**
   * Renders the chart as a JFreeChart
   * @param model the model to render the data in the graph
   * @return JFreeChart representation of the graph
   */
  JFreeChart getChart(SpreadsheetReadOnlyModel model);
}
