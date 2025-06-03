package com.yunsseong.barrier_free_map_server.image;

import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponse;
import com.yunsseong.barrier_free_map_server.common.dto.response.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/upload-url")
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> getPresignedUrl(@RequestBody ImageRequest fileName, HttpServletRequest request) {
        return ApiResponseFactory.success(imageService.generatePresignedUrl("plans/" + fileName, request.getContentType()));
    }
}
