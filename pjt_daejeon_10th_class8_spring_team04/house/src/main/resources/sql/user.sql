use whereismyhome;

ALTER TABLE `whereismyhome`.`dongcode`
    ADD COLUMN `fullAddress` VARCHAR(50) NULL AFTER `dongName`;

update dongcode
set `fullAddress` = trim(concat(sidoName, ' ', gugunName, ' ', dongName))
where true;

-- Table whereismyhome.user
CREATE TABLE IF NOT EXISTS whereismyhome.user
(
    uid      INT         NOT NULL AUTO_INCREMENT,
    userId   VARCHAR(50) NOT NULL,
    userPw   VARCHAR(44) NOT NULL,
    userName VARCHAR(50) NOT NULL,
    PRIMARY KEY (uid),
    UNIQUE INDEX userId_UNIQUE (userId ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- Table whereismyhome.board
CREATE TABLE IF NOT EXISTS whereismyhome.board
(
    bno     INT                     NOT NULL AUTO_INCREMENT,
    aptCode BIGINT                  NULL     DEFAULT NULL,
    uid     INT                     NOT NULL,
    title   VARCHAR(100)            NOT NULL,
    content VARCHAR(400)            NOT NULL,
    hit     INT                     NOT NULL DEFAULT '0',
    `like`  INT                     NOT NULL DEFAULT '0',
    date    DATETIME                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    type    ENUM ('NOTICE', 'POST') NOT NULL DEFAULT 'POST',
    PRIMARY KEY (bno),
    INDEX houseinfo2board_idx (aptCode ASC) VISIBLE,
    INDEX user2board_idx (uid ASC) VISIBLE,
    CONSTRAINT houseinfo2board
        FOREIGN KEY (aptCode)
            REFERENCES whereismyhome.houseinfo (aptCode),
    CONSTRAINT user2board
        FOREIGN KEY (uid)
            REFERENCES whereismyhome.user (uid)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- Table whereismyhome.comment
CREATE TABLE IF NOT EXISTS whereismyhome.comment
(
    cno       INT          NOT NULL AUTO_INCREMENT,
    bno       INT          NOT NULL,
    uid       INT          NOT NULL,
    content   VARCHAR(400) NOT NULL,
    date      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    parentCno INT          NULL     DEFAULT NULL,
    PRIMARY KEY (cno),
    INDEX board2comment_idx (bno ASC) VISIBLE,
    INDEX comment2comment_idx (parentCno ASC) VISIBLE,
    INDEX user2comment_idx (uid ASC) VISIBLE,
    CONSTRAINT board2comment
        FOREIGN KEY (bno)
            REFERENCES whereismyhome.board (bno),
    CONSTRAINT comment2comment
        FOREIGN KEY (parentCno)
            REFERENCES whereismyhome.comment (cno),
    CONSTRAINT user2comment
        FOREIGN KEY (uid)
            REFERENCES whereismyhome.user (uid)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- Table whereismyhome.salt
select *
from user;
drop table if exists salt;
CREATE TABLE `salt`
(
    `uid`  int         NOT NULL,
    `salt` varchar(50) NOT NULL,
    KEY `salt2user_idx` (`uid`),
    CONSTRAINT `user2salt` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


insert into user
values (0, 'admin', 0, 'admin');

insert into board(uid, title, content, type)
values (22, '중간발표 공지', '11월 21일에 중간 발표가 있으니 참고하시기 바랍니다.', 'NOTICE');
insert into board(uid, title, content, type)
values (22, '최종발표 공지', '11월 24일에 최종 발표가 있으니 참고하시기 바랍니다.', 'NOTICE');
