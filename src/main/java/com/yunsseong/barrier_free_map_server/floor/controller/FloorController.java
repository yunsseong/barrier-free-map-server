package com.yunsseong.barrier_free_map_server.floor.controller;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.floor.domain.Floor;
import com.yunsseong.barrier_free_map_server.floor.dto.CreateFloorRequest;
import com.yunsseong.barrier_free_map_server.floor.dto.FloorMapper;
import com.yunsseong.barrier_free_map_server.floor.dto.FloorResponse;
import com.yunsseong.barrier_free_map_server.floor.dto.UpdateFloorRequest;
import com.yunsseong.barrier_free_map_server.floor.service.FloorService;
import com.yunsseong.barrier_free_map_server.member.domain.CustomUserDetails;
import com.yunsseong.barrier_free_map_server.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buildings/{buildingId}/floors")
public class FloorController {

    private final FloorService floorService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createFloors(
            @PathVariable Long buildingId,
            @RequestBody List<CreateFloorRequest> requests,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        floorService.createFloors(requests, buildingId, userDetails.getMemberId());
        return ApiResponseFactory.success();
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateFloors(
            @PathVariable Long buildingId,
            @RequestBody List<UpdateFloorRequest> requests,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        floorService.updateFloors(requests, buildingId, userDetails.getMemberId());
        return ApiResponseFactory.success();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FloorResponse>>> getFloors(
            @PathVariable Long buildingId) {
        List<FloorResponse> floors = floorService.getFloors(buildingId);
        return ApiResponseFactory.success(floors);
    }

    @GetMapping("/{floorId}")
    public ResponseEntity<ApiResponse<FloorResponse>> getFloor(
            @PathVariable Long floorId) {
        Floor floor = floorService.findById(floorId);
        FloorResponse response = FloorMapper.toFloorResponse(floor);
        return ApiResponseFactory.success(response);
    }

    @DeleteMapping("/{floorId}")
    public ResponseEntity<ApiResponse<Void>> deleteFloor(
            @PathVariable Long buildingId,
            @PathVariable Long floorId,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        floorService.deleteFloor(buildingId, floorId, memberDetails.getMemberId());
        return ApiResponseFactory.success();
    }
}
