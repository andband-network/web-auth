CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `account_id` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `account_locked` bit(1) DEFAULT NULL,
  `last_successful_login` datetime DEFAULT NULL,
  `failed_login_attempts` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_account_id` (`account_id`),
  UNIQUE KEY `UK_username` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `role` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  KEY `FK_user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `oauth_access_token` (
  `authentication_id` varchar(255) NOT NULL,
  `token_id` varchar(255) NOT NULL,
  `token` blob NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) NOT NULL,
  `authentication` blob NOT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `token_id` varchar(255) NOT NULL,
  `token` blob NOT NULL,
  `authentication` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
