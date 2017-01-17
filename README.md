#Tomcat Auto Deploy
本项目用于tomcat服务器远程自动部署网站应用。
##运行环境
* jdk1.6及其以上版本(为了支持sun公司的httpserver)
* tomcat7及其以上版本，需要配置CATALINA_HOME到环境变量。

##1.*快速开始*
这里用尽量简单的语言描述如何通过命令行自动将本地开发的网站部署到远程主机上。本项目在远程主机和本地都需要进行配置。
###1.*客户端*
将**output/client-output/**文件夹拷贝到本机上。在远程主机配置完成的情况下，将webapp部署到远程主机下是非常简单的，只需通过以下指令:
```
java -jar tad-client.jar -proot -rhttp://192.168.1.2:100 -atrans
```
如此便可以将本地名为trans的webapp，部署到192.168.1.2:100的远程主机上.使用的密码是`root`，这个密码在远程端配置，与远程主机匹配才可以进行部署，否则会报错。<br>
部署成功:
![](https://github.com/lsj9383/tomcat-auto-deploy/blob/master/icon/client-demo.png)

###2.*远程主机*
将**output/server-output/**文件夹拷贝到远程主机上。并执行以下指令:
```
nohup java -jar tad-server.jar -proot &
```
在远程主机上启动tomcat自动部署服务，采用的密码是`root`, 服务的端口默认是`100`。
