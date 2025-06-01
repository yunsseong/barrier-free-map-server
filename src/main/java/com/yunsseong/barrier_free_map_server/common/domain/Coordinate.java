package com.yunsseong.barrier_free_map_server.common.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Coordinate (Double lat, Double lng){
}