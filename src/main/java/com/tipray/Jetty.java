package com.tipray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * 使用Jetty运行调试Web应用, 在Console输入回车停止服务.
 * 
 * @author mg
 */
public class Jetty {
	static{
		if(System.getProperty("catalina.home")==null){
			System.setProperty("catalina.home", "E:\\workspace");
			System.out.println(System.getProperty("catalina.home"));
		}
	}

	private static Server server;
	
	public static final String CONTEXT_PATH = "/DLP";
	public static final String WEB_APP_PATH = "./src/main/webapp";
	public static final int PORT = 8080;
	public static final int PORT_SSL = 8443;
	public static final int LISTENER_PORT = 9800;

	public static void main(String[] args) throws Exception {
		
		System.setProperty("file.encoding", "utf-8");
		
		server = new Server();
		
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(10);
		threadPool.setMaxThreads(500);
		server.setThreadPool(threadPool);
		
		server.setConnectors(new Connector[] { getConnector(PORT),getSslConnector(PORT_SSL) });
		WebAppContext context = new WebAppContext(WEB_APP_PATH, CONTEXT_PATH);
		context.setDefaultsDescriptor(Jetty.class.getResource("/")
				+ "jetty-webdefault.xml");
		HandlerCollection handlers = new HandlerCollection();
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		handlers.setHandlers(new Handler[] { contexts, new DefaultHandler(),
				requestLogHandler });
		contexts.addHandler(context);
		server.setHandler(handlers);
		
		server.setStopAtShutdown(true);
		server.setSendServerVersion(true);
		
		Thread monitor = new MonitorThread(LISTENER_PORT);
		monitor.start();
		
		server.start();
		server.join();
		
		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}
	@SuppressWarnings("deprecation")
	static SelectChannelConnector getConnector(int port){
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		// 每个请求被accept前允许等待的连接数
		connector.setRequestBufferSize(8192 * 2);

		connector.setAcceptQueueSize(50);
		// 同事监听read事件的线程数
		connector.setAcceptors(2);
		// 连接最大空闲时间，默认是200000，-1表示一直连接
		// connector.setMaxIdleTime(3000);
		// 表示线程资源稀少时的maxIdleTime
		connector.setLowResourceMaxIdleTime(1000);
		// connector.setThreadPool(threadPool);
		return connector;
	}
	@SuppressWarnings("deprecation")
	static SslSelectChannelConnector getSslConnector(int port){
		SslSelectChannelConnector connector = new SslSelectChannelConnector();
		connector.setPort(port);
		// 每个请求被accept前允许等待的连接数
		connector.setRequestBufferSize(8192 * 2);

		connector.setAcceptQueueSize(50);
		// 同事监听read事件的线程数
		connector.setAcceptors(2);
		// 连接最大空闲时间，默认是200000，-1表示一直连接
		// connector.setMaxIdleTime(3000);
		// 表示线程资源稀少时的maxIdleTime
		connector.setLowResourceMaxIdleTime(1000);
		// connector.setThreadPool(threadPool);
		

		SslContextFactory contextFactory = connector.getSslContextFactory();
		contextFactory.setKeyStore(WEB_APP_PATH+"/WEB-INF/classes/trdlp.keystore");  
		contextFactory.setKeyStorePassword("tipray");  
//		contextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
		return connector;
	}
	
	private static class MonitorThread extends Thread {

		private ServerSocket socket;

		public MonitorThread(int listenerport) {
			setDaemon(true);
			setName("StopMonitor");
			try {
				socket = new ServerSocket(listenerport, 1,
						InetAddress.getByName("127.0.0.1"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void run() {
			System.out.println("*** running jetty 'stop' thread");
			Socket accept;
			try {
				accept = socket.accept();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(accept.getInputStream()));
				reader.readLine();
				System.out.println("*** stopping jetty embedded server");
				server.stop();
				accept.close();
				socket.close();
				System.exit(0);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new RuntimeException(e);
			}
		}
	}
}
