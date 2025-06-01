package com.yunsseong.barrier_free_map_server.map.dto;

import com.yunsseong.barrier_free_map_server.building.dto.BuildingWithFloorResponse;
import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import com.yunsseong.barrier_free_map_server.map.domain.MapStatus;
import com.yunsseong.barrier_free_map_server.point.domain.dto.PointResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MapResponse(
    Long mapId,
    Long ownerId,
    String name,
    String description,
    MapStatus status,
    LocalDateTime createdDate,
    LocalDateTime updatedDate,
    String uuid,
    Coordinate centralCoordinate,
    List<BuildingWithFloorResponse> buildings,
    List<PointResponse> points
) {
}
