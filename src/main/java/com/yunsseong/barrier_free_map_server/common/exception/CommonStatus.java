package com.yunsseong.barrier_free_map_server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonStatus implements Status {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력이 올바르지 않습니다.", 400),
    INVALID_OBJECT(HttpStatus.BAD_REQUEST, "객체를 찾을 수 없습니다.", 400),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다.", 401);


    private final HttpStatus httpStatus;
    private final String message;
    private final int statusCode;
}
