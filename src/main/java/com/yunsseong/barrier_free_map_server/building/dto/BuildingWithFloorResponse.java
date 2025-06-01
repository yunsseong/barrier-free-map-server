package com.yunsseong.barrier_free_map_server.building.dto;

import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import com.yunsseong.barrier_free_map_server.floor.dto.FloorResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record BuildingWithFloorResponse(
        Long id,
        Long mapId,
        String name,
        String number,
        Boolean wheel,
        Boolean toilet,
        Boolean elevator,
        Boolean dots,
        Boolean floorplane,
        String caution,
        Coordinate coordinate,
        List<FloorResponse> floors
) {
}
