package users;

import layouts.Board;
import engine.Point;

public abstract class User {
	private Board board;
	private String name;

	public User(String s) {
		name = s;
	}

	public User() {
	}

	public Board getBoardLayout() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public abstract void play();

	public boolean play(Point from, Point to) {
		return getBoardLayout().move(from, to);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
