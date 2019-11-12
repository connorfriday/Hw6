package edu.cs3500.spreadsheets.sexp;

import edu.cs3500.spreadsheets.model.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An SExpression visitor that gets any coordinate references and returns them as a list.
 */
public class GetCoordReferences implements SexpVisitor<List<Coord>> {

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
      res.addAll(s.accept(new GetCoordReferences()));
    }
    return res;
  }

  @Override
  public List<Coord> visitSymbol(String s) {
    List<Coord> res = new ArrayList<>();
    Scanner scan = new Scanner(s).useDelimiter(":");
    if (scan.hasNext()) {
      String first = scan.next();
      try {
        Coord lCoord = new Coord(first);
        if (scan.hasNext()) {
          String second = scan.next();
          Coord rCoord = new Coord(second);
          return getMultipleReferences(lCoord, rCoord);
        }
        else {
          res.add(lCoord);
          return res;
        }
      }
      catch (IllegalArgumentException e) {
        return res;
      }
    }
    else {
      return res;
    }
  }

  private List<Coord> getMultipleReferences(Coord firstRef, Coord secondRef) {
    List<Coord> res = new ArrayList<>();
    if (firstRef.row > secondRef.row || firstRef.col > secondRef.col) {
      return res;
    }
    for (int i = firstRef.row; i <= secondRef.row; i++) {
      for (int j = firstRef.col; j <= secondRef.col; j++) {
        res.add(new Coord(j, i));
      }
    }

    return res;
  }

  @Override
  public List<Coord> visitString(String s) {
    return new ArrayList<>();
  }
}
