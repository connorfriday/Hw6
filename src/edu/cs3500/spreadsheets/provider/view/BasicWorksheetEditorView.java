package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.HighlightCell;
import edu.cs3500.spreadsheets.provider.IFeatures;
import edu.cs3500.spreadsheets.provider.model.BasicWorksheetReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the editable GUI view of a basic spreadsheet, so the user can view files and inputs on
 * a spreadsheet as well as edit cells and see the raw contents of cells.
 */
public class BasicWorksheetEditorView extends JFrame implements BasicWorksheetView {
  private BasicWorksheetGraphicalView spreadsheetView;
  private BasicWorksheetReadOnlyModel modelToDisplayandEdit;
  private ButtonEditPanel buttonPanel;


  /**
   * A constructor for the editable GUI view of a spreadsheet which reads an existing model and
   * displays that in the view.
   *
   * @param model The given spreadsheet model/file to be displayed and edited.
   */
  public BasicWorksheetEditorView(BasicWorksheetReadOnlyModel model) {
    super();
    // creates the existing view
    this.spreadsheetView = new BasicWorksheetGraphicalView(model);
    this.modelToDisplayandEdit = model;

    this.buttonPanel = new ButtonEditPanel(this.spreadsheetView.getWidth());
    spreadsheetView.add(this.buttonPanel, BorderLayout.NORTH);

    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel,
            this);
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);

    System.out.println(this.getHighlightedCell());
    this.buttonPanel.setTextField(this.modelToDisplayandEdit
            .getCellAt(this.getHighlightedCell()).getRawContents());

    // sets the text in thee toolbar to be the first highlighted cell
    this.setTextbox();

  }

  /**
   * Sets the text in the toolbar to be the raw contents of the highlighted cell.
   */
  public void setTextbox() {
    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel, this);
    String contents =
            this.modelToDisplayandEdit.getCellAt(this.getHighlightedCell()).getRawContents();
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    if (contents.equals("")) {
      this.buttonPanel.setTextField(contents);
    } else {
      this.buttonPanel.setTextField("=" + contents);
    }
  }

  @Override
  public void render() {
    this.spreadsheetView.setVisible(true);
  }

  /**
   * A method which both revalidates and repaints the spreadsheet.
   */
  @Override
  public void refresh() {
    this.spreadsheetView.spreadsheetPanel.revalidate();
    this.spreadsheetView.spreadsheetPanel.repaint();
  }

  /**
   * Returns the current text in the textbox.
   *
   * @return A string representing the text in the box.
   */
  private String getViewTextField() {
    return this.buttonPanel.getInputText();
  }

  /**
   * Returns the location of the cell which is currently highlighted.
   *
   * @return A coordinate representing the location of the highlighted cell.
   */
  public Coord getHighlightedCell() {
    return this.spreadsheetView.getHighlightedCell();
  }

  /**
   * Changes the location of the highlighted cell in this spreadsheet view.
   *
   * @param columnFactor the number of columns the highlighted cell is moving
   * @param rowFactor    the number of rows the highlighted cell is moving
   */
  public void changeHighlightedCellLocation(int columnFactor, int rowFactor) {
    try {
      this.spreadsheetView.moveHighlightedCell(columnFactor - 1, rowFactor - 1);
    } catch (IllegalArgumentException e) {
      // nothing to do... don't move the highlighted cell
    }
  }

  /**
   * Determines what features should be executed based on the action that occurs is pressed (either
   * the cell's contents are mutated or the edits are denied).
   *
   * @param feature the feature of the spreadsheet that will be added
   */
  public void addIFeatures(IFeatures feature) {
    this.buttonPanel.accept.addActionListener(actionEvent -> {
      String text = getViewTextField();
      Coord location = getHighlightedCell();
      feature.acceptCellEdit(location, text);
    });
    this.buttonPanel.reject.addActionListener(actionEvent -> feature.rejectCellEdit());
    this.buttonPanel.textInput.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent keyEvent) {
        //nothing else to do
      }

      @Override
      public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
          feature.moveHighlightedCell("up arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
          feature.moveHighlightedCell("down arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
          feature.moveHighlightedCell("right arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
          feature.moveHighlightedCell("left arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
          feature.deleteCellContents(getHighlightedCell());
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
          String text = getViewTextField();
          Coord location = getHighlightedCell();
          feature.acceptCellEdit(location, text);
        }
      }

      @Override
      public void keyReleased(KeyEvent keyEvent) {
        //nothing else to do
      }
    });
  }
}
