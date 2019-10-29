CREATE TABLE IF NOT EXISTS `message` (
  `type` varchar(20) NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `message` VALUES ('marquee', '');