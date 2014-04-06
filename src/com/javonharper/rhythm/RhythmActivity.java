package com.javonharper.rhythm;

import java.io.IOException;

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
	private boolean metronomeActive = false;
	MediaPlayer player = null;
	Vibrator vibes;
	public static long START_TRANSITION_DURATION = 2000;
	public static long END_TRANSITION_DURATION = START_TRANSITION_DURATION / 5;
	CircularSeekBar seekbar;

	// Settings for the BPM
	private Integer BPM_STEP = 5;
	private Integer MINIMUM_BPM = 65;
	private Integer MAXIMUM_BPM = 210;
	private Integer INITIAL_BPM = 110;

	// Offsets for the progress bar
	private Integer INITIAL_BPM_PROGRESS = (INITIAL_BPM - MINIMUM_BPM)
			/ BPM_STEP;
	private Integer MAXIMUM_BPM_PROGRESS = (MAXIMUM_BPM - MINIMUM_BPM)
			/ BPM_STEP;

	private Integer currentBpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		player = MediaPlayer.create(getApplicationContext(), R.raw.bpm110);
		player.setLooping(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rhythm);
		initialize();
	}

	@Override
	protected void onStart() {
		super.onStart();
		metronomeActive = false;
	}

	@Override
	protected void onStop() {
		super.onStop();
		stopMetronome();
	}

	private void initialize() {
		vibes = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		initializeSeekbar();
		initializeFonts();
	}

	private void initializeSeekbar() {
		seekbar = (CircularSeekBar) findViewById(R.id.bpmSeekbar);
		seekbar.setOnSeekBarChangeListener(new CircleSeekBarListener());
		seekbar.setProgress(INITIAL_BPM_PROGRESS);
		seekbar.setMax(MAXIMUM_BPM_PROGRESS);
		updateBpm(INITIAL_BPM_PROGRESS);
	}

	public void toggleMetronome(View view) {
		vibrate();
		if (metronomeActive) {
			stopMetronome();
		} else {
			startMetronome();
		}
	}

	private void startMetronome() {
		metronomeActive = true;
		changeButtonText("Stop");
		startMetronomeAudio();
		saturateBackground();
	}

	private void stopMetronome() {
		metronomeActive = false;
		changeButtonText("Start");
		stopMetronomeAudio();
		resetBackground();
	}

	private void vibrate() {
		vibes.vibrate(50);
	}

	/*
	 * Methods for playing the metronome
	 */
	private void startMetronomeAudio() {
		player.start();
	}

	private void stopMetronomeAudio() {
		player.stop();
	}

	private void updateBpm(int progressValue) {
		currentBpm = (progressValue * BPM_STEP) + MINIMUM_BPM;
		changeBpmText(Integer.toString(currentBpm));

		try {
			updateBpmTrack(currentBpm);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateBpmTrack(Integer bpm) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {
		String paddedBpm = String.format("%03d",
				Integer.parseInt(bpm.toString()));
		String filename = "raw/bpm" + paddedBpm;
		int bpmTrackId = getResources().getIdentifier(filename, "raw",
				getPackageName());

		player.reset();
		player = MediaPlayer.create(getApplicationContext(), bpmTrackId);
		if (metronomeActive) {
			player.start();
		}
	}

	/*
	 * Methos for updating the View
	 */
	private void initializeFonts() {
		Typeface font = Typeface.createFromAsset(getAssets(),
				"SourceSansPro-Light.ttf");

		TextView bpmTextView = (TextView) findViewById(R.id.bpmTextView);
		bpmTextView.setTypeface(font);

		TextView startStopButton = (TextView) findViewById(R.id.start_stop_button);
		startStopButton.setTypeface(font);
	}

	private void changeButtonText(String text) {
		Button startStopButton = (Button) findViewById(R.id.start_stop_button);
		startStopButton.setText(text);
	}

	private void changeBpmText(String text) {
		TextView bpmTextView = (TextView) findViewById(R.id.bpmTextView);
		bpmTextView.setText(text);
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
			updateBpm(progress);
		}
	}
}
