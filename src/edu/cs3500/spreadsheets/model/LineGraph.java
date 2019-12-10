package edu.cs3500.spreadsheets.model;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Represents a line graph for a spreadsheet.
 */
public class LineGraph implements SpreadsheetGraph {

  private final String name;
  private Coord topLeft;
  private Coord bottomRight;
  private boolean columnOriented;
  private final String refs;

  /**
   * Constructs a new graph.
   * @param name the name of the graph
   * @param refs the references of the graph
   */
  LineGraph(String name, String refs) {
    if (name == null || refs == null) {
      throw new IllegalArgumentException("Name or Refs is null");
    }
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (name.contains(" ")) {
      throw new IllegalArgumentException("Name cannot contain spaces");
    }
    this.refs = refs;
    this.name = name;
    columnOriented = false;
    setLeftAndRight(refs);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getType() {
    return "LINE";
  }

  @Override
  public Map<Coord, Coord> getValues() {
    Map<Coord, Coord> map = new HashMap<>();
    if(columnOriented) {
      for(int x = topLeft.row; x <= bottomRight.row; x++) {
        map.put(new Coord(topLeft.col, x), new Coord(bottomRight.col, x));
      }
    }
    else {
      for(int x = topLeft.col; x <= bottomRight.col; x++) {
        map.put(new Coord(x, topLeft.row), new Coord(x, bottomRight.row));
      }
    }
    return map;
  }

  @Override
  public String getRefs() {
    return this.refs;
  }

  @Override
  public JPanel getChart(SpreadsheetReadOnlyModel model) {
   XYDataset dataset = createDataset(model);
   JFreeChart chart = ChartFactory.createXYLineChart(
       this.name, "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, true, true, false);
   JPanel chartPanel = new ChartPanel(chart);
   return chartPanel;
  }

  private XYDataset createDataset(SpreadsheetReadOnlyModel model) {
    XYSeriesCollection dataset = new XYSeriesCollection();

    XYSeries series = new XYSeries("Series");

    Map<Coord, Coord> pointSet = this.getValues();
    for(Coord c : pointSet.keySet()) {
      double x = Double.parseDouble(model.getComputedValue(c));
      double y = Double.parseDouble(model.getComputedValue(pointSet.get(c)));
      series.add(x, y);
    }
    dataset.addSeries(series);
    return dataset;
  }

  private void setLeftAndRight(String refs) {
    Scanner scan = new Scanner(new StringReader(refs));
    scan.useDelimiter(":");
    if (scan.hasNext()) {
      topLeft = new Coord(scan.next());
    }
    if (scan.hasNext()) {
      bottomRight = new Coord(scan.next());
    }
    if (topLeft == null || bottomRight == null) {
      throw new IllegalArgumentException("Need rectangular reference for a graph.");
    }

    if (bottomRight.col - topLeft.col != 1 && bottomRight.row - topLeft.row != 1) {
      throw new IllegalArgumentException("Need a dimension of two for rectangular"
          + " reference to create a line graph.");
    }

    if (bottomRight.col - topLeft.col == 1) {
      columnOriented = true;
    }
  }
}
