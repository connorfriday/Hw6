package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SpreadsheetEditableGUI extends SpreadsheetGUI {
  private Features features;
  private JButton commitChange;
  private JButton cancelChange;
  private JTextField entryField;
  private JPanel editPanel;
  private Coord currentCell;

  /**
   * Constructs a spreadsheet GUI.
   *
   * @param model Spreadsheet model to view
   */
  public SpreadsheetEditableGUI(SpreadsheetModel model) {
    super(model);

    editPanel();

    this.currentCell = new Coord(1, 1);
  }


  private void editPanel() {
    editPanel = new JPanel();

    editPanel.add(this.optionsButton());

    commitChange = new JButton(Character.toString((char) 10003));
    commitChange.addActionListener(e -> {
      features.updateCellValue(currentCell, entryField.getText());
      cellPanel.repaintCell(currentCell);
    });
    editPanel.add(commitChange);

    cancelChange = new JButton("X");
    cancelChange.addActionListener(e -> {
      entryField.setText(model.getRawValue(currentCell));
      entryField.updateUI();
    });
    editPanel.add(cancelChange);

    entryField = new JTextField();
    entryField.setPreferredSize(new Dimension(WIDTH / 2, 20));
    editPanel.add(entryField);

    this.add(editPanel, BorderLayout.PAGE_START);
  }

  private JButton optionsButton() {
    JButton optionsButton = new JButton("Options");
    optionsButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String[] options = {"Save File", "Load File"};
        int choice = JOptionPane.showOptionDialog(SpreadsheetEditableGUI.this,
            "", "Options", JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        if(choice == 0) {
          String file =  JOptionPane.showInputDialog(SpreadsheetEditableGUI.this, "Save As:", "Save",
              JOptionPane.QUESTION_MESSAGE, null, null, ".txt").toString();

          SpreadsheetEditableGUI.this.features.saveFile(file);
        }
        if(choice == 1) {
          JFileChooser chooser = new JFileChooser();
          int returnVal = chooser.showOpenDialog(SpreadsheetEditableGUI.this);
          if(returnVal == JFileChooser.APPROVE_OPTION) {
            SpreadsheetEditableGUI.this.features.loadFile(chooser.getSelectedFile().getName());
          }
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
