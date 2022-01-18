package com.dschwertz.minesweeper.model;

public class Game {
    private final int width;
    private final int height;
    private final int mines;
    private int ctFlags;
    private static int[][] answerField;
    private static int[][] field;

    /*
    Description:    field values
                        (-2) is flagged
                        (-1) is hidden
                        0-8 unhidden & indicates number of adjacent mines
                        9 unhidden & is a mine.
                    answerField is initialized after first left click.
    */
    public Game(int width, int height, int mines) {
        if (width < 1){
            throw new IllegalArgumentException("width must be positive: " + width);
        }
        if (height < 1){
            throw new IllegalArgumentException("height must be positive: " + height);
        }
        if (mines >= (width * height)){
            throw new IllegalArgumentException("Too many mines: " + mines);
        }
        if (mines < 1){
            throw new IllegalArgumentException("Mines must be positive: " + mines);
        }

        this.width = width;
        this.height = height;
        this.mines = mines;
        this.ctFlags = 0;
        answerField = null;
        field = new int[height][width];
        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                field[i][j] = -1;
            }
        }
    }

    @Override
    public String toString() {
        String header = String.format("Minesweeper: height = %d, width = %d, mines = %d%n", height, width, mines);
        StringBuilder result = new StringBuilder(header);

        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                if (field[i][j] == -1) {
                    result.append(".");
                } else if (field[i][j] == -2){
                    result.append("F");
                } else {
                    result.append(field[i][j]);
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    /*
    Description: Mechanics of left click. Origin is upper left corner of field.
    Parameters: column (x value), row (y value)
    Return: Int
        (-1) = Clicked on a mine
        0 = Square is uncovered, field is flooded, play continues.
        1 = Game is won (i.e. all coordinates are uncovered except for mines)
     */
    public int left(int col, int row) {
        if (col < 0 || width <= col) throw new RuntimeException("Incorrect col: " + col);
        if (row < 0 || height <= row) throw new RuntimeException("Incorrect row: " + row);

//        First left click, randomly set mines in answerField
        if (answerField == null) {
            answerField = new int[height][width];
            int[] dRow = {-1, -1, 0, 1, 1, 1, 0, -1};
            int[] dCol = {0, 1, 1, 1, 0, -1, -1, -1};

            for (int i = 0; i < mines; ++i) {
                int randCol = (int) (Math.random() * width);
                int randRow = (int) (Math.random() * height);

//                If the random coordinate is the first click coordinate or already a mine, try again
                if (randRow == row && randCol == col || answerField[randRow][randCol] == 9){
                    --i;
                } else {
                    answerField[randRow][randCol] = 9;
                    for (int j = 0; j < dRow.length; ++j){
                        int tempRow = randRow + dRow[j];
                        int tempCol = randCol + dCol[j];
                        if (tempRow >= 0 && tempRow < height && tempCol >= 0 && tempCol < width && answerField[tempRow][tempCol] != 9){
                            ++answerField[tempRow][tempCol];
                        }
                    }
                }
            }
        }

        if (field[row][col] != -1){
            return 0;
        }

        switch (answerField[row][col]) {
            case 0:
                try {
                    Flood.flood(field, answerField, new Coordinate(col, row), 0);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 9:
                return -1;
            default:
                field[row][col] = answerField[row][col];
        }

//       Test for win condition
        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j) {
                if (field[i][j] < 0){
                    if (answerField[i][j] != 9){
                        return 0;
                    }
                }
            }
        }

    return 1;
    }

    public void right(int col, int row) {
        if (field[row][col] == -1){
            field[row][col] = -2;
            ++ctFlags;
        } else if (field[row][col] == -2) {
            field[row][col] = -1;
            --ctFlags;
        }
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getMines(){
        return this.mines;
    }

    public int getFlags(){
        return this.ctFlags;
    }

    public int[][] getField(){
        return field;
    }

    public int[][] getAnswer() {
        if (answerField == null) throw new NullPointerException("Cannot show answer before first left click");
        return answerField;
    }
}
