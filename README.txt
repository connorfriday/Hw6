This file details the changes we made to our model for this assignment.

1. We added a method to the interface that got the coordinates of all non-empty cells.
   This is used by our views to know which cells to draw with contents. Encapsulation is
   not broken as the Coords provided are final.

2. We adjusted our setCell implementation so that we still store the equal sign in the
   cells contents. Previously we had cut it off to make the parser work, but we need to
   store it so we can write it back onto a file with out textual view.