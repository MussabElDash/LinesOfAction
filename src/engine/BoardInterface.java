package engine;

import java.util.ArrayList;

public interface BoardInterface {
	boolean move(Point start, Point end);

	boolean isGameOver();

	int getWinner();

	int getColor(Point x);

	int getTurn();

	ArrayList<Point> getPossibleMoves(Point start);

}