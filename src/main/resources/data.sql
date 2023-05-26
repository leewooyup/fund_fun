--init

-- DELETE FROM users;
-- DELETE FROM ALARM;
-- SELECT * FROM alarm;
-- SELECT * FROM users;
-- SELECT user_id FROM users WHERE email = 'user';
-- Users

INSERT INTO users(USER_ID,CREATED_AT,UPDATED_AT, BENEFIT,"COUNT",EMAIL,GENDER,MONEY,NAME,PASSWORD,PHONE,REG_DATE,"ROLE",TOTAL_INVESTMENT) VALUES(sys_guid(), NULL, NULL, 0, 100, 'user', 0, 10000, 'kim', '$2a$12$Gc5HoP76Uqg28P6vXKbmtuxse1qQhZpZ64Ny4e9Tga8zM2efHiPwW', '0100-0100', NULL, 1, 0);
INSERT INTO users(USER_ID,CREATED_AT,UPDATED_AT, BENEFIT,"COUNT",EMAIL,GENDER,MONEY,NAME,PASSWORD,PHONE,REG_DATE,"ROLE",TOTAL_INVESTMENT) VALUES(sys_guid(), NULL, NULL, 0, 100, 'user1', 0, 200, 'lee', '$2a$12$Gc5HoP76Uqg28P6vXKbmtuxse1qQhZpZ64Ny4e9Tga8zM2efHiPwW', '0100-0100', NULL, 2, 0);
INSERT INTO users(USER_ID,CREATED_AT,UPDATED_AT, BENEFIT,"COUNT",EMAIL,GENDER,MONEY,NAME,PASSWORD,PHONE,REG_DATE,"ROLE",TOTAL_INVESTMENT) VALUES(sys_guid(), NULL, NULL, 0, 100, 'user2', 0, 300, 'park', '$2a$12$Gc5HoP76Uqg28P6vXKbmtuxse1qQhZpZ64Ny4e9Tga8zM2efHiPwW', '0100-0100', NULL, 0, 0);

-- Alarm
-- DROP SEQUENCe alarm_seq;
-- CREATE SEQUENCE alarm_seq
--    START WITH 0
--    INCREMENT BY 1
--    MINVALUE 0
--    MAXVALUE 100
--;

--INSERT INTO alarm(id, content, is_read, USER_id) values(100, '알림1', 0, (SELECT user_id FROM users WHERE email = 'user'));
--INSERT INTO alarm(id, content, is_read, USER_id) values(101, '알림2', 0, (SELECT user_id FROM users WHERE email = 'user'));
--INSERT INTO alarm(id, content, is_read, USER_id) values(102, '알림3', 0, (SELECT user_id FROM users WHERE email = 'user1'));
--INSERT INTO alarm(id, content, is_read, USER_id) values(103, '알림4', 0, (SELECT user_id FROM users WHERE email = 'user2'));
