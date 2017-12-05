/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.common.dbhelp.dbutils.handlers;

import com.kt.common.dbhelp.dbutils.BasicStringRowProcessor;
import com.kt.common.dbhelp.dbutils.ResultSetHandler;
import com.kt.common.dbhelp.dbutils.StringRowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <code>ResultSetHandler</code> implementation that converts a 
 * <code>ResultSet</code> into an <code>String[]</code>. This class is
 * thread safe.
 * 
 * @see com.kt.common.dbhelp.dbutils.ResultSetHandler
 */
public class StringArrayHandler implements ResultSetHandler<String[]> {

    /**
     * Singleton processor instance that handlers share to save memory.  Notice
     * the default scoping to allow only classes in this package to use this
     * instance.
     */
    static final StringRowProcessor ROW_PROCESSOR = new BasicStringRowProcessor();

    /**
     * The StringRowProcessor implementation to use when converting rows
     * into arrays.
     */
    private final StringRowProcessor convert;

    /** 
     * Creates a new instance of ArrayHandler using a 
     * <code>BasicRowProcessor</code> for conversion.
     */
    public StringArrayHandler() {
        this(ROW_PROCESSOR);
    }

    /** 
     * Creates a new instance of ArrayHandler.
     * 
     * @param convert The <code>StringRowProcessor</code> implementation
     * to use when converting rows into arrays.
     */
    public StringArrayHandler(StringRowProcessor convert) {
        super();
        this.convert = convert;
    }

    /**
     * Places the column values from the first row in an <code>Object[]</code>.
     * 
     * @return An Object[] or <code>null</code> if there are no rows in the
     * <code>ResultSet</code>.
     * 
     * @throws java.sql.SQLException if a database access error occurs
     * @see com.kt.common.dbhelp.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public String[] handle(ResultSet rs) throws SQLException {
        return rs.next() ? this.convert.toArray(rs) : null;
    }


}
