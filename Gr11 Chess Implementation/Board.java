import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
	public static void main(String[] args) {
		new Board();
	}
	
	JFrame frame;
	private Piece[][] board = new Piece[8][8];
	private Piece selectedP = null;
	private King bkPointer = null;//black king pointer
	private King wkPointer = null;//white king pointer

	public Board() {
		frame = new JFrame("Chess");
		frame.setBounds(10, 10, 512, 512);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		init();
		repaint();
	}

	void init() {
		/** WHITE PIECES **/
		board[0][0] = new Rook(0, 0, ID.BLACKROOK, false);
		board[0][1] = new Knight(0, 1, ID.BLACKKNIGHT, false);
		board[0][2] = new Bishop(0, 2, ID.BLACKBISHOP, false);
		board[0][4] = bkPointer = new King(0, 4, ID.BLACKKING, false);
		board[0][3] = new Queen(0, 3, ID.BLACKQUEEN, false);
		board[0][5] = new Bishop(0, 5, ID.BLACKBISHOP, false);
		board[0][6] = new Knight(0, 6, ID.BLACKKNIGHT, false);
		board[0][7] = new Rook(0, 7, ID.BLACKROOK, false);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(1, i, ID.BLACKPAWN, false);
		}
		/** BLACK PIECES **/
		board[7][0] = new Rook(7, 0, ID.ROOK, true);
		board[7][1] = new Knight(7, 1, ID.KNIGHT, true);
		board[7][2] = new Bishop(7, 2, ID.BISHOP, true);
		board[7][4] = wkPointer = new King(7, 4, ID.KING, true);
		board[7][3] = new Queen(7, 3, ID.QUEEN, true);
		board[7][5] = new Bishop(7, 5, ID.BISHOP, true);
		board[7][6] = new Knight(7, 6, ID.KNIGHT, true);
		board[7][7] = new Rook(7, 7, ID.ROOK, true);
		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn(6, i, ID.PAWN, true);
		}
	}

	public void paint(Graphics g) {
		boolean white = true;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (white) {
					g.setColor(new Color(230, 201, 147));
				} else {
					g.setColor(new Color(161, 118, 39));
				}
				g.fillRect(x * 64, y * 64, 64, 64);
				white = !white;
			}
			white = !white;
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null && board[i][j] != selectedP) {
					g.drawImage(board[i][j].getImage(), board[i][j].getXp() * 64, board[i][j].getYp() * 64, this);
				}
			}
			if (selectedP != null) {
				if (selectedP.isDragged()) {
					g.drawImage(selectedP.getImage(), selectedP.getX(), selectedP.getY(), this);
					g.setColor(Color.green.darker());
					selectedP.getMoves().clear();
					selectedP.generateMoves(board);
					selectedP.cleanMoves(board);
					for (Moves move : selectedP.getMoves()) {
						if (move.isValid) {
							g.fillOval((move.col * 64) + 20, (move.row * 64) + 20, 20, 20);
						}
					}
				}
			}
			if (Piece.whiteInCheck) {
				g.setColor(Color.MAGENTA);
				g.fillOval((wkPointer.getXp() * 64) + 10, (wkPointer.getYp() * 64) + 10, 40, 40);
			}else if (Piece.blackInCheck) {
				g.setColor(Color.MAGENTA);
				g.fillOval((bkPointer.getXp() * 64) + 10, (bkPointer.getYp() * 64) + 10, 40, 40);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selectedP != null) {
			selectedP.setDragged(true);
			selectedP.setX(e.getX() - 32);
			selectedP.setY(e.getY() - 32);
			frame.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectedP = getPiece(e.getX(), e.getY());

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		BoardLogic l = new BoardLogic();
		if (selectedP != null) {
			selectedP.setDragged(false);
			selectedP.movePiece(e.getX() / 64, e.getY() / 64, board);
			selectedP = null;

			if (Piece.blackInCheck == false && l.inCheck(true, board) || l.inCheck(false, board)) {
//				JOptionPane.showMessageDialog(null, "YOU ARE IN CHECK!");
				System.out.println("YOU ARE IN CHECK");
			}
			Piece.blackInCheck = l.inCheck(false, board);
			Piece.whiteInCheck = l.inCheck(true, board);
			if (l.gameWon(true, board) || l.gameWon(false, board)) {
				System.out.println("ITS OVER!");
			}
		}
		frame.repaint();
	}

	public Piece getPiece(int x, int y) {
//		System.out.println(x / 64 + " " + y / 64);
		int xp = x / 64;
		int yp = y / 64;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].xp == xp && board[i][j].yp == yp) {
						return board[i][j];
					}
				}
			}
		}
		return null;
	}
}