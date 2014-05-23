package checkers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class Checker extends LinearLayout {

	public Checker(Context context) {
		super(context);
	}

	public Checker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Checker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

}
