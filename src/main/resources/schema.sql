CREATE TABLE IF NOT EXISTS  oauth_access_token (
authentication_id varchar(255) NOT NULL,
token_id varchar(255) NOT NULL,
token blob NOT NULL,
user_name varchar(255) NULL,
client_id varchar(255) NOT NULL,
authentication blob NOT NULL,
refresh_token varchar(255) NULL,
PRIMARY KEY (authentication_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS  oauth_refresh_token (
token_id varchar(255) NOT NULL,
token blob NOT NULL,
authentication blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;