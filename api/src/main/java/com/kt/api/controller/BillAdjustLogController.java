package com.kt.api.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.billing.FormalBillTuneLog;
import com.kt.service.SearchFilter;
import com.kt.service.billing.AccountService;
import com.kt.service.billing.BillService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Garfield Chen on 2016/5/6.
 */
@Controller
@RequestMapping("/billadjustlog")
public class BillAdjustLogController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(BillAdjustLogController.class);

    @Autowired
    BillService billService;
    
    @RequestMapping(value = "/query")
    @ResponseBody
    public String list(HttpServletRequest request) throws SQLException {
        DataTableVo dt = this.parseData4DT(request);
        String sserviceid = (String) request.getParameter("s_sserviceid2");
       
        String sortedColumnId = (String) request.getParameter("iSortCol_0");
        String sortedOrder = (String) request.getParameter("sSortDir_0");
        String state = (String) request.getParameter("s_sstate");
        int curpage = 0;
        int totalSize = 0;
        if (dt.getiDisplayStart() > 0) {
            curpage = dt.getiDisplayStart() / dt.getiDisplayLength();
        }
        // logger.debug("list systemuser =" + saccount + "; sphone=" + sphone +
        // "; sname=" + sname); // "aphone=" + aphone + "; aacount=" + aacount
        // );

        SearchFilter searchorg = new SearchFilter();
        curpage = curpage + 1;
        if (StringUtils.isNotBlank(sserviceid)) {
            searchorg.setCustomerId(sserviceid);
        }

    


        String[] supportedOrderFields = new String[]{"SEQUENCE_ID", "PRIMAL_AMOUNT", "CURRENT_AMOUNT"};

        if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
            int orderIndex = Integer.parseInt(sortedColumnId);

            if (orderIndex < supportedOrderFields.length) {
                String orderFieldName = supportedOrderFields[orderIndex];
                if (orderFieldName != null) {
                    searchorg.setOrderByField(orderFieldName);
                    if (sortedOrder != null
                            && sortedOrder.toLowerCase().equals(ORDER_DESC)) {
                        searchorg.setOrderType(ORDER_DESC);
                    } else {
                        searchorg.setOrderType(ORDER_ASC);
                    }
                }
            }
        }

        searchorg.setPageSize(1000000);
        try {
            handleDataTable(dt, curpage, totalSize, searchorg);
        } catch (Exception e) {
            LOGGER.error("list orgs failed", e);
            return FAIL;
        }
        // dt.setsEcho(String.valueOf(curpage+1));
        String json = this.formateData2DT(dt);
        return json;

    }

    public void handleDataTable(DataTableVo dt, int curpage, int totalSize,
                                SearchFilter searchorg) throws Exception {
        Page<FormalBillTuneLog> page = null;
        List<FormalBillTuneLog> bills = null;
        try {
            page = billService.listAllByPageForFormalBillLog(curpage, searchorg);
            bills = page.getData();
            totalSize = page.getRecordCount();
        } catch (Exception e) {
            // logger.error("list Exception:"+e);
            throw new Exception("Error in list orgs!", e);
        }

        if (bills != null) {
            List<TableData> tds = new ArrayList<TableData>();
            for (int i = 0, n = bills.size(); i < n; i++) {
            	FormalBillTuneLog bill = bills.get(i);

                TableData td = new TableData();
              
                td.setCurrentAmount(new BigDecimal(bill.getCurrentAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                td.setPrimalAmount(new BigDecimal(bill.getPrimalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                td.setOperator(bill.getOperator());
                td.setSequence(bill.getSequenceId());
                td.setComments(bill.getComments());
                tds.add(td);
            }
            dt.setData(tds);
            dt.setiTotalRecords(totalSize);

            // logger.debug("completed table Handling");
        }
    }

    private String truncateDate(String date) {
        if (StringUtils.isBlank(date)) {
            return "";
        }
        if (date.length() >= 10) {
            return StringUtils.left(date, 10);
        } else {
            return date;
        }
    }

    private class TableData {

          
        private Long sequence;
        private String primalAmount ;
        private String currentAmount ;
        private String operator = "";
        private String comments;
        

        public TableData() {

        }


		public Long getSequence() {
			return sequence;
		}


		public void setSequence(Long sequence) {
			this.sequence = sequence;
		}


		public String getPrimalAmount() {
			return primalAmount;
		}


		public void setPrimalAmount(String primalAmount) {
			this.primalAmount = primalAmount;
		}


		public String getCurrentAmount() {
			return currentAmount;
		}


		public void setCurrentAmount(String currentAmount) {
			this.currentAmount = currentAmount;
		}


		public String getOperator() {
			return operator;
		}


		public void setOperator(String operator) {
			this.operator = operator;
		}


		public String getComments() {
			return comments;
		}


		public void setComments(String comments) {
			this.comments = comments;
		}

     
		
      


    }

}
