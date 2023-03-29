
public class Queen extends Piece {
	int val = 10;
	public Queen(int yp, int xp, ID id, boolean white) {
		super(yp, xp, id, white);
	}

	// The way that the queen moves is pretty much a combination of the bishop and
	// rook, find all positions
	public void generateMoves(Piece[][] board) {
		// Up
		for (int i = yp - 1; i > -1; i--) {
			if (board[i][xp] == null) {
				this.getMoves().add(new Moves(i, xp, this));
			} else if (board[i][xp].isWhite() == this.isWhite()) {
				break;
			} else if (board[i][xp].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(i, xp, this));
				break;
			}
		}
		// Down
		for (int i = yp + 1; i < 8; i++) {
			if (board[i][xp] == null) {
				this.getMoves().add(new Moves(i, xp, this));
			} else if (board[i][xp].isWhite() == this.isWhite()) {
				break;
			} else if (board[i][xp].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(i, xp, this));
				break;
			}
		}
		// Right
		for (int i = xp + 1; i < 8; i++) {
			if (board[yp][i] == null) {
				this.getMoves().add(new Moves(yp, i, this));
			} else if (board[yp][i].isWhite() == this.isWhite()) {
				break;
			} else if (board[yp][i].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(yp, i, this));
				break;
			}
		}
		// Left
		for (int i = xp - 1; i > -1; i--) {
			if (board[yp][i] == null) {
				this.getMoves().add(new Moves(yp, i, this));
			} else if (board[yp][i].isWhite() == this.isWhite()) {
				break;
			} else if (board[yp][i].isWhite() != this.isWhite()) {
				this.getMoves().add(new Moves(yp, i, this));
				break;
			}
		}
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
