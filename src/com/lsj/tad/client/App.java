package com.lsj.tad.client;

import com.lsj.util.ZipUtil;

public class App {
	
	public static void main(String[] args) throws Exception {
		ZipUtil zipUtil = new ZipUtil();
		zipUtil.Compress("abc", "1.zip");
		zipUtil.Decompress("1.zip", "unzip");
	}

}
