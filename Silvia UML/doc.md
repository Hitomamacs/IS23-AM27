## INTRODUCTION 

Here is a brief description of how the game works: 

1. The number of players is between 2 and 4
2. The first player is randomly chosen and the chair is given to him 
3. The game board has a different configuration depending on the number of players 
4. A personal objective card is given to each player 
5. A common objective card (or more than one?) is chosen 
6. The first player starts the game  
7. During his round, each player has to select from 1 to 3 tiles from the game board, each of the tiles must have a free side 
8. The player places tiles in a column of his shelf
9. It is necessary to check if the player has completed the objectives of the common objective card
10. The turn goes to the next player 
11. Points 7, 8, 9 and 10 repeat until a player has completed his shelf 
12. When a player complete his shelf, the game continues until the first player's turn comes (now the first player doesn't have to play anymore)
13. The points of the personal objective card and the points linked to the adjacent rows and columns within the shelf are counted
14. The player who has scored the most points wins and if two or more players have the same score, the player "farthest from the first player" wins

N.B.: whenever the tiles on the game board run out or if there are only isolated tiles left, it is necessary to place more


---


## UML AND DESIGN PATTERN IDEAS 

In the game we can identify some main factors: the game board, the bag, the tiles, the grid of the single players, the players and the personal and common objective cards.
I have not chosen any design pattern for the tiles, I consider the factory method less efficient than the enumeration because we already know the colors of the cards. 
I have decided to use the strategy pattern to create the correct game board based on the number of players as the number of players is discovered at runtime. 
We created two abstract classes for the cards, each has a number of subclasses (we didn't draw them all in the UML diagram) equal to the number of personal/common objective cards that exist. Each eight-class implements a specific type of card. 

Memento pattern can be used to keep track of all the moves that players make during a game.

I believe that chat can be implemented using a separate thread as discussed in the meeting.

Singleton pattern can be used to show all players common objective cards. 

Within a game turn of a single player it is necessary that the various component are notified every time a clock occurs: 
- the Observer/Mediator pattern could be a good fit for this scenario where components within a game turn need to be notified when they are selected

