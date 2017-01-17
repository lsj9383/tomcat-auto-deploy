# <font color="#FF0000">T</font>omcat Auto Deploy
本项目用于tomcat服务器远程自动部署网站应用。
##运行环境
* jdk1.6及其以上版本(为了支持sun公司的httpserver)。
* tomcat7及其以上版本，需要配置CATALINA_HOME到环境变量。
* 远程主机要求linux环境。

##一.*快速开始*
这里用尽量简单的语言描述如何通过命令行自动将本地开发的网站部署到远程主机上。本项目在远程主机和本地都需要进行配置。
###1.*客户端*
将**output/client-output/**文件夹拷贝到本机上。在远程主机配置完成的情况下，将webapp部署到远程主机下是非常简单的，只需通过以下指令:
```
java -jar tad-client.jar -rhttp://192.168.1.2:100 -atrans
```
如此便可以将本地名为trans的webapp，部署到192.168.1.2:100的远程主机上。<br>
部署成功:
![](https://github.com/lsj9383/tomcat-auto-deploy/blob/master/icon/client-demo.png)

###2.*远程主机*
将**output/server-output/**文件夹拷贝到远程主机上。并执行以下指令:
```
nohup java -jar tad-server.jar &
```
在远程主机上启动tomcat自动部署服务，采用的密码是`root`, 服务的端口默认是`100`。

##二、*配置*
该项目涉及到的配置的参数

###1.*默认值*
在没有设置默认值的时候，各项参数采用默认值。无论是客户端还是服务器端，都用`环境变量CATALINA_HOME`来指定tomcat的根目录。服务器端采用`100`作为服务的默认端口。

###2.*配置文件*
服务器端的配置相对简单，不采用配置文件，仅在客户端使用.配置文件为`client-conf.json`。
* password, 指定远程主机部署时需要的指令。
* remote, 指定要部署应用的远程主机
* catalinHome,  指定tomcat的根路径
* appName, 指定要部署的appName

###3.*命令行*
命令行配置的参数的优先级是大于配置文件的，也就是说配置文件和命令行指定的相同参数，会采用命令行而非配置文件的。这样可以将基础值/常用值设定在配置文件中。
#####客户端
```
java -jar -a<appName>
          -r<remote>
		  -p<password>
		  -c<catalinHome>
```

#####远程端
```
java -jar <port>
          -p<password>
		  -c<catalinHome>
```