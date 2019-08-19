/*
 Navicat Premium Data Transfer

 Source Server         : moknpay-dev
 Source Server Type    : MySQL
 Source Server Version : 50619
 Source Host           : mysql.moknpay.com:3306
 Source Schema         : mokn_istio

 Target Server Type    : MySQL
 Target Server Version : 50619
 File Encoding         : 65001

 Date: 19/08/2019 22:54:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for istio_route_version
-- ----------------------------
DROP TABLE IF EXISTS `istio_route_version`;
CREATE TABLE `istio_route_version`  (
  `sysno` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version_type` tinyint(1) NOT NULL COMMENT '发布类型：1:直接发布2:流量权重3:金丝雀',
  `version_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '版本代号',
  `version_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '版本标题',
  `version_remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '版本说明',
  `created_at` datetime(0) NOT NULL COMMENT '创建时间',
  `deploy_at` datetime(0) NULL DEFAULT NULL COMMENT '部署时间',
  `release_at` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `rollback_at` datetime(0) NULL DEFAULT NULL COMMENT '回滚时间',
  `version_status` tinyint(1) NOT NULL COMMENT '版本状态：\r\n1创建\r\n2部署中\r\n3部署成功\r\n4部署失败\r\n5上线中\r\n6上线成功\r\n7上线失败\r\n8回滚中\r\n9回滚成功\r\n10回滚失败\r\n11取消',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`sysno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for istio_route_version_item
-- ----------------------------
DROP TABLE IF EXISTS `istio_route_version_item`;
CREATE TABLE `istio_route_version_item`  (
  `sysno` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version_sysno` bigint(20) NOT NULL COMMENT '所属版本主键',
  `version_type` tinyint(1) NOT NULL COMMENT '发布类型，冗余version表字段',
  `namespace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '命名空间',
  `name_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '应用名称',
  `subset_old` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '旧版本号subset',
  `subset_new` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '新版本号subset',
  `weight` int(11) NOT NULL DEFAULT 0 COMMENT '新版本流量权重',
  `old_json` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '旧版json',
  `created_at` datetime(0) NOT NULL COMMENT '创建时间',
  `deploy_at` datetime(0) NULL DEFAULT NULL COMMENT '部署时间',
  `release_at` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `rollback_at` datetime(0) NULL DEFAULT NULL COMMENT '回滚时间',
  `version_status` tinyint(1) NOT NULL COMMENT '版本状态：\r\n1创建\r\n2部署中\r\n3部署成功\r\n4部署失败\r\n5上线中\r\n6上线成功\r\n7上线失败\r\n8回滚中\r\n9回滚成功\r\n10回滚失败\r\n11取消',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `http_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `http_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`sysno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for k8s_conf
-- ----------------------------
DROP TABLE IF EXISTS `k8s_conf`;
CREATE TABLE `k8s_conf`  (
  `sysno` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `config_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '配置类型：configmap,secret',
  `namespace` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '命名空间',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '配置名称',
  `currency_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '当前使用编号',
  `currency_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '当前类型：create创建update更新rollback回滚',
  `created_at` datetime(0) NOT NULL COMMENT '创建时间',
  `memo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sysno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for k8s_conf_record
-- ----------------------------
DROP TABLE IF EXISTS `k8s_conf_record`;
CREATE TABLE `k8s_conf_record`  (
  `sysno` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `conf_sysno` bigint(20) NOT NULL COMMENT 'conf表主键',
  `record_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '记录编号',
  `save_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '保存类型：create创建update更新rollback回滚',
  `conf_data` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '配置数据',
  `record_status` tinyint(1) NOT NULL COMMENT '执行状态：1待执行2执行成功3执行失败4取消',
  `created_at` datetime(0) NOT NULL COMMENT '创建时间',
  `namespace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '命名空间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  PRIMARY KEY (`sysno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for resource_template
-- ----------------------------
DROP TABLE IF EXISTS `resource_template`;
CREATE TABLE `resource_template`  (
  `sysno` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `resource` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `template` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '模板',
  PRIMARY KEY (`sysno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of resource_template
-- ----------------------------
INSERT INTO `resource_template` VALUES (1, 'VirtualService_Default', 'VirtualService-路由-默认', '{\r\n	\"apiVersion\": \"networking.istio.io/v1alpha3\",\r\n	\"kind\": \"VirtualService\",\r\n	\"metadata\": {\r\n		\"name\": \"{{.HOST}}\",\r\n		\"resourceVersion\": \"{{.RESOURCE_VERSION}}\",\r\n		\"labels\":{\r\n		\"istioVersion\": \"{{.ISTIO_VERSION}}\",\r\n		\"istioVersionType\": \"{{.ISTIO_VERSION_TYPE}}\"\r\n		}\r\n	},\r\n	\"spec\": {\r\n		\"hosts\": [\r\n			\"{{.HOST}}\"\r\n		],\r\n		\"http\": [{\r\n			\"route\": [{\r\n				\"destination\": {\r\n					\"host\": \"{{.HOST}}\",\r\n					\"subset\": \"{{.SUBSET}}\"\r\n				}\r\n			}]\r\n		}]\r\n	}\r\n}');
INSERT INTO `resource_template` VALUES (2, 'VirtualService_Canary', 'VirtualService-路由-金丝雀', '{\r\n	\"apiVersion\": \"networking.istio.io/v1alpha3\",\r\n	\"kind\": \"VirtualService\",\r\n	\"metadata\": {\r\n		\"name\": \"{{.HOST}}\",\r\n		\"resourceVersion\": \"{{.RESOURCE_VERSION}}\",\r\n		\"labels\":{\r\n		\"istioVersion\": \"{{.ISTIO_VERSION}}\",\r\n		\"istioVersionType\": \"{{.ISTIO_VERSION_TYPE}}\"\r\n		}\r\n	},\r\n	\"spec\": {\r\n		\"hosts\": [\r\n            \"{{.HOST}}\"\r\n        ],\r\n		\"http\": [{\r\n				\"match\": [{\r\n					\"headers\": {\r\n						\"{{.HTTP_HEADER_KEY}}\": {\r\n							\"exact\": \"{{.HTTP_HEADER_VALUE}}\"\r\n						}\r\n					}\r\n				}],\r\n				\"route\": [{\r\n					\"destination\": {\r\n						\"host\": \"{{.HOST}}\",\r\n						\"subset\": \"{{.SUBSET_CANARY}}\"\r\n					}\r\n				}]\r\n			},\r\n			{\r\n				\"route\": [{\r\n					\"destination\": {\r\n						\"host\": \"{{.HOST}}\",\r\n						\"subset\": \"{{.SUBSET_DEFAULT}}\"\r\n					}\r\n				}]\r\n			}\r\n		]\r\n	}\r\n}');
INSERT INTO `resource_template` VALUES (3, 'VirtualService_Weight', 'VirtualService-路由-流量权重', '{\r\n	\"apiVersion\": \"networking.istio.io/v1alpha3\",\r\n	\"kind\": \"VirtualService\",\r\n	\"metadata\": {\r\n		\"name\": \"{{.HOST}}\",\r\n		\"resourceVersion\": \"{{.RESOURCE_VERSION}}\",\r\n		\"labels\":{\r\n		\"istioVersion\": \"{{.ISTIO_VERSION}}\",\r\n		\"istioVersionType\": \"{{.ISTIO_VERSION_TYPE}}\"\r\n		}\r\n	},\r\n	\"spec\": {\r\n		\"hosts\": [\r\n            \"istio-service-user\"\r\n        ],\r\n		\"http\": [{\r\n			\"route\": [{\r\n					\"destination\": {\r\n						\"host\": \"{{.HOST}}\",\r\n						\"subset\": \"{{.SUBSET_A}}\"\r\n					},\r\n					\"weight\": {{.WEIGHT_A}}\r\n				},\r\n				{\r\n					\"destination\": {\r\n						\"host\": \"{{.HOST}}\",\r\n						\"subset\": \"{{.SUBSET_B}}\"\r\n					},\r\n					\"weight\": {{.WEIGHT_B}}\r\n				}\r\n			]\r\n		}]\r\n	}\r\n}');
INSERT INTO `resource_template` VALUES (4, 'DestinationRule_Create_Default', 'DestinationRule-创建-默认', '{\r\n    \"apiVersion\": \"networking.istio.io/v1alpha3\",\r\n    \"kind\": \"DestinationRule\",\r\n    \"metadata\": {\r\n        \"name\": \"{{.HOST}}\",\r\n        \"namespace\": \"default\"\r\n    },\r\n    \"spec\": {\r\n        \"host\": \"{{.HOST}}\",\r\n        \"subsets\": [\r\n            {\r\n                \"labels\": {\r\n                    \"version\": \"{{.SUBSET}}\"\r\n                },\r\n                \"name\": \"{{.SUBSET}}\"\r\n            }\r\n        ]\r\n    }\r\n}');
INSERT INTO `resource_template` VALUES (5, 'VirtualService_Create_Default', 'VirtualService-创建-默认', '{\r\n    \"apiVersion\":\"networking.istio.io/v1alpha3\",\r\n    \"kind\":\"VirtualService\",\r\n    \"metadata\":{\r\n        \"name\":\"{{.NAME}}\",\r\n        \"labels\":{\r\n            \"istioVersion\":\"{{.ISTIO_VERSION}}\",\r\n            \"istioVersionType\":\"{{.ISTIO_VERSION_TYPE}}\"\r\n        }\r\n    },\r\n    \"spec\":{\r\n        \"hosts\":[\r\n            \"{{.HOST}}\"\r\n        ],\r\n        \"http\":[\r\n            {\r\n                \"route\":[\r\n                    {\r\n                        \"destination\":{\r\n                            \"host\":\"{{.HOST}}\",\r\n                            \"subset\":\"{{.SUBSET}}\"\r\n                        }\r\n                    }\r\n                ]\r\n            }\r\n        ]\r\n    }\r\n}');
INSERT INTO `resource_template` VALUES (6, 'VirtualService_Create_Gateway', 'VirtualService-创建-网关', '{\r\n    \"apiVersion\":\"networking.istio.io/v1alpha3\",\r\n    \"kind\":\"VirtualService\",\r\n    \"metadata\":{\r\n        \"name\":\"{{.NAME}}\",\r\n		\"labels\":{\r\n		\"istioVersionType\": \"{{.ISTIO_VERSION_TYPE}}\"\r\n		}\r\n    },\r\n    \"spec\":{\r\n        \"hosts\":[\r\n            \"{{.HOST}}\"\r\n        ],\r\n        \"gateways\":[\r\n            \"{{.GATEWAY_NAME}}\"\r\n        ],\r\n        \"http\":[\r\n            {\r\n                \"route\":[\r\n                    {\r\n                        \"destination\":{\r\n                            \"host\":\"{{.ROUTE_HOST}}\",\r\n                            \"port\":{\r\n                                \"number\":{{.ROUTE_PORT}}\r\n                            }\r\n                        }\r\n                    }\r\n                ]\r\n            }\r\n        ]\r\n    }\r\n}');
INSERT INTO `resource_template` VALUES (11, 'Gateway_Create_Default', 'Gateway-创建-默认', '{\r\n    \"apiVersion\": \"networking.istio.io/v1alpha3\",\r\n    \"kind\": \"Gateway\",\r\n    \"metadata\": {\r\n        \"name\": \"{{.NAME}}\"\r\n    },\r\n    \"spec\": {\r\n        \"selector\": {\r\n            \"istio\": \"ingressgateway\"\r\n        },\r\n        \"servers\": [\r\n            {\r\n                \"hosts\": [\r\n                    \"{{.HOST}}\"\r\n                ],\r\n                \"port\": {\r\n                    \"name\": \"{{.PORT_NAME}}\",\r\n                    \"number\": {{.PORT_PORT}},\r\n                    \"protocol\": \"{{.PROT_PROTOCOL}}\"\r\n                }\r\n            }\r\n        ]\r\n    }\r\n}');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `sysno` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录帐号',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色：admin管理员，release操作员，audit审计员',
  `created_at` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`sysno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '123456', '管理员', 'admin', '2019-08-15 14:27:55');

SET FOREIGN_KEY_CHECKS = 1;
