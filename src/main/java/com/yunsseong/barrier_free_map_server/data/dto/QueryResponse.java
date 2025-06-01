package com.yunsseong.barrier_free_map_server.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.swing.text.Position;
import java.util.List;

@Getter
@Builder
public class QueryResponse {

    @JsonProperty("meta")
    private MapMetaDataResponse metaResponse;

    @JsonProperty("pos")
    private List<PosResponse> posResponses;

    @JsonProperty("parking")
    private List<PositionResponse> parkingResponses;

    @JsonProperty("ramp")
    private List<PositionResponse> rampResponses;
}
