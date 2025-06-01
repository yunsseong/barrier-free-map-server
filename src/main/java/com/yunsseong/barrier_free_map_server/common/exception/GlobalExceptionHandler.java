package com.yunsseong.barrier_free_map_server.common.exception;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.yunsseong.barrier_free_map_server.common.exception.CommonStatus.INVALID_INPUT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> businessExceptionHandler(BusinessException e) {
        return ApiResponseFactory.failure(e.getStatus());
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ApiResponse<Void>> systemExceptionHandler(SystemException e) {
        return ApiResponseFactory.failure(e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ApiResponseFactory.failure(INVALID_INPUT, errors );
    }
}
