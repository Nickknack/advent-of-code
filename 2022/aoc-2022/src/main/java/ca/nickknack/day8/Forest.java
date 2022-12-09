package ca.nickknack.day8;

public class Forest {
    private Tree[][] trees;
    private final int rows;
    private final int columns;

    public Forest(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        trees = new Tree[rows][columns];
    }

    public void addTree(int row, int column, int size) {
        trees[row][column] = new Tree(size);
    }

    public int visibleTreesCount() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isTreeVisible(i, j)) {
                    count++;
                }
            }
        }

        return count;
    }

    public int calculateGreatestScenicScore() {
        int bestScenicScore = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int treeScenicScore = calculateTreeScenicScore(i, j);
                if (treeScenicScore > bestScenicScore) {
                    bestScenicScore = treeScenicScore;
                }
            }
        }

        return bestScenicScore;
    }

    private boolean isTreeVisible(int row, int column) {
        return  isTreeVisibleNorth(row, column) || isTreeVisibleSouth(row, column) || isTreeVisibleWest(row, column) || isTreeVisibleEast(row, column);
    }

    private boolean isTreeVisibleNorth(int row, int column) {
        int treeSize = trees[row][column].getSize();

        for (int i = row - 1; i >= 0; i--) {
            if (trees[i][column].getSize() >= treeSize) {
                return false;
            }
        }

        return true;
    }

    private boolean isTreeVisibleSouth(int row, int column) {
        int treeSize = trees[row][column].getSize();

        for (int i = row + 1; i < this.rows; i++) {
            if (trees[i][column].getSize() >= treeSize) {
                return false;
            }
        }

        return true;
    }

    private boolean isTreeVisibleWest(int row, int column) {
        int treeSize = trees[row][column].getSize();

        for (int i = column - 1; i >= 0; i--) {
            if (trees[row][i].getSize() >= treeSize) {
                return false;
            }
        }

        return true;
    }

    private boolean isTreeVisibleEast(int row, int column) {
        int treeSize = trees[row][column].getSize();

        for (int i = column + 1; i < this.columns; i++) {
            if (trees[row][i].getSize() >= treeSize) {
                return false;
            }
        }

        return true;
    }

    private int calculateTreeScenicScore(int row, int column) {
        return calculateNorthViewingDistance(row, column) * calculateSouthViewingDistance(row, column) * calculateWestViewingDistance(row, column) * calculateEastViewingDistance(row, column);
    }

    private int calculateNorthViewingDistance(int row, int column) {
        int treeSize = trees[row][column].getSize();
        int viewingDistance = 0;

        for (int i = row - 1; i >= 0; i--) {
            viewingDistance++;
            if (trees[i][column].getSize() >= treeSize) {
                return viewingDistance;
            }
        }

        return viewingDistance;
    }

    private int calculateSouthViewingDistance(int row, int column) {
        int treeSize = trees[row][column].getSize();
        int viewingDistance = 0;

        for (int i = row + 1; i < this.rows; i++) {
            viewingDistance++;
            if (trees[i][column].getSize() >= treeSize) {
                return viewingDistance;
            }
        }

        return viewingDistance;
    }

    private int calculateWestViewingDistance(int row, int column) {
        int treeSize = trees[row][column].getSize();
        int viewingDistance = 0;

        for (int i = column - 1; i >= 0; i--) {
            viewingDistance++;
            if (trees[row][i].getSize() >= treeSize) {
                return viewingDistance;
            }
        }

        return viewingDistance;
    }

    private int calculateEastViewingDistance(int row, int column) {
        int treeSize = trees[row][column].getSize();
        int viewingDistance = 0;

        for (int i = column + 1; i < this.columns; i++) {
            viewingDistance++;
            if (trees[row][i].getSize() >= treeSize) {
                return viewingDistance;
            }
        }

        return viewingDistance;
    }
}
