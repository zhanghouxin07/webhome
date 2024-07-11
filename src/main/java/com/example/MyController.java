package com.example;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class MyController {

    @GetMapping("/hello")
    public String hello() {
        return "{\"truth\":\"爱你胖胖\",\"time\": \"一万年\",\"city\":\"Everywhere\"}";
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String imageName) throws IOException {
        File file = new File("src/main/resources/static/pages/photos/" + imageName + ".jpg");  // 替换为您实际的图片存储路径
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }
}