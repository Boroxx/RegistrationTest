package com.boristenelsen.registrationTest.services;

import com.boristenelsen.registrationTest.Exceptions.FileStorageException;
import com.boristenelsen.registrationTest.Exceptions.MyFileNotFoundException;
import com.boristenelsen.registrationTest.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();


        //this.fileStorageLocation = Paths.get("test").toAbsolutePath().normalize();
    }

    public String storeFile(MultipartFile file, String id) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path temp = this.fileStorageLocation.resolve(id);
            Files.createDirectories(temp);

            // Copy file to the target location (Replacing existing file with the same name)

            Path targetLocation = temp.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String id) {
        try {
            Path temp = this.fileStorageLocation.resolve(id);
            Path filePath = temp.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public String getDownloadUri(String fileName, String folderid) {

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/" + folderid + "/")
                .path(fileName)
                .toUriString();
    }

    public String getViewUri(String fileName, String folderid) {

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(folderid + "/")
                .path(fileName)
                .toUriString();
    }
}
