package com.lsj.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	static public boolean DeleteFile(File file){	//只要有一个文件没删干净，就返回false
		System.gc();
		if(file.isFile()){
			return file.delete();
		}else if(!file.exists()){
			return false;
		}else{
			File[] fileList = file.listFiles();
			boolean res = true;
			for(File cfile : fileList){
				res &= DeleteFile(cfile);
			}
			return res&file.delete();
		}
	}
	
	static public void SaveFile(File newFile, InputStream is) throws Exception{
		OutputStream os = new FileOutputStream(newFile);
		byte[] bytes = new byte[65535];
		int length = 0;
		while((length = is.read(bytes)) != -1){
			os.write(bytes, 0, length);
		}
		os.close();
	}
}
