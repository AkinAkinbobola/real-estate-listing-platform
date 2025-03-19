package com.akinbobola.backend.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    @Value("${application.file.uploads.photos-output-path}")
    private String fileUploadPath;

    public List <String> saveImages (Integer listingId, MultipartFile[] images, Integer userId) {
        final String uploadSubPath = "users" + File.separator + userId + File.separator + "listings" + File.separator + "images" + File.separator + listingId;

        return uploadFiles(images, uploadSubPath);
    }

    private List <String> uploadFiles (MultipartFile[] images, String uploadSubPath) {
        final String finalPath = fileUploadPath + File.separator + uploadSubPath;

        File targetFolder = new File(finalPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();

            if (!folderCreated) {
                return null;
            }
        }

        return Arrays.stream(images).map(image -> {
            final String fileExtension = getFileExtension(image.getOriginalFilename());

            final String targetFilePath = finalPath + File.separator + UUID.randomUUID() + "." + fileExtension;

            Path targetPath = Paths.get(targetFilePath);

            try {
                Files.write(targetPath, image.getBytes());
            } catch (IOException e) {
                log.error("Error saving file: {}", e.getMessage());
            }
            return targetFilePath;
        }).toList();
    }

    private String getFileExtension (String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex == -1) {
            return "";
        }

        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    public List <String> saveFloorPlans (Integer listingId, MultipartFile[] floorPlans, Integer userId) {
        final String uploadSubPath = "users" + File.separator + userId + File.separator + "listings" + File.separator + "floor-plans" + File.separator + listingId;

        return uploadFiles(floorPlans, uploadSubPath);
    }
}
