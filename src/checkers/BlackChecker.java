package checkers;

import action.of.lines.R;
import android.content.Context;
import android.util.AttributeSet;

public class BlackChecker extends Checker {

	public BlackChecker(Context context) {
		super(context);
		setBackgroundResource(R.drawable.black);
	}

	public BlackChecker(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.black);
	}

	public BlackChecker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundResource(R.drawable.black);
	}

}
