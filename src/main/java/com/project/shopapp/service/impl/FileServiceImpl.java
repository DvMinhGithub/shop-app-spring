package com.project.shopapp.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.shopapp.service.FileService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FileServiceImpl implements FileService {

    MessageUtils messageUtils;

    @NonFinal
    @Value("${file.upload-dir}")
    private String uploadDir;

    @NonFinal
    @Value("${server.url}")
    private String serverUrl;

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException(messageUtils.getMessage(MessageKeys.FILE_IS_EMPTY));
        }

        Path uploadPath = Paths.get(uploadDir);
        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        if (file.getOriginalFilename() == null) {
            throw new IOException(messageUtils.getMessage(MessageKeys.FILE_UPLOAD_FAILED));
        }
        String originalFilename = sanitizeFilename(file.getOriginalFilename());
        String fileExtension = "";
        if (originalFilename.isEmpty()) {
            fileExtension = getExtension(originalFilename);
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadPath.resolve(uniqueFileName);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new IOException(messageUtils.getMessage(MessageKeys.FILE_UPLOAD_FAILED), e);
        }

        return serverUrl + "/files/" + uniqueFileName;
    }

    private String getExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex);
    }

    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
}
