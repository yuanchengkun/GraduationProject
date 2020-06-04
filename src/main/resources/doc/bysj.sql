/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : bysj

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-06-04 09:43:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition` (
  `comid` int(255) NOT NULL AUTO_INCREMENT,
  `comtpid` int(255) DEFAULT NULL,
  `starttime` varchar(255) DEFAULT NULL COMMENT '竞赛报名开始时间',
  `endtime` varchar(255) DEFAULT NULL COMMENT '竞赛报名结束时间',
  `miaosu` varchar(1023) DEFAULT NULL COMMENT '竞赛的描述',
  `comtime` varchar(255) DEFAULT NULL COMMENT '竞赛开始时间',
  `pic` varchar(255) DEFAULT NULL COMMENT '宣传图片',
  `comsrc` varchar(255) DEFAULT NULL COMMENT '介绍连接',
  PRIMARY KEY (`comid`),
  KEY `comtpid` (`comtpid`),
  CONSTRAINT `competition_ibfk_1` FOREIGN KEY (`comtpid`) REFERENCES `competitiontype` (`comtpid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of competition
-- ----------------------------
INSERT INTO `competition` VALUES ('15', '1', '2020-04-08', '2020-05-12', '是由国际计算机协会（ACM）主办的，一项旨在展示大学生创新能力、团队精神和在压力下编写程序、分析和解决问题能力的年度竞赛。', '2020-05-13', 'c515c57bb2484d9aab8a22f4d5996721472309f790529822793f3fe0d7ca7bcb0a46d480.jpg', 'http://acm.cumt.edu.cn/');
INSERT INTO `competition` VALUES ('16', '1', '2020-05-13', '2020-06-13', '经过近40年的发展，ACM国际大学生程序设计竞赛已经发展成为全球最具影响力的大学生程序设计竞赛。赛事目前由方正集团赞助。', '2020-07-13', '29c394f5965e4d7f92106676e89b717804.jpg', 'http://icpc.shu.edu.cn/');
INSERT INTO `competition` VALUES ('17', '4', '2020-05-11', '2020-06-27', '青少年创意编程比赛是蓝桥杯大赛专门面向全国中小学生设置的，比赛以学校或课外培训机构为单位报名。', '2020-07-17', '7d867d5182474fdc92dcdaf60480170bTIM图片20200513162646.png', 'http://kid.lanqiao.cn/');
INSERT INTO `competition` VALUES ('18', '2', '2020-05-07', '2020-05-27', '动画设计是一款炫酷的比赛', '2020-06-27', 'a3e5aff09a57481a8e287b219335c351u=2493812313,2853321380&fm=76.jpg', null);

-- ----------------------------
-- Table structure for competitiontype
-- ----------------------------
DROP TABLE IF EXISTS `competitiontype`;
CREATE TABLE `competitiontype` (
  `comtpid` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `comclass` varchar(255) DEFAULT NULL COMMENT '竞赛档次，分为1234档',
  `comlevel` varchar(255) DEFAULT NULL COMMENT '竞赛级别 分为国家省级校级学科竞赛',
  `comOrganizer` varchar(255) DEFAULT NULL COMMENT '主办单位',
  PRIMARY KEY (`comtpid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of competitiontype
-- ----------------------------
INSERT INTO `competitiontype` VALUES ('1', 'ACM', '第一档次', '国家级学科竞赛', '中国');
INSERT INTO `competitiontype` VALUES ('2', '动画设计', '第二档次', '省级学科竞赛', '四川');
INSERT INTO `competitiontype` VALUES ('4', '蓝桥杯', '第一档次', '国家级学科竞赛', '教育部高等学校计算机科学与技术教学指导委员会 工业和信息化部人才交流中心');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `fileid` int(255) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) DEFAULT NULL,
  `savename` varchar(255) DEFAULT NULL,
  `uploaddate` varchar(255) DEFAULT NULL,
  `userid` int(255) DEFAULT NULL,
  `teamid` int(255) DEFAULT NULL,
  `filelimit` int(255) DEFAULT '0' COMMENT '文档公开：0个人文档 1 小组文档 2 公开文档',
  PRIMARY KEY (`fileid`),
  KEY `file_ibfk_1` (`userid`),
  KEY `file_ibfk_2` (`teamid`),
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `file_ibfk_2` FOREIGN KEY (`teamid`) REFERENCES `team` (`teamid`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('1', '附件1 计算机学院论文模版.doc', '1589360029526.doc', '2020-05-13', '32', null, '2');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `notid` int(255) NOT NULL AUTO_INCREMENT,
  `type` int(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(2047) NOT NULL,
  `sendtime` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`notid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', '0', '“蓝桥杯”校赛报名通知', '报名啦', '2020-05-13', '管理员');
INSERT INTO `notice` VALUES ('2', '0', '校领导带队检查指导校园疫情防控和返校复课准备工作', '本网讯】5月12日，学校党委书记周激流、校长余敏明分别带队深入航空港校区和龙泉校区一线检查指导校园疫情防控和返校复课准备工作。\n\n校领导一行先后来到两个校区大门口、教室、学生餐厅、发热预检分诊点、学生宿舍、临时隔离观察区等防疫重点场所进行了细致的走访和查验，听取了相关单位负责人工作汇报。', '2020-05-13', '管理员');
INSERT INTO `notice` VALUES ('3', '0', '最新通知', '一个最新通知', '2020-05-30', '管理员');

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `teamid` int(255) NOT NULL AUTO_INCREMENT,
  `comid` int(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL COMMENT '备注',
  `teaid` int(255) DEFAULT NULL,
  `teamname` varchar(255) NOT NULL,
  `state` int(255) NOT NULL DEFAULT '1' COMMENT '团队状态，1有效 2申请创建中 0失效 3申请失败',
  `captainid` int(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL COMMENT '宣传图片',
  PRIMARY KEY (`teamid`),
  KEY `comid` (`comid`),
  KEY `teaid` (`teaid`),
  KEY `captainid` (`captainid`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`comid`) REFERENCES `competition` (`comid`) ON DELETE SET NULL,
  CONSTRAINT `team_ibfk_2` FOREIGN KEY (`teaid`) REFERENCES `users` (`id`) ON DELETE SET NULL,
  CONSTRAINT `team_ibfk_3` FOREIGN KEY (`captainid`) REFERENCES `users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES ('10', '15', '欢迎大神加入', '32', '敏而好学组', '0', '46', '363866abdce547f3b2f71ab8b5b8ab12u=3866544121,2270221504&fm=26&gp=0.jpg');
INSERT INTO `team` VALUES ('11', '16', '欢迎大佬加入', '32', '敏而好学组', '1', '36', '50215cd8bdfd43e3ac284b4e5264da10u=3866544121,2270221504&fm=26&gp=0.jpg');
INSERT INTO `team` VALUES ('12', '16', '欢迎给位佛祖菩萨加入', '32', '如来佛祖', '1', '39', '298c43fdbc454831bda0c48421d9552fu=3719702404,1977991003&fm=74&app=80&f=JPEG&size=f121,121.jpg');

-- ----------------------------
-- Table structure for teamuser
-- ----------------------------
DROP TABLE IF EXISTS `teamuser`;
CREATE TABLE `teamuser` (
  `userid` int(255) NOT NULL,
  `teamid` int(255) DEFAULT NULL,
  `state` int(255) NOT NULL DEFAULT '1' COMMENT '组团状态 成为团员0 申请中1 取消申请2  拒绝申请3 团队过期4',
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  KEY `teamuser_ibfk_2` (`teamid`),
  CONSTRAINT `teamuser_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `teamuser_ibfk_2` FOREIGN KEY (`teamid`) REFERENCES `team` (`teamid`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teamuser
-- ----------------------------
INSERT INTO `teamuser` VALUES ('46', '10', '0', '1');
INSERT INTO `teamuser` VALUES ('36', '10', '4', '2');
INSERT INTO `teamuser` VALUES ('36', '10', '4', '3');
INSERT INTO `teamuser` VALUES ('36', '10', '0', '5');
INSERT INTO `teamuser` VALUES ('38', '10', '0', '11');
INSERT INTO `teamuser` VALUES ('37', '10', '4', '12');
INSERT INTO `teamuser` VALUES ('37', '10', '4', '14');
INSERT INTO `teamuser` VALUES ('37', '10', '0', '15');
INSERT INTO `teamuser` VALUES ('36', '11', '0', '16');
INSERT INTO `teamuser` VALUES ('38', '11', '2', '17');
INSERT INTO `teamuser` VALUES ('38', '11', '0', '18');
INSERT INTO `teamuser` VALUES ('39', '12', '0', '19');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(127) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `truename` varchar(255) DEFAULT NULL,
  `type` int(127) DEFAULT '1' COMMENT '1 学生 2 老师 3 组长 0 管理员',
  `describe1` varchar(255) DEFAULT NULL,
  `createby` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `state` int(127) NOT NULL DEFAULT '1',
  `email` varchar(255) DEFAULT NULL,
  `card` int(255) DEFAULT NULL,
  `teamid` int(255) DEFAULT NULL,
  `comid` int(255) DEFAULT NULL COMMENT '报名的竞赛',
  `comtpid` int(255) DEFAULT NULL COMMENT '指导的竞赛类型',
  `userfile` varchar(255) DEFAULT NULL COMMENT '个人介绍文档',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  KEY `teamid` (`teamid`),
  KEY `comid` (`comid`),
  KEY `comtpid` (`comtpid`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`teamid`) REFERENCES `team` (`teamid`) ON DELETE SET NULL,
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`comid`) REFERENCES `competition` (`comid`) ON DELETE SET NULL,
  CONSTRAINT `users_ibfk_3` FOREIGN KEY (`comtpid`) REFERENCES `competitiontype` (`comtpid`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '111111', '管理员', '0', '系统管理员', '管理员', null, '1', '627261542@qq.com', null, null, null, null, null);
INSERT INTO `users` VALUES ('32', 'laoshi001', 'qwe123', '谢芳1', '2', null, null, '2020-05-13', '1', '18145342@qq.com', '123001', null, null, '1', null);
INSERT INTO `users` VALUES ('33', 'laoshi002', 'qwe123', '梁建军', '2', null, null, '2020-05-13', '1', '21502215@qq.com', '123002', null, null, '2', null);
INSERT INTO `users` VALUES ('34', 'laoshi003', 'qwe123', '林树青', '2', null, null, '2020-05-13', '0', '21561235@qq.com', null, null, null, null, null);
INSERT INTO `users` VALUES ('35', 'laoshi004', 'qwe123', '吴广裕', '2', null, null, '2020-05-13', '1', '123542315@qq.com', '123004', null, null, '4', null);
INSERT INTO `users` VALUES ('36', 'yckdebaba', '123456qqq', '曹文杰', '1', null, null, '2020-05-13', '1', '1039334471@qq.com', '2016051232', '11', '16', null, null);
INSERT INTO `users` VALUES ('37', 'xueshen001', 'qwe123', '叶子鹏', '1', null, null, '2020-05-14', '1', '627261542@qq.com', '2016051201', null, null, null, 'be9df47f4e6140e09aa31af6e9a2a30307z888piCmgw-已转档.pdf');
INSERT INTO `users` VALUES ('38', 'xueshen002', 'qwe123', '冯美欣', '1', '', '', '2020-05-14', '1', '627261542@qq.com', '2016051202', '11', '16', null, 'ba8fd1d91193471bb63c4f4b2ad195e958e888piCN8W-已转档.pdf');
INSERT INTO `users` VALUES ('39', 'xueshen003', 'qwe123', '吴舒婷', '1', '', '', '2020-05-14', '1', '627261542@qq.com', '2016051203', '12', '16', null, '4d083ccd0ca14ee5aca25e6c89e30b7758e888piCN8W-已转档.pdf');
INSERT INTO `users` VALUES ('40', 'xueshen004', 'qwe123', '王丽萍', '1', null, null, null, '1', '123543123@qq.com', '2016051204', null, null, null, '1a454934099a41f39ee0e8dfe1be8c8964C888piC5jN-已转档.pdf');
INSERT INTO `users` VALUES ('41', 'xueshen005', 'qwe123', '王玉杰', '1', null, null, null, '1', '123543123@qq.com', '2016051205', null, null, null, 'c33d6486f4634c1ea5c0d772cf27018390Z888piCe9V-已转档.pdf');
INSERT INTO `users` VALUES ('42', 'xueshen006', 'qwe123', '叶问', '1', null, null, null, '1', '423542131@qq.com', '2016051206', null, null, null, 'bd149c912b0847468560765137d0c31695W888piCwPZ-已转档.pdf');
INSERT INTO `users` VALUES ('43', 'xueshen007', 'qwe123', '李小龙', '1', null, null, null, '1', '124538721@qq.com', '2016051207', null, null, null, 'fb2ecce96f1e43a5978622b1b630b39a225888piCMd9-已转档.pdf');
INSERT INTO `users` VALUES ('44', 'xueshen008', 'qwe123', '李小璐', '1', null, null, null, '1', '1215321@qq.com', '2016051208', null, null, null, '9c1eae50a4e8499890221b1b7a1adc7c502888piC7th-已转档 (1).pdf');
INSERT INTO `users` VALUES ('45', 'xueshen009', 'qwe123', '张迪伟', '1', null, null, null, '1', '123543123@qq.com', '2016051209', null, null, null, '350780b4de854e0c9e368be39a5e3b9d502888piC7th-已转档.pdf');
INSERT INTO `users` VALUES ('46', 'xueshen010', 'qwe123', '牛泽达', '1', null, null, null, '1', '423542131@qq.com', '2016051210', null, null, null, 'afa91009244048a1964e6e41dfb07ce3727888piCWhI-已转档.pdf');
INSERT INTO `users` VALUES ('47', 'xueshen011', 'qwe123', '张智宇', '1', null, null, null, '1', '124538721@qq.com', '2016051211', null, null, null, null);
INSERT INTO `users` VALUES ('48', 'xueshen012', 'qwe123', '万虎杰', '1', null, null, null, '1', '1215321@qq.com', '2016051212', null, null, null, null);

-- ----------------------------
-- Procedure structure for checkcom
-- ----------------------------
DROP PROCEDURE IF EXISTS `checkcom`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkcom`(IN `nowtime` varchar(255))
BEGIN
	#Routine body goes here...
  
 UPDATE team SET state = 0
 WHERE comid in (SELECT comid from competition WHERE comtime < nowtime);
	

UPDATE teamuser SET state = 4
 WHERE teamid in (SELECT teamid from team WHERE comid in(SELECT comid from competition WHERE comtime < nowtime))AND state in ( 1,2,3);

	UPDATE users SET comid = NULL
	WHERE  comid in(SELECT comid from competition WHERE comtime < nowtime);

  UPDATE users SET teamid = NULL
	WHERE  teamid in (SELECT teamid from team WHERE comid in(SELECT comid from competition WHERE comtime < nowtime));
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for plus1inout
-- ----------------------------
DROP PROCEDURE IF EXISTS `plus1inout`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `plus1inout`(IN `arg` int,OUT `res` int)
BEGIN
	#Routine body goes here...
	SET res = arg + 1;
END
;;
DELIMITER ;
