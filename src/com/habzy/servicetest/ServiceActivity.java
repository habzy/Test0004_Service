package com.habzy.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceActivity extends Activity implements OnClickListener {
	protected static final String TAG = "LocalService";
	/** Called when the activity is first created. */

	private Button startAndbind;
	private Button nativeCrash;
	private Button unbind;
	private Button bind;
	private boolean show = true;
	private Intent mIntent = new Intent();
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "onServiceConnected");

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "onServiceDisconnected");

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		SharedPreferences preference = getSharedPreferences(
				ServiceActivity.class.toString(), MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean("SharedPreferences", false);
		editor.commit();

		startAndbind = (Button) findViewById(R.id.startAndbind);
		startAndbind.setOnClickListener(this);

		unbind = (Button) findViewById(R.id.unbind);
		unbind.setOnClickListener(this);

		bind = (Button) findViewById(R.id.bind);
		bind.setOnClickListener(this);

		nativeCrash = (Button) findViewById(R.id.nativeCrash);
		nativeCrash.setOnClickListener(this);

		mIntent.setAction("com.habzy.service.LocalService");

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.startAndbind) {
			if (show) {
				startService(mIntent);
				bindService(mIntent, conn, BIND_AUTO_CREATE);
			} else {
				unbindService(conn);
				stopService(mIntent);
			}
			show = !show;
		} else if (v.getId() == R.id.nativeCrash) {
			startService(mIntent);
		} else if (v.getId() == R.id.unbind) {
			unbindService(conn);
		} else if (v.getId() == R.id.bind) {
			bindService(mIntent, conn, BIND_AUTO_CREATE);
		}

	}
}