/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : SsHome

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 26/12/2020 17:47:54
*/

drop database SsHome;
create database SsHome;
use SsHome;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `gmtCreate` bigint(20) DEFAULT NULL,
  `gmtModified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES (1, 'Piper', '123456', 1601117858620, 1601117858620);
COMMIT;

-- ----------------------------
-- Table structure for likeRecord
-- ----------------------------
DROP TABLE IF EXISTS `likeRecord`;
CREATE TABLE `likeRecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `questionId` int(11) DEFAULT NULL,
  `gmtCreate` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of likeRecord
-- ----------------------------
BEGIN;
INSERT INTO `likeRecord` VALUES (1, 3, 7, 1608888941995);
INSERT INTO `likeRecord` VALUES (2, 3, 6, 1608889132177);
INSERT INTO `likeRecord` VALUES (3, 3, 5, 1608889141621);
INSERT INTO `likeRecord` VALUES (4, 3, 1, 1608889202891);
INSERT INTO `likeRecord` VALUES (5, 1, 7, 1608972824221);
COMMIT;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  `gmtCreate` bigint(20) DEFAULT NULL,
  `gmtModified` bigint(20) DEFAULT NULL,
  `gmtAuthorRead` bigint(20) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `view_count` int(11) DEFAULT '0',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of question
-- ----------------------------
BEGIN;
INSERT INTO `question` VALUES (1, '这个问题不能删除', '<p>删除了就会报错的，建议新建其他问题测试</p>', 1601117858620, 1601117858620, 1608881089017, 1, 1, 2, 2, '别动我');
INSERT INTO `question` VALUES (5, '测试专用', '<p><iframe src=\"//player.bilibili.com/player.html?aid=202951817&amp;bvid=BV1Ma411F7rQ&amp;cid=261999311&amp;page=1\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" width=\"100%\" height=\"600px\"> </iframe></p><hr><pre type=\"Bash\"><code><xmp>package com.sapphire.demo;\r\n\r\nimport org.springframework.boot.SpringApplication;\r\nimport org.springframework.boot.autoconfigure.SpringBootApplication;\r\n\r\n@SpringBootApplication\r\npublic class DemoApplication {\r\n\r\n    public static void main(String[] args) {\r\n        SpringApplication.run(DemoApplication.class, args);\r\n    }\r\n\r\n}\r\n	</xmp></code></pre>', 1608881277357, 1608881277357, 1608974025612, 1, 3, 1, 3, '测试');
INSERT INTO `question` VALUES (6, '我是佩佩', '<p><font color=\"#c24f4a\">本人才是真的佩佩哦，不要搞错了</font></p>', 1608885556618, 1608885556618, 1608888670177, 2, 0, 1, 3, '测试');
INSERT INTO `question` VALUES (7, '我是一个普通的用户', '<p><font color=\"#1c487f\">没别的，发个帖子</font></p>', 1608888717529, 1608888717529, 1608889733158, 3, 0, 2, 2, '测试');
COMMIT;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `questionId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  `gmtCreate` bigint(20) DEFAULT NULL,
  `gmtModified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of reply
-- ----------------------------
BEGIN;
INSERT INTO `reply` VALUES (11, 5, 2, '<p>我是佩佩，我来回复一下</p>', 1608882978114, 1608882978114);
INSERT INTO `reply` VALUES (12, 5, 1, '<p>我本人来回复一下吧</p>', 1608883774811, 1608883774811);
INSERT INTO `reply` VALUES (13, 5, 3, '<p>很好，要努力学习</p>', 1608889573253, 1608889573253);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `bio` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `avatarUrl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `adminBoolean` int(11) DEFAULT '0',
  `gmtCreate` bigint(20) DEFAULT NULL,
  `gmtModified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '34004352', 'Sara is my one and only ~', 'Sapphire611', '2390b729-73c5-42e4-b661-2e54f3c732c9', 'Rabbit611', '/avatar/34004352.jpeg', 1, 1601117858620, 1607183818153);
INSERT INTO `user` VALUES (2, 'piper', 'I am Piper.', 'Piper', '2390b729-73c5-42e4-b661-2e54f3c732c8', '123456', '/avatar/piper.jpeg', 1, 1601117858620, 1607183818153);
INSERT INTO `user` VALUES (3, 'user', 'I am user.', 'User1', '2390b729-73c5-42e4-b661-2e54f3c732c7', '123456', '/avatar/user.jpeg', 0, 1601117858620, 1607183818153);
COMMIT;

-- ----------------------------
-- Table structure for viewRecord
-- ----------------------------
DROP TABLE IF EXISTS `viewRecord`;
CREATE TABLE `viewRecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `questionId` int(11) DEFAULT NULL,
  `gmtCreate` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of viewRecord
-- ----------------------------
BEGIN;
INSERT INTO `viewRecord` VALUES (1, 1, 1, 1607758234489);
INSERT INTO `viewRecord` VALUES (2, 1, 2, 1607953373918);
INSERT INTO `viewRecord` VALUES (3, 1, 3, 1608864245075);
INSERT INTO `viewRecord` VALUES (4, 1, 4, 1608864724635);
INSERT INTO `viewRecord` VALUES (5, 1, 5, 1608881278888);
INSERT INTO `viewRecord` VALUES (6, 2, 5, 1608882962891);
INSERT INTO `viewRecord` VALUES (7, 2, 6, 1608886429787);
INSERT INTO `viewRecord` VALUES (8, 3, 6, 1608888737342);
INSERT INTO `viewRecord` VALUES (9, 3, 5, 1608888742952);
INSERT INTO `viewRecord` VALUES (10, 3, 7, 1608888763715);
INSERT INTO `viewRecord` VALUES (11, 3, 1, 1608889201342);
INSERT INTO `viewRecord` VALUES (12, 1, 7, 1608889951396);
INSERT INTO `viewRecord` VALUES (13, 1, 6, 1608972838396);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
