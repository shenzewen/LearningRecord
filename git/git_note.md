# git note

### 1. ssh公钥生成命令

```ssh-keygen -t rsa -C "XXXXXX@XXX.com” -f ~/.ssh/gitlab_id-rsa```

可以指定邮箱和生成路径

### 2. 区分多个SSH-KEY

在~/.ssh目录下添加config配置文件内容如下

```shell
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

### 3. 初次使用配置name和email

```shell
#全局
git config --global user.name "XXXX"
git config --global user.email "XXXX@XX.com"
#单个项目，在该项目下执行
git config user.name "XXXX"
git config user.email "XXXX@XX.com"
```

也可以在项目中的.git/config文件中修改name和email

### 4.Git 基础

##### 1.获取 Git 仓库

  * 在现有目录中初始化仓库

```shell
#如果你打算使用 Git 来对现有的项目进行管理，你只需要进入该项目目录并输入：
$ git init
```

  * 克隆现有的仓库

```shell
$ git clone https://github.com/libgit2/libgit2 mylibgit
```



##### 2.记录每次更新到仓库

* 检查当前文件状态

```shell
#要查看哪些文件处于什么状态，可以用 git status 命令
$ git status
```

* 跟踪新文件

```shell
#使用命令 git add 开始跟踪一个文件。 所以，要跟踪 README 文件，运行：
$ git add README
```

* 状态简览

```shell
#运行 git status -s ，状态报告输出如下：
$ git status -s
 M README
MM Rakefile
A  lib/git.rb
M  lib/simplegit.rb
?? LICENSE.txt
#新添加的未跟踪文件前面有 ?? 标记，新添加到暂存区中的文件前面有 A 标记，修改过的文件前面有 M 标记。出现在右边的 M 表示该文件被修改了但是还没放入暂存区，出现在靠左边的 M 表示该文件被修改了并放入了暂存区
```

* 忽略文件

```shell
#我们可以创建一个名为 .gitignore 的文件，列出要忽略的文件模式。 来看一个实际的例子：
$ cat .gitignore
*.[oa]
*~
```

* 查看已暂存和未暂存的修改

```shell
#要查看尚未暂存的文件更新了哪些部分，不加参数直接输入 git diff：
$ git diff
#此命令比较的是工作目录中当前文件和暂存区域快照之间的差异， 也就是修改之后还没有暂存起来的变化内容。
#然后用 git diff --cached 查看已经暂存起来的变化：（--staged 和 --cached 是同义词）
$ git diff --cached
```

* 提交更新

```shell
#现在的暂存区域已经准备妥当可以提交了。 在此之前，请一定要确认还有什么修改过的或新建的文件还没有 git add 过，否则提交的时候不会记录这些还没暂存起来的变化。 这些修改过的文件只保留在本地磁盘。 所以，每次准备提交前，先用 git status 看下，是不是都已暂存起来了， 然后再运行提交命令 git commit：
$ git commit
#这种方式会启动文本编辑器以便输入本次提交的说明。
```



### 5.远程仓库操作

```shell
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

### 6. 文件比对

```shell
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

### 7. subtree拆分大的git库到多个独立git库

```shell
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

