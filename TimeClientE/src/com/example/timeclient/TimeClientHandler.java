package com.example.timeclient;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import android.os.Handler;
import android.os.Message;

public class TimeClientHandler extends IoHandlerAdapter {

	private Handler handler;

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		Message msg = new Message();
		msg.what = MainActivity.MSG_RECV;
		msg.obj = message.toString();
		handler.sendMessage(msg);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}

}
