package com.justinlopez.uploadvideos.domain.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.justinlopez.uploadvideos.domain.usecase.ICloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CloudinaryService implements ICloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map uploadVideo(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        var result = cloudinary.uploader().upload(file, ObjectUtils.asMap("resource_type", "video"));
        file.delete();
        return result;
    }

    @Override
    public Map delete(String id) {
        ApiResponse apiResponse;
        try {
            apiResponse = cloudinary.api().deleteResources(Arrays.asList(id), ObjectUtils.asMap(
                    "type", "upload",
                    "resource_type", "video"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Map.of("message", apiResponse);
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }
        return file;
    }

}
