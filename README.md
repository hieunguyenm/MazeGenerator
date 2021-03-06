# Maze Generator

Maze generator using a [modified randomized version of Prim's Algorithm](https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim.27s_algorithm) written in Java.

Graphics are drawn using the [Processing](https://processing.org) library.

**Disclaimer**: This was written in first year of university so code is bad :P

## Information
From a grid, random adjacent cells that are not part of the maze but are adjacent to cells that are part of the maze 
are called "*frontiers*". 

The program keeps track of these frontier cells and randomly chooses a frontier cell to make 
part of the maze. It then keeps track of the new frontier cells from the new part of the maze.

This process is repeated until there are no more frontier cells left.

## Example

![](https://i.imgur.com/wYhd2DX.gif)
