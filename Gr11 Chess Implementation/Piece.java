import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

class Moves {
	int row;
	int col;
	int value;
	Piece piece;
	boolean isValid;

	public Moves() {
		this.value = -10000000;
	}
	public Moves(int row, int col, Piece piece) {
		this.row = row;
		this.col = col;
		isValid = true;
		this.piece = piece;
	}
	public void giveValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	public Piece getPiece() {
		return piece;
	}
}

public class Piece {
	int val = 50;
	public int xp;// x-position (in terms of board) think of it as a col
	public int yp;// y-position (in terms of board) think of it as a row
	public static boolean whiteInCheck = false;
	public static boolean blackInCheck = false;
	int x;// x-position (on gui)
	int y;// y position (on gui)
	boolean white;
	boolean dragging;
	public boolean hasMoved = false;
	ID id;
	private ArrayList<Moves> moves = new ArrayList<Moves>();// This will store every possible move that the user can
	BufferedImage all;// used to contain the full spriteSheet
	HashMap<ID, Image> map;
	Image imgs[];// used to contain all subImages from the spritesheet
	Image image;
	public BoardLogic l = new BoardLogic();
	CompMove c = new CompMove();

	private void handleImage() {
		int iter = 0; // used to iterate through indices of array
		try {
			all = ImageIO.read(new File("C:\\Users\\uyios\\eclipse-workspace\\First Chess Project\\src\\chess.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imgs = new Image[12];

		for (int y = 0; y < 400; y += 200) {
			for (int x = 0; x < 1200; x += 200) {
				imgs[iter] = all.getSubimage(x, y, 200, 200);
				imgs[iter] = imgs[iter].getScaledInstance(64, 64, Image.SCALE_DEFAULT);// resizes the image
				iter++;
			}
		}
	}

	public void generateMoves(Piece[][] board) {

	}

	@SuppressWarnings("serial")
	public Piece(int yp, int xp, ID id, boolean white) {
		this.xp = xp;
		this.yp = yp;
		this.x = xp * 64;
		this.y = yp * 64;
		this.id = id;
		this.white = white;
		handleImage();

		map = new HashMap<ID, Image>() {
			{
				put(ID.KING, imgs[0]);
				put(ID.QUEEN, imgs[1]);
				put(ID.BISHOP, imgs[2]);
				put(ID.KNIGHT, imgs[3]);
				put(ID.ROOK, imgs[4]);
				put(ID.PAWN, imgs[5]);
				/** Black Pieces **/
				put(ID.BLACKKING, imgs[6]);
				put(ID.BLACKQUEEN, imgs[7]);
				put(ID.BLACKBISHOP, imgs[8]);
				put(ID.BLACKKNIGHT, imgs[9]);
				put(ID.BLACKROOK, imgs[10]);
				put(ID.BLACKPAWN, imgs[11]);
			}
		};
	}

	/**
	 * First we generate every valid move that is with the piece that called this
	 * method and add the moves to a list. Then we "clean" that list by taking out
	 * moves that would put the player in check. Then we check the position that the
	 * player is trying to move to. If the position they are trying to move to
	 * exists within the set of moves that we created it means that their move is
	 * valid if not, their move is not valid. If the move is valid we make the move, if
	 * not, we dont make it
	 * 
	 * @param xp
	 * @param yp
	 * @param board
	 */
	public void movePiece(int xp, int yp, Piece[][] board) {
		generateMoves(board);
		cleanMoves(board);
		if (validMove(xp, yp, board)) {
			board[yp][xp] = this;
			board[this.yp][this.xp] = null;
			this.xp = xp;
			this.yp = yp;
			hasMoved = true;
			c.makeMove(!this.isWhite(), board);
		}
	}

	public ArrayList<Moves> getMoves() {
		return moves;
	}

	public boolean containsMove(int yp, int xp, Piece[][] board) {
		for (Moves move : this.getMoves()) {
			if (move.getRow() == yp && move.getCol() == xp && move.isValid) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This took longer to make than I'd like to admit. Pretty much, we go through every
	 * possible move that we can make and we check whether or not making that move would
	 * put us in check. If it does, we mark that move as unvalid else, we leave the
	 * move alone
	 * 
	 * @param board
	 */
	public void cleanMoves(Piece[][] board) {
		Piece formerPiece;
		int formerXP = this.xp;
		int formerYP = this.yp;
		for (Moves mov : this.getMoves()) {
			formerPiece = board[mov.row][mov.col];
			board[mov.row][mov.col] = this;
			board[this.yp][this.xp] = null;
			this.xp = mov.col;
			this.yp = mov.row;
			if (l.inCheck(this.isWhite(), board)) {
				mov.isValid = false;
			}
			this.xp = formerXP;
			this.yp = formerYP;
			board[this.yp][this.xp] = this;
			board[mov.row][mov.col] = formerPiece;
		}
	}

	public boolean inCheck(boolean isWhite) {
		if (isWhite) {
			return whiteInCheck;
		} else {
			return blackInCheck;
		}
	}

	public boolean validMove(int xp, int yp, Piece[][] board) {
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

	public void setDragged(boolean dragged) {
		this.dragging = dragged;
	}

	public boolean isDragged() {
		return this.dragging;
	}

	public boolean isWhite() {
		return white;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getYp() {
		return yp;
	}

	public void setYp(int yp) {
		this.yp = yp;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		return map.get(id);
	}

	public ID getID() {
		return this.id;
	}
}
