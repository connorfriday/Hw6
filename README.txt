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

4. Created Features interface
  a) Controller implements this interface as it must implement those features
    - this way the view can have access to features methods but not the Controller method start()
  b) View uses delegates to this interface when events occur that implicate a change in the model
