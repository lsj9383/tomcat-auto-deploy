package com.lsj.tad.server;

import java.io.File;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import org.apache.commons.fileupload.FileItem;

import com.lsj.tad.Conf;
import com.lsj.util.FileUtil;
import com.lsj.util.ZipUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class App {
	public static void main(String[] args) throws Exception {
		int port = 100;
		for(String arg : args){
			if(!arg.startsWith("-")){
				port = Integer.parseInt(arg);
			}
		}
		
		InetSocketAddress addr = new InetSocketAddress(port);
		HttpServer server = HttpServer.create(addr, 0);
		server.createContext("/", new MyHandler(args));
		server.createContext("/access", new AccessHandler(args));
		server.setExecutor(null);
		server.start();
		System.out.println(Command.pid()+" Server is listening on port "+port);
    }
	
	static class AccessHandler extends AbstractMIMEHttpHandler {
		final private String[] args;
    	public AccessHandler(String[] args){
    		this.args = args;
    	}
    	
		@Override
		public void execute(HttpExchange exchange, MimeContext mimeContext, PrintWriter out) {
			Conf conf = new Conf("", args);
			String password = mimeContext.getParamMap().get("password");
			if(password.equals(conf.getPassword())){
				out.print("true");
			}else{
				out.print("false");
			}
		}
	} 
    
    static class MyHandler extends AbstractMIMEHttpHandler {
    	final private String[] args;
    	public MyHandler(String[] args){
    		this.args = args;
    	}
    	
		@Override
		public void execute(HttpExchange exchange, MimeContext mimeContext, PrintWriter out) {
			Conf conf = new Conf("", args);
			String password = mimeContext.getParamMap().get("password");
			String appname = mimeContext.getParamMap().get("appname");
			FileItem fiWebapp = mimeContext.getFileItem().get("webapp");
			FileItem fiWork = mimeContext.getFileItem().get("work");
			//1).输入参数检查
			if(password == null || appname == null || fiWebapp == null || fiWork == null){
				out.print("parameter is not enough");
				return ;
			}
			
			if(!conf.getPassword().equals(password)){
				out.print("password is error");
				return ;
			}
			conf.setAppName(appname);
			
			try{
				//2).缓存文件
				String strTempFileWebApp = "webapp"+Math.random();		//加随机数，以免多线程冲突
				String strTempFileWork = "work"+Math.random();
				FileUtil.SaveFile(new File(strTempFileWebApp), fiWebapp.getInputStream());
				FileUtil.SaveFile(new File(strTempFileWork), fiWork.getInputStream());
				
				//3).将文件部署至Tomcat
				FileUtil.DeleteFile(new File(conf.getWebappPath()));
				FileUtil.DeleteFile(new File(conf.getWorkPath()));
				ZipUtil zipUtil = new ZipUtil();
				zipUtil.Decompress(strTempFileWebApp, new File(conf.getWebappPath()).getParent());
				zipUtil.Decompress(strTempFileWork, new File(conf.getWorkPath()).getParent());
				FileUtil.DeleteFile(new File(strTempFileWebApp));
				FileUtil.DeleteFile(new File(strTempFileWork));
				
				//4).重启tomcat服务
				System.out.println(Command.exeCmd("shutdown.sh"));
				System.out.println(Command.exeCmd("startup.sh"));
				out.println("deploy success");
			}catch(Exception e){
				out.print("deploy error");
			}
		}
    }
}