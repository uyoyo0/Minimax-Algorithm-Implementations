//
//Piece.whiteInCheck = l.inCheck(true, board);
//		Piece.blackInCheck = l.inCheck(false, board);
//
//		if (l.gameWon(true, board)) {
//			return 100000000;
//		} else if (l.gameWon(false, board)) {
//			return -100000000;
//		}
//
//		if (l.isStalemate(true, board) || l.isStalemate(false, board)) {
//			return 0;
//		}
//		if (depth == 2) {
//			return staticEval(isWhite, board) - staticEval(!isWhite, board);
//		}
//
//		ArrayList<ArrayList<Moves>> moves = collectMoves(isWhite, board);
//		
//		Piece formerPiece = null;// former piece
//		int formerXp, formerYp;// former position
//		boolean hadMoved;// if the piece in question had already moved
//		if (maximizer) {
//			int bestMove = -10000000;
//			int currentMove;
//
//			for (int i = 0; i < 8; i++) {
//				for (Moves mov : moves.get(i)) {
//					if (validMove(mov.getPiece(), mov.getCol(), mov.getRow(), board) && mov.isValid) {
//						formerPiece = board[mov.getRow()][mov.getCol()];
//						formerYp = mov.getPiece().getYp();
//						formerXp = mov.getPiece().getXp();
//						hadMoved = mov.getPiece().hasMoved;
//
//						/** Simulate move */
//						board[mov.getRow()][mov.getCol()] = mov.getPiece();
//						board[mov.getPiece().getYp()][mov.getPiece().getXp()] = null;
//						mov.getPiece().setYp(mov.getRow());
//						mov.getPiece().setXp(mov.getCol());
//						mov.getPiece().hasMoved = true;
//						currentMove = minMax(isWhite, board, !maximizer, alpha, beta, depth + 1);
//						bestMove = Math.max(currentMove, bestMove);
//						beta = Math.max(currentMove, beta);
//						/** restore info */
//						board[formerYp][formerXp] = mov.getPiece();
//						board[mov.getRow()][mov.getCol()] = formerPiece;
//						mov.getPiece().setXp(formerXp);
//						mov.getPiece().setYp(formerYp);
//						mov.getPiece().hasMoved = hadMoved;
//						if (beta <= alpha) {
//							break;
//						}
//					}
//				}
//			}
//			return bestMove;
//		} else {
//			int bestMove = 10000000;
//			int currentMove;
//
//			for (int i = 0; i < 8; i++) {
//				for (Moves mov : moves.get(i)) {
//
//					if (validMove(mov.getPiece(), mov.getCol(), mov.getRow(), board) && mov.isValid) {
//						formerPiece = board[mov.getRow()][mov.getCol()];
//						formerYp = mov.getPiece().getYp();
//						formerXp = mov.getPiece().getXp();
//						hadMoved = mov.getPiece().hasMoved;
//
//						/** Simulate move */
//						board[mov.getRow()][mov.getCol()] = mov.getPiece();
//						board[mov.getPiece().getYp()][mov.getPiece().getXp()] = null;
//						mov.getPiece().setYp(mov.getRow());
//						mov.getPiece().setXp(mov.getCol());
//						mov.getPiece().hasMoved = true;
//						currentMove = minMax(isWhite, board, !maximizer, alpha, beta, depth + 1);
//						bestMove = Math.min(currentMove, bestMove);
//						beta = Math.min(currentMove, beta);
//						/** restore info */
//						board[formerYp][formerXp] = mov.getPiece();
//						board[mov.getRow()][mov.getCol()] = formerPiece;
//						mov.getPiece().setXp(formerXp);
//						mov.getPiece().setYp(formerYp);
//						mov.getPiece().hasMoved = hadMoved;
//						if (beta <= alpha) {
//							break;
//						}
//					}
//				}
//			}
//			return bestMove;
//		}