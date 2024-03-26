create table if not exists crazy_rain_center.user
(
    id           bigint auto_increment comment '用户id'
        primary key,
    username     varchar(255)                       null comment '用户昵称',
    userAccount  varchar(255)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '手机号',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 正常 ',
    createTime   datetime                           null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 null comment '是否删除',
    userRole     int      default 0                 null comment '0默认用户，1管理员'
);

