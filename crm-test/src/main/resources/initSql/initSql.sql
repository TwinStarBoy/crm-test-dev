CREATE DATABASE crm DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

drop table if exists CUSTOMER;

/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
create table CUSTOMER
(
   ID                   int not null auto_increment comment '主键',
   USERNAME             varchar(50) comment '用户名',
   PASSWORD             varchar(200) comment '密码',
   MOBILE               varchar(15) comment '联系手机号',
   IDENTIFICATION       varchar(20) comment '身份证号',
   EMAIL                varchar(50) comment '邮箱',
   IS_EMAIL_VERIFY      varchar(1) comment '是否邮箱验证(0：否，1：是)',
   IS_FROZEN            varchar(1) comment '是否冻结（0：否，1：是）',
   IS_ENABLE            varchar(1) comment '是否失效（0：否，1：是）',
   CREATE_TIME          timestamp comment '创建时间',
   UPDATE_TIME          timestamp comment '修改时间',
   primary key (ID)
);

