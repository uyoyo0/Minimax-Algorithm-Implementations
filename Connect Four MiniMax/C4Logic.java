
public class C4Logic {
	private Cell pointer;
	private boolean[][] visited = new boolean[6][7];
	final int MAX_ROW = 0;// The highest row has an index value of 0
	final int MIN_ROW = 5;// The lowest row has an index value of 5
	final int MAX_COL = 6;// The farthest to the right the row can be is index 6
	final int MIN_COL = 0;// The farthest to the left the row can be is index 0

	public Cell findCell(Cell[][] board, int x, int y) {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				pointer = board[i][j];
				/**
				 * Check whether or not the x and y variable the user clicked was inside of a
				 * cell I have to add roughly 60 to their x and y values because thats what
				 * their dimensions are (roughly)
				 */
				if (x > pointer.getX() - 15 && x < pointer.getX() + 65 && y > pointer.getY() + 35
						&& y < pointer.getY() + 100) {
					if (isValid(board, i, j)) {
						return pointer;
					} else {
						System.out.println("Invalid placement!");
					}
				}
			}
		}
		return null;

	}

	public boolean isValid(Cell[][] board, int i, int j) {
		if (board[i][j].getState() != States.EMPTY) {
			return false;
		}
		if (board[i][j].getState() == States.EMPTY && i == board.length - 1
				|| board[i + 1][j].getState() != States.EMPTY) {
			return true;
		}
		return false;
	}

	public int staticEval(Cell[][] board, States state) {
		int temp;
		int numMax = 0;// The amount of max consecutive pieces
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getState() == state) {

					temp = vertCheck(board, i, j);
					if (temp == 3)
						numMax++;
					resetVisited();

					temp = dCheck2(board, i, j, state);
					if (temp == 3)
						numMax++;
					resetVisited();

					temp = dCheck1(board, i, j, state);
					if (temp == 3)
						numMax++;
					resetVisited();

					temp = horzCheck(board, i, j, state);
					if (temp == 3)
						numMax++;
					resetVisited();
				}
			}
		}
		return numMax;
	}

	public boolean checkGame(Cell[][] board) {
		int max = -1;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getState() != States.EMPTY) {
					max = Math.max(max, vertCheck(board, i, j));
					resetVisited();
					max = Math.max(max, dCheck2(board, i, j, board[i][j].getState()));
					resetVisited();
					max = Math.max(max, dCheck1(board, i, j, board[i][j].getState()));
					resetVisited();
					max = Math.max(max, horzCheck(board, i, j, board[i][j].getState()));
					resetVisited();
				}
			}
		}
		return max >= 4;
	}

	public int vertCheck(Cell[][] board, int row, int col) {
		if (row == MIN_ROW) {
			return 1;
		}
		if (board[row + 1][col].getState() == board[row][col].getState()) {
			return 1 + vertCheck(board, row + 1, col);
		}
		return 0;
	}

	public int horzCheck(Cell[][] board, int row, int col, States state) {
		if (col > MAX_COL || col < MIN_COL) {
			return 0;
		}
		if (visited[row][col]) {
			return 0;
		}
		visited[row][col] = true;
		if (board[row][col].getState() == state) {
			return 1 + horzCheck(board, row, col - 1, state) + horzCheck(board, row, col + 1, state);
		}

		return 0;
	}

	private int dCheck1(Cell[][] board, int row, int col, States state) {
		int colLeft = col - 1;// These four variables are self explanatory
		int colRight = col + 1;
		int rowAbove = row - 1;
		int rowBelow = row + 1;

		if (col > MAX_COL || col < MIN_COL || row > MIN_ROW || row < MAX_ROW) {
			return 0;
		}

		if (visited[row][col]) {
			return 0;
		}

		if (board[row][col].getState() == state) {
			visited[row][col] = true;
			return 1 + (dCheck1(board, rowBelow, colLeft, state) + dCheck1(board, rowAbove, colRight, state));
		}
		return 0;
	}

	private int dCheck2(Cell[][] board, int row, int col, States state) {
		int colLeft = col - 1;// These four variables are self explanatory
		int colRight = col + 1;
		int rowAbove = row - 1;
		int rowBelow = row + 1;

		if (col > MAX_COL || col < MIN_COL || row > MIN_ROW || row < MAX_ROW) {
			return 0;
		}

		if (visited[row][col]) {
			return 0;
		}

		if (board[row][col].getState() == state) {
			visited[row][col] = true;
			return 1 + (dCheck2(board, rowBelow, colRight, state) + dCheck2(board, rowAbove, colLeft, state));
		}
		return 0;
	}

	public void resetVisited() {
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				visited[i][j] = false;
			}
		}
	}
}
