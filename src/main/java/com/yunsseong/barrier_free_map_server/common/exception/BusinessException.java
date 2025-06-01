package com.yunsseong.barrier_free_map_server.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final Status status;

    public BusinessException(Status status) {
        super(status.getMessage());
        this.status = status;
    }
}
