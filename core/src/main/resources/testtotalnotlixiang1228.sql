declare   
   v_number int:=1;
   monthLater int:=0;
   monthEarly int:=0;
   intervalnum int:=1;
   price float:=0.0;
   beforeprice float:=0.0;
  cursor csr_dept_normal_1
             is
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,b.ATTRIBUTE_VALUE as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=1 and b.ATTRIBUTE_VALUE!=1 and d.id=a.charge_id and (d.type='MONTHLY_PAY_BY_HOSTS' or d.type='MONTHLY_PAY_BY_PORTS' or d.type='MONTHLY_PAY_BY_STORAGE')
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
(b.ATTRIBUTE_NAME='HOSTS_AMOUNT' or b.ATTRIBUTE_NAME='PORTS_AMOUNT' or b.ATTRIBUTE_NAME='STORAGE_SIZE'))t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='COMMON_UNIT_PRICE'
union all
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,'1' as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=1 and b.ATTRIBUTE_VALUE!=1 and d.id=a.charge_id and d.type='PSTN_MONTHLY_PACKET' 
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t)t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='MONTHLY_FEE';
row_dept csr_dept_normal_1%rowtype; 
   cursor csr_dept_normal_3
             is
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,b.ATTRIBUTE_VALUE as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=3 and d.id=a.charge_id and (d.type='MONTHLY_PAY_BY_HOSTS' or d.type='MONTHLY_PAY_BY_PORTS' OR d.type='MONTHLY_PAY_BY_STORAGE')
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
(b.ATTRIBUTE_NAME='HOSTS_AMOUNT' or b.ATTRIBUTE_NAME='PORTS_AMOUNT' OR b.ATTRIBUTE_NAME='STORAGE_SIZE'))t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='COMMON_UNIT_PRICE'
union all
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,'1' as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=3 and d.id=a.charge_id and d.type='PSTN_MONTHLY_PACKET' 
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t)t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='MONTHLY_FEE';
row_dept_3 csr_dept_normal_3%rowtype;  

      cursor csr_dept_normal_6
             is
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,b.ATTRIBUTE_VALUE as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=6 and d.id=a.charge_id and (d.type='MONTHLY_PAY_BY_HOSTS' or d.type='MONTHLY_PAY_BY_PORTS' OR d.type='MONTHLY_PAY_BY_STORAGE')
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
(b.ATTRIBUTE_NAME='HOSTS_AMOUNT' or b.ATTRIBUTE_NAME='PORTS_AMOUNT'))t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='COMMON_UNIT_PRICE'
union all
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,'1' as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=6 and d.id=a.charge_id and d.type='PSTN_MONTHLY_PACKET' 
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t)t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='MONTHLY_FEE';
row_dept_6 csr_dept_normal_6%rowtype; 

   cursor csr_dept_normal_12
             is
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,b.ATTRIBUTE_VALUE as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=12 and d.id=a.charge_id and (d.type='MONTHLY_PAY_BY_HOSTS' or d.type='MONTHLY_PAY_BY_PORTS' OR d.type='MONTHLY_PAY_BY_STORAGE')
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
(b.ATTRIBUTE_NAME='HOSTS_AMOUNT' or b.ATTRIBUTE_NAME='PORTS_AMOUNT' OR b.ATTRIBUTE_NAME='STORAGE_SIZE'))t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='COMMON_UNIT_PRICE'
union all
select t.*,b.ATTRIBUTE_VALUE as price from (select t.* ,'1' as UNIT from(select c.code,e.DISPLAY_NAME, a.first_installment, substr(a.effectivestartdate,0,10) as start_month ,substr(a.effectiveenddate,0,10) as end_month ,a.charge_id,b.ATTRIBUTE_VALUE as contractterm  
from b_order a ,B_CHARGE_SCHEME_ATTRIBUTES b , b_customer c,B_CHARGE_SCHEME d,b_product e where a.charge_id=b.SCHEME_ID and b.ATTRIBUTE_NAME='MONTH_AMOUNT' and e.pid=a.product_id
and a.state='IN_EFFECT' and c.pid= a.customer_id and a.pay_interval=12 and d.id=a.charge_id and d.type='PSTN_MONTHLY_PACKET' 
and a.customer_id!='f5ac0bc7-d990-407c-8629-b304578f59b0' and substr(a.effectivestartdate,0,7)<to_char(add_months(trunc(sysdate),1),'yyyy-mm') 
and substr(a.EFFECTIVEENDDATE,0,7)>=to_char(add_months(trunc(sysdate),1),'yyyy-mm') and b.attribute_value!=0) t)t ,B_CHARGE_SCHEME_ATTRIBUTES b where  t.charge_id=b.SCHEME_ID and
b.ATTRIBUTE_NAME='MONTHLY_FEE';
row_dept_12 csr_dept_normal_12%rowtype;  
begin
      --for循环
       for row_dept in csr_dept_normal_1 loop
        monthEarly :=months_between(to_date(to_char(add_months(trunc(sysdate),1),'yyyymm'), 'yyyymm'),to_date(substr(row_dept.start_month,0,7), 'yyyy-mm'));

        if (monthEarly<row_dept.contractterm and monthEarly>1) or (
        monthEarly=row_dept.contractterm and substr(row_dept.start_month,9,2)>15 and monthEarly>1)
       then 
            price:=row_dept.unit*row_dept.price/1.0672;
			beforeprice:=row_dept.unit*row_dept.price;
            insert into b_bill_invoice (PID,INVOICE_NAME, DOCUMENT_TYPE,DOCUMENT_DATE,CUSTOMER_ID,SALES_CHANNEL,CONTRACT_COMMENCE,CONTRACT_TERM,PAYMENT_INTERVAL,REVENUE_TYPE,PRODUCT_DESC,PRODUCT_TYPE,UNIT,LISTED_PRICE,INVOICED_AMOUNT,NET_INVOICED_AMOUNT,PAYMENT_INTERVAL_DB,CREATE_DATE,BEFORE_TAX_VALUE) 
                                      values(row_dept.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||to_char(sysdate,'yyyy-mm-dd hh:mm:ss')||v_number,row_dept.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||1,'Invoice',to_char(sysdate,'yyyy/mm/dd'),row_dept.code,'Direct',row_dept.start_month,row_dept.contractterm,row_dept.contractterm,'Network subscription fee',row_dept.display_name,row_dept.display_name,row_dept.unit,price,price,price,1,to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),beforeprice);
                v_number:=v_number+1;                         
      
       end if;
       commit;
       end loop;
      --for循环
       for row_dept_3 in csr_dept_normal_3 loop
                intervalnum:=ceil(row_dept_3.contractterm/3);
                monthLater :=months_between(to_date(to_char(add_months(trunc(sysdate),0),'yyyymm'), 'yyyymm'),to_date(substr(row_dept_3.start_month,0,7), 'yyyy-mm'));
                monthEarly :=months_between(to_date(to_char(add_months(trunc(sysdate),1),'yyyymm'), 'yyyymm'),to_date(substr(row_dept_3.start_month,0,7), 'yyyy-mm'));
        if monthLater<row_dept_3.contractterm and   MOD(monthLater,3)=0 and substr(row_dept_3.start_month,9,2)>15 and monthLater>0
        
        then 
                if row_dept_3.contractterm-monthLater<3
                then 
                     price:=(row_dept_3.contractterm-monthLater)*row_dept_3.unit*row_dept_3.price/1.0672; 
					 beforeprice:=(row_dept_3.contractterm-monthLater)*row_dept_3.unit*row_dept_3.price; 					 
                else
                     price:=3*row_dept_3.unit*row_dept_3.price/1.0672;
					 beforeprice:=3*row_dept_3.unit*row_dept_3.price;
                end if;
                insert into b_bill_invoice (PID,INVOICE_NAME, DOCUMENT_TYPE,DOCUMENT_DATE,CUSTOMER_ID,SALES_CHANNEL,CONTRACT_COMMENCE,CONTRACT_TERM,PAYMENT_INTERVAL,REVENUE_TYPE,PRODUCT_DESC,PRODUCT_TYPE,UNIT,LISTED_PRICE,INVOICED_AMOUNT,NET_INVOICED_AMOUNT,PAYMENT_INTERVAL_DB,CREATE_DATE,BEFORE_TAX_VALUE) 
                                      values(row_dept_3.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||to_char(sysdate,'yyyy-mm-dd hh:mm:ss')||'-'||v_number,row_dept_3.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||1,'Invoice',to_char(sysdate,'yyyy/mm/dd'),row_dept_3.code,'Direct',row_dept_3.start_month,row_dept_3.contractterm,intervalnum,'Network subscription fee',row_dept_3.display_name,row_dept_3.display_name,row_dept_3.unit,price,price,price,1,to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),beforeprice);
                v_number:=v_number+1;                           
       elsif monthEarly<row_dept_3.contractterm and  MOD(monthEarly,3)=0 and substr(row_dept_3.start_month,9,2)<=15
       
       then 
                if row_dept_3.contractterm-monthEarly<3
                then 
                     price:=(row_dept_3.contractterm-monthEarly)*row_dept_3.unit*row_dept_3.price/1.0672;
					 beforeprice:=(row_dept_3.contractterm-monthEarly)*row_dept_3.unit*row_dept_3.price;                      
                else
                     price:=3*row_dept_3.unit*row_dept_3.price/1.0672;
					 beforeprice:=3*row_dept_3.unit*row_dept_3.price;
                end if;
                insert into b_bill_invoice (PID,INVOICE_NAME, DOCUMENT_TYPE,DOCUMENT_DATE,CUSTOMER_ID,SALES_CHANNEL,CONTRACT_COMMENCE,CONTRACT_TERM,PAYMENT_INTERVAL,REVENUE_TYPE,PRODUCT_DESC,PRODUCT_TYPE,UNIT,LISTED_PRICE,INVOICED_AMOUNT,NET_INVOICED_AMOUNT,PAYMENT_INTERVAL_DB,CREATE_DATE,BEFORE_TAX_VALUE) 
                                      values(row_dept_3.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||to_char(sysdate,'yyyy-mm-dd hh:mm:ss')||'-'||v_number,row_dept_3.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||1,'Invoice',to_char(sysdate,'yyyy/mm/dd'),row_dept_3.code,'Direct',row_dept_3.start_month,row_dept_3.contractterm,intervalnum,'Network subscription fee',row_dept_3.display_name,row_dept_3.display_name,row_dept_3.unit,price,price,price,1,to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),beforeprice);
                     v_number:=v_number+1;    
       end if;
       commit;
       end loop;
       
       --for循环
       for row_dept_6 in csr_dept_normal_6 loop
                intervalnum:=ceil(row_dept_6.contractterm/6);
                monthLater :=months_between(to_date(to_char(add_months(trunc(sysdate),0),'yyyymm'), 'yyyymm'),to_date(substr(row_dept_6.start_month,0,7), 'yyyy-mm'));
                monthEarly :=months_between(to_date(to_char(add_months(trunc(sysdate),1),'yyyymm'), 'yyyymm'),to_date(substr(row_dept_6.start_month,0,7), 'yyyy-mm'));
        if monthLater<row_dept_6.contractterm and   MOD(monthLater,6)=0 and substr(row_dept_6.start_month,9,2)>15 and monthLater>0
        
        then 
                if row_dept_6.contractterm-monthLater<6
                then 
                     price:=(row_dept_6.contractterm-monthLater)*row_dept_6.unit*row_dept_6.price/1.0672; 
					 beforeprice:=(row_dept_6.contractterm-monthLater)*row_dept_6.unit*row_dept_6.price; 					 
                else
                     price:=6*row_dept_6.unit*row_dept_6.price/1.0672;
					 beforeprice:=6*row_dept_6.unit*row_dept_6.price;
                end if;
                insert into b_bill_invoice (PID,INVOICE_NAME, DOCUMENT_TYPE,DOCUMENT_DATE,CUSTOMER_ID,SALES_CHANNEL,CONTRACT_COMMENCE,CONTRACT_TERM,PAYMENT_INTERVAL,REVENUE_TYPE,PRODUCT_DESC,PRODUCT_TYPE,UNIT,LISTED_PRICE,INVOICED_AMOUNT,NET_INVOICED_AMOUNT,PAYMENT_INTERVAL_DB,CREATE_DATE,BEFORE_TAX_VALUE) 
                                      values(row_dept_6.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||to_char(sysdate,'yyyy-mm-dd hh:mm:ss')||'-'||v_number,row_dept_6.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||1,'Invoice',to_char(sysdate,'yyyy/mm/dd'),row_dept_6.code,'Direct',row_dept_6.start_month,row_dept_6.contractterm,intervalnum,'Network subscription fee',row_dept_6.display_name,row_dept_6.display_name,row_dept_6.unit,price,price,price,1,to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),beforeprice);
                v_number:=v_number+1;                           
       elsif monthEarly<row_dept_6.contractterm and  MOD(monthEarly,6)=0 and substr(row_dept_6.start_month,9,2)<=15
       
       then 
                if row_dept_6.contractterm-monthEarly<6
                then 
                     price:=(row_dept_6.contractterm-monthEarly)*row_dept_6.unit*row_dept_6.price/1.0672;   
					 beforeprice:=(row_dept_6.contractterm-monthEarly)*row_dept_6.unit*row_dept_6.price;					 
                else
                     price:=6*row_dept_6.unit*row_dept_6.price/1.0672;
					 beforeprice:=6*row_dept_6.unit*row_dept_6.price;
                end if;
                insert into b_bill_invoice (PID,INVOICE_NAME, DOCUMENT_TYPE,DOCUMENT_DATE,CUSTOMER_ID,SALES_CHANNEL,CONTRACT_COMMENCE,CONTRACT_TERM,PAYMENT_INTERVAL,REVENUE_TYPE,PRODUCT_DESC,PRODUCT_TYPE,UNIT,LISTED_PRICE,INVOICED_AMOUNT,NET_INVOICED_AMOUNT,PAYMENT_INTERVAL_DB,CREATE_DATE,BEFORE_TAX_VALUE) 
                                      values(row_dept_6.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||to_char(sysdate,'yyyy-mm-dd hh:mm:ss')||'-'||v_number,row_dept_6.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||1,'Invoice',to_char(sysdate,'yyyy/mm/dd'),row_dept_6.code,'Direct',row_dept_6.start_month,row_dept_6.contractterm,intervalnum,'Network subscription fee',row_dept_6.display_name,row_dept_6.display_name,row_dept_6.unit,price,price,price,1,to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),beforeprice);
                     v_number:=v_number+1;    
       end if;
       commit;
       end loop;
      --for循环
       for row_dept_12 in csr_dept_normal_12 loop
                intervalnum:=ceil(row_dept_12.contractterm/12);
                monthLater :=months_between(to_date(to_char(add_months(trunc(sysdate),0),'yyyymm'), 'yyyymm'),to_date(substr(row_dept_12.start_month,0,7), 'yyyy-mm'));
                monthEarly :=months_between(to_date(to_char(add_months(trunc(sysdate),1),'yyyymm'), 'yyyymm'),to_date(substr(row_dept_12.start_month,0,7), 'yyyy-mm'));
        if monthLater<row_dept_12.contractterm and   MOD(monthLater,12)=0 and substr(row_dept_12.start_month,9,2)>15 and monthLater>0
        
        then 
                if row_dept_12.contractterm-monthLater<12
                then 
                     price:=(row_dept_12.contractterm-monthLater)*row_dept_12.unit*row_dept_12.price/1.0672; 
					 beforeprice:=(row_dept_12.contractterm-monthLater)*row_dept_12.unit*row_dept_12.price; 					 
                else
                     price:=12*row_dept_12.unit*row_dept_12.price/1.0672;
					 beforeprice:=12*row_dept_12.unit*row_dept_12.price;
                end if;
                insert into b_bill_invoice (PID,INVOICE_NAME, DOCUMENT_TYPE,DOCUMENT_DATE,CUSTOMER_ID,SALES_CHANNEL,CONTRACT_COMMENCE,CONTRACT_TERM,PAYMENT_INTERVAL,REVENUE_TYPE,PRODUCT_DESC,PRODUCT_TYPE,UNIT,LISTED_PRICE,INVOICED_AMOUNT,NET_INVOICED_AMOUNT,PAYMENT_INTERVAL_DB,CREATE_DATE,BEFORE_TAX_VALUE) 
                                      values(row_dept_12.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||to_char(sysdate,'yyyy-mm-dd hh:mm:ss')||'-'||v_number,row_dept_12.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||1,'Invoice',to_char(sysdate,'yyyy/mm/dd'),row_dept_12.code,'Direct',row_dept_12.start_month,row_dept_12.contractterm,intervalnum,'Network subscription fee',row_dept_12.display_name,row_dept_12.display_name,row_dept_12.unit,price,price,price,1,to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),beforeprice);
                v_number:=v_number+1;                           
       elsif monthEarly<row_dept_12.contractterm and  MOD(monthEarly,12)=0 and substr(row_dept_12.start_month,9,2)<=15
       
       then 
                if row_dept_12.contractterm-monthEarly<12
                then 
                     price:=(row_dept_12.contractterm-monthEarly)*row_dept_12.unit*row_dept_12.price/1.0672; 
					 beforeprice:=(row_dept_12.contractterm-monthEarly)*row_dept_12.unit*row_dept_12.price; 					 
                else
                     price:=12*row_dept_12.unit*row_dept_12.price/1.0672;
					 beforeprice:=12*row_dept_12.unit*row_dept_12.price;
                end if;
                insert into b_bill_invoice (PID,INVOICE_NAME, DOCUMENT_TYPE,DOCUMENT_DATE,CUSTOMER_ID,SALES_CHANNEL,CONTRACT_COMMENCE,CONTRACT_TERM,PAYMENT_INTERVAL,REVENUE_TYPE,PRODUCT_DESC,PRODUCT_TYPE,UNIT,LISTED_PRICE,INVOICED_AMOUNT,NET_INVOICED_AMOUNT,PAYMENT_INTERVAL_DB,CREATE_DATE,BEFORE_TAX_VALUE) 
                                      values(row_dept_12.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||to_char(sysdate,'yyyy-mm-dd hh:mm:ss')||'-'||v_number,row_dept_12.code||'-'||to_char(add_months(trunc(sysdate),1),'yyyymm')||'01-'||1,'Invoice',to_char(sysdate,'yyyy/mm/dd'),row_dept_12.code,'Direct',row_dept_12.start_month,row_dept_12.contractterm,intervalnum,'Network subscription fee',row_dept_12.display_name,row_dept_12.display_name,row_dept_12.unit,price,price,price,1,to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),beforeprice);
                     v_number:=v_number+1;    
       end if;
       commit;
       end loop;
       --CLOSE csr_dept_normal_1;
end;

