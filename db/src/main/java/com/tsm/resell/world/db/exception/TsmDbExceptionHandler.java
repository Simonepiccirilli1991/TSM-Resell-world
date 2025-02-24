package com.tsm.resell.world.db.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class TsmDbExceptionHandler {


    @ExceptionHandler(TsmDbException.class)
    public Mono<ResponseEntity<TsmError>> erroreHandler(TsmDbException e){
        log.error("Error has been throw with err: {}",e);
        var msg = errorCaseMapper(e);
        return Mono.just(ResponseEntity.internalServerError().body(new TsmError(msg)));
    }


    private String errorCaseMapper(TsmDbException exc){
        return switch (exc){

            case TsmDbException e when "01".equals(e.getCode()) -> "Entity Already Present present";
            case TsmDbException e when "02".equals(e.getCode()) -> "Error during persiting Data";
            case TsmDbException e when "03".equals(e.getCode()) -> "Action not allowed";
            case TsmDbException e when "04".equals(e.getCode()) -> "Error while rolling back";

            default -> "Generic Errpr on TSM DB";
        };
    }
}
