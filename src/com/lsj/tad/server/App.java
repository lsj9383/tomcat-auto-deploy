package com.lsj.tad.server;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Map.Entry;

import com.lsj.util.AbstractMIMEHttpHandler;
import com.lsj.util.MimeContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class App {

    public static void main(String[] args) throws Exception {
    	 InetSocketAddress addr = new InetSocketAddress(80);
    	 HttpServer server = HttpServer.create(addr, 0);
    	 server.createContext("/", new MyHandler());  
         server.setExecutor(null);  
         server.start();  
         System.out.println("Server is listening on port 80");  
    }
    
    static class MyHandler extends AbstractMIMEHttpHandler {
		@Override
		public void execute(HttpExchange exchange, MimeContext mimeContext, PrintWriter out) {
			out.println("hello");
			for(Entry<String, String> entry : mimeContext.getParamMap().entrySet()){
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
		}
    	
    }
}