## My Demo About Spring —— SsHome(赛菲尔的窝)

- [视频地址](https://www.bilibili.com/video/BV1dK4y1b7YH)
- [Bootstrap](https://v3.bootcss.com/css/)
- [Spring 文档](https://spring.io/guidess)
- [GitHub OAuth](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)
- [OKHttp](https://square.github.io/okhttp/)
- [lomnok](https://projectlombok.org/)
- [api-getUsers-demo](https://api.github.com/users/Sapphire611)
- [新版本mysql修改root密码时提示语法错误的解决方法](https://blog.csdn.net/hunt_er/article/details/82901331)
- [彻底解决mysql中文乱码](https://blog.csdn.net/u012410733/article/details/61619656)

### Git
- git init  初始化 git
- git status 查看当前Git状态
- git add . 将当前目录所有文件都加入缓存
- git remote add origin https://github.com/Sapphire611/demo.git
- git commit -m "add readme"
- git commit --amend --no-edit
- git push -u origin master

### Annotation
- @Controller 把当前类作为路由API的承载者
- @Component 仅仅把当前类作为容器初始化到Spring的上下文
- @Autowire 把Spring容器中写好的实例加载到上下文
- @Value 不通过配置文件的注入属性


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

### 服务器mysql字段？
