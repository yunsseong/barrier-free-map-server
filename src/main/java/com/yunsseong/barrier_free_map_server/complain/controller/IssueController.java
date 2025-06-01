package com.yunsseong.barrier_free_map_server.complain.controller;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.complain.IssueRequest;
import com.yunsseong.barrier_free_map_server.complain.domain.Issue;
import com.yunsseong.barrier_free_map_server.complain.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueController {

    private final IssueService issueService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Issue>>> queryIssues() {
        return ApiResponseFactory.success(issueService.getIssues());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> submitIssue(IssueRequest issueRequest) {
        issueService.postIssue(issueRequest);
        return ApiResponseFactory.success();
    }
}
