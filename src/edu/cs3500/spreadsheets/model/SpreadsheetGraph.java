package edu.cs3500.spreadsheets.model;

import java.util.List;
import java.util.Map;
import org.jfree.chart.JFreeChart;

/**
 * Represents a graph to be used in a spreadsheet.
 */
public interface SpreadsheetGraph {

  String getName();

  String getType();

  Map<Coord, Coord> getValues();

  String getRefs();

  JFreeChart getChart(SpreadsheetReadOnlyModel model);
}
