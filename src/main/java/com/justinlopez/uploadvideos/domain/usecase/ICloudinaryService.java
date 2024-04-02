package com.justinlopez.uploadvideos.domain.usecase;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ICloudinaryService {

    Map upload(MultipartFile multipartFile) throws IOException;

    Map delete(String id) throws IOException;

}
