package com.yunsseong.barrier_free_map_server.floor.dto;

import lombok.Builder;

@Builder
public record FloorResponse(Long floorId, Long idx, String floorLabel, String planeImageUrl) {
}
