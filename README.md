# CSc133-Driving-Game
This project was a cumulative homework assignment for a Object Oriented Computer Graphics Programming course. I believe not *all* practices here are great, but are done instead more to show the understanding and implementation of certain concepts.

Any version/assignment can be ran by navigating the command prompt to the bin folder, and running executing the following command, but replacing # with 1, 2, 3, or 4 for each assignment:

java a#.Starter

When the semester ended and summer rolled around, there were a few things that I did try to implement after the final iteration of the assignment, which can be found in package a4. This include the following:

(1) Draw the objects in a fashion that looks a little more like a game, and
(2) some general code clean-up, and optimization.

Here are a few things which I would like to eventually get around to, but have decided to set this project aside for now to focus my energy elsewhere:

(1) Fix controls so that instead of hitting the arrows once to change values, instead you must hold them down, and
(2) condense all individual command classes into a single GameCommands class to reduce complexity.

Note: Much of the text in this file is likely to be verbatim from the assignment PDFs. Although, many more details can be found within each PDF.

The overall assignment was to build a game in Java that acted as a 2D driving simulator. You control a race car, and maneuver around the map to avoid enemy AI cars, birds, and oil slicks, while trying to get fuel cans and progress to the next checkpoint.


--- A1: Class Associations
The goal of A1 was to develop a good initial class hierarchy and control structure by designing the program in UML and then implementing it in Java. This version uses keyboard input commands to control and display the contents of a “game world” containing the set of objects in the game. In future assignments many of the keyboard commands are replaced by interactive GUI operations, and the game also gets added graphics, animation, and sound. For now the game is in “text mode” or "console mode" with user input coming from the keyboard and “output” being lines of text on the screen.


--- A2: Design Patterns and GUIs
The goal of A2 is to extend the game from Assignment #1 (A1) to incorporate several important design patterns, and a Graphical User Interface (GUI). The rest of the game will appear to the user to be similar to the one in A1, and most of the code from A1 will be reused, although it will require some modification and reorganization. While this assignment adds a GUI, the map is still presented in text form on the console.


--- A3: Interactive Graphics and Animation
The goal of this assignment is to gain experience with interactive graphics and animation techniques such as repainting, timer-driven animation, collision detection, and object selection. Specifically, the following modifications were made to the game:

(1) the program is to make use of the proxy and factory method design patterns,
(2) the game world map is to display in the GUI, rather than in text form on the console,
(3) the movement (animation) of game objects is to be driven by a timer,
(4) the game is to support dynamic collision detection and response,
(5) the game is to support simple interactive editing of some of the objects in the world, and
(6) the game is to include sounds appropriate to collisions and other events.


--- A4: Transformations
The goal of A4 is to extend the program from Assignment #3 (A3) to include several uses of 2D transformations plus additional graphics and interactive operations. As always, all operations from previous assignments must continue to work as before (except where superseded by new operations). Specifically, you are to add the following things to your program:

(1) local, world, and device coordinate systems,
(2) hierarchical object transformations,
(3) shock waves drawn as Bezier curves (drawn via self implemented algorithm), and
(4) zooming and panning around the world via mouse interaction.

Note about the final assignment: Part of the specification was to implement zooming and panning only while the game is running and not while it is paused. I have since changed this to allow zooming and panning even while paused, to make it easier to see the hierarchical drawing of the car, specifically the front wheels turning with the steering direction.