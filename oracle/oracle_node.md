# Oracle笔记

## 1.安装步骤
### 1). 安装依赖包，官方给出如下：
```shell
SUSE 安装命令为zypper

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
[root@ora12c Server]# groupadd oper
[root@ora12c Server]# useradd -g oinstall -G dba,oper oracle
[root@ora12c Server]# passwd oracle
Changing password for user oracle.
New UNIX password:
BAD PASSWORD: it is based on a dictionary word
Retype new UNIX password:
passwd: all authentication tokens updated successfully.
```

### 3). 创建目录

```shell
[root@ora12c Server]# mkdir -p /usr/local/oracle/
[root@ora12c Server]# chown -R oracle:oinstall /usr/local/oracle/
[root@ora12c Server]# chmod -R 775 /usr/local/oracle/
```

### 4). 修改内核参数

```shell
在/etc/sysctl.conf文件下加入如下参数

[root@ora12c Server]# vim /etc/sysctl.conf
fs.aio-max-nr = 1048576
fs.file-max = 6815744
kernel.shmall = 2097152
kernel.shmmax = 1967415296
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

### 6). 修改环境变量

```shell
在 .bash_profile配置如下变量

oracle@ora12c ~]$ vim .bash_profile
export ORACLE_BASE=/usr/local/oracle/12c
export ORACLE_HOME=$ORACLE_BASE/db1
export ORACLE_SID=orcl
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
[root@ora12c Server]# cd /usr/local/oracle/oraInventory/
[root@ora12c oraInventory]# ./orainstRoot.sh
Changing permissions of /oracle/oraInventory.
Adding read,write permissions for group.
Removing read,write,execute permissions for world.

Changing groupname of /usr/local/oracle/oraInventory to oinstall.
The execution of the script is complete.

[root@ora12c oraInventory]# cd /usr/local/oracle/12c/db1/
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
命令：sqlplus 用户名/密码@ip地址[:端口]/service_name [as sysdba]
示例：sqlplus sys/pwd@ip:1521/test as sysdba 

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
#打开所有PDB容器
alter pluggable database all open;

#切换/启用PDB容器
alter session set container=ORCLPDB;

#查看PDB名称对应的SERVICE
select name,network_name,pdb from v$services;

#创建PDB
create pluggable database PDB2 admin user pdb2admin identified by 123456 file_name_convert=('/usr/local/oracle/12c/oradata/orcl/pdbseed','/usr/local/oracle/12c/oradata/orcl/pdb2');

create pluggable database testpdb admin user testpdbadmin identified by 123456 file_name_convert=('/data/app/oracle/oradata/pdbseed','/data/app/oracle/oradata/testpdb');

#删除PDB
drop pluggable database PDB2 including datafiles;

#创建、启动、关闭、删除服务的相关命令，重启后服务丢失
exec dbms_service.create_service('name','network_name');
exec dbms_service.start_service('name');
exec dbms_service.stop_service('name');
exec dbms_service.delete_service('name');

#创建用户
create user username identified by password;  --新建用户并设置密码
create user chase_dzfp identified by 123456;
create user orclpdb identified by orclpdb123;
grant create session to username;  --赋予登陆权限
grant unlimited tablespace to username; --赋予用户无限表空间
grant connect,resource to username;  --给用户赋予权限
GRANT CREATE ANY VIEW,DROP ANY VIEW,CONNECT,RESOURCE,CREATE SESSION,DBA TO chase_dzfp; --授予DBA权限
grant create view to  username; --赋予用户创建视图的权限
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

## 4. sqlplus常用连接命令

```shell
#  sqlplus 连接数据库的几种方式
1. sqlplus / as sysdba 这是典型的操作系统认证，不需要listener进程
2. sqlplus sys/oracle 这种连接方式只能连接本机数据库，同样不需要listener进程
3. sqlplus sys/oracle@orcl 这种方式需要listener进程处于可用状态。最普遍的通过网络连接。
4. sqlplus sys/pwd@ip:1521/test as sysdba 同上
	sqlplus chase_dzfp/123456@localhost:1521/ALEDZ
```

## 5. 常见问题解决

```shell
1. ORA-27101:shared memory realm does not exit
解决方法: 在sqlplus中执行如下语句
alter system set listener_networks='(( NAME=listener)(LOCAL_LISTENER=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.0.182)(PORT=2521)))))' scope=spfile;

2. /usr/local/jdk1.8.0_144/jre/lib/amd64/libawt_xawt.so: libXtst.so.6: 无法打开共享对象文件: 没有那个文件或目录
解决方法：yum install libXi libXp libXtst

3. ORA-01035: ORACLE only available to users with RESTRICTED SESSION privilege
描述：ORA-01035：ORACLE仅对具有RESTRICTED SESSION特权的用户可用
解决方法：1. 授予用户restricted session权限，grant restricted session to DZFP_TEST;
		  2. 将数据库修改为正常模式（即退出限制模式）
			 alter system disable restricted session;
			 由正常模式切换到限制模式的方法
			 alter system enable restricted session;
```

## 6. 密码过期解决方案
```shell
1. 查看用户的proifle是哪个，一般是default ：
SELECT username,PROFILE FROM dba_users;
tips: 如果是pdb用户需要切换pdb才能看到该用户

2. 查看对应的概要文件(如default)的密码有效期设置：
SELECT * FROM dba_profiles s WHERE s.profile='DEFAULT' AND resource_name='PASSWORD_LIFE_TIME';

3. 将概要文件(如default)的密码有效期由默认的180天修改成“无限制”：
ALTER PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;

4. 修改后，还没有被提示ORA-28002警告的用户账号不会再碰到同样的提示;而已经被提示的用户账号必须再改一次密码，举例如下：
alter user 用户名 identified by <原来的密码> account unlock; ----不用换新密码
样例： alter user sys as sysdba identified by abcABC;
```

## 7. xstart连接问题
```shell
yum install xorg-x11-xauth
yum install xterm
#运行 ./runInstaller 报如下错误时执行
#Could not execute auto check for display colors using command /usr/bin/xdpyinfo. Check if the DISPLAY variable is set.
yum install xdpyinfo

xstart启动命令栏输入
/usr/bin/xterm -ls -display $DISPLAY
```

## 7. 使用sqlplus导入sql脚本，出现表中数据中文乱码
```shell
绝大多数情况是环境变量NLS_LANG的值和数据库字符集不一致导致，建议修改NLS_LANG，方便、简单、安全。 
解决步骤如下： 
1.查看环境变量： **查询值为空，说明未设置环境变量
echo $NLS_LANG

2.使用PL/SQL或者SQLPlus执行： 查询服务端的字符集编码 
select userenv(‘language’) from dual;

3.设置环境变量：xxxx是由步骤2查出来的字符集编码值 
[oracle@ ~]$ export NLS_LANG=xxxx

4.登录sqlplus执行插入语句或导入sql脚本文件

SQL > @filename.sql

扩展： 
修改NLS_LNAG,有两种方式 
1，临时修改 在当前登录终端执行export NLS_LANG=XX (XX：表示数据库字符集) 
本地登录退出后失效 
2，永久修改 修改.bash_profile，在文件中加入export NLS_LANG=XX (XX：表示数据库字符集) 
重新登录后永久生效 
问题：如果在设置环境变量的时候出现“XX: not a valid identifier”，检查=前后是否有空格、检查XX中是否有空格，如果有空格要将XX用双引号包括。例如：export NLS_LANG=“SIMPLIFIED CHINESE_CHINA.ZHS16GBK”
```
## 8. Centos7 Linux 设置开机启动oracle
```
# root用户下, 打开oratab文件，修改N为Y
vi /etc/oratab
orcl:/usr/local/oracle/12c/db1:Y

# user oracle 下面修改：
cd $ORACLE_HOME/bin
vi dbstart
找到 ORACLE_HOME_LISTNER=$1  这行， 修改成:
ORACLE_HOME_LISTNER=/usr/local/oracle/12c/db1      //建议这一条
或者直接修改成：
ORACLE_HOME_LISTNER=$ORACLE_HOME

同样道理修改 dbshut
[oracle@data55 bin]$ vi dbshut
测试运行 dbshut, dbstart 看能否启动oracle 服务及listener服务

# root下修改系统启动项
vi /etc/rc.d/rc.local
末尾添加
su - oracle -lc dbstart

# 在CentOS7中,官方将/etc/rc.d/rc.local 的开机自启的权限禁止掉了,他为了兼容性,设置了这个,但是并不默认启动.如果需要的话.执行以下代码
chmod +x /etc/rc.d/rc.local
```

## 9. 配置PDB自启动，随着DB启动而启动
```
# 切换到CBD
alter session set container=CDB$ROOT;

# 创建PDB自启动的Trigger
CREATE OR REPLACE TRIGGER open_pdbs
AFTER STARTUP ON DATABASE
BEGIN
EXECUTE IMMEDIATE 'ALTER PLUGGABLE DATABASE ALL OPEN';
END open_pdbs;
/

# 删除PDB自启动的Trigger
DROP TRIGGER open_pdbs
```

## 10. ORACLE 12C EM端口关闭及修改方法
```
sqlplus / as sysdba  连接到CDB库
exec dbms_xdb_config.sethttpsport(0);   把em端口设置成0，即可关闭em
设置完成后，退出sql命令窗口，使用lsnrctl status 查询状态
 
 修改em端口
sqlplus / as sysdba  连接pdb数据库
exec dbms_xdb_config.sethttpsport(5500);  设置em的端口及启动
查询em状态，退出sql窗口
lsnrctl status
```

## 11. 数据库无法连接，防火墙问题
```
# firewall
# 查看防火墙所有开放的端口
firewall-cmd --zone=public --list-ports

#查看防火墙状态
firewall-cmd --state

添加指定需要开放的端口： 
firewall-cmd --add-port=123/tcp --permanent 

重载入添加的端口： 
firewall-cmd --reload 

查询指定端口是否开启成功： 
firewall-cmd --query-port=123/tcp

移除指定端口： 
firewall-cmd --permanent --remove-port=123/tcp

#扩展信息
#查看监听的端口
netstat -lnpt
#检查端口被哪个进程占用
netstat -lnpt |grep prot
#查看进程的详细信息
ps PID


# iptables
#直接改iptables配置就可以了：
vim /etc/sysconfig/iptables
#最后
service iptables restart
```

## 12. oracle默认监听端口查看
```
SQL> show parameter local_listener;
 
NAME				     TYPE	 VALUE
------------------------------------ ----------- ------------------------------
local_listener			     string	 LISTENER_ORCL
 
SQL>   alter system set local_listener="(address=(protocol=tcp)(host=szwvm)(port=2521))";
 
System altered.

```