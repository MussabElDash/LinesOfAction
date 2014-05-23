package utilities;

import java.util.Stack;

import engine.Board;
import engine.Point;

public class UndoTool {
	private Stack<Turn> theStack;
	private Board board;

	public UndoTool(Board board) {
		this.board = board;
		theStack = new Stack<Turn>();
	}

	public UndoTool(UndoTool u) {
		theStack = new Stack<Turn>();
		theStack.addAll(u.theStack);
	}

	public void addMove(Point from, Point to) {
		Turn t = new Turn(from, to);
		if (board.getColor(to) != 0) {
			t.setEaten(true);
		}
		theStack.push(t);
	}

	public void undo() {
		Turn t = theStack.pop();
		Point from = t.getFrom();
		Point to = t.getTo();
		int turn = board.getColor(to);
		board.getBoard()[from.getY()][from.getX()] = board.getColor(to);
		board.getCheckers(turn).remove(to);
		board.getCheckers(turn).add(from);
		if (t.isEaten()) {
			board.getBoard()[to.getY()][to.getX()] = 3 - turn;
			board.getCheckers(3 - turn).add(to);
		} else {
			board.getBoard()[to.getY()][to.getX()] = 0;
		}
		board.changeturn();
		if (board.isGameOver()) {
			board.setGameOver(false);
		}
	}

	public boolean isEmpty() {
		return theStack.isEmpty();
	}

	public Pair getFirstMove() {
		Turn t = theStack.remove(0);
		return new Pair(t.getFrom(), t.getTo());
	}

	public int getSize() {
		return theStack.size();
	}
}
