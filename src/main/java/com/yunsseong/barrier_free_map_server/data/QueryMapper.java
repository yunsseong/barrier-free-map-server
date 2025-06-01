package com.yunsseong.barrier_free_map_server.data;

import com.yunsseong.barrier_free_map_server.building.domain.Building;
import com.yunsseong.barrier_free_map_server.data.dto.*;
import com.yunsseong.barrier_free_map_server.floor.domain.Floor;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.point.domain.Point;
import com.yunsseong.barrier_free_map_server.point.domain.PointType;

import java.util.List;

public class QueryMapper {

    public static QueryResponse toQueryResponse(BarrierFreeMap map) {
        return QueryResponse.builder()
                .metaResponse(
                        MapMetaDataResponse.builder()
                                .title(map.getName())
                                .description(map.getDescription())
                                .lat(map.getCentralCoordinate().lat().toString())
                                .lng(map.getCentralCoordinate().lng().toString())
                                .build()
                )
                .posResponses(
                        map.getBuildings().stream()
                        .map(QueryMapper::toPosResponse)
                        .toList()
                )
                .parkingResponses(
                        toPositionResponse(map.getPoints(), PointType.parking)
                ).rampResponses(
                        toPositionResponse(map.getPoints(), PointType.ramp)
                )
                .build();
    }

    public static PosResponse toPosResponse(Building building) {
        if(building.getFloorplan()) {
            return PosWithFloorplanResponse.builder()
                    .id(building.getNumber())
                    .title(building.getName())
                    .lat(building.getCoordinate().lat().toString())
                    .lng(building.getCoordinate().lng().toString())
                    .wheel(building.getWheel())
                    .toilet(building.getToilet())
                    .elevator(building.getElevator())
                    .dots(building.getDots())
                    .floorplan(building.getFloorplan())
                    .caution(building.getCaution())
                    .floors(toFloorResponse(building.getFloors()))
                    .build();
        }

        return PosWithoutFloorplaneResponse.builder()
                .id(building.getNumber())
                .title(building.getName())
                .lat(building.getCoordinate().lat().toString())
                .lng(building.getCoordinate().lng().toString())
                .wheel(building.getWheel())
                .toilet(building.getToilet())
                .elevator(building.getElevator())
                .dots(building.getDots())
                .floorplan(building.getFloorplan())
                .caution(building.getCaution())
                .build();
    }

    public static List<FloorResponse> toFloorResponse(List<Floor> floors) {
        return floors.stream()
                .map(floor -> FloorResponse.builder()
                        .idx(floor.getIdx())
                        .floorLabel(floor.getFloorLabel())
                        .planImageUrl(floor.getPlanImageUrl())
                        .build()
                ).toList();
    }

    public static List<PositionResponse> toPositionResponse(List<Point> points, PointType pointType) {
        return points.stream()
                .filter(point -> point.getType().equals(pointType))
                .map(point -> PositionResponse.builder()
                        .lat(point.getCoordinate().lat().toString())
                        .lng(point.getCoordinate().lng().toString())
                        .memo(point.getMemo())
                        .build()
                ).toList();
    }
 }
