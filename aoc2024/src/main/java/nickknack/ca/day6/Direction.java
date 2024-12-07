package nickknack.ca.day6;

import java.util.Arrays;
import java.util.Optional;

enum Direction {
	UP(-1, 0, '^'),
	RIGHT(0, 1, '>'),
	DOWN(1, 0, 'v'),
	LEFT(0, -1, '<');

	private final int rowMover;
	private final int columnMover;
	private final char symbol;

	Direction(int rowMover, int columnMover, char symbol) {
		this.rowMover = rowMover;
		this.columnMover = columnMover;
		this.symbol = symbol;
	}

	static Direction fromSymbol(char symbol) {
		Optional<Direction> direction = Arrays.stream(Direction.values())
				.filter(d -> d.symbol == symbol)
				.findAny();

		return direction.orElseThrow(() -> new RuntimeException("Invalid symbol [%s]".formatted(symbol)));
	}

	Direction nextDirection() {
		return switch(this) {
			case UP -> RIGHT;
			case RIGHT -> DOWN;
			case DOWN -> LEFT;
			case LEFT -> UP;
		};
	}

	int getRowMover() {
		return rowMover;
	}

	int getColumnMover() {
		return columnMover;
	}
}
