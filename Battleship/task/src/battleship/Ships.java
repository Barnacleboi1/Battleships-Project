package battleship;
import java.util.*;
public enum Ships {
    AIRCRAFT_CARRIER(5),
    BATTLESHIP(4),
    SUBMARINE(3),
    CRUISER(3),
    DESTROYER(2),
    ;
    public static final List<Ships> shipsList = List.of(Ships.values());
    private final int cells;
    Ships(int cells) {
        this.cells = cells;
    }
    public int getCells() {
        return cells;
    }
}
