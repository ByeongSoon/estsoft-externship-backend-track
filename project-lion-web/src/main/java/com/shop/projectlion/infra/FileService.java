package com.shop.projectlion.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.shop.projectlion.global.error.exception.ErrorCode.NO_REQUIRED_FIRST_IMAGE;

@Slf4j
@Service
public class FileService {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public String getFullFileUploadPath(String filename) {
        return fileUploadPath + filename;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fileUploadUrl = getFullFileUploadPath(storeFileName);
        multipartFile.transferTo(new File(getFullFileUploadPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName, fileUploadUrl);
    }

    private String  createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        if (multipartFiles.get(0).isEmpty()) {
            throw new IOException(NO_REQUIRED_FIRST_IMAGE.getMessage());
        }
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
            } else {
                storeFileResult.add(null);
            }
        }
        return storeFileResult;
    }

    public void deleteFile(String fileUploadUrl) {
        File deleteFile = new File(fileUploadUrl);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}