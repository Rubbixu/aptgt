sledrop database if exists aptgt;

create database aptgt default character set utf8;
use aptgt;
drop table if exists `euser`;
create table euser(
	uid integer auto_increment primary key,
	udate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	name varchar(50),
	password varchar(20),
	email varchar(50),
	utype integer
)engine = myisam  default charset = utf8;
alter table euser add column vcode integer default 0;
insert into euser (name, password, email, utype) values ("admin","shelby2303","admin@auburn.edu",1);
insert into euser (name, password, email, utype) values ("teacher","teacher","teacher@auburn.edu",2);
insert into euser (name, password, email, utype) values ("student","student","student@auburn.edu",4);
insert into euser (name, password, email, utype) values ("student1","student1","student1@auburn.edu",4);
insert into euser (name, password, email, utype) values ("student2","student2","student2@auburn.edu",4);
insert into euser (name, password, email, utype) values ("student3","student3","student3@auburn.edu",4);
insert into euser (name, password, email, utype) values ("student4","student4","student4@auburn.edu",4);

create table semester(
	id integer auto_increment primary key,
	year integer,
	semester integer,
	iscurrent integer default 0
)engine = myisam  default charset = utf8;
insert into semester (year, semester, iscurrent) values (2018, 1, 1);
drop table if exists `lesson`;
create table lesson(
	lid integer auto_increment primary key,
	name varchar(50),
	ldesc varchar(2000),
	udate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	uid integer,
	semesterid integer,
	ltype integer
)engine = myisam  default charset = utf8;

drop table if exists `lesson_student`;
create table lesson_student(
	id integer auto_increment primary key,
	lid integer,
	sid integer,
	stype integer
)engine = myisam  default charset = utf8;

drop table if exists `lesson_file`;
create table lesson_file(
	fid integer auto_increment primary key,
	name varchar(100),
	path varchar(1000),
	ftype varchar(10),
	fdesc varchar(2000),
	lid integer
)engine = myisam  default charset = utf8;

drop table if exists `exam`;
create table exam(
	eid integer auto_increment primary key,
	ispractice integer,
	name varchar(50),
	edesc varchar(2000),
	edue datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	etype integer,
	lid integer,
	uid integer
)engine = myisam  default charset = utf8;

drop table if exists `exam_video`;
create table exam_video(
	fid integer auto_increment primary key,
	name varchar(100),
	path varchar(1000),
	ftype varchar(10),
	fdesc varchar(2000),
	eid integer
)engine = myisam  default charset = utf8;





drop table if exists `exam_word`;
create table exam_word(
	fid integer auto_increment primary key,
	name varchar(100),
	pronunciation varchar(100),
	path varchar(1000),
	ftype varchar(10),
	fdesc varchar(2000),
	eid integer
)engine = myisam  default charset = utf8;


drop table if exists `word_video`;
create table word_video(
	fid integer auto_increment primary key,
	wid integer,
	eid integer,
	name varchar(100),
	path varchar(1000),
	fdesc varchar(2000)
)engine = myisam  default charset = utf8;



drop table if exists `exam_result`;
create table exam_result(
	rid integer auto_increment primary key,
	uid integer,
	eid integer,
	answer varchar(2000),
	score varchar(500),
	total float
)engine = myisam  default charset = utf8;

drop table if exists `student_exam`;
create table student_exam(
	seid integer auto_increment primary key,
	sid integer,
	eid integer,
	taken integer default 0
)engine = myisam  default charset = utf8;

drop table if exists `word_student`;
create table word_student(
	wsid integer auto_increment primary key,
	sid integer,
	wid integer,
	eid integer,
	answer varchar(200),
	score float 
)engine = myisam  default charset = utf8;

drop table if exists `comment`;
create table comment(
	cid integer auto_increment primary key,
	uid integer,
	commentdate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	comment varchar(2000)
)engine = myisam  default charset = utf8;



