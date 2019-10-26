# Oracle笔记

## 1.安装步骤
### 1). 安装依赖包，官方给出如下：
```shell
binutils-2.17.50.0.6
compat-libstdc++-33-3.2.3
compat-libstdc++-33-3.2.3 (32 bit)
gcc-4.1.2
gcc-c++-4.1.2
glibc-2.5-58
glibc-2.5-58 (32 bit)
glibc-devel-2.5-58
glibc-devel-2.5-58 (32 bit)
ksh
libaio-0.3.106
libaio-0.3.106 (32 bit)
libaio-devel-0.3.106
libaio-devel-0.3.106 (32 bit)
libgcc-4.1.2
libgcc-4.1.2 (32 bit)
libstdc++-4.1.2
libstdc++-4.1.2 (32 bit)
libstdc++-devel 4.1.2
libXext-1.0.1
libXext-1.0.1 (32 bit)
libXtst-1.0.1
libXtst-1.0.1 (32 bit)
libX11-1.0.3
libX11-1.0.3 (32 bit)
libXau-1.0.1
libXau-1.0.1 (32 bit)
libXi-1.0.1
libXi-1.0.1 (32 bit)
make-3.81
sysstat-7.0.2
额外增加x86_64

1. compat-libcap1-1.10-1 (x86_64)
2. compat-libstdc++-33-3.2.3-69.el6 (x86_64)
3. gcc-4.4.4-13.el6 (x86_64)
4. gcc-c++-4.4.4-13.el6 (x86_64)
5. glibc-devel-2.12-1.7.el6 (x86_64)
6. ksh  <== any version of ksh is acceptable
7. libstdc++-devel-4.4.4-13.el6 (x86_64)
8. libaio-devel-0.3.107-10.el6 (x86_64) 

进入Server目录。使用rpm -qa查询包是否安装，使用rpm -ivh安装未安装的包

[root@ora12c Server]# pwd
/media/OL5.7 x86_64 dvd 20110728/Server
[root@ora12c Server]# rpm -qa compat-libstdc
[root@ora12c Server]# rpm -ivh compat-libstdc++-33-3.2.3-61.
compat-libstdc++-33-3.2.3-61.i386.rpm
compat-libstdc++-33-3.2.3-61.x86_64.rpm
[root@ora12c Server]# rpm -ivh compat-libstdc++-33-3.2.3-61.i386.rpm
warning: compat-libstdc++-33-3.2.3-61.i386.rpm: Header V3 DSA signature: NOKEY, key ID 1e5e0159
Preparing...                ########################################### [100%]
     package compat-libstdc++-33-3.2.3-61.i386 is already installed
[root@ora12c Server]# rpm -ivh compat-libstdc++-33-3.2.3-61.x86_64.rpm
warning: compat-libstdc++-33-3.2.3-61.x86_64.rpm: Header V3 DSA signature: NOKEY, key ID 1e5e0159
Preparing...                ########################################### [100%]
     package compat-libstdc++-33-3.2.3-61.x86_64 is already installed
按照此方法将所有包安装
```

### 2). 创建Oracle用户和组
```shell
[root@ora12c Server]# groupadd oinstall
[root@ora12c Server]# groupadd dba
[root@ora12c Server]# useradd -g oinstall -G dba oracle
[root@ora12c Server]# passwd oracle
Changing password for user oracle.
New UNIX password:
BAD PASSWORD: it is based on a dictionary word
Retype new UNIX password:
passwd: all authentication tokens updated successfully.
```

### 3). 创建目录

```shell
[root@ora12c Server]# mkdir -p /oracle/
[root@ora12c Server]# chown -R oracle:oinstall /oracle/
[root@ora12c Server]# chmod -R 775 /oracle/
```

### 4). 修改内核参数

```shell
在/etc/sysctl.conf文件下加入如下参数

[root@ora12c Server]# vim /etc/sysctl.conf
fs.aio-max-nr = 1048576
fs.file-max = 6815744
kernel.shmall = 2097152
kernel.shmmax = 536870912
kernel.shmmni = 4096
kernel.sem = 250 32000 100 128
net.ipv4.ip_local_port_range = 9000 65500
net.core.rmem_default = 262144
net.core.rmem_max = 4194304
net.core.wmem_default = 262144
net.core.wmem_max = 1048586
/sbin/sysctl -p 使参数生效

[root@ora12c Server]# /sbin/sysctl -p
fs.aio-max-nr = 1048576
fs.file-max = 6815744
kernel.shmall = 2097152
kernel.shmmax = 536870912
kernel.shmmni = 4096
kernel.sem = 250 32000 100 128
net.ipv4.ip_local_port_range = 9000 65500
net.core.rmem_default = 262144
net.core.rmem_max = 4194304
net.core.wmem_default = 262144
net.core.wmem_max = 1048586
net.ipv4.ip_forward = 0
net.ipv4.conf.default.rp_filter = 2
net.ipv4.conf.default.accept_source_route = 0
kernel.sysrq = 0
kernel.core_uses_pid = 1
net.ipv4.tcp_syncookies = 1
kernel.msgmnb = 65536
kernel.msgmax = 65536
kernel.shmmax = 68719476736
kernel.shmall = 4294967296
```

### 5). 修改用户限制

```shell
在/etc/security/limits.conf加入相关配置

[root@ora12c Server]# vim /etc/security/limits.conf
oracle           soft    nproc   2047
oracle           hard    nproc   16384
oracle           soft    nofile  1024
oracle           hard    nofile  65536
oracle           soft    stack  10240
oracle           hard    stack  10240

```

### 6). 修改用户限制

```shell
在 .bash_profile配置如下变量

oracle@ora12c ~]$ vim .bash_profile
export ORACLE_BASE=/oracle/12c
export ORACLE_HOME=$ORACLE_BASE/db1
export ORACLE_SID=orcl12c
export PATH=$ORACLE_HOME/bin:$PATH:$HOME/bin
export EDITOR=/bin/vi
使配置文件生效
[oracle@ora12c ~]$ source .bash_profile
```

### 7). 上传数据库安装文件和解压

### 8). 运行Xstart连接oracle用户安装

执行database目录下的./runInstaller
下一步下一步，
root用户下运行两个脚本
```shell
[root@ora12c Server]# cd /oracle/oraInventory/
[root@ora12c oraInventory]# ./orainstRoot.sh
Changing permissions of /oracle/oraInventory.
Adding read,write permissions for group.
Removing read,write,execute permissions for world.

Changing groupname of /oracle/oraInventory to oinstall.
The execution of the script is complete.
[root@ora12c oraInventory]# cd /oracle/12c/db1/
[root@ora12c db1]# ./root.sh
Performing root user operation for Oracle 12c

The following environment variables are set as:
    ORACLE_OWNER= oracle
    ORACLE_HOME=  /oracle/12c/db1

Enter the full pathname of the local bin directory: [/usr/local/bin]:
   Copying dbhome to /usr/local/bin ...
   Copying oraenv to /usr/local/bin ...
   Copying coraenv to /usr/local/bin ...


Creating /etc/oratab file...
Entries will be added to the /etc/oratab file as needed by
Database Configuration Assistant when a database is created
Finished running generic part of root script.
Now product-specific root actions will be performed.
```
初始化数据库完成后，12C会自动创建监听

```shell
[oracle@ora12c ~]$ lsnrctl status

LSNRCTL for Linux: Version 12.1.0.1.0 - Production on 27-JUN-2013 12:41:16

Copyright (c) 1991, 2013, Oracle.  All rights reserved.

Connecting to (DESCRIPTION=(ADDRESS=(PROTOCOL=IPC)(KEY=EXTPROC1521)))
STATUS of the LISTENER
------------------------
Alias                     LISTENER
Version                   TNSLSNR for Linux: Version 12.1.0.1.0 - Production
Start Date                27-JUN-2013 12:01:32
Uptime                    0 days 0 hr. 39 min. 45 sec
Trace Level               off
Security                  ON: Local OS Authentication
SNMP                      OFF
Listener Parameter File   /oracle/12c/db1/network/admin/listener.ora
Listener Log File         /oracle/12c/diag/tnslsnr/ora12c/listener/alert/log.xml
Listening Endpoints Summary...
  (DESCRIPTION=(ADDRESS=(PROTOCOL=ipc)(KEY=EXTPROC1521)))
  (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=ora12c)(PORT=1521)))
  (DESCRIPTION=(ADDRESS=(PROTOCOL=tcps)(HOST=ora12c)(PORT=5500))(Security=(my_wallet_directory=/oracle/12c/admin/orcl12c/xdb_wallet))(Presentation=HTTP)(Session=RAW))
Services Summary...
Service "orcl12c" has 1 instance(s).
  Instance "orcl12c", status READY, has 1 handler(s) for this service...
Service "orcl12cXDB" has 1 instance(s).
  Instance "orcl12c", status READY, has 1 handler(s) for this service...
Service "pdborcl12c" has 1 instance(s).
  Instance "orcl12c", status READY, has 1 handler(s) for this service...
The command completed successfully

```

使用sqlplus连接数据库

```shell
[oracle@ora12c ~]$ sqlplus / as sysdba

SQL*Plus: Release 12.1.0.1.0 Production on Thu Jun 27 12:41:41 2013

Copyright (c) 1982, 2013, Oracle.  All rights reserved.


Connected to:
Oracle Database 12c Enterprise Edition Release 12.1.0.1.0 - 64bit Production
With the Partitioning, OLAP, Advanced Analytics and Real Application Testing options

SQL> set linesize 150
SQL> select * from v$version;

BANNER                                                       CON_ID
-------------------------------------------------------------------------------- ----------
Oracle Database 12c Enterprise Edition Release 12.1.0.1.0 - 64bit Production			0
PL/SQL Release 12.1.0.1.0 - Production                                     			 0
CORE     12.1.0.1.0     Production                                         			 0
TNS for Linux: Version 12.1.0.1.0 - Production                             			 0
NLSRTL Version 12.1.0.1.0 - Production                                     			 0
```


## 2.oracle基本操作命令

```sqlplus
#启动数据库
startup

#关闭数据库
shutdown immediate

#查看所有pdb
show pdbs

#打开PDB容器
alter pluggable database [pdb_name] open;

#切换/启用PDB容器
alter session set container=[pdb_name];

#查看PDB名称对应的SERVICE
select name,network_name,pdb from v$services;

#创建、启动、关闭、删除服务的相关命令，重启后服务丢失
exec dbms_service.create_service('name','network_name');
exec dbms_service.start_service('name');
exec dbms_service.stop_service('name');
exec dbms_service.delete_service('name');
```

## 3. 监听服务listener

```shell

# 查看已启用的service服务
lsnrctl service
# 查看监听状态，也可以看到已启用service服务的列表
lsnrctl status
# 关闭监听
lsnrctl stop
# 启动监听
lsnrctl start
```

## 4. 常用命令

```shell
#  sqlplus 连接数据库的几种方式
1. sqlplus / as sysdba 这是典型的操作系统认证，不需要listener进程
2. sqlplus sys/oracle 这种连接方式只能连接本机数据库，同样不需要listener进程
3. sqlplus sys/oracle@orcl 这种方式需要listener进程处于可用状态。最普遍的通过网络连接。
4. sqlplus sys/pwd@ip:1521/test as sysdba 同上
```