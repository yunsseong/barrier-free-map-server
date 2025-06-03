package com.yunsseong.barrier_free_map_server.image;

import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final S3Service s3Service;

    public PresignedUrlResponse generatePresignedUrl(String key) {
        return PresignedUrlResponse.builder()
                .uploadUrl(s3Service.generatePresignedUploadUrl(key, Duration.ofMinutes(5)))
                .build();
    }
}
