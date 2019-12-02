package edu.cs3500.spreadsheets.provider.cell;

import edu.cs3500.spreadsheets.provider.function.LessThan;
import edu.cs3500.spreadsheets.provider.function.Add;
import edu.cs3500.spreadsheets.provider.function.CellVisitor;
import edu.cs3500.spreadsheets.provider.function.Multiply;
import edu.cs3500.spreadsheets.provider.function.Repeat;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * A class that represents a cell in a spreadsheet that contains a function.
 */
public class CellFunction implements CellFormula {
  private String func;
  private List<CellFormula> arguments;
  public CellFormula cellFunctionEvaluated;
  private Coord location;
  private boolean containsReference;

  /**
   * Constructs a {@code CellFunction} object. A constructor for this CellFunction that takes in the
   * function name as a String and the list of formulas that the function will be applied to.
   *
   * @param func      a String representing the name of the desired function.
   * @param arguments the list of CellFormulas to be evaluated and applied to the given function.
   */
  public CellFunction(String func, List<CellFormula> arguments, Coord locationOfCell) {
    this.func = func;
    this.arguments = arguments;
    this.location = locationOfCell;
    this.cellFunctionEvaluated = this.evaluateToCellFormula();
  }

  @Override
  public Object evaluateCell() {
    if (containsReference) {
      return "REF!";
    } else {
      return this.cellFunctionEvaluated.evaluateCell();
    }
  }

  @Override
  public String getRawContents() {
    String contents = "(" + func;
    for (CellFormula form : arguments) {
      contents += (" " + form.getRawContents());
    }
    return contents + ")";
  }

  @Override
  public Object accept(CellVisitor visit) {
    return visit.visitFunction(this);
  }

  /**
   * Returns a a CellFormula (either a CellDouble, CellString, or CellBoolean) that has the
   * evaluated value of this function as its value field.
   *
   * @return a CellFormula with the evaluated value of this function.
   */
  private CellFormula evaluateToCellFormula() {
    switch (this.func) {
      // if the function name is SUM
      case "SUM":
        // if there are no arguments, throw an exception
        if (this.arguments.size() == 0) {
          throw new IllegalArgumentException("No arguments given for SUM");
        } else {
          // else create a new instance of Add
          Add sum = new Add();
          // set a result sum
          double evalSum = 0;
          // for every cell in the arguments of the function
          for (CellFormula form : this.arguments) {
            if (form.evaluateCell().equals("REF!")) {
              this.containsReference = true;
            } else {
              // add them to the evalSum
              evalSum += sum.apply(form);
            }
          }
          // replace the CellFunction with the now evaluated CellDouble
          return new CellDouble(evalSum);
        }
        // if the function name is PRODUCT
      case "PRODUCT":
        // if there are no arguments, throw an exception
        if (this.arguments.size() == 0) {
          throw new IllegalArgumentException("No arguments given for PRODUCT");
        } else {
          // else create a new instance of Multiply
          Multiply product = new Multiply();
          // set a result product
          double evalProd = 1;
          // are there any double values?
          int numOthers = 0;
          // for each cell in the arguments of the function
          for (CellFormula form : this.arguments) {
            if (form.evaluateCell().equals("REF!")) {
              this.containsReference = true;
            } else {
              if (product.apply(form) == 0.0) {
                numOthers += 1;
              } else {
                // multiply them to the evalProd
                evalProd *= product.apply(form);
              }
              // sets the product to the default value of 0, if there are no
              // doubles in the multiplication function
              if (numOthers == this.arguments.size()) {
                evalProd *= 0;
              }
            }
          }
          // replace the CellFunction with the now evaluated CellDouble
          return new CellDouble(evalProd);
        }
        // if the function name is REPT
      case "REPT":
        // if there are not two arguments, throw an exception
        if (this.arguments.size() != 2) {
          throw new IllegalArgumentException("Invalid number of arguments given for REPT");
        } else {
          // else create a new instance of Repeat
          Repeat rept = new Repeat();
          // set a result string
          String evalRept = "";
          int i;
          double num = (Double) this.arguments.get(1).evaluateCell();
          for (i = 0; i < num; i++) {
            if (this.arguments.get(0).evaluateCell().equals("REF!")) {
              this.containsReference = true;
            } else {
              // for each time we repeat, append the result to evalRept
              evalRept += rept.apply(this.arguments.get(0));
            }
          }
          // replace the CellFunction with the now evaluated CellString
          return new CellString(evalRept);
        }
        // if the function name is LESSTHAN
      case "<":
        // if there are not two arguments, throw an exception
        if (this.arguments.size() != 2) {
          throw new IllegalArgumentException("Invalid number of arguments given for LESSTHAN");
        } else {
          // else create an instance of LessThan
          LessThan less = new LessThan();
          // set a result boolean
          Boolean evalLess;
          // determine the value of the first argument
          if (this.arguments.get(0).evaluateCell().equals("REF!")
                  || this.arguments.get(1).evaluateCell().equals("REF!")) {
            this.containsReference = true;
          }
          double value1 = less.apply(this.arguments.get(0));
          // determine the value of the second argument
          double value2 = less.apply(this.arguments.get(1));
          // if the first value is less than the second
          evalLess = value1 < value2;
          // replace the CellFunction with the now evaluated CellBoolean
          return new CellBoolean(evalLess);

        }
        // else, the function name is not supported
      default:
        throw new IllegalArgumentException("Unsupported function");
    }
  }
}




