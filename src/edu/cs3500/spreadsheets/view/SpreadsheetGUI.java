package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Represents a scrollable GUI to view a Spreadsheet
 */
public class SpreadsheetGUI extends JFrame implements SpreadsheetView, ActionListener {

  private SpreadsheetModel model;
  private int CELLSTOBESHOWNX = 20;
  private int CELLSTOBESHOWNY = 40;
  private int furthestX = CELLSTOBESHOWNX;
  private int furthestY = CELLSTOBESHOWNY;
  private List<List<JComponent>> visibleCells;
  private JPanel cellPanel;


  /**
   * Constructs a spreadsheet GUI.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetGUI(SpreadsheetModel model) {
    //construct model
    this.model = model;
    //set default size to 1000 x 1000
    setSize(1000, 1000);
    //set default location to 0 0
    setLocation(0, 0);
    //set close frame to Exit on Close
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //allow min size to be 500 x 500
    this.setMinimumSize(new Dimension(500, 500));
    //use a border layout for the overall frame
    this.setLayout(new BorderLayout());

    //set the cellPanel's layout to a GridBagLayout
    GridBagLayout layout = new GridBagLayout();
    cellPanel = new JPanel(layout);

    //Initialize visibleCells
    visibleCells = new ArrayList<>();

    // add all of the components onto the JFrame
    fillCells();
    // add scroll buttons
    scrollButtons();
    // display the cells
    displayCells();
  }

  //displays cells with specific formatting for grid bag layout
  private void displayCells() {
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1;
    c.weighty = 1;
    for (int i = 0; i < visibleCells.size(); i++) {
      for (int j = 0; j < visibleCells.get(i).size(); j++) {
        c.gridx = j;
        c.gridy = i;
        this.cellPanel.add(visibleCells.get(i).get(j), c);
      }
    }
    //add the panel full of cells to this frame
    this.add(cellPanel);
  }


  //fill the cells with values
  private void fillCells() {

    //add the first row, blank first square and A-Z for the rest
    List<JComponent> tempList = new ArrayList<>();
    tempList.add(new JLabel(""));
    for (int x = furthestX - this.CELLSTOBESHOWNX + 1; x <= this.CELLSTOBESHOWNX; x++) {
      JLabel tempJLabel = new JLabel(Coord.colIndexToName(x), SwingConstants.CENTER);
      tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
      tempList.add(tempJLabel);
    }
    visibleCells.add(tempList);

    //add the rest of the rows, row number and then the cells
    for (int y = furthestY - this.CELLSTOBESHOWNY + 1; y <= this.CELLSTOBESHOWNY; y++) {
      tempList = new ArrayList<>();

      for (int x = furthestX - this.CELLSTOBESHOWNX; x <= this.CELLSTOBESHOWNX; x++) {
        if (x == furthestX - this.CELLSTOBESHOWNX) {
          JLabel tempJLabel = new JLabel(Integer.toString(y), SwingConstants.CENTER);
          tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
          tempList.add(tempJLabel);
        } else {
          JTextField tempTextField;
          try {
            tempTextField = new JTextField(model.getComputedValue(new Coord(x, y)));
          } catch (IllegalArgumentException e) {
            tempTextField = new JTextField("#ERROR");
          }
          tempTextField.setBorder(new LineBorder(Color.GRAY, 1));
          tempTextField.setEditable(false);
          tempTextField.setColumns(10);
          tempList.add(tempTextField);
        }
      }
      visibleCells.add(tempList);
    }
  }

  //add the scroll buttons to this frame
  private void scrollButtons() {

    JPanel scrollPanel = new JPanel(new FlowLayout());

    char up = '↑';
    JButton upScroll = new JButton(Character.toString(up));
    upScroll.setActionCommand("Up");
    upScroll.addActionListener(this);
    scrollPanel.add(upScroll);

    char down = '↓';
    JButton downScroll = new JButton(Character.toString(down));
    downScroll.setActionCommand("Down");
    downScroll.addActionListener(this);
    scrollPanel.add(downScroll);

    char left = '←';
    JButton leftScroll = new JButton(Character.toString(left));
    leftScroll.setActionCommand("Left");
    leftScroll.addActionListener(this);
    scrollPanel.add(leftScroll);

    char right = '→';
    JButton rightScroll = new JButton(Character.toString(right));
    rightScroll.setActionCommand("Right");
    rightScroll.addActionListener(this);
    scrollPanel.add(rightScroll);

    String pageUp = "↑↑";
    JButton pageUpScroll = new JButton(pageUp);
    pageUpScroll.setActionCommand("Page Up");
    pageUpScroll.addActionListener(this);
    scrollPanel.add(pageUpScroll);

    String pageDown = "↓↓";
    JButton pageDownScroll = new JButton(pageDown);
    pageDownScroll.setActionCommand("Page Down");
    pageDownScroll.addActionListener(this);
    scrollPanel.add(pageDownScroll);

    String pageLeft = "←←";
    JButton pageLeftScroll = new JButton(pageLeft);
    pageLeftScroll.setActionCommand("Page Left");
    pageLeftScroll.addActionListener(this);
    scrollPanel.add(pageLeftScroll);

    String pageRight = "→→";
    JButton pageRightScroll = new JButton(pageRight);
    pageRightScroll.setActionCommand("Page Right");
    pageRightScroll.addActionListener(this);
    scrollPanel.add(pageRightScroll);

    this.add(scrollPanel, BorderLayout.PAGE_END);
  }

  /**
   * Renders the GUI.
   * @throws IOException if an output issue occurs
   */
  @Override
  public void render() throws IOException {
    setVisible(true);
  }

  //perform certain actions based on shown buttons
  @Override
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {
      case "Up":
        shiftUp();
        break;
      case "Down":
        shiftDown();
        break;
      case "Left":
        shiftLeft();
        break;
      case "Right":
        shiftRight();
        break;
      case "Page Up":
        pageUp();
        break;
      case "Page Down":
        pageDown();
        break;
      case "Page Left":
        pageLeft();
        break;
      case "Page Right":
        pageRight();
        break;
    }
  }

  //scroll up
  private void shiftUp() {
    if (furthestY <= CELLSTOBESHOWNY) {
      return;
    }
    cellPanel.removeAll();
    visibleCells.remove(CELLSTOBESHOWNY);
    this.furthestY--;
    ArrayList<JComponent> tempList = new ArrayList<>();
    for (int x = furthestX - this.CELLSTOBESHOWNX; x <= furthestX; x++) {
      if (x == furthestX - this.CELLSTOBESHOWNX) {
        JLabel tempJLabel = new JLabel(Integer.toString(furthestY - CELLSTOBESHOWNY + 1),
            SwingConstants.CENTER);
        tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
        tempList.add(tempJLabel);
      } else {
        JTextField tempTextField;
        try {
          tempTextField =
              new JTextField(model.getComputedValue(new Coord(x, furthestY - CELLSTOBESHOWNY + 1)));
        } catch (IllegalArgumentException e) {
          tempTextField = new JTextField("#ERROR");
        }
        tempTextField.setBorder(new LineBorder(Color.GRAY, 1));
        tempTextField.setEditable(false);
        tempTextField.setColumns(10);
        tempList.add(tempTextField);
      }
    }
    visibleCells.add(1, tempList);
    displayCells();
    cellPanel.updateUI();
  }

  //scroll down
  private void shiftDown() {
    cellPanel.removeAll();
    visibleCells.remove(1);
    this.furthestY++;
    ArrayList<JComponent> tempList = new ArrayList<>();
    for (int x = furthestX - this.CELLSTOBESHOWNX; x <= furthestX; x++) {
      if (x == furthestX - this.CELLSTOBESHOWNX) {
        JLabel tempJLabel = new JLabel(Integer.toString(furthestY),
            SwingConstants.CENTER);
        tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
        tempList.add(tempJLabel);
      } else {
        JTextField tempTextField;
        try {
          tempTextField = new JTextField(
              model.getComputedValue(new Coord(x, furthestY)));
        }
        catch (IllegalArgumentException e ) {
          tempTextField = new JTextField("#ERROR");
        }
        tempTextField.setBorder(new LineBorder(Color.GRAY, 1));
        tempTextField.setEditable(false);
        tempTextField.setColumns(10);
        tempList.add(tempTextField);
      }
    }
    visibleCells.add(tempList);
    displayCells();
    cellPanel.updateUI();
  }

  //scroll right
  private void shiftRight() {
    cellPanel.removeAll();
    this.furthestX++;
    for (int i = 0; i <= CELLSTOBESHOWNY; i++) {
      visibleCells.get(i).remove(1);
    }

    for (int i = 0; i <= CELLSTOBESHOWNY; i++) {
      if (i == 0) {
        JLabel tempJLabel = new JLabel(Coord.colIndexToName(furthestX),
            SwingConstants.CENTER);
        tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
        visibleCells.get(i).add(tempJLabel);
      } else {
        JTextField tempTextField;
        try {
          tempTextField = new JTextField(
              model.getComputedValue(new Coord(furthestX, i)));
        }
        catch (IllegalArgumentException e ) {
          tempTextField = new JTextField("#ERROR");
        }
        tempTextField.setBorder(new LineBorder(Color.GRAY, 1));
        tempTextField.setEditable(false);
        tempTextField.setColumns(10);
        visibleCells.get(i).add(tempTextField);
      }
    }
    displayCells();
    cellPanel.updateUI();
  }

  //scroll left
  private void shiftLeft() {
    if (furthestX <= CELLSTOBESHOWNX) {
      return;
    }
    cellPanel.removeAll();
    this.furthestX--;
    for (int i = 0; i <= CELLSTOBESHOWNY; i++) {
      visibleCells.get(i).remove(CELLSTOBESHOWNX);
    }

    for (int i = 0; i <= CELLSTOBESHOWNY; i++) {
      if (i == 0) {
        JLabel tempJLabel = new JLabel(Coord.colIndexToName(furthestX - CELLSTOBESHOWNX + 1),
            SwingConstants.CENTER);
        tempJLabel.setBorder(new LineBorder(Color.GRAY, 1));
        visibleCells.get(i).add(1, tempJLabel);
      } else {
        JTextField tempTextField;
        try {
          tempTextField = new JTextField(
              model.getComputedValue(new Coord(furthestX - CELLSTOBESHOWNX + 1, i)));
        }
        catch (IllegalArgumentException e ) {
          tempTextField = new JTextField("#ERROR");
        }

        tempTextField.setBorder(new LineBorder(Color.GRAY, 1));
        tempTextField.setEditable(false);
        tempTextField.setColumns(10);
        visibleCells.get(i).add(1, tempTextField);
      }
    }
    displayCells();
    cellPanel.updateUI();
  }

  private void pageUp() {
    int count = CELLSTOBESHOWNY;
    while(furthestY > CELLSTOBESHOWNY && count > 0) {
      this.shiftUp();
      count--;
    }
  }

  private void pageDown() {
    int count = CELLSTOBESHOWNY;
    while (count > 0) {
      this.shiftDown();
      count--;
    }
  }

  private void pageLeft() {
    int count = CELLSTOBESHOWNX;
    while (furthestX > CELLSTOBESHOWNX && count > 0) {
      this.shiftLeft();
      count--;
    }
  }

  private void pageRight() {
    int count = CELLSTOBESHOWNX;
    while (count > 0) {
      this.shiftRight();
      count--;
    }
  }
}
