Changes required for Assignment 9:

////////////////////////////////////////////////////////////////////////////////////////////////
Extra Credit Implementation 1: Column References
-To implement column references, the changes were pretty easy.
-We already identify our current references with a visitor, and we took the same approach to
-add functionality of referencing columns. We simply added a new visitor to our
-EvaluateSexp visitor, and then changed how we checked cycles by moving to using an accumulator.
-This enabled support of column references.


Files added:
-GetColumnReferences

Files modified:
-BasicSpreadsheetModel
  -Changed how we check for cycles: moved to using an accumulator

-Evaluate Sexp
  -Changed how we got the list of references by just adding in the column references
  -This called the GetColumnReferences visitor

////////////////////////////////////////////////////////////////////////////////////////////////
Extra Credit Implementation 2: Graphing View
-To implement a graphing view, we needed to do a few things. First, we created an interface
-that would represent spreadsheet graphs. Then, we chose to create an implementation of this for
-Line Graphs. Next, we changed to model to allow support of graphs, and to store the graphs.
-This meant we also needed to change how file reader looked for graphs, so we made a Graph reader.
-On the other end, we also changed how saving a file works (textual view) to accommodate graphs.
-We added some buttons in the view to allow to load/view a graph, delete a graph,
-and create a graph. Finally, we added support for these functions in the Features interface,
-and implemented this methods in our controller.

Files added:
-GraphEnabledWorksheetReader
-SpreadsheetGraph
-LineGraph (Spreadsheet graph implementation)
-SpreadsheetEditableGraph

Files modified:
-Features
  -added support to allow for removing / creating / loading graphs
-SpreadsheetControllerEditable
  -implemented new methods from features
-SpreadsheetView
  -requiring a new refreshGraph() method, which updates graphs with real time data
-BasicSpreadsheetModel + Interface
  -added storing of graphs
  -added storing of supported graph types
  -get the graphs and graph types
-ReadOnlyBasicSpreadSheetModel + Interface
  -get the graphs and graph types
-Textual View
  -output saved graphs as text
-BeyondGood
  -to show graph view and to use the GraphEnabledWorksheetReader