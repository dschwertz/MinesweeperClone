package com.dschwertz.minesweeper.model;

import java.util.HashSet;

public class Flood {
    private static final int[] dCol = {0, 1, 1, 1, 0, -1, -1, -1};
    private static final int[] dRow = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final HashSet<Coordinate> floodSet = new HashSet<>();

    /*
    Description: Flood coordinates in a field. Also floods perimeter nodes. Nodes with value (-2) are not flooded.
    Return: NONE (updates the static field by reference)
    */
    public static void flood(int[][] field, int[][] answerField, Coordinate node, int replacement){
        floodFill(field, answerField, node, replacement);
        for (Coordinate a:
                floodSet) {
            field[a.y][a.x] = answerField[a.y][a.x];
        }
        floodSet.clear();
    }

    private static void floodFill(int[][] field, int[][] answerField, Coordinate node, int replacement) {
        if (field == null || field.length == 0) throw new IllegalArgumentException("Invalid field");

        int target = answerField[node.y][node.x];

        if (field[node.y][node.x] == -2) return;        // flood exception (mine flag == -2)
        if (field[node.y][node.x] == replacement) return;

        field[node.y][node.x] = replacement;

        for (int i = 0; i < dRow.length; ++i) {
            Coordinate next = new Coordinate(node.x + dCol[i], node.y + dRow[i]);
            if (isValid(answerField, next, target)) {
                floodFill(field, answerField, next, replacement);
            } else if (isValid(field, next)) {
                floodSet.add(next);
            }
        }
    }

    private static boolean isValid(int[][] field, Coordinate node) {
        return node.y >= 0 && node.y < field.length && node.x >= 0 && node.x < field[0].length;
    }

    private static boolean isValid(int[][] field, Coordinate node, int target) {
        return isValid(field, node) && field[node.y][node.x] == target;
    }
}
