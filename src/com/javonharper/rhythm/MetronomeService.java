package com.javonharper.rhythm;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MetronomeService extends IntentService {

	public MetronomeService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String dataString = intent.getDataString();
	}
}
