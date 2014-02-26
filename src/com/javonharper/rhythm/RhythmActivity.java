package com.javonharper.rhythm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.devadvance.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener;

public class RhythmActivity extends Activity {
	private boolean active = false;
	private Timer timer = null;
	private long MILLISECONDS_IN_A_MINUTE = 60000L;
	MediaPlayer player = null;
	Vibrator vibes;
	public static long START_TRANSITION_DURATION = 2000;
	public static long END_TRANSITION_DURATION = START_TRANSITION_DURATION / 5;
	CircularSeekBar seekbar;
	private Integer INITIAL_BPM = 110;
	private Integer currentBpm = INITIAL_BPM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		player = MediaPlayer.create(getApplicationContext(), R.raw.woodblock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rhythm);
		vibes = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		seekbar = (CircularSeekBar) findViewById(R.id.bpmSeekbar);
		seekbar.setOnSeekBarChangeListener(new CircleSeekBarListener());
		changeBpmText(INITIAL_BPM.toString());
	}

	@Override
	protected void onStart() {
		super.onStart();
		active = false;
		initializeFonts();
	}

	@Override
	protected void onStop() {
		super.onStop();
		stopMetronome();
	}

	public void toggleMetronome(View view) {
		vibrate();
		if (active) {
			startMetronome();
		} else {
			stopMetronome();
		}
	}

	private void stopMetronome() {
		changeButtonText("Stop");
		resetBackground();
		startMetronomeTimer();
	}

	private void startMetronome() {
		changeButtonText("Start");
		saturateBackground();
		stopMetronomeTimer();
	}

	private void startMetronomeTimer() {
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

	private void stopMetronomeTimer() {
		active = false;

		if (timer != null) {
			timer.cancel();
		}
	}

	private void changeButtonText(String text) {
		Button startStopButton = (Button) findViewById(R.id.start_stop_button);
		startStopButton.setText(text);
	}

	private void changeBpmText(String text) {
		TextView bpmTextView = (TextView) findViewById(R.id.bpmTextView);
		bpmTextView.setText(text);
	}

	private long getBpmInterval() {
		return MILLISECONDS_IN_A_MINUTE / getBpm();
	}

	private long getBpm() {
		return currentBpm;
	}

	private void initializeFonts() {
		Typeface font = Typeface.createFromAsset(getAssets(),
				"SourceSansPro-Light.ttf");

		TextView bpmTextView = (TextView) findViewById(R.id.bpmTextView);
		bpmTextView.setTypeface(font);

		TextView startStopButton = (TextView) findViewById(R.id.start_stop_button);
		startStopButton.setTypeface(font);
	}

	private void vibrate() {
		vibes.vibrate(50);
	}

	private void saturateBackground() {
		View view = (View) findViewById(R.id.appView);
		TransitionDrawable background = (TransitionDrawable) view
				.getBackground();
		background.startTransition((int) START_TRANSITION_DURATION);
	}

	private void resetBackground() {
		View view = (View) findViewById(R.id.appView);
		TransitionDrawable background = (TransitionDrawable) view
				.getBackground();
		background.reverseTransition((int) END_TRANSITION_DURATION);
	}

	public class CircleSeekBarListener implements
			OnCircularSeekBarChangeListener {
		@Override
		public void onStopTrackingTouch(CircularSeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(CircularSeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProgressChanged(CircularSeekBar circularSeekBar,
				int progress, boolean fromUser) {
			changeBpmText(Integer.toString(progress));
		}
	}
}
