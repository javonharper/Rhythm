package com.javonharper.rhythm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RhythmActivity extends Activity {
	private boolean active = false;
	private Timer timer = null;
	private long MILLISECONDS_IN_A_MINUTE = 60000L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rhythm);
	}

	public void toggleMetronome(View view) {
		if (active) {
			changeButtonText("Start");
			stopMetronome();
		} else {
			changeButtonText("Stop");
			startMetronome();
		}
	}

	public void increaseBpm(View view) {

	}

	public void decreaseBpm(View view) {

	}

	private void startMetronome() {
		active = true;
		long interval = getBpmInterval();
		timer = new Timer("metronome", true);
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Log.d("okay", "tock");
			}
		}, 0, interval);
	}

	private void stopMetronome() {
		active = false;
		if (timer != null) {
			timer.cancel();
		}
	}

	private void changeButtonText(String text) {
		Button startStopButton = (Button) findViewById(R.id.start_stop_button);
		startStopButton.setText(text);
	}

	private long getBpmInterval() {
		return MILLISECONDS_IN_A_MINUTE / getBpm();
	}

	private long getBpm() {
		TextView bpmTextView = (TextView) findViewById(R.id.bpmView);
		return Long.parseLong((String) bpmTextView.getText());
	}
}
