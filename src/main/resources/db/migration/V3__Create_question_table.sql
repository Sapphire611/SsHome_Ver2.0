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
