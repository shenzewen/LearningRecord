# Docker 命令

## 1. 安装Docker

```shell
# ubuntu系统安装
$ sudo apt install docker-ce
# 启动docker
$ sudo systemctl start docker
# 开机启动docker
$ sudo systemctl enable docker
# 停止docker
$ sudo systemctl stop docker
```

## 2.Docker常用命令&操作

### 1）、镜像操作

| 操作 | 命令                                         | 说明                                                    |
| ---- | -------------------------------------------- | ------------------------------------------------------- |
| 检索 | docker search 关键字 eg：docker search redis | 我们经常去docker hub上检索镜像的详细信息，如镜像的TAG。 |
| 拉取 | docker pull 镜像名:tag                       | :tag是可选的，tag表示标签，多为软件的版本，默认是latest |
| 列表 | docker images                                | 查看所有本地镜像                                        |
| 删除 | docker rmi image-id                          | 删除指定的本地镜像                                      |

### 2）、容器操作

```shell
#1、搜索镜像
$ sudo docker search tomcat
#2、拉取镜像
$ sudo docker pull tomcat
#3、根据镜像启动容器
$ sudo docker run --name mytomcat -d tomcat:latest
#4、查看运行中的容器
$ sudo docker ps  
#5、 停止运行中的容器
$ sudo docker stop  容器的id
#6、查看所有的容器
$ sudo docker ps -a
#7、启动容器
$ sudo docker start 容器id
#8、删除一个容器
$ sudo docker rm 容器id
#9、启动一个做了端口映射的tomcat
$ sudo docker run -d -p 8888:8080 tomcat
-d：后台运行
-p: 将主机的端口映射到容器的一个端口    主机端口:容器内部的端口
--name: 指定容器名称
-v:挂载文件夹，可以挂载多个
--restart: 指定是否自动重启 

#10、查看容器的日志
$ sudo docker logs container-name/container-id

更多命令参看
https://docs.docker.com/engine/reference/commandline/docker/
可以参考每一个镜像的文档
```

### 3）、其他注意事项

* 如果创建时未指定容器是否重启，可以使用下面的命令修改

```shell
$ sudo docker update --restart=always 容器名/容器ID
--restart具体参数值详细信息：
no -  容器退出时，不重启容器；
on-failure - 只有在非0状态退出时才从新启动容器；
always - 无论退出状态是如何，都重启容器；
```

* docker容器和系统时区不一样

```shell
# 使用如下命令可以使容器中的时区和系统时区相同, date获取时间可以测试
$ sudo docker cp /etc/localtime 容器名/容器ID:/etc/localtime
# 在使用jvm的时候，发现时区还是没有变化, 就需要使用下面的命令了
$ sudo docker cp /etc/timezone 容器名/容器ID:/etc/timezone
# /etc/timezone文件中有当前时区的设置，北京时间的设置为Asia/Shanghai, 可以使用cat命令查看
$ cat /etc/timezone
```

* 进入docker容器中

```shell
# 进入docker容器方法用好几种, 这里只写我用过的一种, 使用exec进入
sudo docker exec -it 容器名/容器ID /bin/bash
```

