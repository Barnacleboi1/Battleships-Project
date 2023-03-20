package battleship;

public class Coordinate {
    private final int i, j;

    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }
    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
    @Override
    public String toString() {
        return String.format("coord i: %d, coord j: %d", i, j);
    }
}
