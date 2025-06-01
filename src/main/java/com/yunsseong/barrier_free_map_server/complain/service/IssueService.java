package com.yunsseong.barrier_free_map_server.complain.service;

import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import com.yunsseong.barrier_free_map_server.complain.IssueRequest;
import com.yunsseong.barrier_free_map_server.complain.domain.Issue;
import com.yunsseong.barrier_free_map_server.complain.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    public Issue getIssue(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_INPUT));
    }

    public List<Issue> getIssues() {
        return issueRepository.findAll();
    }

    public void postIssue(IssueRequest request) {
        issueRepository.save(
                Issue.builder()
                        .content(request.content())
                        .build()
        );
    }
}
