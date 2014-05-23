package engine;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Point implements Serializable {

	private int x;
	private int y;

	public Point(int x, int y) {
		setX(x);
		setY(y);
	}

	public Point(android.graphics.Point point) {
		setX(point.x);
		setY(point.y);
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

	public int getRow() {
		return y;
	}

	public int getColumn() {
		return x;
	}

	@Override
	public boolean equals(Object obj) {
		Point o = (Point) obj;
		return getX() == o.getX() && getY() == o.getY();
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}