--------------------------------------------------------
--  CREATE TABLES
--------------------------------------------------------
CREATE TABLE KT_BATCHES
(
  ID VARCHAR2(40) PRIMARY KEY NOT NULL,
  CATEGORY VARCHAR2(100) NOT NULL,
  STATUS VARCHAR2(20) NOT NULL,
  CREATE_TIME VARCHAR2(20) NOT NULL
);
CREATE TABLE KT_BATCH_TASKS
(
  ID VARCHAR2(36) PRIMARY KEY NOT NULL,
  BATCH_ID VARCHAR2(36) NOT NULL,
  PROCESSOR_CLASS VARCHAR2(100) NOT NULL,
  PARAMETERS CLOB,
  STATUS VARCHAR2(20) NOT NULL
);
CREATE TABLE KT_CORE_ORG
(
  PID VARCHAR2(64) PRIMARY KEY NOT NULL,
  CODE VARCHAR2(100) DEFAULT NULL,
  ORGNAME VARCHAR2(100) DEFAULT NULL,
  SITENAME VARCHAR2(100) DEFAULT NULL,
  DOMAIN VARCHAR2(200) DEFAULT NULL,
  LOGOURL VARCHAR2(500) DEFAULT NULL,
  HOMEPAGE VARCHAR2(200) DEFAULT NULL,
  CONTACTNAME VARCHAR2(200) DEFAULT NULL,
  CONTACTEMAIL VARCHAR2(100) DEFAULT NULL,
  STATUS NUMBER(1,0) DEFAULT NULL,
  TYPE NUMBER(1,0) DEFAULT NULL,
  CREATOR VARCHAR2(36) DEFAULT NULL,
  CREATETIME VARCHAR2(255) DEFAULT NULL,
  UPDATER VARCHAR2(36) DEFAULT NULL,
  UPDATETIME VARCHAR2(255) DEFAULT NULL,
  LOWTOUCH NUMBER(1,0) DEFAULT 0 NOT NULL,
  FINADMINNAME VARCHAR2(200) DEFAULT NULL,
  FINADMINEMAIL VARCHAR2(100) DEFAULT NULL,
  CRM_CUSTOMER_ID VARCHAR2(100) DEFAULT NULL
);
CREATE TABLE KT_CORE_ORGUSER
(
  PID VARCHAR2(64) PRIMARY KEY NOT NULL,
  CONSUMER_USER_ID VARCHAR2(32) DEFAULT NULL,
  ORG_ID VARCHAR2(64) DEFAULT NULL,
  USERNAME VARCHAR2(64) DEFAULT NULL NOT NULL,
  PASSWORD VARCHAR2(256) DEFAULT NULL,
  EMAIL VARCHAR2(255) DEFAULT NULL NOT NULL,
  FULLNAME VARCHAR2(255) DEFAULT NULL NOT NULL,
  PHONE VARCHAR2(255) DEFAULT NULL,
  MOBILE VARCHAR2(255) DEFAULT NULL,
  STATUS NUMBER(3,0) DEFAULT NULL NOT NULL,
  LOCALE VARCHAR2(10) DEFAULT NULL,
  POINTS NUMBER(11,0) DEFAULT 0,
  LAST_LOGIN_TIME VARCHAR2(255) DEFAULT NULL,
  LOGIN_FAIL_COUNT NUMBER(11,0) DEFAULT NULL,
  LOCK_STATUS NUMBER(11,0) DEFAULT NULL,
  LOCK_TIME VARCHAR2(255) DEFAULT NULL,
  USER_AGENT VARCHAR2(255) DEFAULT NULL,
  CREATOR VARCHAR2(255) DEFAULT NULL,
  CREATE_TIME VARCHAR2(255) DEFAULT NULL,
  UPDATER VARCHAR2(255) DEFAULT NULL,
  UPDATE_TIME VARCHAR2(255) DEFAULT NULL,
  ROLE_ID VARCHAR2(64),
  PASSWORD_ALG VARCHAR2(20)
);
CREATE TABLE KT_CORE_ORGUSER_EXT
(
  PARANAME VARCHAR2(2000) NOT NULL,
  PARAVALUE VARCHAR2(2000),
  UPDATE_TIME VARCHAR2(64),
  USERID VARCHAR2(64) NOT NULL,
  PRIMARY KEY (USERID, PARANAME)
);
CREATE TABLE KT_CORE_SERVICES
(
  PID VARCHAR2(64) PRIMARY KEY NOT NULL,
  TYPE VARCHAR2(20) NOT NULL,
  TOTAL_LICENSE NUMBER(10,0) NOT NULL,
  REMAINING_LICENSE NUMBER(10,0) NOT NULL,
  CREATEBY VARCHAR2(64),
  CREATETIME VARCHAR2(20),
  AUTO_GRANT NUMBER(1,0) DEFAULT 0 NOT NULL,
  ORG_ID VARCHAR2(64),
  WEBEX_SITE_URL VARCHAR2(2000),
  WEBEX_SERVICE_TYPE VARCHAR2(2),
  DISABLED NUMBER(1,0) DEFAULT 0 NOT NULL,
  SERVICE_NAME VARCHAR2(200),
  EXPIRE_AFTER_DAYS NUMBER(5,0) DEFAULT 0
);
CREATE TABLE KT_CORE_USER_SERVICE_GRANT
(
  PID VARCHAR2(64) PRIMARY KEY NOT NULL,
  USERID VARCHAR2(64) NOT NULL,
  SERVICEID VARCHAR2(64) NOT NULL,
  GRANT_BY VARCHAR2(64),
  GRANTTIME VARCHAR2(20) NOT NULL,
  MC_ENABLED NUMBER(1,0) DEFAULT 0 NOT NULL,
  EC_ENABLED NUMBER(1,0) DEFAULT 0 NOT NULL,
  TC_ENABLED NUMBER(1,0) DEFAULT 0 NOT NULL,
  SC_ENABLED NUMBER(1,0) DEFAULT 0
    NOT NULL,
  EXPIRATION_DATE VARCHAR2(255)
);
CREATE TABLE KT_MAIL_JOBS
(
  ID VARCHAR2(40) PRIMARY KEY NOT NULL,
  BATCH_NO VARCHAR2(40) NOT NULL,
  TEMPLATE VARCHAR2(100) NOT NULL,
  TYPE VARCHAR2(20) NOT NULL,
  VARIABLES CLOB,
  SENDER VARCHAR2(50),
  RECIPIENTS VARCHAR2(300) NOT NULL,
  SCHEDULED_SEND_TIME VARCHAR2(20),
  CREATE_TIME VARCHAR2(20) NOT NULL,
  SENT_TIME VARCHAR2(20),
  STATUS VARCHAR2(20) NOT NULL
);
CREATE TABLE KT_MAIL_TEMPLATES
(
  TEMPLATE_NAME VARCHAR2(100) PRIMARY KEY NOT NULL,
  LOCALE VARCHAR2(20) NOT NULL,
  SUBJECT VARCHAR2(200) NOT NULL,
  BODY CLOB NOT NULL,
  TYPE VARCHAR2(20) NOT NULL
);
CREATE TABLE PASSWORD_RESET_LINKS
(
  PID VARCHAR2(64) NOT NULL,
  LINK VARCHAR2(64) NOT NULL,
  USERNAME VARCHAR2(256) NOT NULL,
  EXPIRED_TIME VARCHAR2(255) NOT NULL,
  CREATED_TIME TIMESTAMP DEFAULT sysdate
    NOT NULL
);

CREATE UNIQUE INDEX KT_CORE_USER_SERVICE_GRAN_UK1 ON KT_CORE_USER_SERVICE_GRANT (USERID, SERVICEID);

----------------------------
--- 创建CI超级管理员  'ciadmin@ketianyun.com', default password 'admin@ci2'
----------------------------
INSERT INTO KT_CORE_ORGUSER (PID,USERNAME,PASSWORD,EMAIL,FULLNAME,STATUS,LOCALE,LOGIN_FAIL_COUNT,LOCK_STATUS,CREATE_TIME,UPDATE_TIME,ROLE_ID,PASSWORD_ALG) values
  ('0343b162-a3fe-403e-ba6c-9636316ed8dc','ciadmin@ketianyun.com','06c57e40a1b74cf203fba7ada1041d1dfda90fdc6a2fb0817756ecf681fcc325','ciadmin@ketianyun.com','CI超级管理员',1,'zh_CN',0,0,'2015-09-12 00:00:00','2015-10-26 00:00:00','999',null);

----------------------------
--- 创建内部伙伴账号  'ec_system@ketianyun.com', default password 'ec_system@ci2'
----------------------------
INSERT INTO KT_CORE_ORGUSER (PID,USERNAME,PASSWORD,EMAIL,FULLNAME,STATUS,LOCALE,LOGIN_FAIL_COUNT,LOCK_STATUS,CREATE_TIME,UPDATE_TIME,ROLE_ID,PASSWORD_ALG) values
  ('a9f80bee-57bc-4ce4-8b9b-cc7805e800cb','ec_system@ketianyun.com','57f5b4e184e795b0da7bf104036b481009666ddd7890e48fbe633d8b519eb092','ec_system@ketianyun.com','EC系统账号',1,'zh_CN',0,0,'2015-09-12 00:00:00','2015-10-26 00:00:00','888',null);

----------------------------
--- 创建内部伙伴账号 'bss_system@ketianyun.com', default password 'bss_system@ci2'
----------------------------
INSERT INTO KT_CORE_ORGUSER (PID,USERNAME,PASSWORD,EMAIL,FULLNAME,STATUS,LOCALE,LOGIN_FAIL_COUNT,LOCK_STATUS,CREATE_TIME,UPDATE_TIME,ROLE_ID,PASSWORD_ALG) values
  ('441bd348-6e5b-42ff-8a10-f35ed1e1a8a5','bss_system@ketianyun.com','008843505642c042ebdc6341fcbb763fd7fcb2eed404014974596a383423130b','bss_system@ketianyun.com','BSS系统账号',1,'zh_CN',0,0,'2015-09-12 00:00:00','2015-10-26 00:00:00','888',null);


----------------------------
--- 创建内部伙伴账号 'ltadmin@ketianyun.com', 初始密码 'ltadmin@ci2'
----------------------------
INSERT INTO KT_CORE_ORGUSER (PID,USERNAME,PASSWORD,EMAIL,FULLNAME,STATUS,LOCALE,LOGIN_FAIL_COUNT,LOCK_STATUS,CREATE_TIME,UPDATE_TIME,ROLE_ID,PASSWORD_ALG, ORG_ID) values
  ('16369b29-3759-4d4f-9d7b-20dc8468a269','ltadmin@ketianyun.com','e9854de9cf3dd577613fe0a2e28d4fa89f2d6c43575edbb0858a7685bba68857','ltadmin@ketianyun.com','试用组织管理员',1,'zh_CN',0,0,'2015-09-12 00:00:00','2015-10-26 00:00:00','500',null, 'ff808081-4e8d-5cdc-014e-9752bd800009');


----------------------------
--- INSERT EMAIL TEMPLATES
----------------------------
Insert into KT_MAIL_TEMPLATES (TEMPLATE_NAME,LOCALE,SUBJECT,TYPE,BODY) values ('PWD_RESET','zh_CN','科天云账号密码修改','html','<html lang="zh" xmlns="http://www.w3.org/1999/xhtml"><head><meta charset="utf-8"/><title></title> <style>
    .box {  width: 638px;border-collapse: collapse;border-spacing: 0;font-family: ''Microsoft Yahei'';  }
    body {font-size: 14px;color: #474747;background-color: #eaeaec;}
    img {  border: 0;  }
    .logo {  padding: 20px 5px;  }
    .content {  background-color: #fff;  }
.title {  padding: 40px;  font-size: 30px;  color: #95C55A;  }
.contents {  color: #575757;  font-size: 15px;  padding-bottom: 15px;  padding-left: 40px;  padding-right: 20px;  }
.blue {  color: #008fd3;  }
.contents-bottom {  padding-top: 55px;  }
.copy {  padding: 40px;  }
.copy table tr td {  text-align: center;  font-size: 14px;  color: #979797;  padding-bottom: 5px;  width: 648px;  }
.copyright {  padding-top: 10px;  }
.call {  font-weight: 600;  }
.contents table tr td {  width: 434px;  }
.contents li {  color: #95C55A;  }
.contents li span {  color: #000;  }
.float-r {
    border: 2px solid #058fd3;
    -moz-border-radius: 10px;
    -webkit-border-radius: 10px;
    border-radius: 10px;
    padding: 15px;
    font-size: 12px;
}

.table-title {
    font-size: 15px;
    padding-bottom: 20px;
}

.float-td {
    padding-right: 40px;
    vertical-align: top;
}

.contents-bottom img {
    margin-bottom: -4px;
}    </style></head><body><table class="box" align="center" border="0" cellspacing="0" cellpadding="0"><tr><td><table border="0" cellspacing="0" cellpadding="0"><tr><td class="logo"><img src="http://www.ketianyun.com/documents/11909/50237/logo/94bf6f9e-e2d4-4719-80ce-7d9898c6e98f?t=1440840194607"/></td></tr></table></td></tr><tr><td class="content"><table border="0" cellspacing="0" cellpadding="0"><tr><td class="title"> 密码重置</td></tr><tr><td class="contents"> 请您点击<a href=''${resetPasswordLink}''>重新设置密码</a>修改您的科天云帐号密码。谢谢！</td></tr><tr><td class="contents"> Please change your Ketianyun account password by clicking <a href=''${resetPasswordLink}''>Changing Password</a> link in time. Thanks! </td></tr><td class="contents-bottom" colspan=2><img src="http://www.ketianyun.com/documents/11909/50237/bottom/590340eb-a5d9-4c81-84ae-7049bb0825e1?t=1440840217362"/></td></table></td></tr><tr><td class="copy"><table border="0" cellspacing="0" cellpadding="0"><tr><td>此邮件为系统邮件，请勿直接回复。</td></tr><tr><td>如需进一步了解科天云，请访问：www.ketianyun.com</td></tr><tr><td>如有任何疑问，请拨打客服电话：400-9308838</td></tr><tr><td class="copyright">Copyright <span style="font-size:14px;font-family:Arial, Helvetica, sans-serif;">&#169;</span> 2015 广州视畅信息科技有限公司. All rights reserved. </td></tr></table></td></tr></table></body></html>');
Insert into KT_MAIL_TEMPLATES (TEMPLATE_NAME,LOCALE,SUBJECT,TYPE,BODY) values ('USER_WELCOME_10K_TRIAL','zh_CN','欢迎使用科天云','html', '<html lang="zh" xmlns="http://www.w3.org/1999/xhtml"><head><meta charset="utf-8"/><title></title> <style>        .box {
        width: 638px;
        border-collapse: collapse;
        border-spacing: 0;
        font-family: ''Microsoft Yahei'';
    }

    body {
        font-size: 14px;
        color: #474747;
        background-color: #eaeaec;
    }

    img {
        border: 0;
    }

    .logo {
        padding: 20px 5px;
    }

    .content {
        background-color: #fff;
    }

    .title {
        padding: 40px;
        font-size: 30px;
        color: #95C55A;
    }

    .contents {
        color: #575757;
        font-size: 15px;
        padding-bottom: 15px;
        padding-left: 40px;
        padding-right: 20px;
    }

    .blue {
        color: #008fd3;
    }

    .contents-bottom {
        padding-top: 55px;
    }

    .copy {
        padding: 40px;
    }

    .copy table tr td {
        text-align: center;
        font-size: 14px;
        color: #979797;
        padding-bottom: 5px;
        width: 648px;
    }

    .copyright {
        padding-top: 10px;
    }

    .call {
        font-weight: 600;
    }

    .contents table tr td {
        width: 434px;
    }

    .contents li {
        color: #95C55A;
    }

    .contents li span {
        color: #000;
    }

    .float-r {
        border: 2px solid #058fd3;
        -moz-border-radius: 10px;
        -webkit-border-radius: 10px;
        border-radius: 10px;
        padding: 15px;
        font-size: 12px;
    }

    .table-title {
        font-size: 15px;
        padding-bottom: 20px;
    }

    .float-td {
        padding-right: 40px;
        vertical-align: top;
    }

    .contents-bottom img {
        margin-bottom: -4px;
    }    </style></head><body><table class="box" align="center" border="0" cellspacing="0" cellpadding="0"><tr><td><table border="0" cellspacing="0" cellpadding="0"><tr><td class="logo"><img src="http://www.ketianyun.com/documents/11909/50237/logo/94bf6f9e-e2d4-4719-80ce-7d9898c6e98f?t=1440840194607"/></td></tr></table></td></tr><tr><td class="content"><table border="0" cellspacing="0" cellpadding="0"><tr><td class="title"> 欢迎加入科天云</td></tr><tr><td class="contents"><font style=''font-size:20px;color:#0088A8;''>${broker}</font>邀请您试用科天云WebEx高清视频会议系统！ </td></tr><tr><td class="contents"> 您的账号为：${account}</td></tr><tr><td class="contents"> 为保障您的权益，请您点击<a href=''${resetPasswordLink}''>重新设置密码</a>修改您的科天云帐号密码，谢谢！ </td></tr><tr><td class="contents"> 如有任何疑问，请拨打客服电话：<font style=''color:#3A74C2;''>400-9308838</font></td></tr><td class="contents-bottom" colspan=2><img src="http://www.ketianyun.com/documents/11909/50237/bottom/590340eb-a5d9-4c81-84ae-7049bb0825e1?t=1440840217362"/></td></table></td></tr><tr><td class="copy"><table border="0" cellspacing="0" cellpadding="0"><tr><td>此邮件为系统邮件，请勿直接回复。</td></tr><tr><td>如需进一步了解科天云，请访问：www.ketianyun.com</td></tr><tr><td>如有任何疑问，请拨打客服电话：400-9308838</td></tr><tr><td class="copyright">Copyright <span style="font-size:14px;font-family:Arial, Helvetica, sans-serif;">&#169;</span> 2015 广州视畅信息科技有限公司. All rights reserved. </td></tr></table></td></tr></table></body></html> </pre></body></html>');
Insert into KT_MAIL_TEMPLATES (TEMPLATE_NAME,LOCALE,SUBJECT,TYPE,BODY) values ('USER_WELCOME','zh_CN','欢迎使用科天云','html','<html lang="zh" xmlns="http://www.w3.org/1999/xhtml"><head><meta charset="utf-8"/><title></title> <style>        .box {
        width: 638px;
        border-collapse: collapse;
        border-spacing: 0;
        font-family: ''Microsoft Yahei'';
    }

    body {
        font-size: 14px;
        color: #474747;
        background-color: #eaeaec;
    }

    img {
        border: 0;
    }

    .logo {
        padding: 20px 5px;
    }

    .content {
        background-color: #fff;
    }

    .title {
        padding: 40px;
        font-size: 30px;
        color: #95C55A;
    }

    .contents {
        color: #575757;
        font-size: 15px;
        padding-bottom: 15px;
        padding-left: 40px;
        padding-right: 20px;
    }

    .blue {
        color: #008fd3;
    }

    .contents-bottom {
        padding-top: 55px;
    }

    .copy {
        padding: 40px;
    }

    .copy table tr td {
        text-align: center;
        font-size: 14px;
        color: #979797;
        padding-bottom: 5px;
        width: 648px;
    }

    .copyright {
        padding-top: 10px;
    }

    .call {
        font-weight: 600;
    }

    .contents table tr td {
        width: 434px;
    }

    .contents li {
        color: #95C55A;
    }

    .contents li span {
        color: #000;
    }

    .float-r {
        border: 2px solid #058fd3;
        -moz-border-radius: 10px;
        -webkit-border-radius: 10px;
        border-radius: 10px;
        padding: 15px;
        font-size: 12px;
    }

    .table-title {
        font-size: 15px;
        padding-bottom: 20px;
    }

    .float-td {
        padding-right: 40px;
        vertical-align: top;
    }

    .contents-bottom img {
        margin-bottom: -4px;
    }    </style></head><body><table class="box" align="center" border="0" cellspacing="0" cellpadding="0"><tr><td><table border="0" cellspacing="0" cellpadding="0"><tr><td class="logo"><img src="http://www.ketianyun.com/documents/11909/50237/logo/94bf6f9e-e2d4-4719-80ce-7d9898c6e98f?t=1440840194607"/></td></tr></table></td></tr><tr><td class="content"><table border="0" cellspacing="0" cellpadding="0"><tr><td class="title"> 欢迎加入科天云</td></tr><tr><td class="contents"> 为保障您的权益，请您点击<a href=''${resetPasswordLink}''>重新设置密码</a>修改您的科天云帐号密码。谢谢！</td></tr><tr><td class="contents"> To protect your KeTianYun service, please change your Ketianyun account password by clicking <a href=''${resetPasswordLink}''>Changing Password</a> link in time. Thanks! </td></tr><td class="contents-bottom" colspan=2><img src="http://www.ketianyun.com/documents/11909/50237/bottom/590340eb-a5d9-4c81-84ae-7049bb0825e1?t=1440840217362"/></td></table></td></tr><tr><td class="copy"><table border="0" cellspacing="0" cellpadding="0"><tr><td>此邮件为系统邮件，请勿直接回复。</td></tr><tr><td>如需进一步了解科天云，请访问：www.ketianyun.com</td></tr><tr><td>如有任何疑问，请拨打客服电话：400-9308838</td></tr><tr><td class="copyright">Copyright <span style="font-size:14px;font-family:Arial, Helvetica, sans-serif;">&#169;</span> 2015 广州视畅信息科技有限公司. All rights reserved. </td></tr></table></td></tr></table></body></html>');
Insert into KT_MAIL_TEMPLATES (TEMPLATE_NAME,LOCALE,SUBJECT,TYPE,BODY) values ('WEBEX_SERVICE_GRANTED','zh_CN','科天云会议主持人账号开通成功','html','<html lang="zh" xmlns="http://www.w3.org/1999/xhtml"><head><meta charset="utf-8"/><title></title> <style>        .box {
        width: 638px;
        border-collapse: collapse;
        border-spacing: 0;
        font-family: ''Microsoft Yahei'';
    }

    body {
        font-size: 14px;
        color: #474747;
        background-color: #eaeaec;
    }

    img {
        border: 0;
    }

    .logo {
        padding: 20px 5px;
    }

    .content {
        background-color: #fff;
    }

    .title {
        padding: 40px;
        font-size: 30px;
        color: #95C55A;
    }

    .contents {
        color: #575757;
        font-size: 15px;
        padding-bottom: 15px;
        padding-left: 40px;
        padding-right: 20px;
    }

    .blue {
        color: #008fd3;
    }

    .contents-bottom {
        padding-top: 55px;
    }

    .copy {
        padding: 40px;
    }

    .copy table tr td {
        text-align: center;
        font-size: 14px;
        color: #979797;
        padding-bottom: 5px;
        width: 648px;
    }

    .copyright {
        padding-top: 10px;
    }

    .call {
        font-weight: 600;
    }

    .contents table tr td {
        width: 434px;
    }

    .contents li {
        color: #95C55A;
    }

    .contents li span {
        color: #000;
    }

    .float-r {
        border: 2px solid #058fd3;
        -moz-border-radius: 10px;
        -webkit-border-radius: 10px;
        border-radius: 10px;
        padding: 15px;
        font-size: 12px;
    }

    .table-title {
        font-size: 15px;
        padding-bottom: 20px;
    }

    .float-td {
        padding-right: 40px;
        vertical-align: top;
    }

    .contents-bottom img {
        margin-bottom: -4px;
    }    </style></head><body><table class="box" align="center" border="0" cellspacing="0" cellpadding="0"><tr><td><table border="0" cellspacing="0" cellpadding="0"><tr><td class="logo"><img src="http://www.ketianyun.com/documents/11909/50237/logo/94bf6f9e-e2d4-4719-80ce-7d9898c6e98f?t=1440840194607"/></td></tr></table></td></tr><tr><td class="content"><table border="0" cellspacing="0" cellpadding="0"><tr><td class="title"> 欢迎加入科天云</td></tr><tr><td class="contents"> 您的会议主持人账号开通成功。</td></tr><tr><td class="contents"> 您的账号为：${userName}</td></tr><tr><td class="contents"> 您可以登录 <a href=''?''>${webexSiteUrl}</a>，开始使用科天云服务。谢谢！</td></tr><tr><td class="contents"> Your KeTianYun Host account has been activated successfully.</td></tr><tr><td class="contents"> Your username: ${userName}</td></tr><tr><td class="contents"> You can login <a href=''?''>${webexSiteUrl}</a> enjoy KeTianYun Web Conference Service.Thanks! </td></tr><td class="contents-bottom" colspan=2><img src="http://www.ketianyun.com/documents/11909/50237/bottom/590340eb-a5d9-4c81-84ae-7049bb0825e1?t=1440840217362"/></td></table></td></tr><tr><td class="copy"><table border="0" cellspacing="0" cellpadding="0"><tr><td>此邮件为系统邮件，请勿直接回复。</td></tr><tr><td>如需进一步了解科天云，请访问：www.ketianyun.com</td></tr><tr><td>如有任何疑问，请拨打客服电话：400-9308838</td></tr><tr><td class="copyright">Copyright <span style="font-size:14px;font-family:Arial, Helvetica, sans-serif;">&#169;</span> 2015 广州视畅信息科技有限公司. All rights reserved. </td></tr></table></td></tr></table></body></html>');