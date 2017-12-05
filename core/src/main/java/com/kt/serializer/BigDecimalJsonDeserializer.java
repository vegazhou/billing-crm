package com.kt.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**   
 * Description: 日期转换 - "yyyy-MM-dd"   
 */    
public class BigDecimalJsonDeserializer extends JsonDeserializer<BigDecimal> {
    @Override    
    public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try { 
        	BigDecimal v = jp.getDecimalValue();
        	if(v == null){
        		v = new BigDecimal(0);
        	}
            return v;    
        } catch (Exception e) {    
            return new BigDecimal(0);    
        }    
    }    
}    