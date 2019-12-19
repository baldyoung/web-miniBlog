/*
MiniBlog_SingleUser
版本号:Version1.00   ---------------------- 2019-12-4
MB_User表新增_name字段，用来代表用户昵称   --- 2019-12-13
MB_ArticleComment新增parent_comment_id字段，标识父级评论编号   --- 2019-12-13
*/




-- 创建数据库
/*
character 指定数据库存储字符串的默认字符集；
collate 指定数据库的默认校对规则，用来比较字符串的方式，解决排序和字符分组的问题；
*/
CREATE DATABASE IF NOT EXISTS MiniBlog_SingleUser
	DEFAULT CHARACTER SET utf8
	DEFAULT COLLATE utf8_general_ci;

-- 查看建数据库的语句
-- show create database MiniBlog;
-- 查看建表的语句
-- SHOW CREATE TABLE MB_User;

-- 跳转到指定数据库下
USE MiniBlog_SingleUser;


-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 普通用户表
DROP TABLE IF EXISTS MB_User;
CREATE TABLE MB_User(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]用户编号：唯一且不为空' ,
	_name VARCHAR(50) NOT NULL COMMENT'用户昵称' ,
	account VARCHAR(20) UNIQUE NOT NULL COMMENT'用户登录名' ,
	_password VARCHAR(12) NOT NULL COMMENT'用户登录密码' ,
	mailbox VARCHAR(50) NOT NULL COMMENT'用户邮箱' ,
	registrationTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]用户注册时间，不用操作由数据库默认操作' ,
-- ------------------------以下是可选字段，用于扩展
	sentenceA VARCHAR(200) DEFAULT '' COMMENT'[默认]用户备用参数一',
	realName VARCHAR(30) DEFAULT 'unknow' COMMENT'[可选]用户的真实姓名' ,
	IDCardNo VARCHAR(20) DEFAULT 'unknow' COMMENT'[可选]用户的身份证号' ,
	sex ENUM('男', '女', '你猜') DEFAULT '你猜' COMMENT'[可选]用户性别' ,
	phoneNumber VARCHAR(20) DEFAULT 'unknow' COMMENT'[可选]用户联系电话'
)COMMENT'普通用户表';

INSERT INTO MB_User(account, _password, mailbox, _name) VALUES ('dev', '123', '123@126.com', '乌龙');
# SELECT _password 'password' FROM MB_User WHERE account = 'admin';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 管理员表
DROP TABLE IF EXISTS MB_Administrator;
CREATE TABLE MB_Administrator(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]管理员编号：唯一且不为空',
	account VARCHAR(20) UNIQUE NOT NULL COMMENT'管理员登录账户：唯一且不为空',
	_password VARCHAR(20) NOT NULL COMMENT'管理员登录密码',
	roleId INT UNSIGNED NOT NULL COMMENT'角色编号',
	mailbox VARCHAR(50) DEFAULT 'unknow' COMMENT'管理员邮箱',
	registrationTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]用户注册时间，不用操作由数据库默认操作'
)COMMENT'管理员表';

-- [必须]系统默认用户的创建
INSERT INTO MB_Administrator(account, _password, roleId) VALUES('superAdmin', 'miniblog2019super', 0), ('admin', 'miniblog2019', 1);


-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 系统角色表
DROP TABLE IF EXISTS MB_Role;
CREATE TABLE MB_Role(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]角色编号：唯一且不为空',
	_name VARCHAR(30) UNIQUE NOT NULL COMMENT'角色名称：唯一且不为空'
)COMMENT'系统角色表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 系统权限表
DROP TABLE IF EXISTS MB_Access;
CREATE TABLE MB_Access(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]权限编号：唯一且不为空',
	_name VARCHAR(50) UNIQUE NOT NULL COMMENT'权限名称：唯一且不为空',
	path VARCHAR(100) UNIQUE NOT NULL COMMENT'权限的访问路径：唯一且不为空'
)COMMENT'系统权限表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 角色的权限对应表
DROP TABLE IF EXISTS MB_AccessOfRole;
CREATE TABLE MB_AccessOfRole(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	roleId INT NOT NULL COMMENT'角色编号：不为空',
	accessId INT NOT NULL COMMENT'权限编号：不为空'
)COMMENT'角色的权限对应表';


-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 帖子/文章表
DROP TABLE IF EXISTS MB_Article;
CREATE TABLE MB_Article(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	userId INT UNSIGNED NOT NULL COMMENT'所属用户编号：不为空',
	title VARCHAR(100) NOT NULL COMMENT'文章标题：不为空',
	publishTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]发表时间：不为空，由数据库默认操作',
	readNum INT UNSIGNED DEFAULT 0 COMMENT'[默认]已阅数量：初始为0',
	likeAmount INT UNSIGNED DEFAULT 0 COMMENT'[默认]点赞数量：初始为0',
	commentAmount INT UNSIGNED DEFAULT 0 COMMENT'[默认]评论数量：初始为0',
	forbid ENUM('no', 'yes') DEFAULT 'no' COMMENT'[默认]是否禁止访问：对于违规文章进行禁止访问所需的标识字段'
)COMMENT'文章表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 帖子/文章文字内容详情表
DROP TABLE IF EXISTS MB_ArticleDetails;
CREATE TABLE MB_ArticleDetails(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	articleId INT UNSIGNED NOT NULL COMMENT'所属文章的编号：不为空',
	content TEXT NOT NULL COMMENT'内容：不为空'
)COMMENT'文章文字内容详情表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 帖子/文章的图片路径表
DROP TABLE IF EXISTS MB_ArticlePicture;
CREATE TABLE MB_ArticlePicture(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	articleId INT UNSIGNED NOT NULL COMMENT'所属文章的编号：不为空',
	pictureName VARCHAR(30) NOT NULL COMMENT'图片名称：不为空'
)COMMENT'文章的图片路径表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 文章的评论表
DROP TABLE IF EXISTS MB_ArticleComment;
CREATE TABLE MB_ArticleComment(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	articleId INT UNSIGNED NOT NULL COMMENT'所属文章的编号：不为空',
	parent_comment_id INT COMMENT'父级评论编号',
	userId INT UNSIGNED COMMENT'该条评论的所属用户编号',
	content VARCHAR(300) NOT NULL COMMENT'评论的内容：不为空',
	likeAmount INT UNSIGNED DEFAULT 0 COMMENT'[默认]点赞数量：初始为0',
	recordTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]记录时间，不用操作由数据库默认操作'
)COMMENT'文章的评论表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 论坛留言记录表
DROP TABLE IF EXISTS MB_Message;
CREATE TABLE MB_Message(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	userId INT UNSIGNED NOT NULL COMMENT'所属论坛的编号（因为一个用户下只有一个论坛，所以用用户编号来代替）：不为空',
	content VARCHAR(500) NOT NULL COMMENT'留言的内容：不为空',
	likeAmount INT UNSIGNED DEFAULT 0 COMMENT'[默认]点赞数量：初始为0'
)COMMENT'论坛留言记录表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 文章点赞记录表
DROP TABLE IF EXISTS MB_LikeRecordOfArticle;
CREATE TABLE MB_LikeRecordOfArticle(
	id BIGINT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	articleId INT UNSIGNED NOT NULL COMMENT'所属文章的编号：不为空',
	userId INT UNSIGNED COMMENT'点赞用户的编号',
	recordTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]记录时间，不用操作由数据库默认操作'
)COMMENT'文章点赞记录表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 评论点赞记录表
DROP TABLE IF EXISTS MB_LikeRecordOfComment;
CREATE TABLE MB_LikeRecordOfComment(
	id BIGINT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	commentId INT UNSIGNED NOT NULL COMMENT'所属评论的编号：不为空',
	userId INT UNSIGNED COMMENT'点赞用户的编号：不为空',
	recordTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]记录时间，不用操作由数据库默认操作'
)COMMENT'评论点赞记录表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 留言点赞记录表
DROP TABLE IF EXISTS MB_LikeRecordOfMessage;
CREATE TABLE MB_LikeRecordOfMessage(
	id BIGINT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	messageId INT UNSIGNED NOT NULL COMMENT'所属留言的编号：不为空',
	userId INT UNSIGNED NOT NULL COMMENT'点赞用户的编号：不为空',
	recordTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]记录时间，不用操作由数据库默认操作'
)COMMENT'留言点赞记录表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 系统消息记录表
DROP TABLE IF EXISTS MB_SystemNews;
CREATE TABLE MB_SystemNews(
	id BIGINT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	adminId INT UNSIGNED NOT NULL DEFAULT 0 COMMENT'[默认为0，意为系统消息]发送系统消息的管理员编号：不为空',
	userId INT UNSIGNED NOT NULL COMMENT'接收用户的编号：不为空',
	content VARCHAR(600) NOT NULL COMMENT'消息的具体内容：不为空',
	newsTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]消息时间，不用操作由数据库默认操作'
)COMMENT'系统消息记录表';

-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 论坛公告表
DROP TABLE IF EXISTS MB_Intro;
CREATE TABLE MB_Intro(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	userId INT UNSIGNED NOT NULL COMMENT'归属的用户编号，即归属的那个论坛：不为空',
	content VARCHAR(1000) NOT NULL COMMENT'简介的内容：不为空',
	modifiedTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]最新修改时间，不用操作由数据库默认操作'
)COMMENT'论坛公告表';
-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 用户头像表
DROP TABLE IF EXISTS MB_UserPicture;
CREATE TABLE MB_UserPicture(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	userId INT UNSIGNED NOT NULL COMMENT'归属的用户编号：不为空',
	pictureName VARCHAR(30) NOT NULL COMMENT'图片名称：不为空',
	modifiedTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]最新修改时间，不用操作由数据库默认操作'
)COMMENT'用户头像表';
-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 平台简介表
DROP TABLE IF EXISTS MB_About;
CREATE TABLE MB_About(
	id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT COMMENT'[默认]编号：唯一且不为空',
	userId INT UNSIGNED NOT NULL COMMENT'归属的用户编号：不为空',
	content VARCHAR(1000) NOT NULL DEFAULT'' COMMENT'公告内容：不为空',
	createTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'[默认]创建时间，不用操作由数据库默认操作'
)COMMENT'平台简介表';
INSERT INTO MB_About(userId, content) VALUES (0, '太懒了，啥都还没写呢');




