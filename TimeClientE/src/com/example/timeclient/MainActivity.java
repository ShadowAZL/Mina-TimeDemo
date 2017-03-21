package com.example.timeclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final int MSG_RECV = 1;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case MSG_RECV:
					text.setText(msg.obj.toString());
					break;
				default:
					break;
			}
		}
	};
	
	
	private TextView text;
	private Button sendButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView) findViewById(R.id.time_text);
		sendButton = (Button) findViewById(R.id.send_button);
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Log.d("DEBUG", "51");
						ConnectServer.send(handler, "Hello Mina!");
					}
				}).start();;
				
			}
		});
	}
}
