/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50743
Source Host           : localhost:3306
Source Database       : university

Target Server Type    : MYSQL
Target Server Version : 50743
File Encoding         : 65001

Date: 2024-06-03 19:49:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz` (
  `clazz_id` varchar(255) NOT NULL,
  `clazz_name` varchar(255) DEFAULT NULL,
  `dept_id` int(50) DEFAULT NULL,
  PRIMARY KEY (`clazz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES ('1001001', '计算机科学与技术1班', '1001');
INSERT INTO `clazz` VALUES ('1001002', '计算机科学与技术2班', '1001');
INSERT INTO `clazz` VALUES ('1001003', '商务英语1班', '1001');
INSERT INTO `clazz` VALUES ('1002001', '石油工程1班', '1002');
INSERT INTO `clazz` VALUES ('1003001', '经济管理1班', '1003');
INSERT INTO `clazz` VALUES ('1003002', '文法1班', '1003');
INSERT INTO `clazz` VALUES ('1004001', '化学工程1班', '1004');
INSERT INTO `clazz` VALUES ('1005001', '智能制造1班', '1005');
INSERT INTO `clazz` VALUES ('1005002', '控制工程1班', '1005');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `menuCode` varchar(8) DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
  `menuComponent` varchar(200) DEFAULT NULL,
  `menuIcon` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '001', '学生管理', '1', '', 'Student', '0,1', 'student/StudentManager', 'el-icon-s-custom');
INSERT INTO `menu` VALUES ('2', '002', '课程管理', '1', '', 'Course', '0', 'course/CourseManager', 'el-icon-s-management');
INSERT INTO `menu` VALUES ('3', '003', '课程成绩', '1', '', 'Score', '2', 'score/ScoreStudent', 'el-icon-document');
INSERT INTO `menu` VALUES ('4', '004', '选课系统', '1', '', 'CourseSelection', '2', 'course/CourseSelection', 'el-icon-document');
INSERT INTO `menu` VALUES ('5', '005', '课表查询', '1', '', 'ScheduleQuery', '2', 'schedule/ScheduleQuery', 'el-icon-document');
INSERT INTO `menu` VALUES ('6', '006', '课表管理', '1', '', 'ScheduleManager', '0,1', 'schedule/ScheduleManager', 'el-icon-document');

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc` (
  `sno` varchar(255) NOT NULL,
  `cno` int(11) NOT NULL,
  `score` double(10,0) DEFAULT NULL,
  PRIMARY KEY (`sno`,`cno`),
  KEY `cno` (`cno`),
  CONSTRAINT `cno` FOREIGN KEY (`cno`) REFERENCES `t_course` (`cno`),
  CONSTRAINT `sno` FOREIGN KEY (`sno`) REFERENCES `t_student` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sc
-- ----------------------------
INSERT INTO `sc` VALUES ('2306301001', '12011001', '99');
INSERT INTO `sc` VALUES ('2306302104', '1101002', '85');
INSERT INTO `sc` VALUES ('2306302222', '1101001', '99');
INSERT INTO `sc` VALUES ('2306302222', '1101002', '100');
INSERT INTO `sc` VALUES ('2306302222', '1101003', '96');
INSERT INTO `sc` VALUES ('2306302222', '1101004', '98');
INSERT INTO `sc` VALUES ('2306302222', '11011005', null);
INSERT INTO `sc` VALUES ('2306302225', '1101001', null);

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `schedule_id` varchar(255) NOT NULL,
  `xq` int(11) DEFAULT NULL,
  `start` int(11) DEFAULT NULL,
  `end` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `cno` int(50) DEFAULT NULL,
  `clazz_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES ('1001', '1', '1', '2', '1-2节/4教-401室', '1101001', '1001001');
INSERT INTO `schedule` VALUES ('1002', '1', '9', '12', '9-12节/4教-104室', '11011000', '1001001');
INSERT INTO `schedule` VALUES ('1003', '2', '3', '4', '3-4节/4教-404室', '11011005', '1001001');
INSERT INTO `schedule` VALUES ('1004', '3', '1', '2', '1-2节/2教-203室', '1101002', '1001001');
INSERT INTO `schedule` VALUES ('1005', '4', '3', '4', '3-4节/4教-106室', '1101004', '1001001');
INSERT INTO `schedule` VALUES ('1006', '5', '5', '6', '5-6节/2教-303室', '1101003', '1001001');
INSERT INTO `schedule` VALUES ('1007', '2', '7', '8', '5-6节/2教-303室', '1101003', '1001002');
INSERT INTO `schedule` VALUES ('6fa5a4f8ea8d63b7c1425b98c8f15ab8', '4', '9', '10', '9-10节/4教-203室', '1101004', '1001001');
INSERT INTO `schedule` VALUES ('82431c4ffbae24230e4ced80565ee0f1', '1', '3', '4', '2-4节/4教-405室', '1101002', '1001002');
INSERT INTO `schedule` VALUES ('90124179ea374557f9b294d3f03755e8', '4', '1', '2', '1-2节/4教-103室', '1101003', '1001002');
INSERT INTO `schedule` VALUES ('b41c76d895ff76a0049f3685c12512c6', '2', '7', '8', '7-8节/2教-203', '1101003', '1001001');

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `cno` int(50) NOT NULL,
  `cname` varchar(255) DEFAULT NULL,
  `dept_id` int(255) DEFAULT NULL,
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('1101001', '数据结构', '1001');
INSERT INTO `t_course` VALUES ('1101002', '操作系统', '1001');
INSERT INTO `t_course` VALUES ('1101003', '计算机组成原理', '1001');
INSERT INTO `t_course` VALUES ('1101004', '计算机网络', '1001');
INSERT INTO `t_course` VALUES ('11011000', '形势与政策', '1001');
INSERT INTO `t_course` VALUES ('11011005', '软件工程', '1001');
INSERT INTO `t_course` VALUES ('12011001', '文学概论', '1001');
INSERT INTO `t_course` VALUES ('12011002', '语言学概论', '1001');
INSERT INTO `t_course` VALUES ('12011003', '古代汉语', '1001');
INSERT INTO `t_course` VALUES ('12021001', '英语精读', '1001');
INSERT INTO `t_course` VALUES ('12021002', '英语写作', '1001');
INSERT INTO `t_course` VALUES ('21011001', '石油工程导论', '1002');
INSERT INTO `t_course` VALUES ('21011002', '流体力学', '1002');
INSERT INTO `t_course` VALUES ('21011003', '采油工程', '1002');
INSERT INTO `t_course` VALUES ('21011004', '工程力学', '1002');

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `dept_id` int(50) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES ('1001', '大数据与基础科学学院');
INSERT INTO `t_dept` VALUES ('1002', '石油工程学院');
INSERT INTO `t_dept` VALUES ('1003', '经济管理与文法学院');
INSERT INTO `t_dept` VALUES ('1004', '化学工程学院');
INSERT INTO `t_dept` VALUES ('1005', '智能制造与控制工程学院');

-- ----------------------------
-- Table structure for t_major
-- ----------------------------
DROP TABLE IF EXISTS `t_major`;
CREATE TABLE `t_major` (
  `major_id` int(50) NOT NULL AUTO_INCREMENT,
  `major_name` varchar(255) DEFAULT NULL,
  `dept_id` int(50) DEFAULT NULL,
  PRIMARY KEY (`major_id`),
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `dept_id` FOREIGN KEY (`dept_id`) REFERENCES `t_dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5103 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_major
-- ----------------------------
INSERT INTO `t_major` VALUES ('1101', '计算机科学与技术', '1001');
INSERT INTO `t_major` VALUES ('1102', '物联网工程', '1001');
INSERT INTO `t_major` VALUES ('1201', '汉语言文学', '1001');
INSERT INTO `t_major` VALUES ('1202', '英语专业', '1001');
INSERT INTO `t_major` VALUES ('2101', '石油工程', '1002');
INSERT INTO `t_major` VALUES ('2102', '海洋油气工程', '1002');
INSERT INTO `t_major` VALUES ('3101', '法学', '1003');
INSERT INTO `t_major` VALUES ('3102', '财务管理', '1003');
INSERT INTO `t_major` VALUES ('4101', '应用化学', '1004');
INSERT INTO `t_major` VALUES ('4102', '化学工程与工艺', '1004');
INSERT INTO `t_major` VALUES ('5101', '机械设计制造及其自动化', '1005');
INSERT INTO `t_major` VALUES ('5102', '电气工程及其自动化', '1005');

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `sno` varchar(255) NOT NULL,
  `sname` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `dept_id` int(50) DEFAULT NULL,
  `major_id` int(11) DEFAULT NULL,
  `clazz_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sno`),
  KEY `student_dept` (`dept_id`),
  KEY `student_major` (`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('2306301001', '阿尼亚', '18', '女', '大一', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306301002', '苦荞茶', '23', '男', '大二', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302001', '海猫', '132', '女', '大三', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302102', '张云龙', '22', '男', '大二', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302103', '王鑫蕊', '23', '女', '大一', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302104', '朱恒鑫', '22', '男', '大三', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302106', '姜尊浩', '22', '男', '大二', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302107', '张静', '21', '女', '大四', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302108', '李淑悦', '24', '女', '大一', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302109', '韩士达', '20', '男', '大一', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302110', '金明毅', '23', '男', '大一', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302111', '陆瑶', '20', '女', '大一', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302112', '何长波', '20', '男', '大四', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302113', '张锦', '20', '男', '大三', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302114', '刘沿海', '20', '男', '大二', '1002', '2101', '1002001');
INSERT INTO `t_student` VALUES ('2306302115', '陈雨萌', '25', '女', '大一', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306302116', '佴子轩', '25', '男', '大一', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306302117', '齐秀娟', '20', '女', '大二', '1001', '1102', '1001001');
INSERT INTO `t_student` VALUES ('2306302118', '时龙奥', '20', '男', '大一', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302119', '刘国锋', '22', '男', '大三', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302120', '任鸣飞', '20', '男', '大二', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302121', '蒋亚洲', '22', '男', '大一', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302122', '付敦清', '20', '男', '大一', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302123', '张晓莲', '22', '女', '大一', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302124', '刘廷斌', '20', '男', '大二', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302125', '高永琦', '21', '男', '大一', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302126', '曹雅琳', '20', '女', '大一', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302127', '黄贤军', '25', '男', '大三', '1003', '3101', '1003001');
INSERT INTO `t_student` VALUES ('2306302128', '夏冬阳', '20', '男', '大四', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302129', '衣姝蓉', '20', '女', '大一', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302130', '陈道远', '22', '男', '大一', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302131', '王宏祥', '22', '男', '大四', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302201', '高铭', '20', '女', '大二', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302202', '王志宇', '22', '男', '大四', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302203', '燕鸿熙', '20', '男', '大一', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302204', '滕光磊', '21', '男', '大三', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302205', '赵如慧', '20', '女', '大三', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302206', '戴润浩', '21', '男', '大三', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302207', '李绪航', '20', '男', '大一', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302208', '梁学鑫', '25', '男', '大四', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302209', '巴鹏超', '20', '男', '大二', '1004', '4101', '1004001');
INSERT INTO `t_student` VALUES ('2306302210', '逯运昌', '21', '男', '大三', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302211', '孙俊峰', '20', '男', '大四', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302212', '赵师全', '20', '男', '大三', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302213', '熊凡舟', '25', '男', '大四', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302214', '龚亚', '20', '女', '大三', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302215', '秦炜帅', '20', '男', '大一', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302216', '戴艺伟', '20', '女', '大一', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302217', '贾丽丽', '22', '女', '大一', '1001', '1102', '1001001');
INSERT INTO `t_student` VALUES ('2306302218', '李程昊', '20', '男', '大一', '1001', '1102', '1001001');
INSERT INTO `t_student` VALUES ('2306302219', '高子涵', '20', '女', '大四', '1001', '1102', '1001001');
INSERT INTO `t_student` VALUES ('2306302220', '赵敬奇', '22', '男', '大二', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302221', '吴英昊', '25', '男', '大一', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302222', '苗文狄', '22', '男', '大一', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302223', '孟亚宁', '20', '女', '大三', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302224', '宋凯月', '20', '女', '大二', '1001', '1201', '1001001');
INSERT INTO `t_student` VALUES ('2306302225', '孙绍坤', '25', '女', '大四', '1001', '1201', '1001001');
INSERT INTO `t_student` VALUES ('2306302226', '高辰昊', '20', '男', '大一', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302227', '于朝阳', '20', '男', '大三', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302228', '邱洋', '20', '男', '大三', '1004', '4102', '1004001');
INSERT INTO `t_student` VALUES ('2306302229', '高凯', '25', '男', '大一', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302230', '蒋仪伟', '20', '男', '大一', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302231', '贾腾腾', '20', '女', '大一', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302301', '张晨', '22', '女', '大四', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302302', '李进', '20', '男', '大三', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302303', '刘天有', '22', '男', '大二', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302304', '王佳琦', '22', '女', '大二', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302305', '付宇钧', '22', '女', '大四', '1005', '5101', '1005001');
INSERT INTO `t_student` VALUES ('2306302306', '徐乾', '21', '男', '大三', '1005', '5102', '1005001');
INSERT INTO `t_student` VALUES ('2306302307', '谢晴晴', '20', '女', '大四', '1005', '5102', '1005001');
INSERT INTO `t_student` VALUES ('2306302308', '陈榕贤', '21', '男', '大三', '1001', '1201', '1001001');
INSERT INTO `t_student` VALUES ('2306302309', '吴连强', '20', '男', '大三', '1001', '1202', '1001001');
INSERT INTO `t_student` VALUES ('2306302310', '冯凯', '21', '男', '大四', '1001', '1202', '1001001');
INSERT INTO `t_student` VALUES ('2306302311', '杨光', '20', '男', '大一', '1001', '1202', '1001001');
INSERT INTO `t_student` VALUES ('2306302312', '朱紫晨', '20', '男', '大一', '1005', '5102', '1005001');
INSERT INTO `t_student` VALUES ('2306302313', '王倩', '22', '女', '大四', '1005', '5102', '1005001');
INSERT INTO `t_student` VALUES ('2306302314', '陈政熙', '20', '女', '大四', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302315', '李燕霞', '20', '女', '大二', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302316', '彭治锜', '22', '男', '大四', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302317', '李晨曦', '20', '男', '大二', '1003', '3102', '1003001');
INSERT INTO `t_student` VALUES ('2306302318', '阮心茹', '20', '女', '大二', '1005', '5102', '1005001');
INSERT INTO `t_student` VALUES ('2306302319', '刘岳洋', '22', '女', '大三', '1005', '5102', '1005001');
INSERT INTO `t_student` VALUES ('2306302320', '杨韩', '20', '女', '大二', '1005', '5102', '1005001');
INSERT INTO `t_student` VALUES ('2306302321', '黄志民', '20', '男', '大一', '1001', '1202', '1001001');
INSERT INTO `t_student` VALUES ('2306302322', '赵可瑾', '20', '男', '大四', '1001', '1202', '1001001');
INSERT INTO `t_student` VALUES ('2306302323', '傅树元', '20', '男', '大一', '1001', '1202', '1001001');
INSERT INTO `t_student` VALUES ('2306302324', '常春驰', '22', '女', '大一', '1001', '1202', '1001001');
INSERT INTO `t_student` VALUES ('2306302325', '苏伯年', '20', '男', '大二', '1001', '1101', '1001001');
INSERT INTO `t_student` VALUES ('2306302326', '孙家祥', '22', '男', '大四', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306302327', '刘广旭', '20', '男', '大一', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306302328', '石珈源', '25', '男', '大四', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306302329', '李浩冉', '20', '男', '大二', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306302330', '刘建宇', '20', '男', '大一', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306302331', '侯鑫凤', '20', '女', '大一', '1002', '2102', '1002001');
INSERT INTO `t_student` VALUES ('2306303306', '阿米娅', '19', '女', '大一', '1003', '3102', '1003001');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isValid` varchar(4) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sno_user` (`isValid`)
) ENGINE=InnoDB AUTO_INCREMENT=202406 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('202400', 'root', '123456', 'root@qq.com', 'Y', '0');
INSERT INTO `t_user` VALUES ('202401', 'admin', '123456', 'admin@qq.com', 'Y', '1');
INSERT INTO `t_user` VALUES ('202402', '张三', '123456', 'zhang@qq.com', 'Y', '2');
INSERT INTO `t_user` VALUES ('202403', '李四', '12345', 'li@qq.com', 'Y', '2');
INSERT INTO `t_user` VALUES ('202405', '王五', '123456', 'wang@qq.com', 'Y', '2');
