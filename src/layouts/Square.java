package layouts;

import action.of.lines.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import checkers.BlackChecker;
import checkers.Checker;
import checkers.RedChecker;
import engine.Point;

public class Square extends LinearLayout {
	private Point point;
	private Checker checker;
	private BlackChecker blackChecker;
	private RedChecker redChecker;
	private boolean possibleMove;

	public Square(Context context) {
		super(context);
		blackChecker = new BlackChecker(context);
		redChecker = new RedChecker(context);
		LayoutParams params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		blackChecker.setLayoutParams(params);
		redChecker.setLayoutParams(params);
	}

	public Square(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttrs(context, attrs);
		blackChecker = new BlackChecker(context, attrs);
		redChecker = new RedChecker(context, attrs);
		LayoutParams params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		blackChecker.setLayoutParams(params);
		redChecker.setLayoutParams(params);
	}

	// public Square(Context context, AttributeSet attrs, int defStyle) {
	// super(context, attrs, defStyle);
	// getAttrs(context, attrs);
	// blackChecker = new BlackChecker(context, attrs, defStyle);
	// redChecker = new RedChecker(context, attrs, defStyle);
	// LayoutParams params = new LayoutParams(
	// android.view.ViewGroup.LayoutParams.MATCH_PARENT,
	// android.view.ViewGroup.LayoutParams.MATCH_PARENT);
	// blackChecker.setLayoutParams(params);
	// redChecker.setLayoutParams(params);
	// }

	private void getAttrs(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.Square, 0, 0);
		try {
			int x = a.getInteger(R.styleable.Square_X_axis_j, 0);
			int y = a.getInteger(R.styleable.Square_Y_axis_i, 0);
			setPoint(new Point(x, y));
			setColor(x, y);
		} finally {
			a.recycle();
		}
	}

	public void setColor(int i, int j) {
		setGravity(Gravity.CENTER);
		setPoint(new Point(j, i));
		if ((i + j) % 2 == 0) {
			setBackgroundResource(R.drawable.texture);
		} else {
			setBackgroundResource(R.drawable.snow);
		}
	}

	public Point getPoint() {
		return point;
	}

	private void setPoint(Point point) {
		this.point = point;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	public void addRedChecker() {
		removeChecker();
		checker = redChecker;
		addView(checker);
		// new add().execute(null, null);
	}

	public void addBlackChecker() {
		removeChecker();
		checker = blackChecker;
		addView(checker);
		// new add().execute(null, null);
	}

	public void removeChecker() {
		if (checker != null)
			removeView(checker);
		// new remove().execute(null, null);
		checker = null;
	}

	public Checker getChecker() {
		return checker;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (isPossibleMove()) {
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setStrokeWidth(1);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.CYAN);
			int x = 8;
			canvas.drawCircle(getWidth() / 2, getHeight() / 2,
					(getWidth() / x) + 3f, paint);
			paint.setColor(Color.BLACK);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2,
					(getWidth() / x) + 0.1f, paint);
			paint.setColor(Color.GREEN);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2,
					(getWidth() / x) + 0f, paint);
		}
	}

	public boolean isPossibleMove() {
		return possibleMove;
	}

	public void setPossibleMove(boolean possibleMove) {
		this.possibleMove = possibleMove;
	}

	public void setChecker(Checker checker) {
		this.checker = checker;
	}

	public BlackChecker getBlackChecker() {
		return blackChecker;
	}

	public RedChecker getRedChecker() {
		return redChecker;
	}

	// private class remove extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... paramArrayOfParams) {
	// return null;
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// removeView(checker);
	// }
	// }
	//
	// private class add extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... paramArrayOfParams) {
	// return null;
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// addView(checker);
	// }
	// }

}
