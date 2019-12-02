package edu.cs3500.spreadsheets.provider.view;

import java.awt.FlowLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JTextField;


/**
 * Represents an editor panel in a basic spreadsheet editor. Contains a text field to input text
 * into a cell, and has an accept or reject button to either change a cell to the inputted text, or
 * reset the cell to the previous text.
 */
public class ButtonEditPanel extends javax.swing.JPanel {
  public JButton accept;
  JButton reject;
  JTextField textInput;

  /**
   * The constructor for a editor panel.
   *
   * @param width the width of the fram that this panel will be added to
   */
  ButtonEditPanel(int width) {
    // creates a new button panel to house the text fields and accept/reject buttons
    this.setLayout(new FlowLayout());

    // accept button
    this.accept = new JButton("✔");
    this.accept.setActionCommand("Accept button");
    this.add(this.accept);

    // reject button
    this.reject = new JButton("✘");
    this.reject.setActionCommand("Reject button");
    this.add(this.reject);

    // creates the text field and sets its size to mostly fill the horizontal space of the view
    this.textInput = new JTextField(width / 14);
    this.textInput.setPreferredSize(new Dimension(width, 30));

    // adds the text field to the button panel and then adds the button panel to the existing view
    this.add(textInput);
  }

  /**
   * Sets the text field of this panel. Useful for updating the text when the highlighted cell
   * location changes.
   *
   * @param contents the raw contents to be displayed in the text field
   */
  void setTextField(String contents) {
    this.textInput.setText(contents);
  }

  /**
   * Gets the current text in the input field. Useful for determining what the user has typed.
   *
   * @return the String contents of this panel's text field
   */
  String getInputText() {
    return this.textInput.getText();
  }

}
