package checkers;

import action.of.lines.R;
import android.content.Context;
import android.util.AttributeSet;

public class RedChecker extends Checker {

	public RedChecker(Context context) {
		super(context);
		setBackgroundResource(R.drawable.red);
	}

	public RedChecker(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.red);
	}

	public RedChecker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundResource(R.drawable.red);
	}

}
