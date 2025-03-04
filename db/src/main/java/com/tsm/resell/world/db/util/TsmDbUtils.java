package com.tsm.resell.world.db.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.tsm.resell.world.db.exception.TsmDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class TsmDbUtils {


    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

    public Object mapperEntity(Object o , Class<?> c){
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        try{
            return mapper.convertValue(o,c);
        }catch (Exception e){
            log.error("Error on mapperEntity with err: {}",e.getMessage());
            throw new TsmDbException("Error on mapperEntity","05");
        }
    }
}
