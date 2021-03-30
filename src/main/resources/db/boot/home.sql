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

 Date: 30/03/2021 14:53:40
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
  `content` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '评论内容',
  `like_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO `comment` VALUES (59, 27, 1, 4, 1615973414318, 1615973414318, 1, '<p>评论</p>', 0);
INSERT INTO `comment` VALUES (61, 27, 1, 1, 1615974147378, 1615974147378, 0, '<p>好吧，总算结束了</p>', 0);
INSERT INTO `comment` VALUES (65, 36, 1, 1, 1616661576803, 1616661576803, 1, '<p>我的头像是宫村</p>', 0);
INSERT INTO `comment` VALUES (66, 65, 2, 1, 1616661589149, 1616661589149, 0, '丽蓉的头像是堀', 0);
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
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of likeRecord
-- ----------------------------
BEGIN;
INSERT INTO `likeRecord` VALUES (52, 1, 21, 1615474319938, 1);
INSERT INTO `likeRecord` VALUES (69, 1, 27, 1616265412725, 1);
COMMIT;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `notifier` bigint(11) NOT NULL,
  `receiver` bigint(11) NOT NULL,
  `outerId` bigint(11) NOT NULL,
  `type` int(2) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  `gmtCreate` bigint(20) NOT NULL,
  `notifierName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `outerTitle` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of notification
-- ----------------------------
BEGIN;
INSERT INTO `notification` VALUES (4, 4, 1, 27, 1, 0, 1615973414330, 'SensitiveSara', 'test');
INSERT INTO `notification` VALUES (5, 1, 4, 27, 2, 0, 1615973663322, 'Sapphire611', 'test');
COMMIT;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `description` varchar(8192) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `gmtCreate` bigint(20) NOT NULL,
  `gmtModified` bigint(20) NOT NULL,
  `creator` bigint(11) NOT NULL,
  `comment_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `view_count` int(11) DEFAULT '0',
  `tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of question
-- ----------------------------
BEGIN;
INSERT INTO `question` VALUES (27, 'test', '`test`', 1615967296103, 1615967296103, 1, 2, 1, 125, 'css');
INSERT INTO `question` VALUES (30, 'SpringBoot', '## 这是我的Java启动类\r\n\r\n> 现在支持Markdown语法了，很舒服\r\n\r\n```java\r\npackage com.sapphire.demo;\r\n\r\nimport org.mybatis.spring.annotation.MapperScan;\r\nimport org.springframework.boot.SpringApplication;\r\nimport org.springframework.boot.autoconfigure.SpringBootApplication;\r\n\r\n@SpringBootApplication\r\n@MapperScan(\"com.sapphire.demo.mapper\")\r\npublic class DemoApplication {\r\n\r\n    public static void main(String[] args) {\r\n        SpringApplication.run(DemoApplication.class, args);\r\n    }\r\n\r\n}\r\n```', 1615974449558, 1615974460236, 1, 0, 0, 15, 'java,spring');
INSERT INTO `question` VALUES (31, 'java', '11', 1616120096643, 1616120096643, 1, 0, 0, 0, 'java');
INSERT INTO `question` VALUES (32, 'java', '111', 1616120132756, 1616120132756, 1, 0, 0, 2, 'java');
INSERT INTO `question` VALUES (33, 'java', 'java', 1616120537734, 1616120537734, 1, 0, 0, 1, 'java');
INSERT INTO `question` VALUES (34, '上传图片成功了', '## 一张壁纸\r\n\r\n> UCloud还是不错的，免费用\r\n\r\n![](http://sshome.cn-sh2.ufileos.com/2776fb21-0525-43cf-9bf6-a21679010a08.png?UCloudPublicKey=TOKEN_fedda2ac-20e5-497b-94f7-15a051b41f62&Signature=GKE0IqMeJhrfRsEhTVG8N3603ZU%3D&Expires=1616319286)', 1616232943595, 1616232943595, 1, 0, 0, 2, 'java');
INSERT INTO `question` VALUES (36, '堀与宫村', '![](http://sshome.cn-sh2.ufileos.com/775f34a4-445f-46bc-8cb7-fbf2b7bb90e9.jpeg?UCloudPublicKey=TOKEN_fedda2ac-20e5-497b-94f7-15a051b41f62&Signature=piYfWXEn%2FhUgJWIx%2F1n3RwvBNio%3D&Expires=1647849922)', 1616313942931, 1616313942931, 1, 1, 0, 6, '测试');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `accountid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `bio` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `avatarUrl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `adminBoolean` int(2) NOT NULL DEFAULT '0',
  `gmtCreate` bigint(20) NOT NULL,
  `gmtModified` bigint(20) NOT NULL,
  `email` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '34004352', 'Sara is my one and only ~', 'Sapphire611', '6994d374-64fc-4e8f-bb35-2d00688b687d', 'https://avatars.githubusercontent.com/u/34004352?v=4', 1, 1601117858620, 1616384138045, 'liuliyi611@gmail.com');
INSERT INTO `user` VALUES (4, '65452385', '可可爱爱没有脑袋', 'SensitiveSara', 'fcb3b87a-16f3-4f2f-91cb-e1e72203728e', 'https://avatars.githubusercontent.com/u/65452385?v=4', 1, 1611111777605, 1615947730704, NULL);
INSERT INTO `user` VALUES (9, 'test', 'test', 'user1', 'asdads', '/img/sapphire611.jpeg', 0, 1611111777605, 1611111777605, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
