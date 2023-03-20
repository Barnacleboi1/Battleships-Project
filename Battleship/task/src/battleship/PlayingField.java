package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static battleship.Main.*;
import static battleship.Ships.shipsList;

public class PlayingField {

    private String[][] playingField = new String[11][11];
    private Player ownerOfField;
    List<List<Coordinate>> shipsOnField = new ArrayList<>() {
    };
    public List<List<Coordinate>> getShipsOnField() {
        return shipsOnField;
    }
    public String[][] getPlayingField() {
        return playingField;
    }

    public Player getOwnerOfField() {
        return ownerOfField;
    }
    public PlayingField(Player ownerOfField) {
        this.ownerOfField = ownerOfField;
        char temp = 'A';
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0 && j == 0) {
                    playingField[i][j] = " ";
                } else if (i == 0) {
                    playingField[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    playingField[i][j] = String.valueOf(temp++);
                } else {
                    playingField[i][j] = Fog;
                }
            }
        }
        List<Coordinate> shipOne = new ArrayList<>();
        List<Coordinate> shipTwo = new ArrayList<>();
        List<Coordinate> shipThree = new ArrayList<>();
        List<Coordinate> shipFour = new ArrayList<>();
        List<Coordinate> shipFive = new ArrayList<>();

        shipsOnField.add(shipOne);
        shipsOnField.add(shipTwo);
        shipsOnField.add(shipThree);
        shipsOnField.add(shipFour);
        shipsOnField.add(shipFive);
    }
    public void printField() {
        for (int i = 0; i < 11; i++)
        {
            for (int j = 0; j < 11; j++)
            {
                System.out.print(" " + playingField[i][j]);
            }
            System.out.println();
        }
    }
    //this method returns a code
    // 1 if the input is wrong
    // 2 if the input was a hit
    // 3 if the input was a miss
    public int inputShot(PlayingField fieldBeingShotAt, Coordinate userShot) {
        int code = 0;
        try {
            if (Objects.equals(fieldBeingShotAt.getPlayingField()[userShot.getI()][userShot.getJ()], Mark)) {
                playingField[userShot.getI()][userShot.getJ()] = Hit;
                fieldBeingShotAt.getPlayingField()[userShot.getI()][userShot.getJ()] = Hit;
                code = 2;
            }
            else if (Objects.equals(fieldBeingShotAt.getPlayingField()[userShot.getI()][userShot.getJ()], Fog)) {
                playingField[userShot.getI()][userShot.getJ()] = Miss;
                fieldBeingShotAt.getPlayingField()[userShot.getI()][userShot.getJ()] = Miss;
                code = 3;
            }
            else if (Objects.equals(fieldBeingShotAt.getPlayingField()[userShot.getI()][userShot.getJ()], Hit)
                    || Objects.equals(fieldBeingShotAt.getPlayingField()[userShot.getI()][userShot.getJ()], Miss)) {
                code = 4;
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            code = 1;
        }
        return code;
    }
    public void placeShips() {
        int shipNumber = 0;
        while (shipNumber < 5)
        {
            Ships currentShip = shipsList.get(shipNumber);
            String currentShipString = currentShip.toString().toLowerCase().replace('_', ' ');
            System.out.println();
            System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                    currentShipString, currentShip.getCells());
            System.out.println();
            String coords = scanner.nextLine();

            Coordinate coordOne = parseCoordinates(coords.split(" ")[0]);
            Coordinate coordTwo = parseCoordinates(coords.split(" ")[1]);

            switch (wrongInput(coordOne, coordTwo, currentShip)) {
                case 1 -> {
                    System.out.println();
                    System.out.printf("Error! Wrong length of the %s! Try again:", currentShipString);
                    continue;
                }
                case 2 -> {
                    System.out.println("Error! Wrong ship location! Try again:");
                    continue;
                }
                case 3 -> {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    continue;
                }
                case 4 -> {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    continue;
                }
                case 0 -> {
                }
            }
            getCoordsInput(shipNumber, coordOne, coordTwo);
            printField();
            shipNumber++;
        }
    }
    public int wrongInput(Coordinate coordOne, Coordinate coordTwo, Ships currentShip)
    {
        int shipLength = currentShip.getCells();
        int code = 2;


        if (coordOne.getI() == coordTwo.getI())
        {
            if (coordOne.getJ() > coordTwo.getJ())
            {
                try {
                    code = Objects.equals(playingField[coordOne.getI()][coordOne.getJ() + 1], Mark) || Objects.equals(playingField[coordOne.getI()][coordTwo.getJ() - 1], Mark) ? 3 : 0;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    if (coordOne.getJ() == 10){
                        code = Objects.equals(playingField[coordOne.getI()][coordTwo.getJ() - 1], Mark) ? 3 : 0;
                    }
                    else {
                        code = 4;
                    }
                }
            }
            else if (coordTwo.getJ() > coordOne.getJ()) {
                try {
                    code = Objects.equals(playingField[coordOne.getI()][coordOne.getJ() - 1], Mark)
                            || Objects.equals(playingField[coordOne.getI()][coordTwo.getJ() + 1], Mark) ? 3 : 0;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    if (coordTwo.getJ() == 10) {
                        code = Objects.equals(playingField[coordOne.getI()][coordOne.getJ() - 1], Mark) ? 3 : 0;
                    }
                    else {
                        code = 4;
                    }
                }
            }
            code = Math.abs(coordTwo.getJ() - coordOne.getJ()) + 1 != shipLength ? 1 : code;

        }
        else if (coordOne.getJ() == coordTwo.getJ())
        {
            if (coordOne.getI() > coordTwo.getI())
            {
                try {
                    code = Objects.equals(playingField[coordOne.getI() + 1][coordOne.getJ()], Mark) || Objects.equals(playingField[coordTwo.getI() - 1][coordTwo.getJ()], Mark) ? 3 : 0;
                }
                catch (ArrayIndexOutOfBoundsException e){
                    if (coordOne.getI() == 10) {
                        code = Objects.equals(playingField[coordTwo.getI() - 1][coordTwo.getJ()], Mark) ? 3 : 0;
                    }
                    else {
                        code = 4;
                    }
                }
            }
            else if (coordOne.getI() < coordTwo.getI()) {
                try {
                    code = Objects.equals(playingField[coordOne.getI() - 1][coordOne.getJ()], Mark)
                            || Objects.equals(playingField[coordTwo.getI() + 1][coordTwo.getJ()], Mark) ? 3 : 0;
                }
                catch (ArrayIndexOutOfBoundsException e){
                    if (coordTwo.getI() == 10) {
                        code = Objects.equals(playingField[coordOne.getI() - 1][coordOne.getJ()], Mark) ? 3 : 0;
                    }
                    else {
                        code = 4;
                    }
                }
            }
            code = Math.abs(coordTwo.getI() - coordOne.getI()) + 1 != shipLength ? 1 : code;
        }
        return code;
    }
    public void getCoordsInput(int shipNumber, Coordinate coordOne, Coordinate coordTwo) {


        int shipLength = shipsList.get(shipNumber).getCells();


        playingField[coordOne.getI()][coordOne.getJ()] = Mark;
        playingField[coordTwo.getI()][coordTwo.getJ()] = Mark;

        if (coordOne.getI() == coordTwo.getI()) {
            if (coordTwo.getJ() > coordOne.getJ()) {
                for (int i = 0; i < shipLength; i++) {
                    playingField[coordOne.getI()][coordTwo.getJ() - i] = Mark;
                    shipsOnField.get(shipNumber).add(new Coordinate(coordOne.getI(), coordTwo.getJ() - i));

                }
            } else {
                for (int i = 0; i < shipLength; i++) {
                    playingField[coordOne.getI()][coordOne.getJ() - i] = Mark;
                    shipsOnField.get(shipNumber).add(new Coordinate(coordOne.getI(), coordOne.getJ() - i));
                }
            }
        } else if (coordOne.getJ() == coordTwo.getJ()) {
            if (coordTwo.getI() > coordOne.getI()) {
                for (int i = 0; i < shipLength; i++) {
                    playingField[coordTwo.getI() - i][coordTwo.getJ()] = Mark;
                    shipsOnField.get(shipNumber).add(new Coordinate(coordTwo.getI() - i, coordTwo.getJ()));
                }
            } else {
                for (int i = 0; i < shipLength; i++) {
                    playingField[coordOne.getI() - i][coordOne.getJ()] = Mark;
                    shipsOnField.get(shipNumber).add(new Coordinate(coordOne.getI() - i, coordOne.getJ()));
                }
            }
        }
    }
    public boolean checkSunkenShips(Coordinate userShot) {
        List<Coordinate> currentShip = new ArrayList<>();
        boolean result = false;
        int temp;
        for (List<Coordinate> ship : shipsOnField) {
            for (Coordinate coordinate : ship) {
                if (coordinate.getI() == userShot.getI() && coordinate.getJ() == userShot.getJ()) {
                    currentShip = ship;
                    break;
                }
            }
        }
        if (currentShip.isEmpty()) {
            result = false;
        }
        else {
            temp = 0;
            for (Coordinate coordinate : currentShip) {
                if (Objects.equals(playingField[coordinate.getI()][coordinate.getJ()], Mark)) {
                    temp++;
                    result = false;
                }
                if (temp == 0) {
                    result = true;
                }
            }
        }
        return result;
    }
}


