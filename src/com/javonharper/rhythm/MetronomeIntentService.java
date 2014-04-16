package com.javonharper.rhythm;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

public class MetronomeIntentService extends IntentService {
	private MediaPlayer player;

	public MetronomeIntentService() {
		super("MetronomeIntentService");
		player = MediaPlayer.create(this, R.raw.bpm110);
		player.setLooping(true);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String dataString = intent.getDataString();

	}

	private void startMetronomeAudio() {
		player.start();
	}

	private void stopMetronomeAudio() {
		player.stop();
	}

	private void restartMetronome(int trackId) {
		player.reset();
		player = MediaPlayer.create(this, trackId);
	}
}
