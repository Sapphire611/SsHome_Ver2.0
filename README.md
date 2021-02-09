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
