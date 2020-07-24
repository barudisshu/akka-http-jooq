DROP DATABASE IF EXISTS `timetable`;
CREATE DATABASE IF NOT EXISTS `timetable`;
USE `timetable`;

DROP TABLE IF EXISTS `classrooms`;
CREATE TABLE IF NOT EXISTS `classrooms`
(
    `id`          varchar(64) NOT NULL,
    `lib`         varchar(64)          DEFAULT NULL,
    `max`         int(11)              DEFAULT NULL,
    `create_time` timestamp   not null default current_timestamp,
    `modify_time` timestamp   not null default current_timestamp,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `lessons`;
CREATE TABLE IF NOT EXISTS `lessons`
(
    `id`          varchar(64) NOT NULL,
    `lib`         varchar(64)          DEFAULT NULL,
    `create_time` timestamp   not null default current_timestamp,
    `modify_time` timestamp   not null default current_timestamp,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `teachers`;
CREATE TABLE IF NOT EXISTS `teachers`
(
    `id`          varchar(64) NOT NULL,
    `first_name`  varchar(64)          DEFAULT NULL,
    `last_name`   varchar(64)          DEFAULT NULL,
    `create_time` timestamp   not null default current_timestamp,
    `modify_time` timestamp   not null default current_timestamp,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS `plans`;
CREATE TABLE IF NOT EXISTS `plans`
(
    `id`           varchar(64) NOT NULL,
    `teacher_id`   varchar(64) NOT NULL,
    `lesson_id`    varchar(64) NOT NULL,
    `classroom_id` varchar(64) NOT NULL,
    `start`        timestamp   NULL     DEFAULT NULL,
    `end`          timestamp   NULL     DEFAULT NULL,
    `create_time`  timestamp   not null default current_timestamp,
    `modify_time`  timestamp   not null default current_timestamp,
    PRIMARY KEY (`id`),
    key `plans_teacher_id` (`teacher_id`),
    key `plans_lesson_id` (`lesson_id`),
    key `plans_classroom_id` (`classroom_id`),
    constraint `plans_teacher_id` foreign key (`teacher_id`) references `teachers` (`id`) on delete cascade on update cascade,
    constraint `plans_lesson_id` foreign key (`lesson_id`) references `lessons` (`id`) on delete cascade on update cascade,
    constraint `plans_classroom_id` foreign key (`classroom_id`) references `classrooms` (`id`) on delete cascade on update cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

