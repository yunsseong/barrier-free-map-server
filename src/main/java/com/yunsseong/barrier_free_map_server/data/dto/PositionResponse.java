package com.yunsseong.barrier_free_map_server.data.dto;

import lombok.Builder;

@Builder
public record PositionResponse(String lat, String lng, String memo) {
}
