package com.lsj.tad;

public class Conf {
	
	private String password;
	private String remote;
	private String appName;
	private String catalinaHome;
	
	public Conf(){
		password = "root";
		catalinaHome = System.getenv("CATALINA_HOME");
	}
	
	public Conf(String[] args){
		this();
		for(String arg : args){
			String command = arg.substring(0, 2);
			String value = arg.substring(2);
			switch(command){
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
			default: break;
			}
		}
	}
	
	public String getWebappPath(){
		return catalinaHome+"/webapps/"+appName;
	}
	public String getWorkPath(){
		return catalinaHome+"/work/Catalina/localhost/"+appName;
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
