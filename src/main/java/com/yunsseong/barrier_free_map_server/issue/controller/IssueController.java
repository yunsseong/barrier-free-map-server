package com.yunsseong.barrier_free_map_server.issue.controller;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.issue.IssueRequest;
import com.yunsseong.barrier_free_map_server.issue.domain.Issue;
import com.yunsseong.barrier_free_map_server.issue.dto.IssueResponse;
import com.yunsseong.barrier_free_map_server.issue.service.IssueService;
import com.yunsseong.barrier_free_map_server.member.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<IssueResponse>>> queryIssues(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponseFactory.success(issueService.getIssueResponses(userDetails.getMemberId()));
    }

    @PostMapping("/{code}")
    public ResponseEntity<ApiResponse<Void>> submitIssue(@RequestBody IssueRequest issueRequest, @PathVariable String code) {
        issueService.postIssue(issueRequest, code);
        return ApiResponseFactory.success();
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<ApiResponse<Void>> deleteIssue(@PathVariable Long issueId) {
        issueService.deleteIssue(issueId);
        return ApiResponseFactory.success();
    }
}
