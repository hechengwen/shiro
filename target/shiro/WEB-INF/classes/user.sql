CREATE TABLE `user` (
  `user_id` varchar(50) NOT NULL COMMENT '主键，用户id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `company` varchar(50) DEFAULT NULL COMMENT '公司',
  `address` varchar(100) DEFAULT NULL COMMENT '住址',
  `id_card` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `sex` int(6) DEFAULT NULL COMMENT '性别',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;