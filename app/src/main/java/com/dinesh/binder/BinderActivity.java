package com.dinesh.binder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class BinderActivity extends Activity {

	/**
	 * Reference to our bound service.
	 */
	BinderService mService = null;
	boolean mServiceConnected = false;

	/**
	 * Class for interacting with the main interface of the service.
	 */
	private ServiceConnection mConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			Log.d("BinderActivity", "Connected to service.");
			mService = ((BinderService.LocalBinder) binder).getService();
			mServiceConnected = true;
		}

		/**
		 * Connection dropped.
		 */
		@Override
		public void onServiceDisconnected(ComponentName className) {
			Log.d("BinderActivity", "Disconnected from service.");
			mService = null;
			mServiceConnected = false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Invoking methods from service:
		final EditText textToService = (EditText) findViewById(R.id.textToService);
		final Button sendTextToService = (Button) findViewById(R.id.sendTextToService);
		final TextView responseFromService = (TextView) findViewById(R.id.responseFromService);
		sendTextToService.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mService != null) {
					String response = mService.getResponse((CharSequence) textToService.getText());
					responseFromService.setText(response);
				}
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		bindService(new Intent(this, BinderService.class), mConn, Context.BIND_AUTO_CREATE);
		startService(new Intent(this, BinderService.class));
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mServiceConnected) {
			unbindService(mConn);
			stopService(new Intent(this, BinderService.class));
			mServiceConnected = false;
		}
	}

}