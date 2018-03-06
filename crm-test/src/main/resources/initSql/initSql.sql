use crm ; 

drop table if exists CUSTOMER;

/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
create table CUSTOMER
(
   ID                   int not null auto_increment comment '����',
   USERNAME             varchar(50) comment '�û���',
   PASSWORD             varchar(200) comment '����',
   MOBILE               varchar(15) comment '��ϵ�ֻ���',
   IDENTIFICATION       varchar(20) comment '���֤��',
   EMAIL                varchar(50) comment '����',
   IS_EMAIL_VERIFY      varchar(1) comment '�Ƿ�������֤(0����1����)',
   IS_FROZEN            varchar(1) comment '�Ƿ񶳽ᣨ0����1���ǣ�',
   IS_ENABLE            varchar(1) comment '�Ƿ�ʧЧ��0����1���ǣ�',
   CREATE_TIME          timestamp comment '����ʱ��',
   UPDATE_TIME          timestamp comment '�޸�ʱ��',
   primary key (ID)
);

