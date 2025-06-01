package com.yunsseong.barrier_free_map_server.building.controller;

import com.yunsseong.barrier_free_map_server.building.domain.Building;
import com.yunsseong.barrier_free_map_server.building.dto.BuildingMapper;
import com.yunsseong.barrier_free_map_server.building.dto.BuildingRequest;
import com.yunsseong.barrier_free_map_server.building.dto.BuildingResponse;
import com.yunsseong.barrier_free_map_server.building.service.BuildingService;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.member.domain.CustomUserDetails;
import com.yunsseong.barrier_free_map_server.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maps/{mapId}/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BuildingResponse>> createBuilding(
            @PathVariable Long mapId,
            @RequestBody BuildingRequest buildingRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Building building = buildingService.createBuilding(mapId, buildingRequest, userDetails.getMemberId());
        BuildingResponse buildingResponse = BuildingMapper.toBuildingResponse(building);
        return ApiResponseFactory.success(buildingResponse);
    }

    @PutMapping("/{buildingId}")
    public ResponseEntity<ApiResponse<BuildingResponse>> updateBuilding(
            @PathVariable Long mapId,
            @PathVariable Long buildingId,
            @RequestBody BuildingRequest buildingRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        BuildingResponse response = buildingService.updateBuilding(mapId, buildingId, buildingRequest, userDetails.getMemberId());
        return ApiResponseFactory.success(response);
    }

    @DeleteMapping("/{buildingId}")
    public ResponseEntity<ApiResponse<Void>> deleteBuilding(
            @PathVariable Long mapId,
            @PathVariable Long buildingId,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        buildingService.deleteBuilding(mapId, buildingId, memberDetails.getMemberId());
        return ApiResponseFactory.success();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BuildingResponse>>> getBuildings(
            @PathVariable Long mapId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponseFactory.success(buildingService.getBuildings(mapId, userDetails.getMemberId()));
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<ApiResponse<BuildingResponse>> getBuilding(
            @PathVariable Long mapId,
            @PathVariable Long buildingId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Building building = buildingService.getBuilding(mapId, buildingId, userDetails.getMemberId());

        BuildingResponse buildingResponse = BuildingMapper.toBuildingResponse(building);
        return ApiResponseFactory.success(buildingResponse);
    }
}
