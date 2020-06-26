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

### 基础SQL语句，实际内容以db.migrate中的为准，利用flyway一键生成
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
```
create table Reply
(
	id int auto_increment,
	questionId int null,
	userId int null,
	description text null,
	gmtCreate BIGINT null,
	gmtModified BIGINT null,
	constraint table_name_pk
		primary key (id)
);

```
### Terminal
- source ~/.bash_profile
- mvn flyway:migrate

### Port
- sudo lsof -i:6197
- sudo kill -9 42505

### mysql 
```
CREATE EVENT e_test2
ON SCHEDULE EVERY 1 DAY
DO TRUNCATE aaa;
```

```
create database SapphireHome;
use SapphireHome;
create table user(
	 id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	 accountid varchar(100),
	 bio varchar(256) null,
	 name varchar(50),
	 token varchar(36),
	 password varchar(50) null,
	 avatarUrl varchar(100) null,
	 adminBoolean int default 0 null,
	 gmtCreate BIGINT,
	 gmtModified BIGINT
);

create table question
(
    id int auto_increment,
    title varchar(50) null,
    description TEXT null,
    gmtCreate BIGINT null,
    gmtModified BIGINT null,
    gmtAuthorRead BIGINT null,
    creator int null,
    comment_count int default 0 null,
    like_count int default 0 null,
    view_count int default 0 null,
    tag varchar(255) null,
    constraint question_pk primary key (id)
);

create table LikeRecord
(
	id int auto_increment,
	userId int null,
	questionId int null,
	gmtCreate BIGINT null,
	constraint LikeRecord_pk
		primary key (id)
);

create table viewRecord
(
	id int auto_increment,
	userId int null,
	questionId int null,
	gmtCreate BIGINT null,
	constraint LikeRecord_pk
		primary key (id)
);

create table Reply
(
	id int auto_increment,
	questionId int null,
	userId int null,
	description text null,
	gmtCreate BIGINT null,
	gmtModified BIGINT null,
	constraint table_name_pk
		primary key (id)
);


```

```
INSERT INTO `SapphireHome`.`user`(`id`, `accountid`, `bio`, `name`, `token`, `password`, `avatarUrl`, `adminBoolean`, `gmtCreate`, `gmtModified`) VALUES (19, '34004352', 'Sara is my one and only ~', 'Sapphire611', '63d86844-4423-4e62-83ed-6475b1be9531', 'Rabbit611', 'https://avatars2.githubusercontent.com/u/34004352?v=4', 1, 1591117858620, 1593184063892);
INSERT INTO `SapphireHome`.`user`(`id`, `accountid`, `bio`, `name`, `token`, `password`, `avatarUrl`, `adminBoolean`, `gmtCreate`, `gmtModified`) VALUES (20, '65452385', '可可爱爱没有脑袋🧐', 'SensitiveSara', 'f7ab6d41-ed68-48aa-861d-5c835e64a61a', '123456', 'https://avatars2.githubusercontent.com/u/65452385?v=4', 1, 1591625222960, 1591625222960);
INSERT INTO `SapphireHome`.`user`(`id`, `accountid`, `bio`, `name`, `token`, `password`, `avatarUrl`, `adminBoolean`, `gmtCreate`, `gmtModified`) VALUES (23, '67474602', '啵', 'LisaZhang05', '118a9818-5915-4017-875a-f798150add0c', '123456', 'https://avatars0.githubusercontent.com/u/67474602?v=4', 0, 1593185028264, 1593185203783);

INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (26, '我是一个假肉宝', '很假的，真不了的那种', 1591169155053, 1591169155053, 1591169155053, 20, 0, 3, 4, '可爱的肉宝');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (27, '我是一个小兔宝', '我是真的小兔宝', 1591169450774, 1591169450774, 1593081361851, 19, 1, 1, 2, 'Test');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (29, '现在是否可以正常发布问题？', '@RequestParam = false，应该可以了', 1591170252441, 1591170252441, 1591170252441, 19, 0, 2, 1, 'Test');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (49, '标签长度应该小于等于6', '多一点都不行', 1591433716049, 1591433729530, 1593174344442, 19, 5, 1, 4, '我只说六个字');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (55, 'Media heading', 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.', 1593078085168, 1593078085168, 1593174379520, 19, 0, 0, 1, 'Test');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (56, '欢迎大家来玩', '我是最可爱的小肉宝', 1593078154004, 1593078154004, 1593078154004, 20, 1, 0, 2, '爱你们');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (57, 'MATLAB基本介绍', '1.matlab的界面左上角，home标签下，找到layout进行设置/复位，可以设置各板块的显示与隐藏。其中有几个部分，请务必要显示\r\n\r\n①Current Folder：中文一般翻译成工作路径，一般设置成一个自己建立的、有读写权限的文件夹，例如我的文档下建立一个matlab文件夹\r\n\r\n②Command Window：字面意思是命令窗口，用来运行代码，所有的代码都是在这里输入\r\n\r\n③Workspace：字面意思是工作空间，其实就是暂存所有运行结果的地方，“暂”的具体含义是：关闭matlab后丢失\r\n\r\n2.软件中的基本概念 matlab之所以强大，就是因为提供大量的函数，你也可以建立自定义函数，方法是：Home->New->function。\r\n自定义函数一般保存在工作路径下。函数文件的特征是：扩展名m，内容的第一行以function开头，后续内容是“输出变量=函数名(输入变量)”。且函数名和文件名相同。\r\n\r\n每个函数在Command Window中运行，用来完成特定的计算任务，运行方式是输入“输出变量=函数名(输入变量)”，然后按回车。\r\n\r\n例如有个系统自带的函数是用来求绝对值的，函数名abs，所以在Command Window里输入“a=abs(-1)”，就会显示运算结果为“a=1”。且运算结果会在Workspace里出现一个变量a，双击后可看到a的值是1。', 1593078268617, 1593079174762, 1593079175899, 20, 1, 2, 2, 'MATLAB');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (58, 'LET THE BASS KICK', 'O-oooooooooo AAAAE-A-A-I-A-U- JO-oooooooooooo AAE-O-A-A-U-U-A- E-eee-ee-eee AAAAE-A-E-I-E-A- JO-ooo-oo-oo-oo EEEEO-A-AAA-AAAA\r\nO-oooooooooo AAAAE-A-A-I-A-U- JO-oooooooooooo AAE-O-A-A-U-U-A- E-eee-ee-eee AAAAE-A-E-I-E-A- JO-ooo-oo-oo-oo EEEEO-A-AAA-AAAA\r\nO-oooooooooo AAAAE-A-A-I-A-U- JO-oooooooooooo AAE-O-A-A-U-U-A- E-eee-ee-eee AAAAE-A-E-I-E-A- JO-ooo-oo-oo-oo EEEEO-A-AAA-AAAA', 1593078844646, 1593081232187, 1593184108078, 19, 1, 100000, 10000, '？');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (62, '123', 'test', 1593163027945, 1593163027945, 1593164687528, 19, 0, 0, 1, '1');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (63, '测试', '测试', 1593164714843, 1593164714843, 1593184558117, 19, 3, 0, 3, '测试');
INSERT INTO `SapphireHome`.`question`(`id`, `title`, `description`, `gmtCreate`, `gmtModified`, `gmtAuthorRead`, `creator`, `comment_count`, `like_count`, `view_count`, `tag`) VALUES (66, '我是新来的', '大家回复我', 1593186141475, 1593189332632, 1593189308625, 23, 3, 0, 2, 'New');

INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (37, 49, 19, '4', 1592301748599, 1592301748599);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (38, 49, 19, '5', 1592301750842, 1592301750842);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (59, 56, 19, '我是最不可爱的🐷', 1593078183021, 1593078183021);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (61, 57, 19, '好，很好，非常好👌👍', 1593079147900, 1593079147900);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (62, 27, 19, '我可能有毛病', 1593081316207, 1593081316207);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (63, 63, 20, 'qqq', 1593184608510, 1593184608510);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (64, 63, 20, '11111', 1593184611200, 1593184611200);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (66, 66, 19, '我也很累哦', 1593186171208, 1593186171208);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (67, 66, 19, '真的哦', 1593186178206, 1593186178206);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (68, 63, 23, '1', 1593186525176, 1593186525176);
INSERT INTO `SapphireHome`.`Reply`(`id`, `questionId`, `userId`, `description`, `gmtCreate`, `gmtModified`) VALUES (69, 66, 19, '再一次回复你', 1593188993353, 1593188993353);
```