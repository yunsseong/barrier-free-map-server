package com.yunsseong.barrier_free_map_server.common.exception;

import org.springframework.http.HttpStatus;

public interface Status {
    HttpStatus getHttpStatus();
    String getMessage();
    int getStatusCode();
}
