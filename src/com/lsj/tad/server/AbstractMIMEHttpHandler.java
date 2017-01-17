package com.lsj.tad.server;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class AbstractMIMEHttpHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		PrintWriter out = new PrintWriter(exchange.getResponseBody());
		exchange.getResponseHeaders().add("Content-type", "text/plain");
		exchange.sendResponseHeaders(200, 0);
		if(!exchange.getRequestMethod().equals("POST")){
			out.println("request error");
			out.flush();
			out.close();
		}else{
			try {
				MimeContext mimeContext = new MimeContext(exchange);
				execute(exchange, mimeContext, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	abstract public void execute(HttpExchange exchange, MimeContext mimeContext, PrintWriter out);
}
