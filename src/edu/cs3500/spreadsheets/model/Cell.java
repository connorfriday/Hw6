package edu.cs3500.spreadsheets.model;

/**
 * Represents a cell in a spreadsheet, which has a position and contents.
 */
 class Cell {

  private String contents;

  /**
   * Constructs an instance of a spreadsheet cell.
   *
   * @param str represents the contents of the cell
   * @throws IllegalArgumentException if any of the arguments are null
   */
  Cell(String str) {

    if (str == null) {
      throw new IllegalArgumentException("Contents cannot be null!");
    }

    this.contents = str;
  }

  /**
   * Updates the contents of a cell to the given value.
   *
   * @param str the desired contents of the cell.
   */
  void updateContents(String str) {
    if (str == null) {
      throw new IllegalArgumentException("Contents cannot be null");
    }
    this.contents = str;
  }

  /**
   * Returns the contents of a cell as a string.
   *
   * @return the contents of the cell
   */
  String getContents() {
    return this.contents;
  }
}
