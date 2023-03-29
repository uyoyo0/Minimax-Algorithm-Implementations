import java.awt.Color;

public class Cell {
	int width;
	int height;
	int y;
	int x;
	private States state;
	private Color color;

	public Cell(int height, int width, int y, int x) {
		this.height = height;
		this.width = width;
		this.y = y;
		this.x = x;
		state = States.EMPTY;
		this.color = Color.white;
	}

	public Color getColor() {
		return color;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
		if (state == States.COMPUTER) {
			this.color = Color.yellow;
		} else if (state == States.EMPTY) {
			this.color = Color.white;
		} else if (state == States.PLAYER) {
			this.color = Color.red;
		}
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
