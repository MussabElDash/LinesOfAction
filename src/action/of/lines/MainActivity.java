package action.of.lines;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		con = this;

		final TextView level = (TextView) findViewById(R.id.levelNo);
		level.setText("Easy");

		final SeekBar seek = (SeekBar) findViewById(R.id.seekBar1);
		seek.setMax(2);
		seek.setProgress(0);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress == 0) {
					level.setText("Easy");
				} else if (progress == 1) {
					level.setText("Normal");
				} else if (progress == 2) {
					level.setText("Hard");
				}
			}
		});

		Button x = (Button) findViewById(R.id.singlePlay);
		x.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// custom dialog
				final Dialog dialog = new Dialog(con);
				dialog.setContentView(R.layout.single_dialog);

				Button yes = (Button) dialog.findViewById(R.id.button1);
				yes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(con, BoardActivity.class);
						intent.putExtra("Single", true);
						intent.putExtra("Start", true);
						if (seek.getProgress() == 0 || seek.getProgress() == 1)
							intent.putExtra("Level", 2);
						else
							intent.putExtra("Level", seek.getProgress() + 1);
						startActivity(intent);
						dialog.dismiss();
					}
				});

				Button no = (Button) dialog.findViewById(R.id.button2);
				no.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(con, BoardActivity.class);
						intent.putExtra("Single", true);
						intent.putExtra("Start", false);
						if (seek.getProgress() == 0 || seek.getProgress() == 1)
							intent.putExtra("Level", 2);
						else
							intent.putExtra("Level", seek.getProgress() + 1);
						startActivity(intent);
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});

		Button y = (Button) findViewById(R.id.twoPlay);
		y.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(con, BoardActivity.class);
				intent.putExtra("Single", false);
				intent.putExtra("Start", false);
				intent.putExtra("Level", 0);
				startActivity(intent);
			}
		});

		Button z = (Button) findViewById(R.id.howtoplay);
		z.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String url = "http://en.m.wikipedia.org/wiki/Lines_of_Action";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}
}
