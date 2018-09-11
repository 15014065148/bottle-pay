/*
 Navicat Premium Data Transfer

 Source Server         : test环境
 Source Server Type    : MySQL
 Source Server Version : 50629
 Source Host           : 202.61.86.162:8066
 Source Schema         : bottle_pay

 Target Server Type    : MySQL
 Target Server Version : 50629
 File Encoding         : 65001

 Date: 11/09/2018 14:13:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for balance_record
-- ----------------------------
DROP TABLE IF EXISTS `balance_record`;
CREATE TABLE `balance_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(20) DEFAULT NULL COMMENT '用户id',
  `payTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '支付时间',
  `payType` int(2) DEFAULT NULL COMMENT '交易类型 1购买模板 0充值',
  `payTypeId` int(11) DEFAULT NULL COMMENT '\"BC\"',
  `balanceChange` decimal(12, 2) DEFAULT 0.00 COMMENT '余额变动',
  `balance` decimal(12, 2) DEFAULT 0.00 COMMENT '用户余额',
  `payStatus` int(2) DEFAULT NULL COMMENT '状态 1成功 0未成功',
  `createTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 347 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bank
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankName` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bankCode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bankLog` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bill_detail
-- ----------------------------
DROP TABLE IF EXISTS `bill_detail`;
CREATE TABLE `bill_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(20) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `templateId` int(20) DEFAULT NULL COMMENT '模板id',
  `money` decimal(9, 2) DEFAULT NULL COMMENT '支付金额',
  `discountsCharge` decimal(12, 2) DEFAULT NULL COMMENT '优惠金额',
  `sets` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '套餐',
  `setDetail` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '套餐详情',
  `payType` int(2) DEFAULT NULL COMMENT '支付类型 1余额支付',
  `imagePath` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '产品图片地址',
  `urlPath` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '产品链接地址',
  `status` int(2) DEFAULT NULL COMMENT '订单状态 1、已支付 0、未支付',
  `siteNo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '站点编号',
  `siteName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '站点名称',
  `createTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `isSysPay` int(2) DEFAULT NULL COMMENT '是否后台支付 0否 1是',
  `domain` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '域名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vendorId` int(11) DEFAULT NULL COMMENT '供应商Id',
  `merNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商户号(第三方给的)',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账户名称',
  `account` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收款账户',
  `code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付编码',
  `suportMachine` tinyint(4) DEFAULT NULL COMMENT '支持设备 1:pc  2:移动 3: pc + 移动',
  `maxLimit` int(11) DEFAULT NULL COMMENT '单笔存款最大值',
  `maxLimitDaily` int(11) DEFAULT NULL COMMENT '单日最大限额',
  `minLimit` int(11) DEFAULT NULL COMMENT '单笔存款最小值',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账户描述',
  `isEnable` tinyint(4) NOT NULL DEFAULT 2 COMMENT '是否启用 1启用 0禁用',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `createUser` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `createTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyUser` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  `modifyTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `payCode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第三方支付中這個支付渠道的代號',
  `urlMethod` int(2) NOT NULL COMMENT '是否二维码 0不是, 1是',
  `logoUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付方式logo',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '供应商支付渠道' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_bank
-- ----------------------------
DROP TABLE IF EXISTS `channel_bank`;
CREATE TABLE `channel_bank`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankId` int(11) DEFAULT NULL COMMENT '银行Id',
  `channelId` int(11) DEFAULT NULL COMMENT '渠道Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_record
-- ----------------------------
DROP TABLE IF EXISTS `pay_record`;
CREATE TABLE `pay_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fee` decimal(12, 2) DEFAULT NULL COMMENT '金额',
  `handlingCharge` decimal(12, 2) DEFAULT NULL COMMENT '手续费',
  `discountsCharge` decimal(12, 2) DEFAULT NULL COMMENT '优惠金额',
  `outTradeNo` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单号',
  `customerTradeNo` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '盘子订单号',
  `userId` int(18) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `userBalance` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户余额',
  `status` int(2) DEFAULT NULL COMMENT '状态 1成功 0未成功',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '充值描述',
  `channelId` int(11) DEFAULT NULL COMMENT '支付方式',
  `ip` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ip',
  `createTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `payTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '实际支付时间',
  `payError` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '支付成功或失败信息',
  `isSysPay` int(2) DEFAULT NULL COMMENT '是否后台支付 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 383 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for s_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `s_sys_config`;
CREATE TABLE `s_sys_config`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `groups` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `keys` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `values` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_name
-- ----------------------------
DROP TABLE IF EXISTS `tbl_name`;
CREATE TABLE `tbl_name`  (
  `ID` int(11) DEFAULT NULL,
  `mSize` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_balance
-- ----------------------------
DROP TABLE IF EXISTS `user_balance`;
CREATE TABLE `user_balance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(20) DEFAULT NULL COMMENT '用户id',
  `balance` decimal(12, 2) DEFAULT 0.00 COMMENT '用户余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 307 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vendor
-- ----------------------------
DROP TABLE IF EXISTS `vendor`;
CREATE TABLE `vendor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vendorName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付机构',
  `vendorCode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商code 不可变涉及实际代码和实际类名一致',
  `returnUrl` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '返回url',
  `notifyUrl` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '调度url',
  `isEnable` tinyint(4) DEFAULT NULL COMMENT '是否启用 1启用 0不启用',
  `password` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付密钥',
  `createUser` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `createTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyUser` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改人',
  `modifyTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
