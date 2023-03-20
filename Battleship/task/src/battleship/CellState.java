package battleship;

public enum CellState {
    FOG("~"),
    MARK("O"),
    HIT("X"),
    MISS("M");

    private String state;
    public static final String Mark = CellState.MARK.getState();
    public static final String Fog = CellState.FOG.getState();
    public static final String Miss = CellState.MISS.getState();
    public static final String Hit = CellState.HIT.getState();
    CellState(String state) {
            this.state = state;
    }

    public String getState() {
        return state;
    }

}
