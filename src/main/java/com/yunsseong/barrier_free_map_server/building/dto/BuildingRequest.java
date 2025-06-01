package com.yunsseong.barrier_free_map_server.building.dto;

import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;

public record BuildingRequest(
    String name,
    String number,
    Boolean wheel,
    Boolean toilet,
    Boolean elevator,
    Boolean dots,
    Boolean floorplane,
    String caution,
    Coordinate coordinate
) {
}
