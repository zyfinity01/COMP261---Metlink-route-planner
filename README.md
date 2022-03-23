# COMP 261 Assignment 1 - gandhinira

## What the code does
* [x] Minimum
* [x] Core
* [x] Complete
* Challenge
   * [x] Multilingal
   * [ ] Quadtree
   * [x] UI improvements
   * [ ] Displaying cost

I have completed the Minimum, Core, Completion alongside Multilingal and UI improvements which are apart of Challenge.


## Important Strutures
One of the important data structures used in my code was the trie structure, the trie structure allows us to store all the stops in a way that is very efficient to retrieve, it also works well for our purpose as when we are searching through stops we want to show all the stops that match the current input, using traditional arraylists and doing full linear searches would cause the program to hang and not be as responsive. Using the trie allows us to search the datastructure character by character giving us a child of characters that can potentially complete the search term, this way we are searching through very small precurated datastructures that focus the input string.  [code for Tri get](/src/comp261/assig1/Trie.java#L41-54)

## Good example of data structure use
[location of data structures](/src/comp261/assig1/Graph.java#L10-18)
I created many datastructures to imrpove the speed of the program, these data structures are built once when launching the program and allow quick and easy access to the relevant data needed to draw the stops and edges that make up the graph.

Examples include:
<br>
HashMap: stopID >> stop
<br>
HashMap: tripID >> trip

HashMap: stop >> list(associated stops)
<br>
HashMap: stop >> list(associated edges)
<br>
HashMap: stop >> list(trip)
<br>
These particular stuctures result in being able to get associated stops/edges and trips that go through a stop all with a notation of O(1) meaning instant access.

## Challenge stuff
For challenge I managed to complete mouse panning and scroll wheel zooming with the use of scenebuilder, I used the javafx scenebuilder to set what the actions do and what methods they call.

I also used scenebuilder for the language switcher button that allows the user to switch between english and tereo, I added the button using the scenebuilder GUI to drag and drop the button onto the panel.
