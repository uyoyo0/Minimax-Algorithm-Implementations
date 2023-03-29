
public class Rook extends Piece {
	int val = 5;
	boolean hasMoved = false;

	public Rook(int yp, int xp, ID id, boolean white) {
		super(yp, xp, id, white);
	}

	// The rook can move right, left, up and dowm, we will generate all of the moves
	// that can be made moving in these directions
	// note that if you look at it from the perspective of black up becomes down,
	// down becomes up, etc...
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

	public void movePiece(int xp, int yp, Piece[][] board) {
		generateMoves(board);
		cleanMoves(board);
		if (validMove(xp, yp, board)) {
			board[yp][xp] = this;
			board[this.yp][this.xp] = null;
			this.xp = xp;
			this.yp = yp;
			this.hasMoved = true;
			c.makeMove(!this.isWhite(), board);
		}
	}
}
