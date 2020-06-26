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


