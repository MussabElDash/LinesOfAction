package ai;

import java.util.ArrayList;
import java.util.Iterator;

import utilities.Pair;
import engine.Board;

public class AI {
	private int intelligence;
	private Pair best;
	private Board myBoard;

	public AI(int intelligence) {
		this.intelligence = intelligence;
	}

	public Pair nextMove(Board myBoard) {
		this.myBoard = myBoard;
		best = null;
		minMax(intelligence, -10000, 10000);
		return best;
	}

	public void minMax(int intelligence, int alpha, int beta) {
		ArrayList<Pair> moves = myBoard.getAllPossibleMoves(myBoard.getTurn());
		int bSF = 0x80000000;
		int p = 0x80000000;
		for (Iterator<Pair> it = moves.iterator(); it.hasNext(); myBoard.undo()) {
			Pair move = it.next();
			myBoard.move(move.getFirstPair(), move.getSecondPair());
			p = minimize(intelligence - 1, alpha, beta);
			if (p > bSF) {
				best = move;
				bSF = p;
			}
		}
		if (best == null) {
			minMax2(intelligence, alpha, beta);
		}
	}

	private void minMax2(int intelligence, int alpha, int beta) {
		ArrayList<Pair> moves = myBoard.getAllPossibleMoves(myBoard.getTurn());
		int bSF = 0x80000000;
		int p = 0x80000000;
		for (Iterator<Pair> it = moves.iterator(); it.hasNext(); myBoard.undo()) {
			Pair move = it.next();
			myBoard.move(move.getFirstPair(), move.getSecondPair());
			p = minimize(intelligence - 1, alpha, beta);
			if (p >= bSF) {
				best = move;
				bSF = p;
			}
		}
	}

	private int maximize(int intelligence, int alpha, int beta) {
		if (intelligence == 1 || myBoard.isGameOver()) {
			return myBoard.getValue();
		}
		int max = 0x80000000;
		ArrayList<Pair> moves = myBoard.getAllPossibleMoves(myBoard.getTurn());
		for (Iterator<Pair> it = moves.iterator(); it.hasNext(); myBoard.undo()) {
			Pair move = it.next();
			if (alpha >= beta) {
				return alpha;
			}
			myBoard.move(move.getFirstPair(), move.getSecondPair());
			max = Math.max(max, minimize(intelligence - 1, alpha, beta));
			alpha = Math.max(alpha, max);
		}

		return alpha;
	}

	private int minimize(int intelligence, int alpha, int beta) {
		if (intelligence == 1 || myBoard.isGameOver()) {
			return myBoard.getValue();
		}
		int min = 0x7fffffff;
		ArrayList<Pair> moves = myBoard.getAllPossibleMoves(myBoard.getTurn());
		for (Iterator<Pair> it = moves.iterator(); it.hasNext(); myBoard.undo()) {
			Pair move = it.next();
			if (beta <= alpha) {
				return beta;
			}
			myBoard.move(move.getFirstPair(), move.getSecondPair());
			min = Math.min(min, maximize(intelligence - 1, alpha, beta));
			beta = Math.min(beta, min);
		}

		return beta;
	}

}
