package com.tsm.resell.world.db.exception;

import lombok.Data;

@Data
public class TsmDbException extends RuntimeException{

    private String msg;
    private String code;

    public TsmDbException(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }
}
