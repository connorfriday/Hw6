package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * A GUI implementation of viewing a spreadsheet, able to make edits to the spreadsheet.
 */
public class SpreadsheetEditableGUI extends SpreadsheetGUI {

  private SpreadsheetReadOnlyModel model;
  protected Features features;
  private JTextField entryField;
  private Coord currentCell;

  /**
   * Constructs a spreadsheet GUI.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetEditableGUI(SpreadsheetReadOnlyModel model) {
    super(model);

    this.model = model;

    editPanel();

    this.currentCell = new Coord(1, 1);

  }


  //panel that allows edits to be made
  private void editPanel() {

    JPanel editPanel = new JPanel();
    editPanel.setFocusable(false);

    editPanel.add(this.optionsButton());

    JButton commitChange = new JButton(Character.toString((char) 10003));
    commitChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (entryField.getText().equals("")) {
          features.updateCellValue(currentCell, "");
        } else {
          features.updateCellValue(currentCell, entryField.getText());
        }
        cellPanel.repaintCell();
      }
    });
    editPanel.add(commitChange);

    JButton cancelChange = new JButton("X");
    cancelChange.setFocusable(false);
    cancelChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        entryField.setText(model.getRawValue(currentCell));
        entryField.updateUI();
      }
    });
    editPanel.add(cancelChange);
    entryField = new JTextField();
    entryField.setPreferredSize(new Dimension(WIDTH / 2, 20));
    editPanel.add(entryField);

    this.add(editPanel, BorderLayout.PAGE_START);
  }

  //options button that triggers loading/saving file
  private JButton optionsButton() {
    JButton optionsButton = new JButton("Options/Instructions");
    optionsButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String[] options = {"<html><b><u><font color=\"#F5DEB3\">Save File:</u><html>",
            "<html><b><u><font color=\"#20B2AA\">LoadFile:</u><html>",
            "<html><b><u><font color=\"#6495ED\">Instructions:</u><html>"};
        int choice = JOptionPane.showOptionDialog(SpreadsheetEditableGUI.this,
            new JLabel("<html><b><u><i><font color=\"#FF1493\">Select:</u></i><html>",
                JLabel.CENTER),
            "Options/Instructions",
            JOptionPane.YES_NO_CANCEL_OPTION,
            -1, null, options, null);

        if (choice == 0) {
          String file = JOptionPane.showInputDialog(SpreadsheetEditableGUI.this,
              "Save As:", "Save",
              JOptionPane.QUESTION_MESSAGE, null, null, ".txt").toString();

          SpreadsheetEditableGUI.this.features.saveFile(file);
        }
        if (choice == 1) {
          JFileChooser chooser = new JFileChooser();
          int returnVal = chooser.showOpenDialog(SpreadsheetEditableGUI.this);
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            SpreadsheetEditableGUI.this.features.loadFile(chooser.getSelectedFile().getName());
          }
        }
        if (choice == 2) {
          JOptionPane.showMessageDialog(SpreadsheetEditableGUI.this,
              "<html><li><b>Switch cells by clicking on the desired cell"
                  + " or by using the arrow keys.<html>\n"
                  + "<html><li><b> To load/save a file, click options.<html>\n"
                  + "<html><li><b>To scroll, use either the arrow keys or the scroll bars.<html>\n"
                  + "<html><li><b>Use the text field to edit contents,"
                  + " check to confirm and X to revert.<html>");
        }

      }
    });
    return optionsButton;
  }

  @Override
  public void setCurrentCell(Coord coord) {
    this.currentCell = coord;
    entryField.setText(model.getRawValue(coord));
    entryField.updateUI();
  }

  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message,
        "Alert", JOptionPane.INFORMATION_MESSAGE);
  }
}
