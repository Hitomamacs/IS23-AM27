### UML MYSHELFY

In the game we can identify some main actors: the board, the bag, the tiles, the grid, the players and the personal and common objective cards.

## carte
As far as tiles are concerned, we have found two solutions: use the Factory design pattern or use a simple enumeration to distinguish colors. We chose the second method because it is the easiest way to identify the colors of the cards: in fact, the colors of the cards are fixed so using the design pattern seemed almost useless.

We created two different abstract classes: one to represent personal objective cards and the other for common ones. Both classes will have subclasses that implement each type of paper by overriding the create method.

## tabellone
In the game the number of players can vary from 2 to 4 and depending on how many there are, the board must be configured differently. In order to adapt the shape of the board to the number of players we decided to implement the strategy pattern: depending on the dynamic number of players the creation of the board changes because it will override the method creaTaboardone.

##game
The game is managed by two classes: one that manages the game in general and counts points and the other that manages the turn. The class that manages the turn has a list with the order of the players and notifies each player when he has to play.


##memento
it can be used to take track of the moves that players make.
