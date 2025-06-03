package com.yunsseong.barrier_free_map_server.issue.service;

import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import com.yunsseong.barrier_free_map_server.issue.IssueRequest;
import com.yunsseong.barrier_free_map_server.issue.domain.Issue;
import com.yunsseong.barrier_free_map_server.issue.dto.IssueResponse;
import com.yunsseong.barrier_free_map_server.issue.repository.IssueRepository;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.map.service.MapService;
import com.yunsseong.barrier_free_map_server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {

    private final IssueRepository issueRepository;
    private final MapService mapService;

    public Issue getIssue(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_INPUT));
    }

    public Issue getMapIssue(Long issueId, Long mapId) {
        return issueRepository.findByIdAndMapId(issueId, mapId)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_INPUT));
    }

    public List<Issue> getIssueEntities(Long mapId) {
        return issueRepository.findAllByMapId(mapId);
    }

    @Transactional
    public void postIssue(IssueRequest request, String code) {
        BarrierFreeMap map = mapService.getMapByCode(code);
        issueRepository.save(
                Issue.builder()
                        .content(request.content())
                        .map(map)
                        .build()
        );
    }

    public List<Issue> getIssuesByMemberId(Long memberId) {
        List<BarrierFreeMap> maps = mapService.getAllMapEntity(memberId);
        List<Issue> issues = new ArrayList<>();
        for (BarrierFreeMap map : maps) {
            issues.addAll(map.getIssues());
        }
        return issues;
    }

    public List<IssueResponse> getIssueResponses(Long memberId) {
        return getIssuesByMemberId(memberId).stream()
                .map(issue -> IssueResponse.builder()
                        .id(issue.getId())
                        .mapName(issue.getMap().getName())
                        .content(issue.getContent())
                        .createdDate(issue.getCreatedDate().toString())
                        .build()
                ).toList();
    }

    @Transactional
    public void deleteIssue(Long issueId) {
        issueRepository.delete(getIssue(issueId));
    }
}
