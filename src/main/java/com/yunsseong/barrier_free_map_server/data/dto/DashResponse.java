package com.yunsseong.barrier_free_map_server.data.dto;

import lombok.Getter;

@Getter
public class DashResponse {
    private Long mapCount = 0L;
    private Long buildingCount = 0L;
    private Long pointCount = 0L;
    private Long issueCount = 0L;

    public void addMapCount(Long count) {
        mapCount += count;
    }

    public void addBuildingCount(Long count) {
        buildingCount += count;
    }

    public void addPointCount(Long count) {
        pointCount += count;
    }

    public void addIssueCount(Long count) {
        issueCount += count;
    }
}
