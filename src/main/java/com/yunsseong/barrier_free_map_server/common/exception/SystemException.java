package com.yunsseong.barrier_free_map_server.common.exception;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private final Status status;

    public SystemException(Status status) {
        super(status.getMessage());
        this.status = status;
    }
}
