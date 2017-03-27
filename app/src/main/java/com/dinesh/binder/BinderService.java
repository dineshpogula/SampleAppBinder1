package com.dinesh.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BinderService extends Service {
	/**
	 * Local Binder returned when binding with Activity.
	 * This binder will return the enclosing BinderService instance. 
	 */
	public class LocalBinder extends Binder {
		/**
		 * Return enclosing BinderService instance
		 */
		BinderService getService() {
			return BinderService.this;
		}
	}

	private final IBinder mBinder = new LocalBinder();


	@Override
	public IBinder onBind(Intent intent) {
		Log.d("BinderService", "Binding...");
		return mBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("BinderService", "Service started");
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * Public method which can be called from bound clients.
	 * 
	 * This method will return a String as a response to passed query
	 */
	public String getResponse(CharSequence query) {
		return "Who's there? You wrote: " + query;
	}

}
