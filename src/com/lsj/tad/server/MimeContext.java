package com.lsj.tad.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.net.httpserver.HttpExchange;

public class MimeContext {
	
	final private Map<String, String> paramMap = new HashMap<>();
	final private Map<String, FileItem> fileMap = new HashMap<>();
	
	public MimeContext(final HttpExchange exchange) throws Exception{
		ServletFileUpload up = new ServletFileUpload(new DiskFileItemFactory());
		List<FileItem> fileItems = up.parseRequest(new FileParse(exchange));
		for(FileItem fileItem : fileItems) {			
			if(fileItem.getContentType() == null){
				paramMap.put(fileItem.getFieldName(), fileItem.getString());
			}else if(fileItem.getContentType().startsWith("text/plain")){
				paramMap.put(fileItem.getFieldName(), fileItem.getString());
			}else{
				fileMap.put(fileItem.getFieldName(), fileItem);
			}
		}
	}
	
	public Map<String, String> getParamMap(){
		return paramMap;
	}
	
	public Map<String, FileItem> getFileItem(){
		return fileMap;
	}
	private class FileParse implements RequestContext{
    	final private HttpExchange exchange;
    	
    	public FileParse(HttpExchange exchange) {
    		this.exchange = exchange;
		}
    	
    	@Override
		public InputStream getInputStream() throws IOException {
			return exchange.getRequestBody();
		}
		
		@Override
		public String getContentType() {
			return exchange.getRequestHeaders().getFirst("Content-type");
		}
		
		@Override
		public int getContentLength() {
			 try {
				return exchange.getRequestBody().available();
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		@Override
		public String getCharacterEncoding() {
			return "UTF-8";
		}
    }
}
