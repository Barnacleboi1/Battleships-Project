package battleship;
import java.io.IOException;
import java.util.*;
public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final String Mark = CellState.MARK.getState();
    public static final String Fog = CellState.FOG.getState();
    public static final String Miss = CellState.MISS.getState();
    public static final String Hit = CellState.HIT.getState();
    public static void main(String[] args) throws IOException {
        Player player1 = Player.PLAYER_1;
        PlayingField playingFieldOfPlayerOne = new PlayingField(player1);
        System.out.println("Player 1, place your ships on the game field");
        playingFieldOfPlayerOne.printField();
        playingFieldOfPlayerOne.placeShips();

        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        System.in.read();

        Player player2 = Player.PLAYER_2;
        PlayingField playingFieldOfPlayerTwo = new PlayingField(player2);
        System.out.println("Player 2, place your ships on the game field");
        System.out.println();
        playingFieldOfPlayerTwo.printField();
        playingFieldOfPlayerTwo.placeShips();


        PlayingField playerTwoFieldOfShots = new PlayingField(player2);
        PlayingField playerOneFieldOfShots = new PlayingField(player1);


        int shipsSunkenByPlayer1 = 0;
        int shipsSunkenByPlayer2 = 0;
        Player currentPlayer = player1;
        Coordinate userShot;
        System.out.println("Press Enter and pass the move to another player\n" +
                "...");
        System.in.read();

        while (shipsSunkenByPlayer1 < 5 || shipsSunkenByPlayer2 < 5) {

            if (currentPlayer == player1) {
                playerTwoFieldOfShots.printField();
                System.out.println(" ---------------------");
                playingFieldOfPlayerOne.printField();
                System.out.println();
                System.out.println("Player 1, it's your turn:");
                userShot = parseCoordinates(scanner.next());
                switch (playerTwoFieldOfShots.inputShot(playingFieldOfPlayerTwo, userShot)) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("Error! You entered the wrong coordinates! Try again:");
                        continue;
                    }
                    case 4 -> {
                        playerTwoFieldOfShots.printField();
                        System.out.println();
                        System.out.println("You already shot here!");
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("You hit a ship!");
                    }
                    case 3 -> {
                        System.out.println();
                        System.out.println("You missed!");
                    }
                }
                if (playingFieldOfPlayerTwo.checkSunkenShips(userShot)) {
                    System.out.println("You sank a ship!\n");
                    shipsSunkenByPlayer1++;
                }
                currentPlayer = player2;
            }
            if (shipsSunkenByPlayer1 == 5) {
                break;
            }
            System.out.println("Press Enter and pass the move to another player\n" +
                    "...");
            System.in.read();

            if (currentPlayer == player2) {
                playerOneFieldOfShots.printField();
                System.out.println(" ---------------------");
                playingFieldOfPlayerTwo.printField();

                System.out.println("Player 2, it's your turn:");
                userShot = parseCoordinates(scanner.next());
                switch (playerOneFieldOfShots.inputShot(playingFieldOfPlayerOne, userShot)) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("Error! You entered the wrong coordinates! Try again:");
                        continue;
                    }
                    case 4 -> {
                        playerOneFieldOfShots.printField();
                        System.out.println();
                        System.out.println("You already shot here!");
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("You hit a ship!");
                    }
                    case 3 -> {
                        System.out.println();
                        System.out.println("You missed!");
                    }
                }
                if (playingFieldOfPlayerOne.checkSunkenShips(userShot)) {
                    System.out.println("You sank a ship!\n");
                    shipsSunkenByPlayer2++;
                }
                currentPlayer = player1;
            }
            if (shipsSunkenByPlayer2 == 5) {
                break;
            }
            System.out.println("Press Enter and pass the move to another player\n" +
                    "...");
            System.in.read();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
    public static Coordinate parseCoordinates(String coord) {
        coord = coord.toLowerCase();
        char letter;
        int i;
        int j;
        if (coord.length() == 2) {
            letter = coord.charAt(0);
            i = letter - 'a' + 1;
            j = Integer.parseInt(String.valueOf(coord.charAt(1)));
        }
        else if (coord.length() == 3) {
            letter = coord.charAt(0);
            i = letter - 'a' + 1;
            j = Integer.parseInt(coord.substring(1));
        }
        else {
            return null;
        }
        return new Coordinate(i, j);
    }

}
