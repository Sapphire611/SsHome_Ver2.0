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

 Date: 21/01/2021 15:36:19
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of question
-- ----------------------------
BEGIN;
INSERT INTO `question` VALUES (1, '这个问题不能删除', '<p>删除了就会报错的，建议新建其他问题测试</p>', 1601117858620, 1601117858620, 1611132462956, 1, 5, 2, 3, '别动我');
INSERT INTO `question` VALUES (6, '我是佩佩', '<p><font color=\"#c24f4a\">本人才是真的佩佩哦，不要搞错了</font></p>', 1608885556618, 1608885556618, 1611131047861, 2, 5, 1, 3, '测试');
INSERT INTO `question` VALUES (7, '我是一个普通的用户', '<p><font color=\"#1c487f\">没别的，发个帖子</font></p>', 1608888717529, 1608888717529, 1608889733158, 3, 0, 2, 2, '测试');
INSERT INTO `question` VALUES (8, '大家好我是小肉包', '<p>闪亮登场！！</p>', 1611112154429, 1611112154429, NULL, 4, 0, 0, 1, '测试');
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of reply
-- ----------------------------
BEGIN;
INSERT INTO `reply` VALUES (14, 6, 1, '<p>回复1</p>', 1611123993420, 1611123993420);
INSERT INTO `reply` VALUES (15, 6, 1, '<p>回复 2</p>', 1611123999325, 1611123999325);
INSERT INTO `reply` VALUES (16, 6, 1, '<p>我又给了你一个回复3</p>', 1611127702352, 1611127702352);
INSERT INTO `reply` VALUES (17, 6, 1, '<p>可是我已经回复了4</p>', 1611128971771, 1611128971771);
INSERT INTO `reply` VALUES (18, 1, 1, '<p>回复你一次</p>', 1611129640024, 1611129640024);
INSERT INTO `reply` VALUES (19, 1, 2, '<p>回复你</p>', 1611130987785, 1611130987785);
INSERT INTO `reply` VALUES (20, 6, 1, '<p>回复5</p>', 1611131013681, 1611131013681);
INSERT INTO `reply` VALUES (21, 1, 2, '<p>回复你2</p>', 1611132455002, 1611132455002);
INSERT INTO `reply` VALUES (22, 1, 2, '<p>回复你3</p>', 1611135204879, 1611135204879);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '34004352', 'Sara is my one and only ~', 'Sapphire611', '19897e7c-ceba-4567-b13f-d665cc701756', 'Rabbit611', 'https://avatars2.githubusercontent.com/u/34004352?v=4', 1, 1601117858620, 1611194999067);
INSERT INTO `user` VALUES (2, 'piper', 'I am Piper.', 'Piper', '2390b729-73c5-42e4-b661-2e54f3c732c8', '123456', '/avatar/piper.jpeg', 1, 1601117858620, 1607183818153);
INSERT INTO `user` VALUES (3, 'user', 'I am user.', 'User1', '2390b729-73c5-42e4-b661-2e54f3c732c7', '123456', '/avatar/user.jpeg', 0, 1601117858620, 1607183818153);
INSERT INTO `user` VALUES (4, '65452385', '可可爱爱没有脑袋', 'SensitiveSara', '02060ef2-a100-4e89-a58b-46366dbf0c13', '123456', 'https://avatars2.githubusercontent.com/u/65452385?v=4', 1, 1611111777605, 1611111777605);
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

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
INSERT INTO `viewRecord` VALUES (14, 2, 1, 1611128019018);
INSERT INTO `viewRecord` VALUES (15, 1, 8, 1611137643661);
INSERT INTO `viewRecord` VALUES (16, 1, 888, 1611153452328);
INSERT INTO `viewRecord` VALUES (17, 1, 9, 1611195054783);
INSERT INTO `viewRecord` VALUES (18, 1, 999, 1611195267757);
INSERT INTO `viewRecord` VALUES (19, 1, 6666, 1611197561791);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
