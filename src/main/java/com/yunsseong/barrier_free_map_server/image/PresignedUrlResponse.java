package com.yunsseong.barrier_free_map_server.image;

import lombok.Builder;

@Builder
public record PresignedUrlResponse(String url, String key) {
}
