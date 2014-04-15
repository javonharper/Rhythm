package com.javonharper.rhythm;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MetronomeIntentService extends IntentService {
	private MediaPlayer player;

	public MetronomeIntentService(String name) {
		super(name);
		player = MediaPlayer.create(getApplicationContext(), R.raw.bpm110);
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
		player = MediaPlayer.create(getApplicationContext(), trackId);
	}
}
