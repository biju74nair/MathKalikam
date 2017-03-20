package com.myapp.games.math.mathkalikam.puzzle.advanced;

import com.myapp.games.math.mathkalikam.R;
import com.myapp.games.math.mathkalikam.R.id;
import com.myapp.games.math.mathkalikam.R.layout;
import com.myapp.games.math.mathkalikam.R.menu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MathPuzzleAdvanced extends Activity implements OnClickListener {

	Button[][] PuzzButt = new Button[4][4];
	int[][] PuzzleBoard = new int[4][4];
	Button NewGame = null;
	Button about = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);

		storeButtons();
		addListeners();
		this.NewGame = (Button) findViewById(R.id.newGame);
		this.NewGame.setOnClickListener(this);
		
		this.about = (Button) findViewById(R.id.about);
		this.about.setOnClickListener(this);
		
		this.PuzzButt[3][3].setVisibility(View.INVISIBLE);
		NewGame();
	}

	private void addListeners() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				this.PuzzButt[i][j].setOnClickListener(this);
			}
		}
	}

	private void storeButtons() {
		this.PuzzButt[0][0] = (Button) findViewById(R.id.b11);
		this.PuzzButt[0][1] = (Button) findViewById(R.id.b12);
		this.PuzzButt[0][2] = (Button) findViewById(R.id.b13);
		this.PuzzButt[0][3] = (Button) findViewById(R.id.b14);
		this.PuzzButt[1][0] = (Button) findViewById(R.id.b21);
		this.PuzzButt[1][1] = (Button) findViewById(R.id.b22);
		this.PuzzButt[1][2] = (Button) findViewById(R.id.b23);
		this.PuzzButt[1][3] = (Button) findViewById(R.id.b24);
		this.PuzzButt[2][0] = (Button) findViewById(R.id.b31);
		this.PuzzButt[2][1] = (Button) findViewById(R.id.b32);
		this.PuzzButt[2][2] = (Button) findViewById(R.id.b33);
		this.PuzzButt[2][3] = (Button) findViewById(R.id.b34);
		this.PuzzButt[3][0] = (Button) findViewById(R.id.b41);
		this.PuzzButt[3][1] = (Button) findViewById(R.id.b42);
		this.PuzzButt[3][2] = (Button) findViewById(R.id.b43);
		this.PuzzButt[3][3] = (Button) findViewById(R.id.b44);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.newGame:
			NewGame();
			break;
		case R.id.about:
			showStatus("Simple Math Games By Biju B Nair (Amit)", Toast.LENGTH_LONG);
			break;
		default:
			refreshPuzzleBoard(v.getId());
		}
	}

	public void NewGame() {
		generateArray();
	}

	public void generateArray() {
		int[] array = new int[16];
		for (int i = 0; i < 15; i++) {
			array[i] = random(array);
		}

		array[15] = 0;
		int ind = 0;
		for (int j = 0; j < 4; j++)
			for (int k = 0; k < 4; k++) {
				this.PuzzleBoard[j][k] = array[ind];
				this.PuzzButt[j][k].setText(String.valueOf(array[ind]));
				this.PuzzButt[j][k].setTextColor(Color.BLACK);
				if (array[ind] != 0)
					this.PuzzButt[j][k].setVisibility(View.VISIBLE);
				else
					this.PuzzButt[j][k].setVisibility(View.INVISIBLE);
				ind++;
			}
	}

	public void getZeroIndex(int[] index) {
		for (int j = 0; j < 4; j++)
			for (int k = 0; k < 4; k++)
				if (this.PuzzleBoard[j][k] == 0) {
					index[0] = j;
					index[1] = k;
					return;
				}
	}

	public int[] checkIllegalMove(int row, int col) {
		int[] index = new int[2];
		getZeroIndex(index);
		if ((index[0] == row) || (index[1] == col)) {
			if ((index[0] == row - 1) || (index[1] == col - 1)
					|| (index[0] == row + 1) || (index[1] == col + 1))
				return index;
		}
		return null;
	}

	public void displayWinScreen() {
		int[] c = { Color.RED, Color.GREEN, Color.YELLOW, Color.WHITE,
				Color.BLUE, Color.CYAN, Color.GRAY, Color.MAGENTA,
				Color.DKGRAY, Color.LTGRAY };
		int i = 0;
		for (int t = 0; t < 5; t++) {
			if (t >= c.length)
				i = 0;
			else
				i = t;
			for (int j = 0; j < 4; j++)
				for (int k = 0; k < 4; k++) {
					this.PuzzButt[j][k].setBackgroundColor(c[(i++)]);
					if (i == c.length)
						i = 0;
				}
		}
		showStatus("Good Work - U have won the match", Toast.LENGTH_LONG);

	}

	private void showStatus(String text) {
		showStatus(text, Toast.LENGTH_SHORT);
	}

	private void showStatus(String text, int dur) {
		Toast.makeText(this, text, dur).show();
	}

	public boolean checkWinStatus() {
		int i = 1;
		int last = this.PuzzleBoard[3][3];
		this.PuzzleBoard[3][3] = 16;
		for (int j = 0; j < 4; j++)
			for (int k = 0; k < 4; k++) {
				if (this.PuzzleBoard[j][k] != i++) {
					this.PuzzleBoard[3][3] = last;
					return false;
				}
			}
		return true;
	}

	public void refreshPuzzleBoard(int ClickedButtonId) {
		int value = getValueById(ClickedButtonId);
		int[] index =  getIndex(value);
		int[] zind = checkIllegalMove(index[0], index[1]);
		if (zind == null) {
			showStatus("Illegal Move");
			this.PuzzButt[index[0]][index[1]].setTextColor(Color.BLACK);
		} else {
			this.PuzzButt[index[0]][index[1]].setVisibility(View.INVISIBLE);
			this.PuzzButt[zind[0]][zind[1]].setVisibility(View.VISIBLE);
			this.PuzzButt[index[0]][index[1]].setText("0");
			this.PuzzButt[zind[0]][zind[1]].setText(String
					.valueOf(this.PuzzleBoard[index[0]][index[1]]));
			this.PuzzleBoard[zind[0]][zind[1]] = this.PuzzleBoard[index[0]][index[1]];
			this.PuzzleBoard[index[0]][index[1]] = 0;
		}
		if (checkWinStatus()) {
			displayWinScreen();
		}
	}

	private int getValueById(int clickedButtonId) {
		int value = -1;
		for (int j = 0; j < 4; j++){
			for (int k = 0; k < 4; k++) {
				if (clickedButtonId == this.PuzzButt[j][k].getId()) {
					value = Integer.parseInt(this.PuzzButt[j][k].getText().toString());
					break;
				}
			}
		}
		return value;
	}

	public int[] getIndex(int value) {
		int[] index = new int[2];
		for (int j = 0; j < 4; j++){
			for (int k = 0; k < 4; k++) {
				if (value == this.PuzzleBoard[j][k]) {
					index[0] = j;
					index[1] = k;
					break;
				}
			}
		}
		return index;
	}

	public int random(int[] array) {
		while (true) {
			double a = Math.random();
			double c = a * 100.0D;
			int d = (int) c * 10 / 10;

			if ((d > 0) && (d < 16)) {
				int i = 0;
				while (d != array[i]) {
					i++;
					if (i >= array.length) {
						return d;
					}
				}
			}
		}
	}

}
