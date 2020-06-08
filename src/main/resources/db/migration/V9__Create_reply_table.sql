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

