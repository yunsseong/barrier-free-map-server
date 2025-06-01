package com.yunsseong.barrier_free_map_server.map.dto;

import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import com.yunsseong.barrier_free_map_server.map.domain.MapStatus;

public record UpdateMapRequest(
        String name,
        String description,
        MapStatus status,
        Coordinate centralCoordinate
) {
}
