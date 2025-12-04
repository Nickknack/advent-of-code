package nickknack.ca.day4;

public enum Direction {
	NORTH(-1, 0),
	NORTH_EAST(-1, 1),
	EAST(0, 1),
	SOUTH_EAST(1, 1),
	SOUTH(1, 0),
	SOUTH_WEST(1, -1),
	WEST(0, -1),
	NORTH_WEST(-1, -1);

	private int row;
	private int col;

	Direction(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
