package com.lsj.tad.client;

public class ClientConf {
	
	private String username;
	private String password;
	private String remote;
	private String appName;
	private String catalinaHome;
	
	public ClientConf(){
		username = "root";
		password = "root";
		catalinaHome = System.getenv("CATALINA_HOME");
	}
	
	public ClientConf(String[] args){
		this();
		for(String arg : args){
			String command = arg.substring(0, 2);
			String value = arg.substring(2);
			switch(command){
			case "-u":
				username = value;
				break;
			case "-p":
				password = value;
				break;
			case "-r":
				remote = value;
				break;
			case "-a":
				appName = value;
				break;
			case "-c":
				catalinaHome = value;
				break;
			}
		}
	}
	
	public String getWebappPath(){
		return catalinaHome+"/webapps/"+appName;
	}
	
	public String getWorkPath(){
		return catalinaHome+"/work/Catalina/localhost/"+appName;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemote() {
		return remote;
	}
	public void setRemote(String remote) {
		this.remote = remote;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCatalinaHome() {
		return catalinaHome;
	}
	public void setCatalinaHome(String catalinaHome) {
		this.catalinaHome = catalinaHome;
	}
}
