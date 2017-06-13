drop database mongoDBManage;
Create database mongoDBManage
CHARACTER SET 'utf8'
COLLATE 'utf8_general_ci';
log_recorduse mongoDBManage;

/* 部门表*/

create table departments(
	id int(10) primary key auto_increment,
	depName varchar(50)
)ENGINE=MyISAM default charset=utf8 auto_increment=1;
/* 职位表*/
create table positions(
	id int(10) primary key auto_increment,
	posiName varchar(50)
)ENGINE=MyISAM default charset=utf8 auto_increment=1;
/*用户表 */
create table users(
	userId int(10) primary key auto_increment,
	userName varchar(20) not null,
	`password` varchar(50) not null,
	nickname varchar(50) not null,
	gender bit not null, 
	age int(3) not null, 
	depId int not null,
	posiId int not null,
	phone varchar(20) not null,
	email varchar(50) not null
)ENGINE=MyISAM default charset=utf8 auto_increment=1000;
insert into users values(null,'admin','homolo','昵称',false,'25','1','1','18111111111','zhangsan@163.com');
/*连接分类表*/
Create table connectType(
	id int(10) primary key auto_increment,
	typeName varchar(20) not null
)ENGINE=MyISAM default charset=utf8 auto_increment=1;
/*连接表*/
/* drop table connectType*/
Create table connect(
	connectId int(10) primary key auto_increment,
	connectName varchar(50) not null,
	connectTypeId int(10) not null,
	connectIp varchar(20) not null,
	connectPort int not null,
	connectUserName varchar(20) not null,
	connectPassword varchar(20) not null
)ENGINE=MyISAM default charset=utf8 auto_increment=1;
/*drop table log_record;*/
/*日志记录表*/
Create table  log_record(
	id int(10) primary key auto_increment,
	userId int not null,
	connectId int not null,
	datebaseName varchar(50) not null,
	tableName varchar(50) not null,
	operationType bit not null,
	operationTime datetime not null,
	remarks varchar(255) not null
)ENGINE=MyISAM default charset=utf8 auto_increment=1;
insert into users values(null,'张三','123',false,'25','1','1','18111111111','zhangsan@163.com');
/*任务表 drop table task */

Create table task(
	id int(10) primary key auto_increment,
	importType bit not null,
	importIsDB bit not null,
	sourceConnectId int not null,
	sourceDatebaseName varchar(50) not null,	
	receivedConnectId int not null,
	receivedDatebaseName varchar(50) not null,
	remarks varchar(255) not null	
)ENGINE=MyISAM default charset=utf8 auto_increment=1;
/*任务表中数据源的集合信息*/
Create table sourceTable(
	id int(10) primary key auto_increment,
	taskId int(10) not null,
	sourceTableName varchar(50) not null
)ENGINE=MyISAM default charset=utf8 auto_increment=1;
/*
任务表
	id
	源服务器
	源数据库
	接受服务器
	接受数据库
	是否覆盖

任务详表
	id
	所属任务
	表名

*/
/*
	
*/
/* 测试数据
insert into departments values(null,'人事部');
insert into departments values(null,'开发部');
insert into departments values(null,'财务部');
insert into positions values(null,'员工');
insert into positions values(null,'主管');
insert into positions values(null,'经理');

insert into users values(null,'李四','123',false,'25','2','2','18222222222','lisi@163.com');
insert into users values(null,'王五','123',false,'25','3','3','18333333333','wangwu@163.com');

insert into users values(null,'zhangsan','123',true,'25','1','1','18111111111','zhangsan@163.com');
insert into users values(null,'lisi','123',true,'25','2','2','18222222222','lisi@163.com');
insert into users values(null,'wangwu','123',true,'25','3','3','18333333333','wangwu@163.com');
insert into connectType values(null,'类型1');
insert into connectType values(null,'类型2');
insert into connectType values(null,'类型3');
insert into connect values(null,1,'127.0.0.1',27017,'admin1','admin1');
insert into connect values(null,1,'127.0.0.2',27016,'admin2','admin2');
insert into connect values(null,1,'127.0.0.3',27015,'admin3','admin3');
insert into connect values(null,2,'127.0.0.4',27014,'admin4','admin4');
insert into connect values(null,3,'127.0.0.5',27013,'admin5','admin5');
insert into connect values(null,2,'127.0.0.6',27012,'admin6','admin6');

insert into log_record values(null,'1001','1','db','table',false,now(),'');
insert into log_record values(null,'1002','2','db2','table2',false,now(),'');
insert into log_record values(null,'1003','3','db3','table3',false,now(),'');
insert into task value(null,"1","admin","2","school","哎哟")
select t.id,sc.connectIp,t.sourceDatebaseName,rc.connectIp,t.receivedDatebaseName,t.remarks from task as t,connect as sc,connect as rc where t.sourceConnectId=sc.connectId and t.receivedConnectId=rc.connectId and t.remarks like '%哟%' limit 0,20
*/

