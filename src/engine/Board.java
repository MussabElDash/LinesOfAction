package engine;

import java.util.ArrayList;

import utilities.DxDy;
import utilities.Pair;
import utilities.UndoTool;

public class Board implements BoardInterface {
	private int[][] board = new int[8][8];
	private ArrayList<Point> whiteCheckers;
	private ArrayList<Point> blackCheckers;
	private int turn;
	private boolean over;
	private UndoTool undoTool;

	public Board() {
		undoTool = new UndoTool(this);
		whiteCheckers = new ArrayList<Point>();
		blackCheckers = new ArrayList<Point>();

		for (int i = 1; i < 7; i++) {
			board[0][i] = 1;
			board[7][i] = 1;
			board[i][0] = 2;
			board[i][7] = 2;
			whiteCheckers.add(new Point(i, 0));
			whiteCheckers.add(new Point(i, 7));
			blackCheckers.add(new Point(0, i));
			blackCheckers.add(new Point(7, i));
		}
		setTurn(1);
		over = false;
	}

	private void setTurn(int i) {
		turn = i;
	}

	@Override
	public boolean move(Point start, Point end) {
		int playerN = board[start.getRow()][start.getColumn()];
		int otherPlayer = otherPlayer(playerN);
		if (!over && getPossibleMoves(start).contains(end)) {
			if (board[end.getRow()][end.getColumn()] == playerN) {
				return false;
			} else if (board[end.getRow()][end.getColumn()] == otherPlayer) {
				try {
					getCheckers(otherPlayer).remove(end);
				} catch (NullPointerException error) {
					return false;
				}
			}
			try {
				getCheckers(playerN).remove(start);
				getCheckers(playerN).add(end);
			} catch (NullPointerException error) {
				return false;
			}
			undoTool.addMove(start, end);
			board[end.getRow()][end.getColumn()] = board[start.getRow()][start
					.getColumn()];
			board[start.getRow()][start.getColumn()] = 0;
			if (getWinner() != 0) {
				over = true;
			}
			changeturn();
			return true;
		}
		return false;
	}

	public void undo() {
		if (!undoTool.isEmpty()) {
			undoTool.undo();
		}
	}

	public void twoUndos() {
		if (undoTool.getSize() > 1) {
			undoTool.undo();
			undoTool.undo();
		}
	}

	public ArrayList<Point> getCheckers(int i) {
		if (i == 1) {
			return whiteCheckers;
		}
		if (i == 2) {
			return blackCheckers;
		}
		return null;
	}

	public void fDFS(boolean[][] vis, Point start, int type) {
		if (vis[start.getRow()][start.getColumn()]) {
			return;
		}
		if (board[start.getRow()][start.getColumn()] == type) {
			vis[start.getRow()][start.getColumn()] = true;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == 0) {
						continue;
					}
					int ny = start.getRow() + i;
					int nx = start.getColumn() + j;
					if (inBounds(nx, ny)) {
						Point newPoint = new Point(nx, ny);
						fDFS(vis, newPoint, type);
					}
				}
			}
		}
	}

	public boolean checkWin(ArrayList<Point> arr, int type) {
		boolean[][] vis = new boolean[8][8];
		Point start = arr.get(0);
		fDFS(vis, start, type);
		for (int i = 0; i < arr.size(); i++) {
			Point n = arr.get(i);
			if (!vis[n.getRow()][n.getColumn()]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isGameOver() {
		return over;
	}

	@Override
	public int getWinner() {
		boolean flag1 = checkWin(whiteCheckers, 1);
		boolean flag2 = checkWin(blackCheckers, 2);
		if (flag1) {
			return 1;
		} else if (flag2) {
			return 2;
		} else if (flag1 && flag2) {
			return turn;
		} else if (getAllPossibleMoves(turn).isEmpty()) {
			return otherPlayer(turn);
		}
		return 0;
	}

	@Override
	public int getColor(Point x) {
		return board[x.getRow()][x.getColumn()];
	}

	@Override
	public int getTurn() {
		return turn;
	}

	public void changeturn() {
		turn = otherPlayer(turn);
	}

	public int otherPlayer(int i) {
		return 3 - i;
	}

	@Override
	public ArrayList<Point> getPossibleMoves(Point start) {
		return getPossibleMoves(start.getRow(), start.getColumn());
	}

	private ArrayList<Point> getPossibleMoves(int x, int y) {
		int[] dx = { 0, 1, 1, 1 };
		int[] dy = { 1, 0, 1, -1 };
		ArrayList<Point> res = new ArrayList<Point>();
		if (!over && board[x][y] == turn) {
			for (int i = 0; i < dx.length; i++) {
				int sum = 1;
				for (int j = 1; j < 8; j++) {
					if (inBounds(x + j * dx[i], y + j * dy[i])
							&& board[x + j * dx[i]][y + j * dy[i]] != 0) {
						sum++;
					}
					if (inBounds(x - j * dx[i], y - j * dy[i])
							&& board[x - j * dx[i]][y - j * dy[i]] != 0) {
						sum++;
					}
				}
				Point pos = new Point(y + dy[i] * sum, x + dx[i] * sum);
				Point neg = new Point(y - dy[i] * sum, x - dx[i] * sum);
				if (inBounds(pos) && validPoint(x, y, pos, board[x][y])) {
					res.add(pos);
				}
				if (inBounds(neg) && validPoint(x, y, neg, board[x][y])) {
					res.add(neg);
				}
			}
		}
		return res;
	}

	private boolean inBounds(Point p) {
		return inBounds(p.getX(), p.getY());
	}

	private boolean inBounds(int x, int y) {
		if (x < 8 && x >= 0 && y < 8 && y >= 0) {
			return true;
		}
		return false;
	}

	private boolean validPoint(int x, int y, Point p, int player) {
		if (board[p.getRow()][p.getColumn()] == player) {
			return false;
		}
		// int xd = p.getRow() - x, yd = p.getColumn() - y;
		// int dx = (int) Math.ceil(Math.abs(p.getRow() - x) / 8.0);
		// int dy = (int) Math.ceil(Math.abs(p.getColumn() - y) / 8.0);
		// dx = (int) Math.copySign(dx, xd);
		// dy = (int) Math.copySign(dy, yd);
		Point temp = new Point(y, x);
		int dy = DxDy.getDx(temp, p);
		int dx = DxDy.getDy(temp, p);
		int xc = dx, yc = dy;
		while (x + xc != p.getRow() || y + yc != p.getColumn()) {
			if (board[x + xc][y + yc] != (player) && board[x + xc][y + yc] != 0) {
				return false;
			}
			xc += dx;
			yc += dy;
		}

		return true;
	}

	public int[][] getBoard() {
		return board;
	}

	public ArrayList<Pair> getAllPossibleMoves(int x) {
		ArrayList<Point> player = getCheckers(x);
		ArrayList<Pair> allCheckers = new ArrayList<Pair>();
		for (Point p : player) {
			ArrayList<Point> pos = getPossibleMoves(p);
			for (Point i : pos) {
				allCheckers.add(new Pair(p, i));
			}
		}
		return allCheckers;
	}

	public int getValue() {
		double result = 0.0D;
		if (isGameOver()) {
			if (getWinner() == 2) {
				return 0x7fffffff;
			}
			if (getWinner() == 1) {
				return 0x80000000;
			}

		} else {

			double intelligentPlayer = better(turn);
			double human = better(3 - turn);
			result = human - intelligentPlayer;
		}
		return (int) result;
	}

	public double better(int t) {
		ArrayList<Point> x;
		if (t == 1) {
			x = whiteCheckers;
		} else {
			x = blackCheckers;
		}

		int length = x.size();
		double sum = 0.0D;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < i; j++) {
				sum += dist(x.get(i), x.get(j));
			}
		}

		return sum;
	}

	public double dist(Point x, Point y) {
		double xDif = Math.abs(x.getX() - y.getX());
		double yDif = Math.abs(x.getY() - y.getY());
		return Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
	}

	public UndoTool getUndoTool() {
		return undoTool;
	}

	public void setGameOver(boolean b) {
		over = b;
	}
}