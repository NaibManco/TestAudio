package com.example.testaudio;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 
 * @author totoro
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	Recorder mRecorder;
	Player mPlayer;
	Button startBtn;
	Button stopBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mRecorder = new Recorder();
		mPlayer = new Player();
		startBtn = (Button) findViewById(R.id.start_record);
		startBtn.setOnClickListener(this);
		stopBtn = (Button) findViewById(R.id.stop_record);
		stopBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == startBtn) {
			if (!mRecorder.isOpen) {
				mRecorder.openRecorder();
			}
			new Thread(new Runnable(){

				@Override
				public void run() {
					mRecorder.startRecord();	
				}
				
			}).start();
		} else if (v == stopBtn) {
//			if (mRecorder.isRecording) {
//				mRecorder.stopRecord();
//			}
			Intent intent = new Intent(Intent.ACTION_ANSWER);
			startActivity(intent);
		}		
	}
}
