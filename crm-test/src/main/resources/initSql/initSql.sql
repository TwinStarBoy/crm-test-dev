use crm ; 

drop table if exists customer;

/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
create table customer
(
   ID                   int not null auto_increment comment '主键',
   USERNAME             varchar(50) not null comment '用户名',
   PASSWORD             varchar(200) not null comment '密码',
   MOBILE               varchar(15) comment '联系手机号',
   IDENTIFICATION       varchar(20) comment '身份证号',
   EMAIL                varchar(50) not null comment '邮箱',
   IS_EMAIL_VERIFY      varchar(1) comment '是否邮箱验证(0：否，1：是)',
   IS_FROZEN            varchar(1) comment '是否冻结（0：否，1：是）',
   IS_ENABLE            varchar(1) comment '是否失效（0：否，1：是）',
   CREATE_TIME          timestamp default CURRENT_TIMESTAMP comment '创建时间',
   UPDATE_TIME          datetime default NULL comment '修改时间',
   primary key (ID),
   key AK_unique_username (USERNAME),
   key AK_unique_email (EMAIL)
);




