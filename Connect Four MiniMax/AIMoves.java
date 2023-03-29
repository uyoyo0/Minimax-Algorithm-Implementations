import java.util.*;

class Move {
	Cell cell;
	int val = 0;

	public Move(Cell cell) {
		this.cell = cell;
	}
}

public class AIMoves {
	static int maxDepth = 6;
	private int movesMade = 0;
	private C4Logic logic = new C4Logic();

	public void makeMove(Cell[][] board) {
		if (movesMade == 0 && board[5][3].getState() == States.EMPTY) {
			board[5][3].setState(States.COMPUTER);
			movesMade++;
			return;
		}
		Move bestMove = null;
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell pointer;

		/** Determine all possible moves **/
		for (int i = board.length - 1; i > -1; i--) {
			for (int j = 0; j < board[i].length; j++) {
				pointer = board[i][j];
				if (logic.isValid(board, i, j)) {
					moves.add(new Move(pointer));
				}
			}
		}

		/** Select the best move **/
		for (Move move : moves) {
			move.cell.setState(States.COMPUTER);
			move.val = minMax(board, move, 0, false, -10000000, 10000000);

			if (bestMove == null || move.val > bestMove.val) {
				bestMove = move;
			}
			move.cell.setState(States.EMPTY);
		}

		if (bestMove != null) {
			bestMove.cell.setState(States.COMPUTER);
		}
		C4Graphics.message = "Make your move";
	}

	private int minMax(Cell[][] board, Move move, int depth, boolean maximizer, int alpha, int beta) {
		int compScore = logic.staticEval(board, States.COMPUTER);
		int playerScore = logic.staticEval(board, States.PLAYER);
		int temp = compScore - playerScore;
				
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell pointer;

		/** Determine all possible moves **/
		for (int i = board.length - 1; i > -1; i--) {
			for (int j = 0; j < board[i].length; j++) {
				pointer = board[i][j];
				if (logic.isValid(board, i, j)) {
					moves.add(new Move(pointer));
				}
			}
		}
		
		if (depth > maxDepth) {
			return temp;
		}

		if (logic.checkGame(board)) {
			if (maximizer) {
				return (temp + 100000000) * -1;
			} else {
				return temp + 100000000;
			}
		}
		if (moves.isEmpty()) {
			return 0;
		}

		if (maximizer) {
			int bestMove = -10000000;
			int currentMove;

			for (Move mov : moves) {
				mov.cell.setState(States.COMPUTER);
				currentMove = minMax(board, mov, depth + 1, false, alpha, beta);

				bestMove = Math.max(currentMove, bestMove);
				alpha = Math.max(alpha, currentMove);
				mov.cell.setState(States.EMPTY);
				if (beta <= alpha) {
					break;
				}
			}
			return bestMove;
		} else {
			int bestMove = 10000000;
			int currentMove;

			for (Move mov : moves) {
				mov.cell.setState(States.PLAYER);
				currentMove = minMax(board, mov, depth + 1, true, alpha,beta);

				bestMove = Math.min(currentMove, bestMove);
				beta = Math.min(currentMove, beta);
				mov.cell.setState(States.EMPTY);
				if (beta <= alpha) {
					break;
				}
			}
			return bestMove;
		}
	}
}
