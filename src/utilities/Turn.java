package utilities;

import engine.Point;

public class Turn {
	private Point from, to;
	private boolean eaten;
	
	public Turn(Point f , Point t) {
		setFrom(f);
		setTo(t);
	}
	
	public Point getFrom() {
		return from;
	}
	public void setFrom(Point from) {
		this.from = from;
	}
	public Point getTo() {
		return to;
	}
	public void setTo(Point to) {
		this.to = to;
	}
	public boolean isEaten() {
		return eaten;
	}
	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
}
