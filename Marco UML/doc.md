# Introduction
Before modeling any class in our UML diagram it is important to have in mind a clear idea of the various pahses of the game, and the objects we need to model.

1) At the beginning of each game a **starting player** and an order (CCW, CW) is decided
2) The playling **tiles** are placed inside of the playing **board**, the number of tiles and their positions depends also on the number of player (4 = maximum number)
3) The first player decides what tiles to pick, the **condition** to evaluate for deciding if a particular tile can be picked is that it needs to have at least one free space adjacent to itself
4) The player picks 1, 2, 3 adjacent tiles and all of them need to comply with the condition explained above
5) The player puts th tiles in his **PERSONAL BOARD**, they all need to be putted in the **same** column. Once he has done this step his turn is concluded.
6) Following the order now is turn of Player number two that repeats steps **3 to 5**
---

When a player has filled up all of its personal board we conclude the round (finishing at the starting player) and then all points are counted. It might occures that in the main borad are left just single tiles, in that case it is mandatory to **re-fill** all of the boards with new random tiles.

# Modelling Phase
*DISCLAIMER: All of the documetation is subjected to change.*  
I will here try to describe the various classes we need to model


### PlayerFctory 
The job of the player factory is to generate the *general* players, giving them unique ids and creating a particular player Called *First Player*

### General Player
Extension of the class Abstract Player it inherit all of its methods

### Abstract Player 
It is an abstract Class that contains all the methods of common to both **General ** and **first** players

### First Player
It inheritate from the Class **General Player**, it is basically a player that has a particular method were before starting his turn he notifies (maybe using observer pattern)


### Match Orchestrator (Mediator/Observer intended as both)
It takes care of handling all of the low level logic telling a player that it is their turn or giving the command to count the points, it also recieves the number of points from each player and gives them to the main controller

### Match
It comunicates only with the **Match Orchestrator** giving command to start a player turn. Its role is to verify which player is the winner based on the total number of points, give command of starting a new game etc


