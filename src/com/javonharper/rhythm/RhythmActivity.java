package com.javonharper.rhythm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RhythmActivity extends Activity {
	private boolean active = false;
	private Timer timer = null;
	private long MILLISECONDS_IN_A_MINUTE = 60000L;
	MediaPlayer player = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		player = MediaPlayer.create(getApplicationContext(), R.raw.woodblock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rhythm);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initializeFonts();
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
		TextView bpmView = (TextView) findViewById(R.id.bpmView);
		Integer bpm = Integer.valueOf(bpmView.getText().toString()) + 1;
		bpmView.setText(bpm.toString());
		restartMetronome();
	}

	public void decreaseBpm(View view) {
		TextView bpmView = (TextView) findViewById(R.id.bpmView);
		Integer bpm = Integer.valueOf(bpmView.getText().toString()) - 1;
		bpmView.setText(bpm.toString());		
		restartMetronome();
	}
	
	private void restartMetronome() {
		if (active) {
			stopMetronome();
			startMetronome();
		}	
	}

	private void startMetronome() {
		active = true;
		
		long interval = getBpmInterval();
		timer = new Timer("metronome", true);
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				player.start();
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
	
	private void initializeFonts() {
		Typeface font = Typeface.createFromAsset(getAssets(),
				"SourceSansPro-Light.ttf");

		TextView bpmView = (TextView) findViewById(R.id.bpmView);
		bpmView.setTypeface(font);
		
		TextView startStopButton = (TextView) findViewById(R.id.start_stop_button);
		startStopButton.setTypeface(font);
	}
}
