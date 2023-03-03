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

I now use the reference to the points listed above

1. We should use the Factory Method to create players
2. Each concretePlayer has an attribute that indicates his player number 
3. The Strategy pattern could be used to implement different board configurations based on the number of players. This would involve defining a set of board configurations that can be selected at runtime based on the number of players. 
4. We should use the Factory Method to create personal objective cards 
