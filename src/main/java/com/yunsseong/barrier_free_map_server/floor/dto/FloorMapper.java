package com.yunsseong.barrier_free_map_server.floor.dto;

import com.yunsseong.barrier_free_map_server.floor.domain.Floor;
import org.springframework.stereotype.Service;

public class FloorMapper {

    public static FloorResponse toFloorResponse(Floor floor) {
        return FloorResponse.builder()
                .floorId(floor.getId())
                .idx(floor.getIdx())
                .floorLabel(floor.getFloorLabel())
                .planeImageUrl(floor.getPlanImageUrl())
                .build();
    }
}
