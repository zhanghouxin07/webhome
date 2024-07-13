package com.example;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "上传文件为空";
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 创建文件对象
        File destFile = new File(uploadDirectory + fileName);
        // 保存文件
        FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

        return "上传成功";
    }
}