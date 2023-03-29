import java.util.*;

public class CompMove {
	BoardLogic l = new BoardLogic();

	/**
	 * Collect all possible moves that can be made
	 * 
	 * @param isWhite
	 * @param board
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<Moves>> collectMoves(boolean isWhite, Piece[][] board) {
		ArrayList<ArrayList<Moves>> moves = new ArrayList<ArrayList<Moves>>();
		moves.clear();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece temp = board[i][j];
				if (temp != null) {
					if (temp.isWhite() == isWhite) {
						temp.getMoves().clear();
						temp.generateMoves(board);
						temp.cleanMoves(board);
						moves.add((ArrayList<Moves>) temp.getMoves().clone());// for some reason if I make it a clone i
																				// get a HUGE bug
					}
				}
			}
		}

		for (int i = 0; i < moves.size(); i++) {
		}
//		Collections.reverse(moves);
		return moves;
	}

	public void makeMove(boolean isWhite, Piece[][] board) {
		Piece formerPiece = null;// former piece
		int formerXp, formerYp;// former position
		boolean prev_blackInCheck = Piece.blackInCheck;
		boolean prev_whiteInCheck = Piece.whiteInCheck;
		Moves bestMove = null;
		;
		boolean hadMoved;// if the piece in question had already moved
		ArrayList<ArrayList<Moves>> moves = collectMoves(isWhite, board);

		for (int i = 0; i < moves.size(); i++) {
			for (int j = 0; j < moves.get(i).size(); j++) {
				System.out.println(moves.get(i).get(j).row + " " + moves.get(i).get(j).col + " "
						+ moves.get(i).get(j).getPiece().getID());
			}
		}
		/**
		 * Select the best move
		 */
		for (int i = 0; i < moves.size(); i++) {
			for (int j = 0; j < moves.get(i).size(); j++) {
				Moves mov = moves.get(i).get(j);
				/** Store past info */
				if (validMove(mov.getPiece(), mov.getRow(), mov.getCol(), board) && mov.isValid) {
					formerPiece = board[mov.getRow()][mov.getCol()];
					formerYp = mov.getPiece().getYp();
					formerXp = mov.getPiece().getXp();
					hadMoved = mov.getPiece().hasMoved;

					/** Simulate move */
					board[mov.getRow()][mov.getCol()] = mov.getPiece();
					board[mov.getPiece().getYp()][mov.getPiece().getXp()] = null;
					mov.getPiece().setYp(mov.getRow());
					mov.getPiece().setXp(mov.getCol());
					mov.getPiece().hasMoved = true;
					/* Give move value **/
					int temp = minMax(isWhite, board, false, -10000000, 10000000, 0);
					mov.giveValue(temp);
					if (bestMove == null) {
						bestMove = mov;
					} else if (mov.getValue() > bestMove.getValue()) {
						bestMove = mov;
						System.out.println(mov.row + " " + mov.col + " " + mov.getPiece().id);
					}

					/** restore info */
					board[formerYp][formerXp] = mov.getPiece();
					board[mov.getPiece().getYp()][mov.getPiece().getXp()] = formerPiece;
					mov.getPiece().setXp(formerXp);
					mov.getPiece().setYp(formerYp);
					mov.getPiece().hasMoved = hadMoved;
					Piece.blackInCheck = prev_blackInCheck;
					Piece.whiteInCheck = prev_whiteInCheck;
				}
			}
		}
		if (bestMove != null) {
			board[bestMove.getRow()][bestMove.getCol()] = bestMove.getPiece();
			board[bestMove.getPiece().getYp()][bestMove.getPiece().getXp()] = null;
			bestMove.getPiece().setYp(bestMove.getRow());
			bestMove.getPiece().setXp(bestMove.getCol());
			bestMove.getPiece().hasMoved = true;
		}
	}

	int counter = 0;

	public int minMax(boolean isWhite, Piece[][] board, boolean maximizer, int alpha, int beta, int depth) {
		counter++;
		System.out.println(counter);
		Piece.whiteInCheck = l.inCheck(true, board);
		Piece.blackInCheck = l.inCheck(false, board);

		if (l.gameWon(true, board)) {
			return 100000000;
		} else if (l.gameWon(false, board)) {
			return -100000000;
		}

		if (l.isStalemate(true, board) || l.isStalemate(false, board)) {
			return 0;
		}
		if (depth >= 3) {
			return staticEval(isWhite, board) - staticEval(!isWhite, board);
		}

		ArrayList<ArrayList<Moves>> moves = collectMoves(isWhite, board);
		if (moves.size() == 0) {
			return 0;
		}

		Piece formerPiece = null;// former piece
		int formerXp, formerYp;// former position
		boolean hadMoved;// if the piece in question had already moved
		if (maximizer) {
			int bestMove = -10000000;
			int currentMove;

			for (int i = 0; i < moves.size(); i++) {
				for (int j = 0; j < moves.get(i).size(); j++) {
					Moves mov = moves.get(i).get(j);
					if (validMove(mov.getPiece(), mov.getCol(), mov.getRow(), board) && mov.isValid) {
						formerPiece = board[mov.getRow()][mov.getCol()];
						formerYp = mov.getPiece().getYp();
						formerXp = mov.getPiece().getXp();
						hadMoved = mov.getPiece().hasMoved;

						/** Simulate move */
						board[mov.getRow()][mov.getCol()] = mov.getPiece();
						board[mov.getPiece().getYp()][mov.getPiece().getXp()] = null;
						mov.getPiece().setYp(mov.getRow());
						mov.getPiece().setXp(mov.getCol());
						mov.getPiece().hasMoved = true;
						currentMove = minMax(isWhite, board, !maximizer, alpha, beta, depth + 1);
						bestMove = Math.max(currentMove, bestMove);
						alpha = Math.max(currentMove, beta);
						/** restore info */
						board[formerYp][formerXp] = mov.getPiece();
						board[mov.getRow()][mov.getCol()] = formerPiece;
						mov.getPiece().setXp(formerXp);
						mov.getPiece().setYp(formerYp);
						mov.getPiece().hasMoved = hadMoved;
						if (beta <= alpha) {
							break;
						}
					}
				}
			}
			return bestMove;
		} else {
			int bestMove = 10000000;
			int currentMove;
			for (int i = 0; i < moves.size(); i++) {
				for (int j = 0; j < moves.get(i).size(); j++) {
					Moves mov = moves.get(i).get(j);
					if (validMove(mov.getPiece(), mov.getCol(), mov.getRow(), board) && mov.isValid) {
						formerPiece = board[mov.getRow()][mov.getCol()];
						formerYp = mov.getPiece().getYp();
						formerXp = mov.getPiece().getXp();
						hadMoved = mov.getPiece().hasMoved;

						/** Simulate move */
						board[mov.getRow()][mov.getCol()] = mov.getPiece();
						board[mov.getPiece().getYp()][mov.getPiece().getXp()] = null;
						mov.getPiece().setYp(mov.getRow());
						mov.getPiece().setXp(mov.getCol());
						mov.getPiece().hasMoved = true;
						currentMove = minMax(isWhite, board, !maximizer, alpha, beta, depth + 1);
						bestMove = Math.min(currentMove, bestMove);
						beta = Math.min(currentMove, beta);
						/** restore info */
						board[formerYp][formerXp] = mov.getPiece();
						board[mov.getRow()][mov.getCol()] = formerPiece;
						mov.getPiece().setXp(formerXp);
						mov.getPiece().setYp(formerYp);
						mov.getPiece().hasMoved = hadMoved;
						if (beta <= alpha) {
							break;
						}
					}
				}
			}
			return bestMove;
		}
	}
	private int staticEval(boolean isWhite, Piece[][] board) {
		int count = 0;
		if (isWhite && Piece.whiteInCheck) {
			count -= 10;
		}
		if (!isWhite && Piece.blackInCheck) {
			count -=10;
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].isWhite() == isWhite) {
						count += board[i][j].val;
						count++;
						/** Encourage bot to place pieces near center */
						if (i > 1 && i < 6 && j > 1 && j < 7) {
							count++;
						}
						if (i > 2 && i < 5 && j > 2 && j < 6) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	public boolean inCheck(boolean isWhite) {
		if (isWhite) {
			return Piece.whiteInCheck;
		} else {
			return Piece.blackInCheck;
		}
	}

	public boolean validMove(Piece piece, int yp, int xp, Piece[][] board) {
		if (xp > 7 || yp > 7 || xp < 0 || yp < 0) {
			return false;
		}
		if (piece == board[yp][xp]) {
			return false;
		}
		return true;
	}
}
