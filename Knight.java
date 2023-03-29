
public class Knight extends Piece {
	int val = 3;
	public Knight(int yp, int xp, ID id, boolean white) {
		super(yp, xp, id, white);
	}


	// The horse can move in an "L" shape, generate all moves that fit those
	// conditions
	public void generateMoves(Piece[][] board) {
		if (canMove(yp - 2, xp - 1, board)) {
			this.getMoves().add(new Moves(yp - 2, xp - 1, this));
		}
		if (canMove(yp + 2, xp + 1, board)) {
			this.getMoves().add(new Moves(yp + 2, xp + 1, this));
		}
		if (canMove(yp - 1, xp - 2, board)) {
			this.getMoves().add(new Moves(yp - 1, xp - 2, this));
		}
		if (canMove(yp + 1, xp + 2, board)) {
			this.getMoves().add(new Moves(yp + 1, xp + 2, this));
		}
		if (canMove(yp - 2, xp + 1, board)) {
			this.getMoves().add(new Moves(yp - 2, xp + 1, this));
		}
		if (canMove(yp + 2, xp - 1, board)) {
			this.getMoves().add(new Moves(yp + 2, xp - 1, this));
		}
		if (canMove(yp - 1, xp + 2, board)) {
			this.getMoves().add(new Moves(yp - 1, xp + 2, this));
		}
		if (canMove(yp + 1, xp - 2, board)) {
			this.getMoves().add(new Moves(yp + 1, xp - 2, this));
		}
	}

	private boolean canMove(int row, int col, Piece[][] board) {
		if (row > 7 || row < 0 || col > 7 || col < 0) {
			return false;
		}
		if (board[row][col] != null && board[row][col].isWhite() == this.isWhite()) {
			return false;
		}
		return true;
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
}
