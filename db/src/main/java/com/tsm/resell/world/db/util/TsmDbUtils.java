package com.tsm.resell.world.db.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tsm.resell.world.db.exception.TsmDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TsmDbUtils {


    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    public Object mapperEntity(Object o , Class<?> c){
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
