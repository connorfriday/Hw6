package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SpreadsheetEditableGUI extends SpreadsheetGUI implements ActionListener, MouseListener {
  private SpreadsheetModel model;
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

    this.model = model;

    editPanel();

    this.currentCell = null;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Cancel") && currentCell != null) {
      entryField.setText(this.model.getRawValue(currentCell));
    }
  }

  private void editPanel() {
    editPanel = new JPanel();

    commitChange = new JButton(Character.toString((char) 2705));
    commitChange.setActionCommand("Commit");
    editPanel.add(commitChange);

    cancelChange = new JButton("X");
    cancelChange.setActionCommand("Cancel");
    editPanel.add(cancelChange);

    entryField = new JTextField();
    entryField.setPreferredSize(new Dimension(WIDTH / 2, 20));
    editPanel.add(entryField);

    this.add(editPanel, BorderLayout.PAGE_START);
  }

  @Override
  public void setActionListener(ActionListener a) {
    commitChange.addActionListener(a);
    cancelChange.addActionListener(this);
  }

  @Override
  public String getInputString() {
    return entryField.getText();
  }

  @Override
  public Coord getCurrentCell() {
    return this.currentCell;
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    return;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    return;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    return;
  }

  @Override
  public void mouseExited(MouseEvent e) {
    return;
  }

}
