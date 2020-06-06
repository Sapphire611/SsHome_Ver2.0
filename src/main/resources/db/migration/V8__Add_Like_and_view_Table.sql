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

