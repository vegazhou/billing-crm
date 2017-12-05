------- 迁移 组织基本信息
INSERT INTO KT_CORE_ORG (pid, orgname, status, type, lowtouch, createtime, CRM_CUSTOMER_ID)
  select substr(du.UNIT_ID, 0, 8) || '-' || substr(du.UNIT_ID, 9, 4) || '-' || substr(du.UNIT_ID, 13, 4) || '-' || substr(du.UNIT_ID, 17, 4) || '-' || substr(du.UNIT_ID, 21, 12) as PID,
    du.alias_name, 1 as status, 1 as type, 0 as lowtouch, to_char(du.creation_date, 'yyyy-mm-dd hh24:mi:ss'), u.code as CRM_CUSTOMER_ID
  from up_org_dimension_unit du, up_org_unit u where du.parent_dimension_unit_id = '8a84fc1e4d853e14014d855636410009'and u.id = du.unit_id;



------- 设置 试用组织为 lowtouch
UPDATE KT_CORE_ORG SET LOWTOUCH = 1 WHERE PID = 'ff808081-4e8d-5cdc-014e-9752bd800009';

-------- 迁移用户基本信息
insert into KT_CORE_ORGUSER (PID, ORG_ID, USERNAME, PASSWORD, EMAIL, FULLNAME, PHONE, MOBILE, STATUS, LOCK_STATUS, PASSWORD_ALG, LOGIN_FAIL_COUNT, LOCALE, CREATE_TIME, UPDATE_TIME)
  select substr(ID, 0, 8) || '-' || substr(ID, 9, 4) || '-' || substr(ID, 13, 4) || '-' || substr(ID, 17, 4) || '-' || substr(ID, 21, 12), '',
    ACCOUNT, PASSWORD, EMAIL, NAME, OFFICE_TELEPHONE, MOBILE_TELEPHONE, decode(ACCOUNT_ENABLED, 'T', 1, 'F', 0), decode(ACCOUNT_LOCKED, 'T', 1, 'F', 0),
    'MD5', 0 ,'zh_CN', to_char(creation_date, 'yyyy-mm-dd hh24:mi:ss') , to_char(last_update_date, 'yyyy-mm-dd hh24:mi:ss')
  from up_org_user;




-------- 迁移推荐人信息
INSERT INTO KT_CORE_ORGUSER_EXT (PARANAME, PARAVALUE, UPDATE_TIME, USERID)
  select 'REFEREE' as PARANAME, u.credentials_type as PARAVALUE, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') update_time,
         substr(ID, 0, 8) || '-' || substr(ID, 9, 4) || '-' || substr(ID, 13, 4) || '-' || substr(ID, 17, 4) || '-' || substr(ID, 21, 12) as userid
  from  up_org_user u where u.credentials_type is not null;


-------- 迁移用户描述信息
INSERT INTO KT_CORE_ORGUSER_EXT (PARANAME, PARAVALUE, UPDATE_TIME, USERID)
  select 'USER_COMMENTS' as PARANAME, u.DESCRIPTION as PARAVALUE, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') update_time,
         substr(ID, 0, 8) || '-' || substr(ID, 9, 4) || '-' || substr(ID, 13, 4) || '-' || substr(ID, 17, 4) || '-' || substr(ID, 21, 12) as userid
  from  up_org_user u where u.DESCRIPTION is not null;



-------- 迁移用户组织关系
update KT_CORE_ORGUSER uu set  uu.org_id =  (select substr(tt.UNIT_ID, 0, 8) || '-' || substr(tt.UNIT_ID, 9, 4) || '-' || substr(tt.UNIT_ID, 13, 4) || '-' || substr(tt.UNIT_ID, 17, 4) || '-' || substr(tt.UNIT_ID, 21, 12) from
  (
    ( select u.id, u.account, u.name, u.password, o.unit_id, o.parent_dimension_unit_id, o.alias_name , o.unit_path
      from up_org_user u, up_org_dimension_unit o, up_org_unit_user ou
      where  ou.user_id = u.id and o.unit_id = ou.unit_id and o.PARENT_DIMENSION_UNIT_ID = '8a84fc1e4d853e14014d855636410009' )
    union
    ( select t1.id, t1.account, t1.name, t1.password , t2.unit_id, t2.parent_dimension_unit_id, t2.alias_name , t2.unit_path from
      (select u.id, u.account, u.name, u.password, o.unit_id, o.parent_dimension_unit_id, o.alias_name , o.unit_path
       from up_org_user u, up_org_dimension_unit o, up_org_unit_user ou
       where  ou.user_id = u.id and o.unit_id = ou.unit_id and o.PARENT_DIMENSION_UNIT_ID <> '8a84fc1e4d853e14014d855636410009' and o.PARENT_DIMENSION_UNIT_ID <> '-1') t1,
      up_org_dimension_unit t2 where t2.DIMENSION_UNIT_ID = substr(t1.unit_path, 35, 32))
  ) tt
where tt.account = uu.username and rownum<2);


-------- 迁移用户角色
update kt_core_orguser a set role_Id = (select roleIds from
  (select account, REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(nationality, 'normal', '001'), 'host'), 'orgAdmin', '500'), 'financialAdmin', '400'), 'superAdmin', '999'), 'temp'), ',' ,';') as roleIds
   from up_org_user ) t where t.account = a.username and rownum <2) WHERE username not in ('ciadmin@ketianyun.com', 'ec_system@ketianyun.com', 'bss_system@ketianyun.com');





--------------------------------------------------------
---  开始迁移服务定义
--------------------------------------------------------
------- MC 类服务
INSERT INTO kt_core_services (PID, TYPE, TOTAL_LICENSE, REMAINING_LICENSE, AUTO_GRANT, ORG_ID, WEBEX_SITE_URL, WEBEX_SERVICE_TYPE, DISABLED, SERVICE_NAME, CREATETIME)
  select substr(g.pid, 0, 8) || '-' || substr(g.pid, 9, 4) || '-' || substr(g.pid, 13, 4) || '-' || substr(g.pid, 17, 4) || '-' || substr(g.pid, 21, 12) as pid,
         'WEBEX' as TYPE, mclicense, mclicense, 0 AS AUTO_GRANT,
         substr(t.UNITID, 0, 8) || '-' || substr(t.UNITID, 9, 4) || '-' || substr(t.UNITID, 13, 4) || '-' || substr(t.UNITID, 17, 4) || '-' || substr(t.UNITID, 21, 12) AS ORG_ID,
    url, 'MC', 0 as disabled,  sitename, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')
  from  CI_USER_SITE_RELATION_T t, (SELECT LOWER(sys_guid()) as pid FROM DUAL) g where t.ifbssinfo='1' and sitename not like '%_失效' and unitid is not null and mclicense > 0 ;

------- EC 类服务
INSERT INTO kt_core_services (PID, TYPE, TOTAL_LICENSE, REMAINING_LICENSE, AUTO_GRANT, ORG_ID, WEBEX_SITE_URL, WEBEX_SERVICE_TYPE, DISABLED, SERVICE_NAME, CREATETIME)
select substr(g.pid, 0, 8) || '-' || substr(g.pid, 9, 4) || '-' || substr(g.pid, 13, 4) || '-' || substr(g.pid, 17, 4) || '-' || substr(g.pid, 21, 12) as pid,
       'WEBEX' as TYPE, eclicense, eclicense, 0 AS AUTO_GRANT,
       substr(t.UNITID, 0, 8) || '-' || substr(t.UNITID, 9, 4) || '-' || substr(t.UNITID, 13, 4) || '-' || substr(t.UNITID, 17, 4) || '-' || substr(t.UNITID, 21, 12) AS ORG_ID,
  url, 'EC', 0 as disabled,  sitename, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')
from  CI_USER_SITE_RELATION_T t, (SELECT LOWER(sys_guid()) as pid FROM DUAL) g where t.ifbssinfo='1' and sitename not like '%_失效' and unitid is not null and eclicense > 0 ;

------- TC类服务
INSERT INTO kt_core_services (PID, TYPE, TOTAL_LICENSE, REMAINING_LICENSE, AUTO_GRANT, ORG_ID, WEBEX_SITE_URL, WEBEX_SERVICE_TYPE, DISABLED, SERVICE_NAME, CREATETIME)
select substr(g.pid, 0, 8) || '-' || substr(g.pid, 9, 4) || '-' || substr(g.pid, 13, 4) || '-' || substr(g.pid, 17, 4) || '-' || substr(g.pid, 21, 12) as pid,
       'WEBEX' as TYPE, tclicense, tclicense, 0 AS AUTO_GRANT,
       substr(t.UNITID, 0, 8) || '-' || substr(t.UNITID, 9, 4) || '-' || substr(t.UNITID, 13, 4) || '-' || substr(t.UNITID, 17, 4) || '-' || substr(t.UNITID, 21, 12) AS ORG_ID,
  url, 'TC', 0 as disabled,  sitename, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')
from  CI_USER_SITE_RELATION_T t, (SELECT LOWER(sys_guid()) as pid FROM DUAL) g where t.ifbssinfo='1' and sitename not like '%_失效' and unitid is not null and tclicense > 0 ;

------- SC类服务
INSERT INTO kt_core_services (PID, TYPE, TOTAL_LICENSE, REMAINING_LICENSE, AUTO_GRANT, ORG_ID, WEBEX_SITE_URL, WEBEX_SERVICE_TYPE, DISABLED, SERVICE_NAME, CREATETIME)
select substr(g.pid, 0, 8) || '-' || substr(g.pid, 9, 4) || '-' || substr(g.pid, 13, 4) || '-' || substr(g.pid, 17, 4) || '-' || substr(g.pid, 21, 12) as pid,
       'WEBEX' as TYPE, sclicense, sclicense, 0 AS AUTO_GRANT,
       substr(t.UNITID, 0, 8) || '-' || substr(t.UNITID, 9, 4) || '-' || substr(t.UNITID, 13, 4) || '-' || substr(t.UNITID, 17, 4) || '-' || substr(t.UNITID, 21, 12) AS ORG_ID,
  url, 'SC', 0 as disabled,  sitename, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')
from  CI_USER_SITE_RELATION_T t, (SELECT LOWER(sys_guid()) as pid FROM DUAL) g where t.ifbssinfo='1' and sitename not like '%_失效' and unitid is not null and sclicense > 0 ;

------- EE类服务
INSERT INTO kt_core_services (PID, TYPE, TOTAL_LICENSE, REMAINING_LICENSE, AUTO_GRANT, ORG_ID, WEBEX_SITE_URL, WEBEX_SERVICE_TYPE, DISABLED, SERVICE_NAME, CREATETIME)
select substr(g.pid, 0, 8) || '-' || substr(g.pid, 9, 4) || '-' || substr(g.pid, 13, 4) || '-' || substr(g.pid, 17, 4) || '-' || substr(g.pid, 21, 12) as pid,
       'WEBEX' as TYPE, eelicense, eelicense, 0 AS AUTO_GRANT,
       substr(t.UNITID, 0, 8) || '-' || substr(t.UNITID, 9, 4) || '-' || substr(t.UNITID, 13, 4) || '-' || substr(t.UNITID, 17, 4) || '-' || substr(t.UNITID, 21, 12) AS ORG_ID,
  url, 'EE', 0 as disabled,  sitename, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')
from  CI_USER_SITE_RELATION_T t, (SELECT LOWER(sys_guid()) as pid FROM DUAL) g where t.ifbssinfo='1' and sitename not like '%_失效' and unitid is not null and eelicense > 0 ;


------- ketianyun lowtouch org 服务
INSERT INTO KT_CORE_SERVICES (PID, TYPE, TOTAL_LICENSE, REMAINING_LICENSE, AUTO_GRANT, ORG_ID, WEBEX_SITE_URL, WEBEX_SERVICE_TYPE, DISABLED, SERVICE_NAME, CREATETIME) VALUES
  ('82ee4c32-e981-4ae6-8950-ea8264ba126b', 'WEBEX', 9999999, 9999999, 1, 'ff808081-4e8d-5cdc-014e-9752bd800009', 'ketianyun.webex.com.cn', 'MC', 0, 'WebEx注册用户试用服务', TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'));


---------------------------------------------------------
--- 开始迁移服务授权数据
---------------------------------------------------------
update ci_user_host_relation_t set url = REPLACE(URL, '/', '');



--------- 迁移 ketianyun WebEx试用服务
INSERT INTO KT_CORE_USER_SERVICE_GRANT (pid, userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled)
  SELECT substr(u1.pid, 0, 8) || '-' || substr(u1.pid, 9, 4) || '-' || substr(u1.pid, 13, 4) || '-' || substr(u1.pid, 17, 4) || '-' || substr(u1.pid, 21, 12) as pid,
    userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled from
    ( select lower(sys_guid()) as pid, u.pid as userid, '82ee4c32-e981-4ae6-8950-ea8264ba126b' as serviceid, TO_CHAR(hostcreatetime, 'yyyy-mm-dd hh24:mi:ss') as granttime,
             0 as mc_enabled, 0 as ec_enabled, 0 as tc_enabled, 0 as sc_enabled
      from ci_user_host_relation_t t, kt_core_orguser u where t.hostaccount = u.username and SITENAME = 'ketianyun') u1;



--------- MC only 的服务
INSERT INTO KT_CORE_USER_SERVICE_GRANT (pid, userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled)
SELECT substr(u1.pid, 0, 8) || '-' || substr(u1.pid, 9, 4) || '-' || substr(u1.pid, 13, 4) || '-' || substr(u1.pid, 17, 4) || '-' || substr(u1.pid, 21, 12) as pid,
  userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled from
  ( SELECT u.username as uname, LOWER(sys_guid()) as pid, u.pid as userid, s.pid as serviceid, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') as granttime,
           0 as mc_enabled, 0 as ec_enabled, 0 as tc_enabled, 0 as sc_enabled
    FROM ci_user_host_relation_t t, kt_core_orguser u, kt_core_services s
    where u.username = t.hostaccount and s.webex_site_url = t.URL and t.HOSTLICENSEKIND = 'mc' and s.WEBEX_SERVICE_TYPE = 'MC') u1;

-------- EE的服务 mc,ec,tc,sc全开的人
INSERT INTO KT_CORE_USER_SERVICE_GRANT (pid, userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled)
SELECT substr(u1.pid, 0, 8) || '-' || substr(u1.pid, 9, 4) || '-' || substr(u1.pid, 13, 4) || '-' || substr(u1.pid, 17, 4) || '-' || substr(u1.pid, 21, 12) as pid,
  userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled from
  ( SELECT u.username as uname, LOWER(sys_guid()) as pid, u.pid as userid, s.pid as serviceid, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') as granttime,
           1 as mc_enabled, 1 as ec_enabled, 1 as tc_enabled, 1 as sc_enabled
    FROM ci_user_host_relation_t t, kt_core_orguser u, kt_core_services s
    where u.username = t.hostaccount and s.webex_site_url = t.URL and t.HOSTLICENSEKIND = 'mc,ec,tc,sc,ee' and s.WEBEX_SERVICE_TYPE = 'EE') u1;

-------- EE的服务 only mc开放的人
INSERT INTO KT_CORE_USER_SERVICE_GRANT (pid, userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled)
SELECT substr(u1.pid, 0, 8) || '-' || substr(u1.pid, 9, 4) || '-' || substr(u1.pid, 13, 4) || '-' || substr(u1.pid, 17, 4) || '-' || substr(u1.pid, 21, 12) as pid,
  userid, serviceid, granttime, mc_enabled, ec_enabled, tc_enabled, sc_enabled from
  ( SELECT u.username as uname, LOWER(sys_guid()) as pid, u.pid as userid, s.pid as serviceid, TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') as granttime,
           1 as mc_enabled, 0 as ec_enabled, 0 as tc_enabled, 0 as sc_enabled
    FROM ci_user_host_relation_t t, kt_core_orguser u, kt_core_services s
    where u.username = t.hostaccount and s.webex_site_url = t.URL and t.HOSTLICENSEKIND = 'mc,ee' and s.WEBEX_SERVICE_TYPE = 'EE') u1;


-------- 计算Service的total license, 拥有服务的用户数 + remaining license
update kt_core_services svc set svc.total_license =
(select t2.total from
  (select t1.pid, (t1.license_used + t1.TOTAL_LICENSE) as total from
    (select s.pid, count(sg.pid) as license_used , s.TOTAL_LICENSE from KT_CORE_USER_SERVICE_GRANT sg right join kt_core_services s
        on s.pid = sg.serviceid GROUP BY s.pid, s.TOTAL_LICENSE) t1) t2 where t2.pid = svc.pid);



update KT_CORE_ORGUSER set username = lower(username);