package com.kt.common.dbhelp.dialect;

import com.kt.common.dbhelp.dialect.mysql.MySqlPageParser;
import com.kt.common.dbhelp.dialect.oracle.OraclePageSqlParser;

/**
 * 获取翻页sql解析对象
 * <p/>
 * Copyright: Copyright (c) 13-1-15 上午9:27
 * <p/>
 * Company: 
 * <p/>
 * Author: 
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class PageSqlParserFactory {

    /**
     * 获取翻页解析对象
     * @param dialect 数据库类型
     * @return PageSqlParser
     */
    public static PageSqlParser getParser(String dialect) {
        if ("mysql".equals(dialect)) {
            return new MySqlPageParser();
        } else {
            return new OraclePageSqlParser();
        }
    }
}
