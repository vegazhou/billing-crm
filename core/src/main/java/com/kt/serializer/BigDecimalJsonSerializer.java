package com.kt.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**   
 * Description: 日期转换 - "yyyy-MM-dd"   
 */    
public class BigDecimalJsonSerializer extends JsonSerializer<BigDecimal> {
    private static final Logger logger = LoggerFactory.getLogger(BigDecimalJsonSerializer.class);
    @Override    
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        try {
            jsonGenerator.writeNumber(value == null? 0:value.doubleValue());
        } catch (Exception e) {    
            jsonGenerator.writeNumber(0);    
        }    
    }    
}    