# SsHome_Ver2.0

> https://www.bilibili.com/video/BV1r4411r7au

![image](http://sshome.cn-sh2.ufileos.com/01a9a648-70d5-4425-a7dd-db0444e9773c.png?UCloudPublicKey=TOKEN_fedda2ac-20e5-497b-94f7-15a051b41f62&Signature=nbhZ1E2C8%2BFMs4oZXUzM2ScUsKI%3D&Expires=1650702983)

---

技术 | 网站链接
---|---
Bootstrap4 | https://getbootstrap.net/docs/getting-started/introduction/
Spring 文档 | https://spring.io/guidess
GitHub OAuth 2.0 | https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
OKHttp | https://square.github.io/okhttp/
api-getUsers-demo | https://api.github.com/users/Sapphire611
新版本mysql修改root密码时提示语法错误的解决方法 | https://blog.csdn.net/hunt_er/article/details/82901331
彻底解决mysql中文乱码 | https://blog.csdn.net/u012410733/article/details/61619656
Json Editor Online | http://jsonseditoronline.org/
Markdown Editor | https://pandao.github.io/editor.md/

---

## 项目部署说明

> https://blog.csdn.net/Sirius_hly/article/details/82631470

``` console
1. (前置条件)安装 Java、Mysql等
2. git clone https://github.com/Sapphire611/SsHome_Ver2.0.git 
3. 用IDE(Eclipse、STS、VsCode)打开后，等待Maven相关内容下载
4. 运行 src/main/resourse/db/boot/home.sql (source home.sql) ，生成初始数据
5. 运行 DemoApplication.java
```

---

### Port & Terminal

> Mac上命令行界面操作的相关命令，仅供参考

``` linux
source ~/.bash_profile
source ~/.zshrc
mvn flyway:migrate
netstat -apn | grep 80
nohup java -jar demo-0.0.1-SNAPSHOT.jar >/root/Springboot.log 2>&1 &
put Desktop/demo-0.0.1-SNAPSHOT.jar /root
put Desktop/home.sql /root
sudo lsof -i:80
sudo kill -9 42505
```

> mysql 修改密码

```console
alter user 'root'@'localhost' identified by  '新密码';
```

> MyBatisGenerator

``` console
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
