package com.lsj.tad.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

/**
 * Embedded HTTP/1.1 file server based on a classic (blocking) I/O model.
 */
public class App {

    public static void main(String[] args) throws Exception {
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(15000)
                .setTcpNoDelay(true)
                .build();

        final HttpServer server = ServerBootstrap.bootstrap()
                .setListenerPort(80)
                .setServerInfo("Test/1.1")
                .setSocketConfig(socketConfig)
                .setExceptionLogger(new StdErrorExceptionLogger())
                .registerHandler("*", new HttpFileHandler())
                .create();

        server.start();
        server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    static class StdErrorExceptionLogger implements ExceptionLogger {

        @Override
        public void log(final Exception ex) {
            if (ex instanceof SocketTimeoutException) {
                System.err.println("Connection timed out");
            } else if (ex instanceof ConnectionClosedException) {
                System.err.println(ex.getMessage());
            } else {
                ex.printStackTrace();
            }
        }

    }

    static class HttpFileHandler implements HttpRequestHandler  {
        public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context) throws HttpException, IOException {
        	if(!(request instanceof BasicHttpEntityEnclosingRequest)){
        		response.setStatusCode(HttpStatus.SC_OK);
                StringEntity entity = new StringEntity("error", ContentType.create("text/html", "UTF-8"));
                response.setEntity(entity);
        	}else{
        		BasicHttpEntityEnclosingRequest req = (BasicHttpEntityEnclosingRequest) request;

            	HttpEntity reqBody = req.getEntity();
            	System.out.println(reqBody.getContentLength());
            	System.out.println(reqBody.getContentType().getName());
            	System.out.println(reqBody.getContentType().getValue());
            	InputStreamReader isr = new InputStreamReader(reqBody.getContent());
            	char[] chars = new char[10240];
            	String str = new String();
            	int length = 0;
            	while((length = isr.read(chars))!= -1){
            		str += new String(chars, 0, length);
            	}
            	System.out.println(str);
            	response.setStatusCode(HttpStatus.SC_OK);
                StringEntity entity = new StringEntity(
                        "<html><body><h1>Hello</h1></body></html>",
                        ContentType.create("text/html", "UTF-8"));
                response.setEntity(entity);
        	}
        }

    }

}