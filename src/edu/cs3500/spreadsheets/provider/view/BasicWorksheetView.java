package edu.cs3500.spreadsheets.provider.view;

/**
 * Represents a view of a basic worksheet - either graphical or text.
 */
public interface BasicWorksheetView {

  /**
   * Renders a {@link edu.cs3500.spreadsheets.model.BasicWorksheetModel} in some manner.
   */
  void render();

  /**
   * Refreshes a view.
   */
  void refresh();

}



