package com.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {

	private static int READ_BUF_SZIE = 1024;
	private static int PORT = 9123;
	
	public static void main(String[] args) throws IOException{
		IoAcceptor accepter = new NioSocketAcceptor();
		accepter.getFilterChain().addLast("logger", new LoggingFilter());
		accepter.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		accepter.setHandler(new TimeServerHandler());
		accepter.getSessionConfig().setReadBufferSize(READ_BUF_SZIE);
		accepter.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		accepter.bind(new InetSocketAddress(PORT));
		
		System.out.println("TimeServer Start up!");
	}
}
