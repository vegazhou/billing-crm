package com.kt.api.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kt.api.common.APIConstants;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.api.util.JsonRespWrapper;
import com.kt.entity.mysql.billing.ContractPdf;
import com.kt.repo.mongo.entity.ContractPdfBean;
import com.kt.repo.mongo.repository.ContractPdfEntityRepository;
import com.kt.repo.mysql.batch.ContractPdfRepository;
import com.kt.service.ContractService;
import com.kt.util.DateUtil;
import com.mongodb.gridfs.GridFSDBFile;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import wang.huaichao.utils.FileUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Garfield chen on 2016/9/28.
 */
@Controller
@RequestMapping("/contractpdf")
public class ContractPdfController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(ContractPdfController.class);

    @Autowired
    private ContractService contractService;  
    
    @Autowired
    ContractPdfEntityRepository contractPdfEntityRepository; 
    
    @Autowired
    ContractPdfRepository contractPdfRepository;
    
    @Autowired
    GridFsOperations operations;
    
    @RequestMapping("/upload/{contractid}/{number}")
	@ResponseBody
	public Object upload(MultipartFile file, @PathVariable String contractid,@PathVariable String number){
		LOGGER.debug("upload: contractid=" + contractid + file.getOriginalFilename());

	
		try {
			
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			if (fileType.toLowerCase().equals("pdf") ) {
				//file.getInputStream()
				ByteArrayOutputStream baos = FileUtils.readRaw(file.getInputStream());
				ContractPdf contractPdf=new ContractPdf();
				contractPdf.setContractId(contractid);
				contractPdf.setContractNumber(number);
				contractPdf.setPdfName(fileName);
				contractPdf.setCreateTime(DateUtil.now());
			    contractService.createPdf(contractPdf, baos);
				
			}else{
				return JsonRespWrapper.error("文件格式不对，必须为PDF格式！").json("detail", "文件格式不对，必须为PDF格式！");
				
			}
			
		}catch(IOException ie){
			LOGGER.error("IOException on reading uplaoded file!", ie);
		}catch(Exception e){
			LOGGER.error("Exception on reading uplaoded file!", e);
		}
		return JsonRespWrapper.success("合同上传成功");

	}
    
    
    @RequestMapping("/uploadlarge/{contractid}/{number}")
	@ResponseBody
	public Object uploadlarge(MultipartFile file, @PathVariable String contractid,@PathVariable String number){
		LOGGER.debug("uploadlarge: contractid=" + contractid + file.getOriginalFilename());
		
	
		try {
			
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
			
				
				
				ContractPdf contractPdf=new ContractPdf();
				contractPdf.setContractId(contractid);
				contractPdf.setContractNumber(number);
				contractPdf.setPdfName(fileName);
				contractPdf.setCreateTime(DateUtil.now());
			    contractService.createPdfLargeSize(contractPdf, file);
				
		
			
		}catch(IOException ie){
			LOGGER.error("IOException on reading uplaodedlarge file!", ie);
			JsonRespWrapper.error("文件格式不对，必须为PDF格式！").json("detail", ie.getMessage());
		}catch(Exception e){
			LOGGER.error("Exception on reading uplaodedlarge file!", e);
			JsonRespWrapper.error("文件格式不对，必须为PDF格式！").json("detail", e.getMessage());
		}
		return JsonRespWrapper.success("合同上传成功");

	}
    
    
    @RequestMapping(value = "/exportpdf/{pdfpid}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
   	@ResponseStatus(HttpStatus.NO_CONTENT)
   	public  void exportpdf(HttpServletResponse response,@PathVariable String pdfpid) {
   		try {  			
   			
   		    ContractPdfBean pdfFile	= contractPdfEntityRepository.findById(pdfpid);
   		    InputStream inputStream	=	new ByteArrayInputStream(pdfFile.getPdfContent());
   		    ContractPdf pdfFileName=contractPdfRepository.findOne(pdfpid);
   			String fileName= pdfFileName.getPdfName();
   			response.setContentType("multipart/form-data");  
   	      
   	        response.setHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(fileName, "UTF-8")); 
   			//FileInputStream inputStream = new FileInputStream(file);  
   			
       
   			ServletOutputStream out = response.getOutputStream();  
  
   			int len; 
           
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) != -1) {
            	out.write(buffer, 0, len);
            }
          
            inputStream.close();  
            out.close();  
            out.flush();  
  
 			 

   			
   			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
   		} catch (Exception e) {
   			LOGGER.error("error export pdf bill ", e);
   		}
   		
   		

   	}
    
    
    
    @RequestMapping(value = "/exportpdflarge/{pdfpid}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
   	@ResponseStatus(HttpStatus.NO_CONTENT)
   	public  void exportpdflarge(HttpServletRequest request,HttpServletResponse response,@PathVariable String pdfpid) {
   		try { 			
   			
   		  
   		    GridFSDBFile gridFSDBFile=operations.findOne(new Query(Criteria.where("_id").is(pdfpid)));
   		    InputStream inputStream	=	gridFSDBFile.getInputStream();
   		    ContractPdf pdfFileName=contractPdfRepository.findOne(pdfpid);
   			String fileName= pdfFileName.getPdfName();
   			response.setContentType("multipart/form-data");  
	   		if ("FF".equals(getBrowser(request))) {  
	             
	             String filenamedisplay = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
	        	 response.setHeader("Content-Disposition", "attachment;fileName="+filenamedisplay);

	         } else{ 
	        	 response.setHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(fileName, "UTF-8"));
	         }
   			//FileInputStream inputStream = new FileInputStream(file);  
   			
       
   			ServletOutputStream out = response.getOutputStream();  
  
   			int len; 
           
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) != -1) {
            	out.write(buffer, 0, len);
            }
          
            inputStream.close();  
            out.close();  
            out.flush();  
  
 			 

   			
   			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
   		} catch (Exception e) {
   			LOGGER.error("error export pdf bill ", e);
   		}
   		
   		

   	}
    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            contractService.deletePdf(id);
        } catch (Exception e) {
            LOGGER.error("error occurred while deleting pdf " + id, e);
            
        }
    }
    
    @RequestMapping(value = "/query")
   	@ResponseBody
   	public String list(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		String contractId = (String) request.getParameter("s_sserviceid2");		

   		

   		
   		try {
   			handleDataTable(dt, contractId);
   		} catch (Exception e) {
   			LOGGER.error("list orgs failed", e);
   			return FAIL;
   		}
   		// dt.setsEcho(String.valueOf(curpage+1));
   		String json = this.formateData2DT(dt);
   		return json;

   	}

   	public void handleDataTable(DataTableVo dt, 
   			String contractId) throws Exception {
   		int totalSize=0;
   		List<ContractPdf> contracts = null;
   		try {
   			
   			contracts = contractService.listContractPdf(contractId);
   			totalSize=contracts.size();
   		}  catch (Exception e) {
   			// logger.error("list Exception:"+e);
   			throw new Exception("Error in list orgs!", e);
   		}

   		if (contracts != null) {
   			List<TableData> tds = new ArrayList<TableData>();
   			for (int i = 0, n = contracts.size();  i < n; i++) {
   				ContractPdf contractPdf = contracts.get(i);

   				TableData td = new TableData();

   				td.setPid(contractPdf.getId());
   				td.setPdfName(contractPdf.getPdfName());
   				td.setContractNumber(contractPdf.getContractNumber());
				
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

   		private String pid = "b1";
   		private String pdfName = "b2";
   		private String contractNumber="b3";
		
   		public TableData() {

   		}

   		public String getPid() {
   			return pid;
   		}

   		public void setPid(String pid) {
   			this.pid = pid;
   		}

		public String getPdfName() {
			return pdfName;
		}

		public void setPdfName(String pdfName) {
			this.pdfName = pdfName;
		}

		public String getContractNumber() {
			return contractNumber;
		}

		public void setContractNumber(String contractNumber) {
			this.contractNumber = contractNumber;
		}


		
		
	}
   	
   	private String getBrowser(HttpServletRequest request) {  
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();  
        if (UserAgent != null) {  
            if (UserAgent.indexOf("msie") >= 0)  
                return "IE";  
            if (UserAgent.indexOf("firefox") >= 0)  
                return "FF";  
            if (UserAgent.indexOf("safari") >= 0)  
                return "SF";  
        }  
        return null;  
    }  
}
