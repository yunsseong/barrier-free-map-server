package com.yunsseong.barrier_free_map_server.point.domain.dto;

import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import com.yunsseong.barrier_free_map_server.point.domain.PointType;

public record PointRequest(
    Coordinate coordinate,
    String memo,
    PointType type
) {
}