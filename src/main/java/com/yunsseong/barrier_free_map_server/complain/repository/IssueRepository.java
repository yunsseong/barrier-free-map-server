package com.yunsseong.barrier_free_map_server.complain.repository;

import com.yunsseong.barrier_free_map_server.complain.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
