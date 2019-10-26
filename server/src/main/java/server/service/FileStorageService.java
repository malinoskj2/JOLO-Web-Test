package server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path storageDirectory;

    public FileStorageService(@Value("${storage.directory}") String valueFromFile) {
        this.storageDirectory = Paths.get(valueFromFile);
        this.storageDirectory.toFile().mkdirs();
    }

    public FileSystemResource storeFile(final Integer examinerID,
                                        final Integer testSubmissionID,
                                        final Integer questionID,
                                        final MultipartFile file) throws IOException {

        final Path filePath = Paths.get(
                this.storageDirectory.toString(),
                examinerID.toString(),
                testSubmissionID.toString(),
                questionID.toString()
        );

        filePath.getParent().toFile().mkdirs();
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return new FileSystemResource(filePath);
    }

    public FileSystemResource getFile(final Path path) {
        return new FileSystemResource(path);
    }
}
