package com.habzy.servicetest.process2;


import com.habzy.servicetest.R;
import com.habzy.servicetest.ServiceActivity;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {

	private static final String TAG = "LocalService";

	// Unique Identification Number for the Notification.
	// We use it on Notification start, and to cancel it.
	private int NOTIFICATION = R.string.local_service_started;
	

	/**
	 * Class for clients to access. Because we know this service always runs in
	 * the same process as its clients, we don't need to deal with IPC.
	 */
	public class LocalBinder extends Binder {
		LocalService getService() {
			return LocalService.this;
		}
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		
		Log.d(TAG, checkJNI());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		boolean crashed = false;
		SharedPreferences preference = getSharedPreferences(ServiceActivity.class.toString(), MODE_PRIVATE
				);
		crashed = preference.getBoolean("SharedPreferences", false);
		
		Log.d("LocalService", "Received start id " + startId + ": " + intent+";crashed:"+crashed);
		
		if(1 == startId && crashed)
		{
			Editor editor = preference.edit();
			editor.putBoolean("SharedPreferences", false);
			editor.commit();	
		}
		
		if(3 < startId && !crashed)
		{
			Editor editor = preference.edit();
			editor.putBoolean("SharedPreferences", true);
			editor.commit();			
			
			nativeCrash();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// This is the object that receives interactions from clients. See
	// RemoteService for a more complete example.
	private final IBinder mBinder = new LocalBinder();
	
	public static native String checkJNI();
	
	public static native void nativeCrash();


	static {
		System.loadLibrary("native_service");
	}
}