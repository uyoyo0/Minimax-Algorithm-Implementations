
public class Bishop extends Piece {
	int val = 3;
	public Bishop(int yp, int xp, ID id, boolean white) {
		super(yp, xp, id, white);
	}

	// The Bishop can move in an "X" shaped direction, generate all such moves
	public void generateMoves(Piece[][] board) {
		// Down Right
		int rowIter = yp + 1;
		int colIter = xp + 1;

		while (inBounds(rowIter, colIter)) {
			if (board[rowIter][colIter] == null) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
			} else if (board[rowIter][colIter].isWhite() == this.isWhite()) {
				break;
			} else if (board[rowIter][colIter].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
				break;
			}
			rowIter++;
			colIter++;
		}

		// Up left
		rowIter = yp - 1;
		colIter = xp - 1;
		while (inBounds(rowIter, colIter)) {
			if (board[rowIter][colIter] == null) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
			} else if (board[rowIter][colIter].isWhite() == this.isWhite()) {
				break;
			} else if (board[rowIter][colIter].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
				break;
			}
			rowIter--;
			colIter--;
		}
		// Down Left
		rowIter = yp + 1;
		colIter = xp - 1;
		while (inBounds(rowIter, colIter)) {
			if (board[rowIter][colIter] == null) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
			} else if (board[rowIter][colIter].isWhite() == this.isWhite()) {
				break;
			} else if (board[rowIter][colIter].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
				break;
			}
			rowIter++;
			colIter--;
		}

		// Up Right
		rowIter = yp - 1;
		colIter = xp + 1;
		while (inBounds(rowIter, colIter)) {
			if (board[rowIter][colIter] == null) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
			} else if (board[rowIter][colIter].isWhite() == this.isWhite()) {
				break;
			} else if (board[rowIter][colIter].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(rowIter, colIter, this));
				break;
			}
			rowIter--;
			colIter++;
		}
	}

	public boolean validMove(int xp, int yp, Piece[][] board) {
		if (!containsMove(yp, xp, board)) {
			return false;
		}
		if (xp > 7 || yp > 7 || xp < 0 || yp < 0) {
			return false;
		}
		if (board[yp][xp] == null) {
			return true;
		}
		if (this.isWhite() == board[yp][xp].isWhite()) {
			return false;
		}
		if (this == board[yp][xp]) {
			return false;
		}
		return true;
	}

	private boolean inBounds(int row, int col) {
		if (row > 7 || row < 0 || col > 7 || col < 0) {
			return false;
		}
		return true;
	}
}
