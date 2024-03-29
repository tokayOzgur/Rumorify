package com.rumorify.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class FileService {

    public String saveBase4StringAsFile(String image) {
        String fileName = UUID.randomUUID().toString();
        File file = new File(fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            byte[] base64decoded = Base64.getDecoder().decode(image.split(",")[1]);
            os.write(base64decoded);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
