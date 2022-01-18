package com.dschwertz.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("Flood Test")
public class FloodTest {
    int[][] field;
    int[][] answerField;
    int[][] expected;

    @BeforeEach
    void beforeEach() {
        field = new int[][] {
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
    }

    @Test
    @DisplayName("No field")
    void noField() {
        assertThrows(IllegalArgumentException.class,
                () -> Flood.flood(answerField, answerField, new Coordinate(1,1), 2),
                "Invalid Field");
    }

    @Test
    @DisplayName("Already flooded")
    void alreadyFlooded() {
        field = new int[][] {
                {0,0,0,0,0},
                {0,2,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        answerField = new int[][] {
                {0,0,0,0,0},
                {0,2,2,0,0},
                {0,2,2,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        expected = new int[][] {
                {0,0,0,0,0},
                {0,2,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        Flood.flood(field, answerField, new Coordinate(1,1), 2);
        assertTrue(Arrays.deepEquals(expected, field));
    }

    @Test
    @DisplayName("Test single flood")
    void singleFlood() {
        answerField = new int[][] {
                {0,0,0,0,0},
                {0,1,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        expected = new int[][] {
                {0,0,0,0,0},
                {0,2,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        Flood.flood(field, answerField, new Coordinate(1,1), 2);
        assertTrue(Arrays.deepEquals(expected, field));
    }

    @Test
    @DisplayName("Test row flood")
    void rowFlood() {
        answerField = new int[][] {
                {0,0,0,0,0},
                {0,1,1,1,1},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        expected = new int[][] {
                {0,0,0,0,0},
                {0,2,2,2,2},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        Flood.flood(field, answerField, new Coordinate(1,1), 2);
        assertTrue(Arrays.deepEquals(expected, field));
    }

    @Test
    @DisplayName("Test block flood")
    void blockFlood() {
        answerField = new int[][] {
                {0,0,0,0,0},
                {0,1,1,0,0},
                {0,1,1,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        expected = new int[][] {
                {0,0,0,0,0},
                {0,2,2,0,0},
                {0,2,2,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        Flood.flood(field, answerField, new Coordinate(1,1), 2);
        assertTrue(Arrays.deepEquals(expected, field));
    }

    @Test
    @DisplayName("Test ring flood")
    void ringFlood() {
        answerField = new int[][] {
                {0,0,0,0,0},
                {1,1,1,1,1},
                {1,0,0,0,1},
                {1,1,1,0,1},
                {0,0,1,1,1}
        };
        expected = new int[][] {
                {0,0,0,0,0},
                {2,2,2,2,2},
                {2,0,0,0,2},
                {2,2,2,0,2},
                {0,0,2,2,2}
        };
        Flood.flood(field, answerField, new Coordinate(1,1), 2);
        assertTrue(Arrays.deepEquals(expected, field));
    }

    @Test
    @DisplayName("Test edges")
    void edges() {
        answerField = new int[][] {
                {6,7,7,4,8},
                {6,1,1,1,1},
                {5,5,1,4,4},
                {9,3,1,3,5},
                {9,3,3,3,5}
        };
        expected = new int[][] {
                {6,7,7,4,8},
                {6,2,2,2,2},
                {5,5,2,4,4},
                {0,3,2,3,0},
                {0,3,3,3,0}
        };
        Flood.flood(field, answerField, new Coordinate(1,1), 2);
        assertTrue(Arrays.deepEquals(expected, field));
    }

    @Test
    @DisplayName("Do not flood flags (-2)")
    void flags() {
        field = new int[][] {
                {0,0,0,0,0},
                {0,0,0,-2,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        answerField = new int[][] {
                {6,7,7,4,1},
                {6,1,1,1,1},
                {5,5,1,4,1},
                {9,3,1,3,5},
                {9,3,3,3,5}
        };
        expected = new int[][] {
                {6,7,7,4,0},
                {6,2,2,-2,0},
                {5,5,2,4,0},
                {0,3,2,3,0},
                {0,3,3,3,0}
        };
        Flood.flood(field, answerField, new Coordinate(1,1), 2);
        assertTrue(Arrays.deepEquals(expected, field));
    }
}
