# git note

###1. ssh公钥生成命令

```ssh-keygen -t rsa -C "XXXXXX@XXX.com” -f ~/.ssh/gitlab_id-rsa```

可以指定邮箱和生成路径

### 2. 区分多个SSH-KEY

在~/.ssh目录下添加config配置文件内容如下

```Host gitlab.com
#文件内容如下
#gitlab
Host gitlab.com
HostName gitlab.com
PreferredAuthentications publickey
IdentityFile ~/.ssh/gitlab_id-rsa
User youname

Host 192.168.X.XXX
IdentityFile ~/.ssh/id_rsa
User youname
```

### 3. 配置name和email

```git config --global user.name "XXXX@XX.com"
#全局
git config --global user.name "XXXX"
git config --global user.email "XXXX@XX.com"
#单个项目，在该项目下执行
git config user.name "XXXX"
git config user.email "XXXX@XX.com"
```

也可以在项目中的.git/config文件中修改name和email

