package com.dschwertz.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Game Test")
class GameTest {
    Game testGame;

    @BeforeEach
    void beforeEach() {
        testGame = new Game(10, 10, 5);
    }

    @Test
    @DisplayName("Initialize game")
    void init() {
        assertThrows(IllegalArgumentException.class,
                () -> new Game(0, 10, 5),
                "Invalid width");
        assertThrows(IllegalArgumentException.class,
                () -> new Game(10, 0, 5),
                "Invalid height");
        assertThrows(IllegalArgumentException.class,
                () -> new Game(10, 10, 0),
                "Invalid number of mines");
        assertEquals(10, testGame.getHeight(), "Width = 10, Height = 10, Mines = 5");
    }

    @Nested
    @DisplayName("Game Test - left clicks")
    class LeftClicks {
        @Test
        @DisplayName("No show before first left click")
        void beforeFirstLeft() {
            assertThrows(NullPointerException.class,
                    () -> testGame.getAnswer(),
                    "answerField not initialized before first left click");
        }

        @Test
        @DisplayName("Valid left click space")
        void validSpace() {
            assertThrows(RuntimeException.class,
                    () -> testGame.left(-1, 5),
                    "Invalid row click");
            assertThrows(RuntimeException.class,
                    () -> testGame.left(5, -1),
                    "Invalid col click");
        }

        @Test
        @DisplayName("Mine-less first left click")
        void firstLeft() {
            testGame = new Game(100, 100, 9999);
            testGame.left(0, 0);
            assertNotEquals(9, testGame.getAnswer()[0][0]);
        }


        @Nested
        @DisplayName("Game Test - right clicks")
        class RightClicks {
            @Test
            @DisplayName("Valid right click space")
            void rightSpace() {
                assertThrows(RuntimeException.class,
                        () -> testGame.left(-1, 5),
                        "Invalid row click");
                assertThrows(RuntimeException.class,
                        () -> testGame.left(5, -1),
                        "Invalid col click");
            }

            @Test
            @DisplayName("Toggles flags")
            void toggleFlag() {
                assertNotEquals(-2, testGame.getField()[0][0]);
                testGame.right(0,0);
                testGame.left(1,1);
                assertEquals(-2, testGame.getField()[0][0]);
                testGame.right(0,0);
                assertNotEquals(-2, testGame.getField()[0][0]);
            }
        }
    }
}