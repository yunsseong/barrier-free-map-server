package com.yunsseong.barrier_free_map_server.map.dto;

import com.yunsseong.barrier_free_map_server.building.dto.BuildingMapper;
import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.member.domain.Member;
import com.yunsseong.barrier_free_map_server.point.domain.dto.PointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class MapMapper {
    
    public static BarrierFreeMap toBarrierFreeMap(CreateMapRequest request, Member member) {
        return BarrierFreeMap.builder()
                .name(request.name())
                .description(request.description())
                .centralCoordinate(request.centralCoordinate())
                .owner(member)
                .build();
    }

    public static MapResponse toMapResponse(BarrierFreeMap map, String url) {
        return MapResponse.builder()
                .mapId(map.getId())
                .ownerId(map.getOwner().getId())
                .name(map.getName())
                .description(map.getDescription())
                .status(map.getStatus())
                .createdDate(map.getCreatedDate())
                .updatedDate(map.getUpdatedDate())
                .url(url + "/data/" + map.getUuid())
                .centralCoordinate(map.getCentralCoordinate())
                .buildings(map.getBuildings().stream()
                        .map(BuildingMapper::toBuildingWithFloorResponse)
                        .toList())
                .points(map.getPoints().stream()
                        .map(PointMapper::toPointResponse)
                        .toList())
                .build();
    }
    
    public static BarrierFreeMap toUpdatedMap(BarrierFreeMap map, UpdateMapRequest request) {
        return BarrierFreeMap.builder()
                .id(map.getId())
                .name(request.name())
                .description(request.description())
                .uuid(map.getUuid())
                .centralCoordinate(request.centralCoordinate())
                .owner(map.getOwner())
                .status(map.getStatus())
                .buildings(map.getBuildings())
                .points(map.getPoints())
                .createdDate(map.getCreatedDate())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
