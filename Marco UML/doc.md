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

### Tile (interface/maybe better as abstract class)
It serves as a general interface for the various colored tiles

### Colored Tile in the constructor we put the color

It implements/extends Tile specifing the color of the tile in its attributes

### Tile Creator (abstract class for implementing factory pattern)
It serves for implementing the factory pattern

### "Blue" Tile Creator
Extends Tile creator and used for creating tiles of a particular color, we have x of these classes were x is the number of colors present

### Personal Board
Each player has a personal board rappresenting his grid it contains all of the tiles collected by the player

### Tile Spot
It rapresents the spot that can be occupied by a tile, both in the personal board and in the play boardk

### Turn (for implementing strategy pattern, it rappresents the "context")
It mantains a reference to one concrete strategy and has the possibility to change the strategy with a method

### TurnStrategy(interface)
It declears just a method for executing a given strategy

### Pickstrategy
It implements TurnStrategy, interface and handles tha action of picking one or more  tiles from the play board

### FillPlayBoardStrategy
It implements TurnStrategy, it handles the *re-fill* of tiles in the play board once they remain just single one

### TopUpPersonalBoardStrategy
It implements TurnStrategy, it takes care of filling up the player board with tiles given a column

### PassTurnStrategy
It implements TurnStrategy, it sends a message to the Game orchestrator telling that the turn of a given player ended

### CompletedCommonObjStrategy
It implements TurnStrategy, it checks and notifies if "the" player compleated a common objective

# DataFlow
I think that all of the remaining class are quite esplicative by looking at the UML diagram. What I want is to try and explain how the match orchestrator handles all of the communication server-side. I decided to implement it like a sort of mediator/observer. During the game the match class takes care of the match state and the current turn. All messages (from players, generalBoard, TileBag etc) are forwarded to the orchestrator that then decides who to notify with that message. The main Idea is to create a communication protocol (similar to how assembly works (op code, addres etc)) where a single message contains all of the relevant information that needs to b communicated to a certain class. In this way all the flow of message is very clear and makes debugging easy 


## Ideas

* Using a FSM  rapresenting all the states that match can have. Based on that we can comunicate the action we nedd to the game Orchestrator and it will take care of the comunication part. We could implement it using the state pattern
* Memento for memorizing the game state and have persistency 




