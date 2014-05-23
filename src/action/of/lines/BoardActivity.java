package action.of.lines;

import layouts.Square;
import layouts.refresh;
import users.AIUser;
import users.GUI;
import users.User;
import utilities.Game;
import utilities.cons;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import engine.Board;

public class BoardActivity extends Activity {
	Board board;
	boolean single, start;
	Context con;
	Game game;
	int level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		con = this;
		final layouts.Board boardlayout = (layouts.Board) findViewById(R.id.board);
		board = boardlayout.getBoard();

		AdView ad = (AdView) findViewById(R.id.ad);
		ad.loadAd(new AdRequest());

		boardlayout.setRefresh(new refresh() {

			@Override
			public void refreshUI() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Square[][] squares = boardlayout.getSquares();
						for (int i = 0; i < squares.length; i++) {
							for (int j = 0; j < squares[i].length; j++) {
								if (board.getBoard()[i][j] == 1)
									squares[i][j].addRedChecker();
								else if (board.getBoard()[i][j] == 2)
									squares[i][j].addBlackChecker();
								else
									squares[i][j].removeChecker();
							}
						}

					}
				});
			}

			@Override
			public void showWinner() {
				if (game.getBoardLayout().getBoard().isGameOver())
					runOnUiThread(new Runnable() {
						public void run() {
							final Dialog dialog = new Dialog(con);
							dialog.setContentView(R.layout.winner_dialog);

							TextView win = (TextView) dialog
									.findViewById(R.id.TheWinner);
							win.setText("The Winner is "
									+ game.getWinner().getName());
							Button menu = (Button) dialog
									.findViewById(R.id.mainMenu);
							menu.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									finish();
								}
							});

							Button undo = (Button) dialog
									.findViewById(R.id.UNDO);
							undo.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									User p = game.getTurn();
									if (p instanceof GUI) {
										board.twoUndos();
									} else {
										board.undo();
										game.changeTurn();
									}
									boardlayout.refreshBoard();
									dialog.dismiss();
								}
							});

							Button replay = (Button) dialog
									.findViewById(R.id.RePlay);
							replay.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(con,
											BoardActivity.class);
									intent.putExtra("Single", single);
									intent.putExtra("Start", start);
									intent.putExtra("Level", level);
									startActivity(intent);
									finish();
								}
							});
							dialog.show();
						}
					});
			}
		});

		Bundle bundle = getIntent().getExtras();

		single = bundle.getBoolean("Single");
		start = bundle.getBoolean("Start");
		level = bundle.getInt("Level");

		if (single)
			if (start)
				game = new Game(new GUI(), new AIUser(level), boardlayout);
			else
				game = new Game(new AIUser(level), new GUI(), boardlayout);
		else
			game = new Game(new GUI(), new GUI(), boardlayout);

		cons.GAME = game;

		Button undo = (Button) findViewById(R.id.undo);
		undo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				User p = game.getTurn();
				if (single) {
					if (p instanceof GUI) {
						board.twoUndos();
						boardlayout.refreshBoard();
					} else if (board.isGameOver()) {
						board.undo();
						game.changeTurn();
						boardlayout.refreshBoard();
					}
				} else {
					board.undo();
					game.changeTurn();
					boardlayout.refreshBoard();
				}
			}
		});

		Button menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// custom dialog
				final Dialog dialog = new Dialog(con);
				dialog.setContentView(R.layout.menu_dialog);

				Button yes = (Button) dialog.findViewById(R.id.button1);
				yes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});

				Button no = (Button) dialog.findViewById(R.id.button2);
				no.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		new Thread(new Runnable() {

			@Override
			public void run() {
				game.start();
			}
		}).start();
	}

	@Override
	public void onBackPressed() {
		final Dialog dialog = new Dialog(con);
		dialog.setContentView(R.layout.menu_dialog);

		Button yes = (Button) dialog.findViewById(R.id.button1);
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button no = (Button) dialog.findViewById(R.id.button2);
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}
}