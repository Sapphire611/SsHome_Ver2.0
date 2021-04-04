## SsHome_Ver2.0

> https://www.bilibili.com/video/BV1r4411r7au

![image](http://m.qpic.cn/psc?/V146JDoj1Lpm8R/TmEUgtj9EK6.7V8ajmQrEF5q7qfihLNzv7gQWXNsvvfyfApyuPgmT2vuI8RzFFW3zajq6Z0YfiiL3onfnyR5dFfpvZ3YG3ezPrw8QPJsEBw!/b&bo=hAP0AYQD9AEDR2I!&rf=viewer_4&t=5)

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
### 项目部署说明

> https://blog.csdn.net/Sirius_hly/article/details/82631470

```
1. 安装 Java、Mysql、Tomcat等必要工具
2. 通过Git 下载项目到本地，等待编译和相关内容下载完成
3. 运行 src/main/resourse/db/boot/home.sql (source home.sql)，生成具体表数据
4. 更改 application.properites 中的属性，将本地测试状态的属性打开，192上线状态的属性注释掉
5. 启动项目，如果是linux服务器，可参考下列命令用于ftp文件上传、部署
```
---

### Port & Terminal

> Mac上命令行界面操作的相关命令，仅供参考

```
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

```
alter user 'root'@'localhost' identified by  '新密码';
```

> MyBatisGenerator 

```
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
