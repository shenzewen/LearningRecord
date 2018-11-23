# git note

### 1. ssh公钥生成命令

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

### 4.远程仓库操作

```
#1.修改命令
git remote origin set-url [url]
#2.删除命令
git remote rm origin
#3.增加命令
git remote add origin [url]
#4.显示远程仓库(其中'-v'显示对应的克隆地址)
git remote -v
#5.查看远程仓库信息
git remote show [remote-name]
#6.远程仓库重命名
git remote rename [name] [newname]
```

### 5. 文件比对

```
#工作区与暂存区比较
git diff HEAD [filepath]
#工作区与HEAD(当前工作分支)比较
git diff HEAD [filepath]
#暂存区与HEAD比较
git diff --staged [filepath]
git diff --cached [filepath]
#当前分支的文件与branchName 分支的文件进行比较
git diff [branchName] [filepath]
#与某一次提交进行比较
git diff [commitId] [filepath]
```

### 6. subtree拆分大的git库到多个独立git库

```
# 这就是那个大仓库 big-project
$ git clone git@github.com:tom/big-project.git
$ cd big-project

# 把所有 `codes-eiyo` 目录下的相关提交整理为一个新的分支 eiyo
$ git subtree split -P codes-eiyo -b eiyo

# 另建一个新目录并初始化为 git 仓库
$ mkdir ../eiyo
$ cd ../eiyo
$ git init

# 拉取旧仓库的 eiyo 分支到当前的 master 分支
$ git pull ../big-project eiyo

# 推送给新的远端仓库
$ git remote add origin git://github.com:tom/fresh-project.git
$ git push origin -u master
```