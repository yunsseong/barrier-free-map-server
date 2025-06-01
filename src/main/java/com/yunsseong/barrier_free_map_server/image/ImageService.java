package com.yunsseong.barrier_free_map_server.image;

import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;

    public PresignedUrlResponse generatePresignedUrl(String key, String contentType) {

        return PresignedUrlResponse.builder()
                .url(s3Service.generatePresignedUploadUrl(key, contentType, Duration.ofMinutes(5)))
                .key(key)
                .build();
    }

    public void contentTypeChecker(String contentType) {
        String type = contentType.split("\\.")[1];
        if (!type.equals("png") && !type.equals("jpeg") && !type.equals("jpg"))
            throw new BusinessException(CommonStatus.INVALID_INPUT);
    }
}
