package com.ee172.team2.steven.handler;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    // 你可以根據需要添加更多構造方法或方法
}
