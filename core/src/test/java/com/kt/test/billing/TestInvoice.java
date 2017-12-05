package com.kt.test.billing;




import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.kt.common.exception.ApiException;
import com.kt.service.ContractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestInvoice {
	@Configuration
	@ComponentScan(basePackages = { "com.kt" })
	static class ContextConfiguration {
	}

	@Autowired
	ContractService contractService;

	@Test
	public void testFindAll()  {
		try{
//		contractService.createInvoice("33");
		}catch (Exception ee){
			
		}
	}

}
    

    