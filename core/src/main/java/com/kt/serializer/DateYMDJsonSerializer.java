package com.kt.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kt.util.DateUtils;

import java.io.IOException;
import java.util.Date;

/**   
 * Description: 日期转换 - "yyyy-MM-dd"   
 */    
public class DateYMDJsonSerializer extends JsonSerializer<Date> {
    @Override    
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        try {    
            jsonGenerator.writeString(DateUtils.formatDateTime(date));
        } catch (Exception e) {    
            jsonGenerator.writeString(String.valueOf(date.getTime()));    
        }    
    }    
}    