package nickknack.ca.day12;

import java.util.Optional;

public class Plot {
	private char type;
	private Integer fences;
	private boolean inArea;
	private int corners;

	Plot(char type) {
		this.type = type;
		inArea = false;
		corners = 0;
	}

	public void setFences(int fences) {
		this.fences = fences;
	}

	public void setCorners(int corners) {
		this.corners = corners;
	}

	public int getCorners() {
		return corners;
	}

	public Optional<Integer> getFences() {
		return Optional.ofNullable(fences);
	}

	public char getType() {
		return type;
	}

	public void addToArea() {
		this.inArea = true;
	}

	public boolean isInArea() {
		return inArea;
	}
}
