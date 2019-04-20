DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '用户ID(主键)',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `host`;
CREATE TABLE `host` (
  `host_id` int(2) NOT NULL COMMENT '主机ID(主键)',
  `host_address` varchar(20) NOT NULL COMMENT '主机地址',
  `port` int(6) NOT NULL COMMENT '端口号',
  `identifier` int(2) NOT NULL COMMENT '主机标识符，1为路由器，2为交换机',
  PRIMARY KEY (`host_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='连接主机表';


DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` double(4, 2) NOT NULL COMMENT '菜单ID(主键)',
  `menu_name` varchar(40) NOT NULL COMMENT '菜单名',
  `menu_url` varchar(50) NOT NULL COMMENT '菜单URL',
  `menu_css` varchar(20) NULL default NULL COMMENT '菜单CSS',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

INSERT INTO `menu` VALUES (1.0, '首页', '/page/main.html', 'fa fa-dashboard');
INSERT INTO `menu` VALUES (2.0, '路由器基本配置', 'javascript:;', 'fa fa-desktop');
INSERT INTO `menu` VALUES (2.1, '接口配置', '/page/RouterBasic/Interface.html', NULL);
INSERT INTO `menu` VALUES (2.2, '查询配置信息', '/page/RouterBasic/QueryConfiguration.html', NULL);
INSERT INTO `menu` VALUES (2.3, '配置静态路由', '/page/RouterBasic/StaticRouter.html', NULL);
INSERT INTO `menu` VALUES (2.4, '测试连通（Ping）', '/page/RouterBasic/Ping.html', NULL);
INSERT INTO `menu` VALUES (3.0, '路由选择协议配置', 'javascript:;', 'fa fa-cogs');
INSERT INTO `menu` VALUES (3.1, 'Rip协议', '/page/RouterProtocol/Rip.html', NULL);
INSERT INTO `menu` VALUES (3.2, 'Eigrp协议', '/page/RouterProtocol/Eigrp.html', NULL);
INSERT INTO `menu` VALUES (3.3, 'Ospf协议', '/page/RouterProtocol/Ospf.html', NULL);


INSERT INTO `menu` VALUES (4.0, '交换机配置', 'javascript:;', 'fa fa-book');
INSERT INTO `menu` VALUES (4.1, '交换机基本配置', '/page/Switch/SwitchConfigure.html', NULL);
INSERT INTO `menu` VALUES (4.2, 'Vlan配置', '/page/Switch/Vlan.html', NULL);
INSERT INTO `menu` VALUES (4.3, '生成树配置（STP）', '/page/Switch/STP.html', NULL);
INSERT INTO `menu` VALUES (5.0, '其他配置', 'javascript:;', 'fa fa-tasks');
INSERT INTO `menu` VALUES (5.1, '访问控制列表（ACL）', '/page/Other/ACL.html', NULL);
INSERT INTO `menu` VALUES (5.2, '网路地址转换（NAT）', '/page/Other/NAT.html', NULL);
INSERT INTO `menu` VALUES (5.3, '动态主机配置协议（DHCP）', '/page/Other/DHCP.html', NULL);







