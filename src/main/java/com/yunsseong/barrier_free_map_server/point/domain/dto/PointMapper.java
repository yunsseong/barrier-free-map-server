package com.yunsseong.barrier_free_map_server.point.domain.dto;

import com.yunsseong.barrier_free_map_server.point.domain.Point;
import org.springframework.stereotype.Component;

public class PointMapper {

    public static PointResponse toPointResponse(Point point) {
        return new PointResponse(
            point.getId(),
            point.getCoordinate(),
            point.getMemo(),
            point.getType()
        );
    }
}