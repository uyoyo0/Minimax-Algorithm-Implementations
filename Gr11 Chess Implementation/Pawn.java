import java.util.*;

import javax.swing.JOptionPane;

public class Pawn extends Piece {
	private BoardLogic logic = new BoardLogic();
	int val = 1;

	public Pawn(int yp, int xp, ID id, boolean white) {
		super(yp, xp, id, white);
	}

	// Generate all possible valid moves that can be made
	public void generateMoves(Piece[][] board) {
		// different movement directions depending on what color you are
		if (this.isWhite()) {
			if (!hasMoved && board[yp - 2][xp] == null && board[yp - 1][xp] == null) {
				// You can go up to if youre a pawn and have never moved
				this.getMoves().add(new Moves(yp - 2, xp, this));
			}
			if (!(yp - 1 < 0 || xp - 1 < 0)) {
				// Going up one
				if (board[yp - 1][xp] == null) {
					this.getMoves().add(new Moves(yp - 1, xp, this));
				}
				// Capturing a piece with your pawn
				if (board[yp - 1][xp - 1] != null && board[yp - 1][xp - 1].isWhite() != this.isWhite()) {
					this.getMoves().add(0, new Moves(yp - 1, xp - 1, this));
				}
			}

			if (!(yp - 1 < 0 || xp + 1 >= 8)) {
				// Capturing a piece with your pawn
				if (board[yp - 1][xp + 1] != null && board[yp - 1][xp + 1].isWhite() != this.isWhite()) {
					this.getMoves().add(new Moves(yp - 1, xp + 1, this));
				}
				// going up one
				if (board[yp - 1][xp] == null) {
					this.getMoves().add(new Moves(yp - 1, xp, this));
				}
			}
		} else if (!this.isWhite()) {
			if (yp + 2 < 8) {
				if (!hasMoved && board[yp + 2][xp] == null && board[yp + 1][xp] == null) {
					// You can go up to if youre a pawn and have never moved
					this.getMoves().add(new Moves(yp + 2, xp, this));
				}
			}
			if (!(yp + 1 >= 8 || xp - 1 < 0)) {
				// Going up one
				if (board[yp + 1][xp] == null) {
					this.getMoves().add(new Moves(yp + 1, xp, this));
				}
				// Capturing a piece with your pawn
				if (board[yp + 1][xp - 1] != null && board[yp + 1][xp - 1].isWhite() != this.isWhite()) {
					this.getMoves().add(new Moves(yp + 1, xp - 1, this));
				}
			}

			if (!(yp + 1 >= 8 || xp + 1 >= 8)) {
				// Capturing a piece with your pawn
				if (board[yp + 1][xp + 1] != null && board[yp + 1][xp + 1].isWhite() != this.isWhite()) {
					this.getMoves().add(new Moves(yp + 1, xp + 1, this));
				}
				// going up one
				if (board[yp + 1][xp] == null) {
					this.getMoves().add(new Moves(yp + 1, xp, this));
				}
			}
		}
	}

	public void movePiece(int xp, int yp, Piece[][] board) {
		Scanner sc = new Scanner(System.in);
		String playerChoice;
		this.getMoves().clear();
		generateMoves(board);
		cleanMoves(board);
		if (validMove(xp, yp, board)) {
			hasMoved = true;
			board[yp][xp] = this;
			board[this.yp][this.xp] = null;
			this.xp = xp;
			this.yp = yp;
			if (logic.canPromote(this)) {
				playerChoice = (JOptionPane.showInputDialog("Enter what piece you want to promote to: "));
				logic.getPromotion(this, playerChoice, board);
			}
			c.makeMove(!this.isWhite(), board);
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

}
