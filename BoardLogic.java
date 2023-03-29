
public class BoardLogic {

	/**
	 * "isWhite" tells the method whether you want the whiteKing or the blackKing
	 */
	public boolean inCheck(boolean isWhite, Piece[][] board) {
		Piece king = getKing(isWhite, board);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].isWhite() != isWhite) {// only pieces with a different color from us can put us in
															// check
						board[i][j].generateMoves(board);
						if (board[i][j].containsMove(king.getYp(), king.getXp(), board)) {
							board[i][j].getMoves().clear();
							return true;
						} else {
							board[i][j].getMoves().clear();
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * "isWhite" tells the method whether you want the whiteKing or the blackKing
	 * This method will return the king of the specified color (if it exists)
	 */
	private Piece getKing(boolean isWhite, Piece[][] board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].isWhite() == isWhite
							&& (board[i][j].getID() == ID.KING || board[i][j].getID() == ID.BLACKKING)) {
						return board[i][j];
					}
				}
			}
		}
		return null;
	}

	/**
	 * To determine whether a game has been won or not, we look at every possible
	 * move the specified side can make If there are ZERO moves that side can make
	 * it means that that side has lost. However, if the side is able to make a move
	 * it means that the side has not lost yet
	 * 
	 * @param isWhite
	 * @param board
	 * @return
	 */
	public boolean gameWon(boolean isWhite, Piece[][] board) {
		if (isWhite && !Piece.whiteInCheck || !isWhite && !Piece.blackInCheck) {
			return false;
		}
		if (isWhite && Piece.whiteInCheck) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board[i][j] != null) {
						if (board[i][j].isWhite()) {
							board[i][j].generateMoves(board);
							board[i][j].cleanMoves(board);
							for (Moves mov : board[i][j].getMoves()) {
								if (mov.isValid) {
//									board[i][j].getMoves().clear();
									return false;
								}
							}
						}
					}
				}
			}
		} else if (!isWhite && Piece.blackInCheck) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board[i][j] != null) {
						if (!board[i][j].isWhite()) {
							board[i][j].generateMoves(board);
							board[i][j].cleanMoves(board);
							for (Moves mov : board[i][j].getMoves()) {
								if (mov.isValid) {
//										board[i][j].getMoves().clear();
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Exact same as the method above but a stalemate is when the king is NOT in
	 * check
	 * 
	 * @param isWhite
	 * @param board
	 * @return
	 */
	public boolean isStalemate(boolean isWhite, Piece[][] board) {
		if (isWhite && !Piece.whiteInCheck) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board[i][j] != null) {
						if (board[i][j].isWhite()) {
							board[i][j].generateMoves(board);
							board[i][j].cleanMoves(board);
							for (Moves mov : board[i][j].getMoves()) {
								if (mov.isValid) {
//									board[i][j].getMoves().clear();
									return false;
								}
							}
						}
					}
				}
			}
		} else if (!isWhite && !Piece.blackInCheck) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board[i][j] != null) {
						if (!board[i][j].isWhite()) {
							board[i][j].generateMoves(board);
							board[i][j].cleanMoves(board);
							for (Moves mov : board[i][j].getMoves()) {
								if (mov.isValid) {
//										board[i][j].getMoves().clear();
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	private boolean underAttack(Piece[][] board, boolean isWhite, int yp, int xp) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].isWhite() != isWhite) {
						board[i][j].generateMoves(board);
						board[i][j].cleanMoves(board);
						for (Moves mov : board[i][j].getMoves()) {
							if (mov.isValid) {
								if (mov.getRow() == yp && mov.getCol() == xp) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean canCastle(King piece, Piece[][] board, int yp, int xp) {
		/** They obviously have to be on the same row */
		if (yp == piece.getYp()) {
			if (xp > piece.getXp()) {
				/** Make sure that the cells between the king and rook are empty */
				for (int i = piece.getXp() + 1; i < xp; i++) {
					if (board[yp][i] != null || underAttack(board, piece.isWhite(), yp, i)) {
						return false;
					}
				}
				return true;
			} else if (xp < piece.getXp()) {
				/**
				 * Make sure that the cells between the king and rook are empty and not under
				 * attack
				 */
				for (int i = piece.getXp() - 1; i > xp; i--) {
					if (board[yp][i] != null || underAttack(board, piece.isWhite(), yp, i)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public void castle(King king, Rook rook, Piece[][] board) {
		if (king.getXp() > rook.getXp()) {
			/** This moves the king */
			board[king.getYp()][king.getXp() - 2] = king;
			board[king.getYp()][king.getXp()] = null;
			king.setXp(king.getXp() - 2);
			/** This moves the rook */
			board[king.getYp()][king.getXp() + 1] = rook;
			board[rook.getYp()][rook.getXp()] = null;
			rook.setXp(king.getXp() + 1);
		} else if (king.getXp() < rook.getXp()) {
			/** This moves the king */
			board[king.getYp()][king.getXp() + 2] = king;
			board[king.getYp()][king.getXp()] = null;
			king.setXp(king.getXp() + 2);
			/** This moves the rook */
			board[king.getYp()][king.getXp() - 1] = rook;
			board[rook.getYp()][rook.getXp()] = null;
			rook.setXp(king.getXp() - 1);
		}
	}

	public boolean canPromote(Pawn p) {
		if (p.isWhite() && p.getYp() == 0) {
			return true;
		} else if (!p.isWhite() && p.getYp() == 7) {
			return true;
		} else {
			return false;
		}
	}

	public void getPromotion(Pawn pawn, String choice, Piece[][] board) {
		// num represnts the piece you want to get
		if (choice == null) {
			choice = "k";
		}
		String key = choice.substring(0, 1).toLowerCase();
		
		switch (key) {
		case "k":
			if (pawn.isWhite()) {
				board[pawn.getYp()][pawn.getXp()] = new Knight(pawn.getYp(), pawn.getXp(), ID.KNIGHT, true);
			} else {
				board[pawn.getYp()][pawn.getXp()] = new Knight(pawn.getYp(), pawn.getXp(), ID.BLACKKNIGHT, false);
			}
			break;
		case "r":
			if (pawn.isWhite()) {
				board[pawn.getYp()][pawn.getXp()] = new Rook(pawn.getYp(), pawn.getXp(), ID.ROOK, true);
			} else {
				board[pawn.getYp()][pawn.getXp()] = new Rook(pawn.getYp(), pawn.getXp(), ID.BLACKROOK, false);
			}
			break;
		case "b":
			if (pawn.isWhite()) {
				board[pawn.getYp()][pawn.getXp()] = new Bishop(pawn.getYp(), pawn.getXp(), ID.BISHOP, true);
			} else {
				board[pawn.getYp()][pawn.getXp()] = new Bishop(pawn.getYp(), pawn.getXp(), ID.BLACKBISHOP, false);
			}
			break;
		case "q":
			if (pawn.isWhite()) {
				board[pawn.getYp()][pawn.getXp()] = new Queen(pawn.getYp(), pawn.getXp(), ID.QUEEN, true);
			} else {
				board[pawn.getYp()][pawn.getXp()] = new Queen(pawn.getYp(), pawn.getXp(), ID.BLACKQUEEN, false);
			}
			break;
		default:
			if (pawn.isWhite()) {
				board[pawn.getYp()][pawn.getXp()] = new Queen(pawn.getYp(), pawn.getXp(), ID.QUEEN, true);
			} else {
				board[pawn.getYp()][pawn.getXp()] = new Queen(pawn.getYp(), pawn.getXp(), ID.BLACKQUEEN, false);
			}
			break;
		}
	}
}
