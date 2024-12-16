package nickknack.ca.day12;

enum Direction {
	UP(-1, 0),
	RIGHT(0, 1),
	DOWN(1, 0),
	LEFT(0, -1);

	private final int rowMover;
	private final int columnMover;

	Direction(int rowMover, int columnMover) {
		this.rowMover = rowMover;
		this.columnMover = columnMover;
	}

	int getRowMover() {
		return rowMover;
	}

	int getColumnMover() {
		return columnMover;
	}
}
