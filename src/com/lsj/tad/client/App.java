package com.lsj.tad.client;

import java.io.File;

import com.lsj.http.util.HttpMimeParams;
import com.lsj.http.util.HttpParams;
import com.lsj.util.ZipUtil;

public class App {
	
	public static void main(String[] args){
		
		//1).初始化配置文件
		ClientConf conf = new ClientConf(new String[]{"-uroot", "-proot123", "-atrans"});
		//ClientConf conf = new ClientConf(args);
		ZipUtil zipUtil = new ZipUtil();
		
		//2).压缩文件
		System.out.println("compressing...");
		try{
			zipUtil.Compress(conf.getWebappPath(), "webapp.zip");
			zipUtil.Compress(conf.getWorkPath(), "work.zip");
		}catch(Exception e){
			//错误，退出
			System.out.println("Interrupt, compress error...");
			System.exit(1);
		}
		System.out.println("compress finish");
		
		//3).部署(添加MIME，发送并等待响应)
		System.out.println("deploying...");
		HttpParams params = new HttpMimeParams()
				.put("webapp", new File("webapp.zip"))
				.put("work", new File("work.zip"))
				.put("username", conf.getUsername())
				.put("password", conf.getPassword());
		try {
			String strResponse = params.Send(conf.getRemote());
			System.out.println(strResponse);
		} catch (Exception e) {
			//错误，退出
			System.out.println("Interrupt, deploy error...");
			System.exit(1);
		}
		
		System.out.println("deploy finished");
	}

}
