package nickknack.ca.day6;

class Guard {
	private Position position;
	private Direction facing;
	private final Position initialPosition;
	private final Direction initialFacing;

	Guard(int row, int column, char symbol) {
		this.position = new Position(row, column);
		this.facing = Direction.fromSymbol(symbol);
		this.initialPosition = this.position;
		this.initialFacing = this.facing;
	}

	Position getNextPosition() {
		return new Position(position.row() + facing.getRowMover(), position.column() + facing.getColumnMover());
	}

	Position move() {
		position = getNextPosition();
		return position;
	}

	Position getPosition() {
		return position;
	}

	Direction getFacing() {
		return facing;
	}

	void resetGuard() {
		position = initialPosition;
		facing = initialFacing;
	}

	Direction turn() {
		facing = facing.nextDirection();
		return facing;
	}
}
