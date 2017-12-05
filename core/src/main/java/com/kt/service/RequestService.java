package com.kt.service;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.WebExRequest;
import com.kt.repo.mysql.billing.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Garfield Chen on 2016/6/14.
 */
@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;
   



    
    public Page<WebExRequest> listAll(int curPage, SearchFilter search) {
        return requestRepository.listAllByPage(curPage, search);
    }


  
}
