package com.yunsseong.barrier_free_map_server.point.controller;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import com.yunsseong.barrier_free_map_server.member.domain.CustomUserDetails;
import com.yunsseong.barrier_free_map_server.member.domain.MemberDetails;
import com.yunsseong.barrier_free_map_server.point.domain.Point;
import com.yunsseong.barrier_free_map_server.point.domain.dto.PointMapper;
import com.yunsseong.barrier_free_map_server.point.domain.dto.PointRequest;
import com.yunsseong.barrier_free_map_server.point.domain.dto.PointResponse;
import com.yunsseong.barrier_free_map_server.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maps/{mapId}/pois")
public class PointController {

    private final PointService pointService;

    @PostMapping
    public ResponseEntity<ApiResponse<PointResponse>> createPoint(
            @PathVariable Long mapId,
            @RequestBody PointRequest pointRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Point point = pointService.createPoint(mapId, pointRequest, userDetails.getMemberId());
        PointResponse pointResponse = PointMapper.toPointResponse(point);
        return ApiResponseFactory.success(pointResponse);
    }

    @PutMapping("/{poiId}")
    public ResponseEntity<ApiResponse<PointResponse>> updatePoint(
            @PathVariable Long mapId,
            @PathVariable Long poiId,
            @RequestBody PointRequest pointRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Point point = pointService.updatePoint(mapId, poiId, pointRequest, userDetails.getMemberId());
        PointResponse pointResponse = PointMapper.toPointResponse(point);
        return ApiResponseFactory.success(pointResponse);
    }

    @DeleteMapping("/{poiId}")
    public ResponseEntity<ApiResponse<Void>> deletePoint(
            @PathVariable Long mapId,
            @PathVariable Long poiId,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        pointService.deletePoint(mapId, poiId, memberDetails.getMemberId());
        return ApiResponseFactory.success();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PointResponse>>> getAllPoints(
            @PathVariable Long mapId) {
        List<Point> points = pointService.getPointsByMapId(mapId);
        List<PointResponse> pointResponses = points.stream()
                .map(PointMapper::toPointResponse)
                .collect(Collectors.toList());
        return ApiResponseFactory.success(pointResponses);
    }

    @GetMapping("/{poiId}")
    public ResponseEntity<ApiResponse<PointResponse>> getPoint(
            @PathVariable Long mapId,
            @PathVariable Long poiId) {
        Point point = pointService.getPointForMap(mapId, poiId);
        PointResponse pointResponse = PointMapper.toPointResponse(point);
        return ApiResponseFactory.success(pointResponse);
    }
}
