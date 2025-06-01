package com.yunsseong.barrier_free_map_server.building.dto;

import com.yunsseong.barrier_free_map_server.building.domain.Building;
import com.yunsseong.barrier_free_map_server.floor.dto.FloorMapper;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingMapper {

    public static BuildingResponse toBuildingResponse(Building building) {
        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .number(building.getNumber())
                .wheel(building.getWheel())
                .toilet(building.getToilet())
                .elevator(building.getElevator())
                .dots(building.getDots())
                .floorplane(building.getFloorplan())
                .caution(building.getCaution())
                .coordinate(building.getCoordinate())
                .mapId(building.getMap().getId())
                .build();
    }

    public static BuildingWithFloorResponse toBuildingWithFloorResponse(Building building) {
        return BuildingWithFloorResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .number(building.getNumber())
                .wheel(building.getWheel())
                .toilet(building.getToilet())
                .elevator(building.getElevator())
                .dots(building.getDots())
                .floorplane(building.getFloorplan())
                .caution(building.getCaution())
                .coordinate(building.getCoordinate())
                .mapId(building.getMap().getId())
                .floors(building.getFloors().stream()
                        .map(FloorMapper::toFloorResponse)
                        .toList()
                )
                .build();
    }

    public static Building toBuilding(BuildingRequest buildingRequest, BarrierFreeMap map) {
        return Building.builder()
                .name(buildingRequest.name())
                .number(buildingRequest.number())
                .wheel(buildingRequest.wheel())
                .toilet(buildingRequest.toilet())
                .elevator(buildingRequest.elevator())
                .dots(buildingRequest.dots())
                .floorplan(buildingRequest.floorplane())
                .caution(buildingRequest.caution())
                .coordinate(buildingRequest.coordinate())
                .map(map)
                .build();
    }

    public static Building toUpdatedBuilding(Building existingBuilding, BuildingRequest request) {
        return Building.builder()
                .id(existingBuilding.getId())
                .name(request.name())
                .number(request.number())
                .wheel(request.wheel())
                .toilet(request.toilet())
                .elevator(request.elevator())
                .dots(request.dots())
                .floorplan(request.floorplane())
                .caution(request.caution())
                .coordinate(request.coordinate())
                .map(existingBuilding.getMap())
                .build();
    }
}
