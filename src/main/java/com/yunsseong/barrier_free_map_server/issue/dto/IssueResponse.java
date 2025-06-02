package com.yunsseong.barrier_free_map_server.issue.dto;

import lombok.Builder;

@Builder
public record IssueResponse(Long id, String mapName, String content, String createdDate) {
}
