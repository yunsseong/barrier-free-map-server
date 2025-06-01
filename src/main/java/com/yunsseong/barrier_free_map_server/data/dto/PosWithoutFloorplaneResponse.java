package com.yunsseong.barrier_free_map_server.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PosWithoutFloorplaneResponse implements PosResponse {
    private String id;
    private String title;
    private String lat;
    private String lng;
    private Boolean wheel;
    private Boolean toilet;
    private Boolean elevator;
    private Boolean dots;
    private Boolean floorplan;
    private String caution;
}
