package com.yunsseong.barrier_free_map_server.issue.repository;

import com.yunsseong.barrier_free_map_server.issue.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
