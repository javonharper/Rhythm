package com.javonharper.rhythm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RhythmActivity extends Activity {
	private boolean metronomeActive = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rhythm);
	}

	public void toggleMetronome(View view) {
		if (metronomeActive) {
			changeButtonText("Stop");
			stopMetronome();
		} else {
			changeButtonText("Start");
			startMetronome();
		}
	}

	public void increaseBpm(View view) {

	}

	public void decreaseBpm(View view) {

	}

	private void startMetronome() {
		metronomeActive = true;
		
	}

	private void stopMetronome() {
		metronomeActive = false;
	}
	
	private void changeButtonText(String text) {
		Button startStopButton = (Button)findViewById(R.id.start_stop_button);
		startStopButton.setText(text);
	}
}
