package com.workplace.simon.service;

import com.workplace.simon.model.FileDB;
import com.workplace.simon.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileStorageService {
    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDBRepository getFileDBRepository() {
        return fileDBRepository;
    }

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public Optional<FileDB> getFile(Long id) {
        return fileDBRepository.findById(id);
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
