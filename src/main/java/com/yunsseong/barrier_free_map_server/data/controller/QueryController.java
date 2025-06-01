package com.yunsseong.barrier_free_map_server.data.controller;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.data.QueryService;
import com.yunsseong.barrier_free_map_server.data.dto.QueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @GetMapping("/api/data/{nickname}")
    public ResponseEntity<ApiResponse<QueryResponse>> queryMap(@PathVariable String nickname) {
        return ApiResponseFactory.success(queryService.queryResponse(nickname));
    }
}
