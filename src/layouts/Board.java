package layouts;

import java.util.ArrayList;

import action.of.lines.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import checkers.Checker;
import engine.Point;

public class Board extends LinearLayout implements OnClickListener {
	private Square[][] squares;
	private engine.Board board;
	private boolean turnOver;
	private refresh refresh;

	public Board(Context context) {
		super(context);
		addSquares(context, null, 0);
	}

	public Board(Context context, AttributeSet attrs) {
		super(context, attrs);
		addSquares(context, attrs, 0);
	}

	public Board(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		addSquares(context, attrs, defStyle);
	}

	private void addSquares(Context context, AttributeSet attrs, int defStyle) {
		int pad = 10;
		setPadding(pad, pad, pad, pad);
		setBackgroundResource(R.drawable.frame);
		board = new engine.Board();
		setOrientation(LinearLayout.VERTICAL);
		setWeightSum(8);
		squares = new Square[8][8];
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				squares[i][j] = new Square(context, attrs);
				squares[i][j].setColor(i, j);
				squares[i][j].setLayoutParams(new LayoutParams(
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
			}
		}
		for (int i = 0; i < 8; i++) {
			addSquaresRow(i, context, attrs, defStyle);
		}
		refreshBoard();
	}

	private void addSquaresRow(int row, Context context, AttributeSet attrs,
			int defStyle) {
		LinearLayout layout = new LinearLayout(context, attrs);
		layout.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		layout.setWeightSum(8);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		for (int i = 0; i < 8; i++) {
			layout.addView(squares[row][i]);
		}
		addView(layout);
	}

	public void refreshBoard() {
		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard()[i].length; j++) {
				if (board.getBoard()[i][j] == 1)
					squares[i][j].addRedChecker();
				else if (board.getBoard()[i][j] == 2)
					squares[i][j].addBlackChecker();
				else
					squares[i][j].removeChecker();
			}
		}
	}

	private Point possibleStart;

	@Override
	public void onClick(View v) {
		returnAllNotPossible();
		Square theSquare;
		if (v instanceof Checker) {
			Checker theChecker = (Checker) v;
			theSquare = (Square) theChecker.getParent();
		} else
			theSquare = (Square) v;
		Point point = theSquare.getPoint();
		if (possibleStart != null && move(possibleStart, point)) {
			return;
		}
		possibleStart = point;
		ArrayList<Point> possibleMoves = board.getPossibleMoves(possibleStart);
		for (Point p : possibleMoves) {
			getSquare(p).setPossibleMove(true);
			getSquare(p).invalidate();
		}
	}

	public boolean move(Point start, Point end) {
		if (board.move(start, end)) {
			possibleStart = null;
			refresh.refreshUI();
			removeAllListeners();
			refresh.showWinner();
			return true;
		}
		return false;

	}

	public Square getSquare(Point p) {
		return squares[p.getY()][p.getX()];
	}

	public void returnAllNotPossible() {
		for (Square[] sA : squares)
			for (Square sq : sA) {
				sq.setPossibleMove(false);
				sq.invalidate();
			}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(7);
		canvas.drawRect(10, 10, getWidth() - 10, getHeight() - 10, paint);
	}

	public engine.Board getBoard() {
		return board;
	}

	public void removeAllListeners() {
		for (Square[] sA : squares)
			for (Square sq : sA) {
				sq.setOnClickListener(null);
				if (sq.getChecker() != null)
					sq.getChecker().setOnClickListener(null);
			}
		turnOver = true;
	}

	public void addAllListeners() {
		for (Square[] sA : squares)
			for (Square sq : sA) {
				sq.setOnClickListener(this);
				if (sq.getChecker() != null)
					sq.getChecker().setOnClickListener(this);
			}
		turnOver = false;
	}

	public boolean allListenersAreNull() {
		return turnOver;
	}

	public Square[][] getSquares() {
		return squares;
	}

	public void setRefresh(refresh refresh) {
		this.refresh = refresh;
	}

}
