package com.lsj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	//解压到指定路径
	public void Decompress(String strZip, String strDeDir) throws Exception{
		ZipInputStream zin = new ZipInputStream(new FileInputStream(strZip));
		ZipEntry entry = null;
		while((entry=zin.getNextEntry())!=null){
			if(entry.isDirectory()){
				
			}else{
				File file = new File(strDeDir+"/"+entry.getName());
				String parentPath = ParentPath(file.getAbsolutePath());
				new File(parentPath).mkdirs();
				OutputStream fos = new FileOutputStream(file);
				InputStream2OutputStream(zin, fos);
			}
			zin.closeEntry();;
		}
	}
	
	//将指定文件/文件夹压缩到指定的zip
	public void Compress(String strSrc, String strZip) throws Exception{
		ZipOutputStream zout = null;
		try{
			zout = new ZipOutputStream(new FileOutputStream(strZip));
			File file = new File(strSrc);
			Compress("", file, zout);
		}catch(Exception e){
			throw e;
		}finally{
			if(zout != null){
				zout.close();
			}
		}
	}
	
	private void Compress(String strDir, File file, ZipOutputStream zout) throws Exception{
		if(file.isFile()){
			String entryName = strDir+"/"+file.getName();
			zout.putNextEntry(new ZipEntry(entryName));
			InputStream fis = new FileInputStream(file);
			InputStream2OutputStream(fis, zout);
			zout.closeEntry();
		}else if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File subFile : files){
				String strDirName = strDir+"/"+file.getName();
				Compress(strDirName, subFile, zout);
			}
		}else{
			throw new Exception("file error");
		}
	}
	
	private void InputStream2OutputStream(InputStream is, OutputStream os) throws Exception{
		byte[] bytes = new byte[1024];
		int length = 0;
		while((length = is.read(bytes))!=-1){
			os.write(bytes, 0, length);
		}
	}
	
	private String ParentPath(String path){
		return path.substring(0, path.lastIndexOf(File.separator));
	}

}
