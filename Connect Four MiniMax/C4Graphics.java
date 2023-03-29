
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class C4Graphics extends JPanel implements ActionListener, KeyListener, MouseInputListener {

	private AIMoves aiMove = new AIMoves();
	private C4Logic logic;
	private Cell[][] board;
	private Cell pointer = null;
	private JFrame frame;
	private int height = 62;
	private int width = 62;
	private int borderX = 150;
	private int borderY = 85;
	public static String message = "Make your move";

	public C4Graphics(int num) {
		/** FRAME SETUP **/
		board = new Cell[6][7];
		frame = new JFrame("Connect Four");
		frame.setSize(780, 680);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);// centers the window
		frame.add(this);
		frame.addKeyListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		logic = new C4Logic();
		init();
		if (num != 1) {
			aiMove.makeMove(board);
			repaint();
		}
	}

	private void init() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = new Cell(height, width, borderY + (80 * i), borderX + (70 * j));
			}
		}
	}

	public void paint(Graphics g) {
		/** Background and board **/
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 780, 680);
		g.setColor(Color.gray);
		g.fillRect(100, 70, 570, 520);

		/** MESSAGE **/
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		g.setColor(Color.white);
		g.drawString(message, 280, 45);

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				pointer = board[i][j];
				g.setColor(pointer.getColor());
				g.fillOval(pointer.getX(), pointer.getY(), width, height);
			}
		}
	}

	private boolean gameWon = false;

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		pointer = logic.findCell(board, x, y);

		if (pointer != null && !gameWon) {
			pointer.setState(States.PLAYER);
			gameWon = logic.checkGame(board);
			message = "Generating moves...";
			repaint();
			aiMove.makeMove(board);
			gameWon = logic.checkGame(board);
//			repaint();
		}
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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
//		System.out.println("Yes");

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Scanner sc = new Scanner(System.in);
		char a = e.getKeyChar();

		if (a == 'x') {
			System.out.println("What would you like the max depth to be ?");
			aiMove.maxDepth = sc.nextInt();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Type 1 if you want to go first");
		int num = sc.nextInt();
		new C4Graphics(num);
	}
}
