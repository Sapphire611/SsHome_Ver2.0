## My Demo About Spring

[视频地址](https://www.bilibili.com/video/BV1dK4y1b7YH)
[Bootstrap](https://v3.bootcss.com/css/)
[Spring 文档](https://spring.io/guidess)
[GitHub OAuth](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)
[OKHttp](https://square.github.io/okhttp/)
[lomnok](https://projectlombok.org/)
[api-getUsers-demo](https://api.github.com/users/Sapphire611)

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

### SQL
```
CREATE TABLE user(
 id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
 accountid varchar(100),
 name varchar(50),
 token varchar(36),
 gmtCreate BIGINT,
 gmtModified BIGINT
);

```
```
create table question
(
    id int auto_increment,
    title varchar(50) null,
    description TEXT null,
    gmtCreate BIGINT null,
    gmtModified BIGINT null,
    comment_count int default 0 null,
    like_count int default 0 null,
    view_count int default 0 null,
    tag varchar(255) null,
    constraint question_pk primary key (id)
);



```
### Terminal
- source ~/.bash_profile
- mvn flyway:migrate

### Port
- sudo lsof -i:6197
- sudo kill -9 42505