package com.yunsseong.barrier_free_map_server.issue.repository;

import com.yunsseong.barrier_free_map_server.issue.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findByIdAndMapId(Long issueId, Long mapId);

    List<Issue> findAllByMapId(Long mapId);
}
