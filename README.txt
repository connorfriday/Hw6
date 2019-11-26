This file details changes and updates for assignment 7.

1. Created a read only model interface and class for use by views. Views can now only have a
    read only model.

2. Updated our view interface.
    a) setCurrentCell()
       - allowed us to highlight the current cell and track which cell the user is editing
    b) displayMessage()
      - allowed us to send message to the user from controller if a cell update was unable to be
        processed by the model or if a load or save of a file failed
    c) setFeatures()
      - allowed us to hide action/mouse/key events from the controller and
        focus on higher order events

3. Abstracted to a custom CellPanel class which extends JPanel
  a) handles much of the internal cell focusing and shifting of focus as well as scrolling
    - internal changes that don't affect the model
  b) helps to minimize code duplication between both GUI views
  c) can repaint a cell by being given a coordinate (view can tell it to update value when after
      the controller changes the model)

4. Created Features and Controller interface
  a) Controller implements this interface as it must implement those features
    - this way the view can have access to features methods but not the Controller method start()
  b) View uses delegates to this interface when events occur that implicate a change in the model

5. Extra Credit Features
  1) Moving between cells with arrow keys
    - Implemented in the CellPanel class
    - Doesn't involve controller as it is completely internal to the view
    - When attempting to move to a cell that is off screen, the panel will scroll to that cell
   2) Saving a Spreadsheet from within GUI
    - By clicking on the options button in the GUI and selecting save file, then providing a
      filename to save at, a user can save their work
    - The GUI aspects are handled in the view, but once the user provides the filename, all the
      the information is sent to the controller which then saves the file using our previous
      textual view
   3) Loading a Spreadsheet from within GUI
    - By clicking on the options button and selecting load file, the user can then navigate
      through their filesystem and find a file to open, which will then be read and constructed
    - The GUI options are handled in the view, but once the user chooses a file to load in the file
      chooser the filename is passed to the controller which then constructs a model using the
      existing WorksheetReader class and then reruns the controller with that model
