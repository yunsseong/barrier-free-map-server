package com.yunsseong.barrier_free_map_server.data.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MapMetaDataResponse {
    private String title;
    private String description;
    private String lat;
    private String lng;
}
