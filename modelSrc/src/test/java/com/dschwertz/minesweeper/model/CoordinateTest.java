package com.dschwertz.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Coordinate Test")
public class CoordinateTest {

    @Test
    @DisplayName("Compare coordinates")
    void init() {
        Coordinate a = new Coordinate(-1, 1);
        Coordinate a2 = new Coordinate(-1, 1);
        Coordinate b = new Coordinate(0,0);

        assertEquals(a, a2);
        assertNotEquals(a, b);
    }
}
