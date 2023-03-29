
public class King extends Piece {
	int val = 100;
	boolean hasMoved = false;

	public King(int yp, int xp, ID id, boolean white) {
		super(yp, xp, id, white);
	}

	public void generateMoves(Piece[][] board) {
		if (inBounds(yp + 1, xp + 1, board)) {
			this.getMoves().add(new Moves(yp + 1, xp + 1, this));
		}
		if (inBounds(yp - 1, xp - 1, board)) {
			this.getMoves().add(new Moves(yp - 1, xp - 1, this));
		}
		if (inBounds(yp + 1, xp - 1, board)) {
			this.getMoves().add(new Moves(yp + 1, xp - 1, this));
		}
		if (inBounds(yp - 1, xp + 1, board)) {
			this.getMoves().add(new Moves(yp - 1, xp + 1, this));
		}
		if (inBounds(yp + 1, xp, board)) {
			this.getMoves().add(new Moves(yp + 1, xp, this));
		}
		if (inBounds(yp - 1, xp, board)) {
			this.getMoves().add(new Moves(yp - 1, xp, this));
		}
		if (inBounds(yp, xp + 1, board)) {
			this.getMoves().add(new Moves(yp, xp + 1, this));
		}
		if (inBounds(yp, xp - 1, board)) {
			this.getMoves().add(new Moves(yp, xp - 1, this));
		}
	}

	private boolean inBounds(int row, int col, Piece[][] board) {
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

	private boolean tryCastle(int xp, int yp, Piece[][] board) {
		if (this.isWhite() && this.whiteInCheck || !this.isWhite() && this.blackInCheck) {
			return false;
		}
		if (this.hasMoved) {
			return false;
		}
		if (!(xp > 7 || xp < 0 || yp > 7 || yp < 0)) {
			System.out.println("ok");
			if (board[yp][xp] != null) {
				if (board[yp][xp].getID() == ID.BLACKROOK || board[yp][xp].getID() == ID.ROOK) {
					Rook temp = (Rook) board[yp][xp];
					if (!temp.hasMoved) {
						if (l.canCastle(this, board, yp, xp)) {
							l.castle(this, (Rook) board[yp][xp], board);
							System.out.println("YESSSIRRRRRRR");
							return true;
						} else {
							System.out.println("NAHHHHHHHH");
						}
					}
				}
			}
		}
		return false;
	}

	public void movePiece(int xp, int yp, Piece[][] board) {
		if (tryCastle(xp, yp, board)) {
			this.hasMoved = true;
			c.makeMove(!this.isWhite(), board);
			return;
		}
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
