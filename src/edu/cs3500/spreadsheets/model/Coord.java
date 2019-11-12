package edu.cs3500.spreadsheets.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A value type representing coordinates in a {@link Worksheet}.
 */
public class Coord {
  public final int row;
  public final int col;

  /**
   * Constructs an instance of a coordinate.
   * @param col the column
   * @param row the row
   */
  public Coord(int col, int row) {
    if (row < 1 || col < 1) {
      throw new IllegalArgumentException("Coordinates should be strictly positive");
    }
    this.row = row;
    this.col = col;
  }

  /**
   * Constructs an instance of a coordinate given a string reference to that coordinate.
   * ex. A1 ABC34 Z54
   * @param name the string reference to that coordinate
   * @throws IllegalArgumentException if string isn't properly formatted or coordinates are invalid
   */
  public Coord(String name) {
    // mimics the pattern used in WorksheetReader
    final Pattern cellRef = Pattern.compile("([A-Za-z]+)([1-9][0-9]*)");
    Matcher m = cellRef.matcher(name);
    if (m.matches()) {
      int tempCol = Coord.colNameToIndex(m.group(1));
      int tempRow = Integer.parseInt(m.group(2));
      if (tempCol >= 1 && tempRow >= 1) {
        this.row = tempRow;
        this.col = tempCol;
      }
      else {
        throw new IllegalArgumentException("Coordinates should be strictly positive");
      }
    } else {
      throw new IllegalArgumentException("Expected cell ref");
    }
  }

  /**
   * Converts from the A-Z column naming system to a 1-indexed numeric value.
   * @param name the column name
   * @return the corresponding column index
   */
  public static int colNameToIndex(String name) {
    name = name.toUpperCase();
    int ans = 0;
    for (int i = 0; i < name.length(); i++) {
      ans *= 26;
      ans += (name.charAt(i) - 'A' + 1);
    }
    return ans;
  }

  /**
   * Converts a 1-based column index into the A-Z column naming system.
   * @param index the column index
   * @return the corresponding column name
   */
  public static String colIndexToName(int index) {
    StringBuilder ans = new StringBuilder();
    while (index > 0) {
      int colNum = (index - 1) % 26;
      ans.insert(0, Character.toChars('A' + colNum));
      index = (index - colNum) / 26;
    }
    return ans.toString();
  }

  @Override
  public String toString() {
    return colIndexToName(this.col) + this.row;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return row == coord.row
        && col == coord.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
