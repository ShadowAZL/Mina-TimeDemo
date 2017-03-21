package com.example.timeclient;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.os.Handler;
import android.util.Log;


public class ConnectServer {
	
	private static int PORT = 9123;
	
	private static TimeClientHandler timeHandler;
	private static final Object LOCK = new Object();
	private static ConnectFuture cf;
	
	
	public static void connect(){
		
		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		connector.setConnectTimeoutCheckInterval(10000);
		
		timeHandler = new TimeClientHandler();
		connector.setHandler(timeHandler);
		
		cf = connector.connect(new InetSocketAddress("192.168.31.100",PORT));
		cf.awaitUninterruptibly();
	}
	
	public static void send(Handler handler, String msg){
			if(cf == null){
				connect();
			}
			timeHandler.setHandler(handler);
			IoSession session = cf.getSession();
			if(session == null)Log.d("DEBUG","46");
			WriteFuture wf = session.write(msg);
			if(wf == null)Log.d("DEBUG", "49");
			wf.awaitUninterruptibly();
			//cf.getSession().write(msg).awaitUninterruptibly();
	
	}
}
