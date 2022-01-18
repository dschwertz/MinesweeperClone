package console;

import com.dschwertz.minesweeper.model.Game;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Game game = null;
        try{
            game = parseArguments(args);
        } catch (NumberFormatException e){
            System.out.println("Incorrect integer values: " + Arrays.toString(args));
            System.exit(1);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println(helpToString());

        while (true) {
            System.out.println(game);
            try {
                String cmd = in.nextLine();
                Scanner inCmd = new Scanner(cmd);
                String first = inCmd.next();
                switch (first) {
                    case "left":
                        try {
                            int col = Integer.parseInt(inCmd.next());
                            int row = Integer.parseInt(inCmd.next());
                            int gameCondition = game.left(col, row);
                            if (gameCondition == -1) {
                                int[][] show = game.getAnswer();
                                System.out.println(fieldToString(show, game.getHeight(), game.getWidth()));
                                System.out.println("Better luck next time");
                                System.exit(0);
                            } else if (gameCondition == 1) {
                                int[][] show = game.getAnswer();
                                System.out.println(fieldToString(show, game.getHeight(), game.getWidth()));
                                System.out.println("Congratulations, you won!");
                                System.exit(0);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect input: " + e.getMessage());
                        } catch (RuntimeException e){
                            System.out.println("Invalid coordinates: " + e.getMessage());
                        }
                        break;
                    case "right":
                        try {
                            int col = inCmd.nextInt();
                            int row = inCmd.nextInt();
                            game.right(col, row);
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect coordinates");
                        }
                        break;
                    case "show":
                        try {
                            int[][] show = game.getAnswer();
                            System.out.println(fieldToString(show, game.getHeight(), game.getWidth()));
                        }catch (NullPointerException e) {
                            System.out.println("Display error: " + e.getMessage());
                        }
                        break;
                    case "quit":
                        System.out.println("Thanks for playing!");
                        System.exit(0);
                        break;
                    case "help":
                        System.out.println(helpToString());
                        break;
                }
                inCmd.close();
            } catch (NoSuchElementException e){
                System.out.println("Bye");
                System.exit(0);
            }
        }
    }

    static Game parseArguments(String[] args){
        int height;
        int width;
        int mines;

        if (args.length == 3){
            height = Integer.parseInt(args[0]);
            width = Integer.parseInt(args[1]);
            mines = Integer.parseInt(args[2]);

            return new Game(width, height, mines);
        } else if (args.length == 1){
            return switch (args[0]) {
                case "beginner" -> new Game(10, 10, 5);
                case "intermediate" -> new Game(16, 16, 40);
                case "expert" -> new Game(30, 16, 99);
                default -> throw new RuntimeException("Invalid Game Type");
            };
        } else if (args.length == 0){
            return new Game(10, 10, 5);
        }
        throw new RuntimeException("Usage: java console.Main <height> <width> <mines>");
    }

    private static String helpToString() {
        return "Help:\n" +
                "left <col> <row>\n" +
                "  -left click with coordinates (col, row)\n" +
                "right <col> <row>\n" +
                "  -right click with coordinates (col, row)\n" +
                "show\n" +
                "  -show all the mines (cheating)\n" +
                "quit\n" +
                "  -quit the game (EOF works as well)\n" +
                "help\n" +
                "  -displays this menu\n" +
                "";
    }

    private static StringBuilder fieldToString(int[][] field, int height, int width) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                if (field[i][j] == -1) {
                    result.append(".");
                } else if (field[i][j] == 9) {
                    result.append("*");
                } else {
                    result.append(field[i][j]);
                }
            }
            result.append("\n");
        }
        return result;
    }
}
