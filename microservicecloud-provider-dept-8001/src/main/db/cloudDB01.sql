drop database if exists cloudDB01;
create database cloudDB01 character set UTF8;
use cloudDB01;
create table dept
(
    dept_no         bigint not null primary key auto_increment,
    dept_name       varchar(60),
    database_source varchar(60)
);

insert into dept (dept_name, database_source)
values ('开发部', database());
insert into dept (dept_name, database_source)
values ('人事部', database());
insert into dept (dept_name, database_source)
values ('财务部', database());
insert into dept (dept_name, database_source)
values ('市场部', database());
insert into dept (dept_name, database_source)
values ('运维部', database());

select *
from dept;

