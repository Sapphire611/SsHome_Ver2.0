## SsHome_Ver2.0

> https://www.bilibili.com/video/BV1r4411r7au
- [视频地址](https://www.bilibili.com/video/BV1dK4y1b7YH)
- [Bootstrap4](https://getbootstrap.net/docs/getting-started/introduction/)
- [Spring 文档](https://spring.io/guidess)
- [GitHub OAuth](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)
- [OKHttp](https://square.github.io/okhttp/)
- [api-getUsers-demo](https://api.github.com/users/Sapphire611)
- [新版本mysql修改root密码时提示语法错误的解决方法](https://blog.csdn.net/hunt_er/article/details/82901331)
- [彻底解决mysql中文乱码](https://blog.csdn.net/u012410733/article/details/61619656)
- [Json Editor Online](http://jsonseditoronline.org/)

---
### 项目部署说明

> https://blog.csdn.net/Sirius_hly/article/details/82631470

- 1. 安装 Java、Mysql、Tomcat
- 2. 运行 src/main/resourse/db/migration/boot.sql 没有数据项目会报错
- 3. 自行创建 GitHub OAuth App
- 4. 在application.properties 替换对应的clientId、ClientSecret、url、redirectURL等属性
- 5. src/main/resoursetemplate/navagation/中等的登陆链接是全局的，需要手动修改
- 6. 启动项目，如果是linux服务器，可参考下列命令用于ftp文件上传、部署

---

### Port & Terminal
- source ~/.bash_profile
- mvn flyway:migrate

> netstat -apn | grep 80

> nohup java -jar demo-0.0.1-SNAPSHOT.jar >/root/Springboot.log 2>&1 &

> put Desktop/demo-0.0.1-SNAPSHOT.jar /root

> sudo lsof -i:80

> sudo kill -9 42505

### mysql 修改密码
> alter user 'root'@'localhost' identified by  '新密码';

### MyBatisGenerator 

> source ~/.bash_profile

> mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

- NAVBAR LOGIN_URL == 全局
