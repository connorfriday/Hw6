package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.SpreadsheetReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 * The view that is able to support graphing data.
 */
public class SpreadsheetEditableGraph extends SpreadsheetEditableGUI {

  private JPanel graphPanel;
  private SpreadsheetReadOnlyModel model;

  /**
   * Constructs a spreadsheet GUI with graphing capabilities.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetEditableGraph(SpreadsheetReadOnlyModel model) {
    super(model);
    this.model = model;

    setGraphPanel();

  }

  private void setGraphPanel() {

    graphPanel = new JPanel();
    graphPanel.setFocusable(false);

    JButton deleteGraphButton = new JButton("Delete a Graph");
    deleteGraphButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Set<String> availableGraphs = model.getGraphs().keySet();
        if(availableGraphs.size() !=0) {
          String graph = JOptionPane
              .showInputDialog(null, "Choose a graph to delete", "Delete graph",
                  JOptionPane.DEFAULT_OPTION, null, availableGraphs.toArray(),
                  availableGraphs.toArray()[0]).toString();
          features.removeGraph(graph);
        }
        else {
          JOptionPane.showMessageDialog(null, "Sorry, there are no graphs to delete.");
        }
      }
    });



    JButton createGraphButton = new JButton("Create a Graph");
    createGraphButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //todo: require strings be non-empty
        String type = JOptionPane.showInputDialog("Choose a type of graph", model.getGraphTypes());
        String name = JOptionPane.showInputDialog("Insert a name for your graph (No spaces)");
        String refs = JOptionPane.showInputDialog(("Insert a 2-col or 2-row reference to graph"));

        features.addGraph(type, name, refs);
        if (model.getGraphs().containsKey(name)) {
          SpreadsheetEditableGraph.viewGraph(name, model);
        }
      }
    });


    JButton viewGraphButton = new JButton("View a Graph");
    viewGraphButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Set<String> availableGraphs = model.getGraphs().keySet();
        if(availableGraphs.size() != 0) {
          String graph = JOptionPane.showInputDialog(null, "Choose a graph to view", "View graph",
              JOptionPane.DEFAULT_OPTION, null, availableGraphs.toArray(),
              availableGraphs.toArray()[0]).toString();
          if (model.getGraphs().containsKey(graph)) {
            SpreadsheetEditableGraph.viewGraph(graph, model);
          }
        }
        else {
          JOptionPane.showMessageDialog(null, "Sorry, there are no graphs to view.");
        }

      }
    });

    graphPanel.add(viewGraphButton);
    graphPanel.add(createGraphButton);
    graphPanel.add(deleteGraphButton);

    this.add(graphPanel, BorderLayout.PAGE_END);
  }

  private static void viewGraph(String name, SpreadsheetReadOnlyModel model) {
    JFreeChart chart = model.getGraphs().get(name).getChart(model);
    JPanel chartPanel = new ChartPanel(chart);

    JFrame graphFrame = new JFrame();
    graphFrame.add(chartPanel);
    graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    graphFrame.setSize(new Dimension(500,500));
    graphFrame.setVisible(true);
  }

}
