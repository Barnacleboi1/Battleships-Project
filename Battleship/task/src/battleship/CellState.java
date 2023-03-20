package battleship;

public enum CellState {
    FOG("~"),
    MARK("O"),
    HIT("X"),
    MISS("M");

    private String state;

    CellState(String state) {
            this.state = state;
    }

    public String getState() {
        return state;
    }

}
