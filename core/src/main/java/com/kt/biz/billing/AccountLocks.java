package com.kt.biz.billing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public class AccountLocks {
    private static Map<String, ReentrantLock> accountLocks =new HashMap<>();

//    public ReentrantLock getLock(String accountId) {
//
//    }
}
