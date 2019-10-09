
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
-- 用户表
DROP TABLE IF EXISTS MB_User;
CREATE TABLE MB_User(
	id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT'用户编号：唯一且不为空' ,
	account VARCHAR(20) UNIQUE NOT NULL COMMENT'用户登录名' ,
	_password VARCHAR(10) NOT NULL COMMENT'用户登录密码' ,
	mailbox VARCHAR(50) NOT NULL COMMENT'用户邮箱' ,
	registrationTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]用户注册时间，不用操作由数据库默认操作' ,
-- ------------------------以下是可选字段，用于扩展
	realName VARCHAR(30) DEFAULT 'unknow' COMMENT'[可选]用户的真实姓名' ,
	IDCardNo VARCHAR(20) DEFAULT 'unknow' COMMENT'[可选]用户的身份证号' ,
	sex ENUM('男', '女', '你猜') DEFAULT '你猜' COMMENT'[可选]用户性别' ,
	phoneNumber VARCHAR(20) DEFAULT 'unknow' COMMENT'[可选]用户联系电话' 
);
	
SELECT _password 'password' FROM MB_User WHERE account = 'admin';
