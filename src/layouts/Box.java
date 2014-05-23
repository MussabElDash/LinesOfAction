package layouts;

import action.of.lines.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Box extends LinearLayout {

	public Box(Context context) {
		super(context);
		init(context, null, 0);
	}

	public Box(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public Box(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		setBackgroundResource(R.drawable.frame1);
		int left, right, top, bottom;
		left = right = 18;
		top = bottom = 10;
		setPadding(left, top, right, bottom);

		RelativeLayout layout = new RelativeLayout(context, attrs, defStyle);
		layout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		layout.setBackgroundResource(R.drawable.sky);

		Button menu = new Button(context, attrs, defStyle);
		menu.setBackgroundResource(R.drawable.menubutton);

		Button undo = new Button(context, attrs, defStyle);
		undo.setBackgroundResource(R.drawable.undobutton);

		ImageView img = new ImageView(context, attrs, defStyle);
		img.setImageResource(R.drawable.lines);

		layout.addView(menu);
		layout.addView(img);
		layout.addView(undo);

		addView(layout);
	}

}
