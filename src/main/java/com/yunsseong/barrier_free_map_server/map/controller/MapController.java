package com.yunsseong.barrier_free_map_server.map.controller;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.map.dto.CreateMapRequest;
import com.yunsseong.barrier_free_map_server.map.dto.MapResponse;
import com.yunsseong.barrier_free_map_server.map.dto.UpdateMapRequest;
import com.yunsseong.barrier_free_map_server.map.service.MapService;
import com.yunsseong.barrier_free_map_server.member.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maps")
public class MapController {

    private final MapService mapService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MapResponse>>> getAllMaps(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MapResponse> mapResponses = mapService.getAllMaps(userDetails.getMemberId());
        return ApiResponseFactory.success(mapResponses);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MapResponse>> createMap(
            @RequestBody CreateMapRequest createMapRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MapResponse mapResponse = mapService.createEmptyMap(createMapRequest, customUserDetails.getMemberId());
        return ApiResponseFactory.success(mapResponse);
    }

    @GetMapping("/{mapId}")
    public ResponseEntity<ApiResponse<MapResponse>> getMap(
            @PathVariable Long mapId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        MapResponse mapResponse = mapService.getMap(mapId, userDetails.getMemberId());
        return ApiResponseFactory.success(mapResponse);
    }

    @PutMapping("/{mapId}")
    public ResponseEntity<ApiResponse<Void>> updateMap(
            @PathVariable Long mapId,
            @RequestBody UpdateMapRequest updateMapRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        mapService.updateMap(mapId, updateMapRequest, userDetails.getMemberId());
        return ApiResponseFactory.success();
    }

    @DeleteMapping("/{mapId}")
    public ResponseEntity<ApiResponse<Void>> deleteMap(
            @PathVariable Long mapId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        mapService.deleteMap(mapId, userDetails.getMemberId());
        return ApiResponseFactory.success();
    }

    @PostMapping("/{mapId}/publish")
    public ResponseEntity<ApiResponse<Void>> publishMap(
            @PathVariable Long mapId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        mapService.publishMap(mapId, userDetails.getMemberId());
        return ApiResponseFactory.success();
    }

    @PostMapping("/{mapId}/unpublish")
    public ResponseEntity<ApiResponse<Void>> unpublishMap(
            @PathVariable Long mapId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        mapService.unpublishMap(mapId, userDetails.getMemberId());
        return ApiResponseFactory.success();
    }
}
