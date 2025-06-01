package com.yunsseong.barrier_free_map_server.floor.dto;

import lombok.Builder;

@Builder
public record UpdateFloorRequest(Long id, Long idx, String floorLabel, String fileName) {
}
