package com.yunsseong.barrier_free_map_server.data.dto;

import lombok.Builder;

@Builder
public record FloorResponse(Long idx, String floorLabel, String planImageUrl) {
}
