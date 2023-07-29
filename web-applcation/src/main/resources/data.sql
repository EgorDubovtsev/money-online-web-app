CREATE TABLE IF NOT EXISTS users(id bigint  auto_increment NOT NULL PRIMARY KEY, username character, name character, birthdate TIMESTAMP, password character);
CREATE TABLE IF NOT EXISTS roles(id bigint  auto_increment NULL, user_id bigint, role character);

ALTER TABLE users ADD CONSTRAINT name_unique UNIQUE(username);
INSERT INTO users(id, username, name, birthdate, password) values(6666666, 'user1db', 'Пользователь из БД', TO_DATE('01-12-2000','dd-MM-yyyy'), '$2a$10$W6ZjRcfjPB.VvB5MhLEa0eu2PxCVADb7gCw9weuDC3dkNVSSKaNde');
INSERT INTO roles(user_id, role) values(1, 'USER');