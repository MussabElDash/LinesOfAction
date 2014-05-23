package action.of.lines;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
	Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		con = this;

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Intent newActivity = new Intent(con, MainActivity.class);
					startActivity(newActivity);
				}
			}
		}).start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
