CREATE TABLE IF NOT EXISTS `message` (
  `type` varchar(20) NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `message` VALUES ('marquee', '');
INSERT INTO `message` VALUES ('footer', '');
# 同步到HUSTOJ最新版本的数据库
ALTER TABLE `solution` ADD COLUMN `nick` CHAR(20) NOT NULL DEFAULT '' AFTER user_id;
UPDATE `solution` AS `s` INNER JOIN `users` AS `u` ON s.user_id = u.user_id SET s.nick = u.nick;