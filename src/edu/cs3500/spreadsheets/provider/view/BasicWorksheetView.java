package edu.cs3500.spreadsheets.provider.view;

/**
 * Represents a view of a basic worksheet - either graphical or text.
 */
public interface BasicWorksheetView {

  /**
   * Renders a WorksheetModel in some manner.
   */
  void render();

  /**
   * Refreshes a view.
   */
  void refresh();

}



