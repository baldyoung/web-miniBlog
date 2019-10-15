
-- 创建数据库
/*
character 指定数据库存储字符串的默认字符集；
collate 指定数据库的默认校对规则，用来比较字符串的方式，解决排序和字符分组的问题；
*/
CREATE DATABASE IF NOT EXISTS MiniBlog 
	DEFAULT CHARACTER SET utf8 
	DEFAULT COLLATE utf8_general_ci;
	
-- 查看建数据库的语句	
-- show create database MiniBlog;
SHOW CREATE TABLE MB_User;

-- 跳转到指定数据库下
USE MiniBlog;


-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 普通用户表
DROP TABLE IF EXISTS MB_User;
CREATE TABLE MB_User(
	id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]用户编号：唯一且不为空' ,
	account VARCHAR(20) UNIQUE NOT NULL COMMENT'用户登录名' ,
	_password VARCHAR(12) NOT NULL COMMENT'用户登录密码' ,
	mailbox VARCHAR(50) NOT NULL COMMENT'用户邮箱' ,
	registrationTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]用户注册时间，不用操作由数据库默认操作' ,
-- ------------------------以下是可选字段，用于扩展
	realName VARCHAR(30) DEFAULT 'unknow' COMMENT'[可选]用户的真实姓名' ,
	IDCardNo VARCHAR(20) DEFAULT 'unknow' COMMENT'[可选]用户的身份证号' ,
	sex ENUM('男', '女', '你猜') DEFAULT '你猜' COMMENT'[可选]用户性别' ,
	phoneNumber VARCHAR(20) DEFAULT 'unknow' COMMENT'[可选]用户联系电话'
)COMMENT'普通用户表';

SELECT _password 'password' FROM MB_User WHERE account = 'admin';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 管理员表
DROP TABLE IF EXISTS MB_Administrator;
CREATE TABLE MB_Administrator(
	id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]管理员编号：唯一且不为空',
	account VARCHAR(20) UNIQUE NOT NULL COMMENT'管理员登录账户：唯一且不为空',
	_password VARCHAR(20) NOT NULL COMMENT'管理员登录密码',
	roleId INT NOT NULL COMMENT'角色编号',
	mailbox VARCHAR(50) DEFAULT 'unknow' COMMENT'管理员邮箱',
	registrationTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]用户注册时间，不用操作由数据库默认操作'
)COMMENT'管理员表';

-- [必须]系统默认用户的创建
INSERT INTO MB_Administrator(account, _password, roleId) VALUES('superAdmin', 'miniblog2019super', 0), ('admin', 'miniblog2019', 1);


-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 系统角色表
DROP TABLE IF EXISTS MB_Role;
CREATE TABLE MB_Role(
	id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]角色编号：唯一且不为空',
	_name VARCHAR(30) UNIQUE NOT NULL COMMENT'角色名称：唯一且不为空'
)COMMENT'系统角色表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 系统权限表
DROP TABLE IF EXISTS MB_Access;
CREATE TABLE MB_Access(
	id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]权限编号：唯一且不为空',
	_name VARCHAR(50) UNIQUE NOT NULL COMMENT'权限名称：唯一且不为空',
	path VARCHAR(100) UNIQUE NOT NULL COMMENT'权限的访问路径：唯一且不为空'
)COMMENT'系统权限表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 角色的权限对应表
DROP TABLE IF EXISTS MB_AccessOfRole;
CREATE TABLE MB_AccessOfRole(
	id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	roleId INT NOT NULL COMMENT'角色编号：不为空',
	accessId INT NOT NULL COMMENT'权限编号：不为空'
)COMMENT'角色的权限对应表';





