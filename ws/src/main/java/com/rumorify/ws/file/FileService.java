package com.rumorify.ws.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumorify.ws.config.RumorifyProperties;

@Service
public class FileService {
    @Autowired
    private RumorifyProperties props;
    private Tika tika = new Tika();

    public String saveBase4StringAsFile(String image) {
        String fileName = UUID.randomUUID().toString() + "." + getFileType(image).split("/")[1];
        Path path = getFilePath(fileName);
        try {
            Files.createDirectories(path.getParent());
            OutputStream os = new FileOutputStream(path.toFile());
            os.write(decodedImage(image));
            os.close();
            return fileName;
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public String getFileType(String value) {
        return tika.detect(decodedImage(value));
    }

    private byte[] decodedImage(String image) {
        return Base64.getDecoder().decode(image.split(",")[1]);
    }

    public void deleteFile(String image) {
        if (image == null)
            return;
        Path path = getFilePath(image);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private Path getFilePath(String fileName) {
        return Paths.get(props.getStorage().getRoot(), props.getStorage().getProfile(), fileName);
    }

}
