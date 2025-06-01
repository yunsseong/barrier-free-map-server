package com.yunsseong.barrier_free_map_server.issue.controller;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.issue.IssueRequest;
import com.yunsseong.barrier_free_map_server.issue.domain.Issue;
import com.yunsseong.barrier_free_map_server.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issues")
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

    @DeleteMapping("/{issueId}")
    public ResponseEntity<ApiResponse<Void>> deleteIssue(@PathVariable Long issueId) {
        issueService.deleteIssue(issueId);
        return ApiResponseFactory.success();
    }
}
