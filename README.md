# Minesweeper Model

A clone of the classic Minesweeper game model with a simple text-based implementation.

## Getting Started

### Running a Game

To run the text-based version of Minesweeper in your terminal

1. Clone or download and unzip the repository
2. In terminal, navigate to `MinesweeperClone/out/artifacts/Minesweeper_sample_jar`
3. Verify java is installed with `java --version`
4. Run the game with `java -jar Minesweeper.jar`

### Gameplay Notes

###### Coordinates

The coordinates are 0 based with an origin in the upper left corner of the board

- `left 0 0` will reveal the upper-left most square
- `right 9 9` will flag the bottom-right most square of a 10x10 board

###### Difficulty

The default difficulty is Beginner with a 10x10 board with 5 mines. To change the difficulty, add an argument to the jar command.

- Intermediate 16x16 game with 40 mines: `java -jar Minesweeper.jar intermediate`
- Expert 16x30 game with 99 mines: `java -jar Minesweeper.jar expert`
