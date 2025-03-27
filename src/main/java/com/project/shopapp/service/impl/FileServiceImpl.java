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
    protected String uploadDir;

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException(messageUtils.getMessage(MessageKeys.FILE_IS_EMPTY));
        }
        if (Files.notExists(Paths.get(uploadDir))) {
            Files.createDirectories(Paths.get(uploadDir));
        }
        String uniqueFileName = UUID.randomUUID().toString() + getExtension(file.getOriginalFilename());
        Path path = Paths.get(uploadDir + uniqueFileName);
        Files.write(path, file.getBytes());
        return path.toString();
    }

    private String getExtension(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i);
        }
        return extension;
    }
}
