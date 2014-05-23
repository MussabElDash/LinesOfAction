package utilities;

import java.io.Serializable;

import engine.Point;

@SuppressWarnings("serial")
public class Pair implements Serializable {
	private Point firstPair;
	private Point secondPair;

	public Pair(Point st, Point nd) {
		setFirstPair(st);
		setSecondPair(nd);
	}

	public Point getFirstPair() {
		return firstPair;
	}
	public void setFirstPair(Point firstPair) {
		this.firstPair = firstPair;
	}
	public Point getSecondPair() {
		return secondPair;
	}
	public void setSecondPair(Point secondPair) {
		this.secondPair = secondPair;
	}

	@Override
	public String toString() {
		return firstPair + " , " + secondPair;
	}
}
