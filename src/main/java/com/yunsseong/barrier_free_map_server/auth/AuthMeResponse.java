package com.yunsseong.barrier_free_map_server.auth;

import lombok.Builder;

@Builder
public record AuthMeResponse(String email) {
}
