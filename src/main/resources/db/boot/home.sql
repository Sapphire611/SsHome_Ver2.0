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

 Date: 10/03/2021 15:43:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(11) NOT NULL COMMENT '父类Id',
  `type` int(2) NOT NULL COMMENT '父类类型',
  `commentator` bigint(11) NOT NULL COMMENT '评论人id',
  `gmtCreate` bigint(20) NOT NULL COMMENT '创建时间',
  `gmtModified` bigint(20) NOT NULL COMMENT '修改时间',
  `commentCount` bigint(11) NOT NULL DEFAULT '0' COMMENT '二级评论数',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO `comment` VALUES (2, 8, 1, 1, 1612859536605, 1612859536605, 0, '这是一个回复内容');
INSERT INTO `comment` VALUES (25, 17, 1, 1, 1615140892147, 1615140892147, 6, '<p>一楼</p>');
INSERT INTO `comment` VALUES (26, 25, 2, 1, 1615140900747, 1615140900747, 0, '11111');
INSERT INTO `comment` VALUES (27, 25, 2, 1, 1615140904725, 1615140904725, 0, '22222');
INSERT INTO `comment` VALUES (28, 25, 2, 1, 1615140908298, 1615140908298, 0, '33333');
INSERT INTO `comment` VALUES (29, 25, 2, 1, 1615140912690, 1615140912690, 0, '44444');
INSERT INTO `comment` VALUES (30, 25, 2, 1, 1615140918323, 1615140918323, 0, '55555');
INSERT INTO `comment` VALUES (31, 25, 2, 1, 1615167168534, 1615167168534, 0, '6');
INSERT INTO `comment` VALUES (32, 18, 1, 1, 1615361303047, 1615361303047, 2, '<p>吃蛋炒饭</p>');
INSERT INTO `comment` VALUES (33, 32, 2, 1, 1615361324221, 1615361324221, 0, '不 喝西北风');
INSERT INTO `comment` VALUES (34, 32, 2, 1, 1615361339130, 1615361339130, 0, '吃西瓜🍉');
COMMIT;

-- ----------------------------
-- Table structure for likeRecord
-- ----------------------------
DROP TABLE IF EXISTS `likeRecord`;
CREATE TABLE `likeRecord` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `userId` bigint(11) NOT NULL,
  `sourceId` bigint(11) NOT NULL,
  `gmtCreate` bigint(20) NOT NULL,
  `type` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `gmtCreate` bigint(20) NOT NULL,
  `gmtModified` bigint(20) NOT NULL,
  `creator` bigint(11) NOT NULL,
  `comment_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `view_count` int(11) DEFAULT '0',
  `tag` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of question
-- ----------------------------
BEGIN;
INSERT INTO `question` VALUES (1, '这个问题不能删除', '<p>删除了就会报错的，建议新建其他问题测试</p>', 1601117858620, 1612345210696, 1, 0, 2, 57, '别动我');
INSERT INTO `question` VALUES (8, '大家好我是小肉包', '<p>闪亮登场！！</p>', 1611112154429, 1611112154429, 4, 1, 1, 200, '测试');
INSERT INTO `question` VALUES (17, '创建一个新问题', '<p>用来测试二级回复</p>', 1615140883824, 1615140883824, 1, 1, 0, 25, '测试');
INSERT INTO `question` VALUES (18, '今天晚上吃什么呢', '<p>吃个寂寞</p>', 1615361270531, 1615361270531, 1, 1, 0, 6, '测试');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `accountid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `bio` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `avatarUrl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `adminBoolean` int(2) NOT NULL DEFAULT '0',
  `gmtCreate` bigint(20) NOT NULL,
  `gmtModified` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '34004352', 'Sara is my one and only ~', 'Sapphire611', '0e6ac510-fdfa-408c-9b44-b30bea7f1482', 'Rabbit611', 'https://avatars.githubusercontent.com/u/34004352?v=4', 1, 1601117858620, 1615346382191);
INSERT INTO `user` VALUES (4, '65452385', '可可爱爱没有脑袋', 'SensitiveSara', '599722a9-a8ab-4e22-b08e-5f4891856c39', '123456', 'https://avatars.githubusercontent.com/u/65452385?v=4', 1, 1611111777605, 1611237068956);
INSERT INTO `user` VALUES (9, 'null', NULL, NULL, '616ce818-c6c1-4fe0-8a4b-e5143578f375', '900000', NULL, 0, 1615346361423, 1615346361423);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
