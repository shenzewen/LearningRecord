# Mysql笔记

## 1.数据库

```mysql
#创建数据库
CREATE DATABASE databasename;
#删除数据库
DROP DATABASE databasename;
```

## 2.用户及权限

```mysql
#创建用户 其中host可用%代替,表示不限制host
CREATE USER 'username'@'host' IDENTIFIED BY 'password';
#修改用户密码
SET PASSWORD FOR 'username'@'host' = PASSWORD('newpassword');
#如果是当前登陆用户用:
SET PASSWORD = PASSWORD("newpassword");
#删除用户
DROP USER 'username'@'host';
#给用户授权 ALL表示数据库所有操作权限
GRANT ALL ON databasename.* TO 'username'@'host';
```

