# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 删除用户表
drop table if exists `user`;

-- 用户表
create table if not exists user
(
    id           bigint      auto_increment                      comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint      default 0                 null comment '用户性别',
    userPassword varchar(512)                           not null comment '密码',
    phone        varchar(128)                           null comment '用户电话',
    email        varchar(512)                           null comment '用户邮箱',
    userStatus   int          default 0                 null comment '用户状态',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 null comment '是否删除',
    userRole     int          default 0                 null comment '用户角色 0-普通用户 1-管理员',
    planetCode    varchar(512)                           null comment '星球用户'
) comment '用户表' collate = utf8mb4_unicode_ci;

