package nickknack.ca.day9;

import java.util.ArrayList;
import java.util.List;

public class File {
	private final long[] blocks;
	private int freeSpaceIndex;

	private static final char FREE_SPACE = '.';

	public File(long id, int blocks, int freeSpace) {
		this.freeSpaceIndex = blocks;
		final int maxSize = blocks + freeSpace;
		this.blocks = new long[maxSize];
		for (int i = 0; i < blocks; i++) {
			this.blocks[i] = id;
		}
	}

	public boolean hasFreeSpace() {
		return freeSpaceIndex < blocks.length;
	}

	public boolean addValueToSpace(long value) {
		if (hasFreeSpace()) {
			blocks[freeSpaceIndex] = value;
			freeSpaceIndex++;
			return true;
		}

		return false;
	}

	public boolean isEmpty() {
		return freeSpaceIndex == 0;
	}

	public long popValue() {
		if (isEmpty()) {
			throw new RuntimeException("Can't pop value, there are no values left!");
		}

		return blocks[--freeSpaceIndex];
	}

	public List<Long> getBlocks() {
		List<Long> blockList = new ArrayList<>();
		for (int i = 0; i < freeSpaceIndex; i++) {
			blockList.add(blocks[i]);
		}
		return blockList;
	}
}
