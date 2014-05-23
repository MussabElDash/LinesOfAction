package utilities;

import layouts.Board;
import users.GUI;
import users.User;

public class Game {
	private User player1, player2, turn;
	private Board board;

	public Game(User p1, User p2, Board b) {
		setBoardLayout(b);
		setPlayer1Name(p1);
		setPlayer2Name(p2);
		p1.setBoard(b);
		p2.setBoard(b);
		setPlayer1(p1);
		setPlayer2(p2);
		turn = player1;
	}

	public void start() {
		while (true)
			if (getTurn1() != null) {
				getTurn1().play();
				changeTurn();
			}

	}

	public User getPlayer1() {
		return player1;
	}

	public void setPlayer1(User player1) {
		this.player1 = player1;
	}

	public User getPlayer2() {
		return player2;
	}

	public void setPlayer2(User player2) {
		this.player2 = player2;
	}

	public Board getBoardLayout() {
		return board;
	}

	public void setBoardLayout(Board board) {
		this.board = board;
	}

	private User getTurn1() {
		if (!board.getBoard().isGameOver()) {
			return getTurn();
		}
		return null;
	}

	public User getTurn() {
		return turn;
	}

	public void changeTurn() {
		if (turn == player1)
			turn = player2;
		else
			turn = player1;
	}

	private void setPlayer1Name(User p) {
		if (p instanceof GUI) {
			p.setName("Player 1");
			return;
		}
		p.setName("CPU");
	}

	private void setPlayer2Name(User p) {
		if (p instanceof GUI) {
			p.setName("Player 2");
			return;
		}
		p.setName("CPU");
	}

	public User getWinner() {
		if (board.getBoard().getWinner() == 1)
			return player1;
		else if (board.getBoard().getWinner() == 2)
			return player2;
		return null;
	}
}
