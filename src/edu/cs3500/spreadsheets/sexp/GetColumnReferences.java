package edu.cs3500.spreadsheets.sexp;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetReadOnlyModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetColumnReferences implements SexpVisitor<List<Coord>> {

  SpreadsheetReadOnlyModel model;

  public GetColumnReferences(SpreadsheetReadOnlyModel model) {
    this.model = model;
  }

  @Override
  public List<Coord> visitBoolean(boolean b) {
    return new ArrayList<>();
  }

  @Override
  public List<Coord> visitNumber(double d) {
    return new ArrayList<>();
  }

  @Override
  public List<Coord> visitSList(List<Sexp> l) {
    List<Coord> res = new ArrayList<>();
    for (Sexp s : l) {
      res.addAll(s.accept(new GetColumnReferences(model)));
    }
    return res;
  }

  @Override
  public List<Coord> visitSymbol(String s) {
    List<Coord> res = new ArrayList<>();
    Scanner scan = new Scanner(s).useDelimiter(":");
    String firstCol = "";
    String secondCol = "";

    if(scan.hasNext()) {
      firstCol = scan.next();
    }
    if(scan.hasNext()) {
      secondCol = scan.next();
    }
    if(firstCol.isEmpty() || secondCol.isEmpty()) {
      return res;
    }

    if(!firstCol.matches("[a-zA-Z]+") || !secondCol.matches("[a-zA-Z]+")) {

      //todo this probably shouldn't return empty, probably throw exception of a malformed reference
      return res;
    }

    int firstColIndex = Coord.colNameToIndex(firstCol);
    int secondColIndex = Coord.colNameToIndex(secondCol);

    if(firstColIndex > secondColIndex) {
      //todo this probably shouldn't return empty, probably throw exception of a malformed reference
      return res;
    }


    for(Coord c: model.getNonEmptyCoordinates()) {
      if(c.col <= secondColIndex && c.col >= firstColIndex) {
        res.add(c);
      }
    }

    if(res.isEmpty()) {
      res.add(new Coord(firstColIndex, 1));
    }

    return res;

  }

  @Override
  public List<Coord> visitString(String s) {
    return new ArrayList<>();
  }
}