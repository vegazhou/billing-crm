package com.kt.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kt.util.DateUtils;

import java.io.IOException;
import java.util.Date;

/**   
 *
 */    
public class DateYMDJsonDeserializer extends JsonDeserializer<Date> {
    @Override    
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return DateUtils.parseDate(jp.getText());
        } catch (Exception e) {    
            return new Date(jp.getLongValue());    
        }    
    }    
}    