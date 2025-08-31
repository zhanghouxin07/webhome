package com.example;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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
        System.out.println("receive upload files");
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件大小（单位：字节）
        long fileSizeInBytes = file.getSize();
        // 将字节转换为兆字节（MB）并打印
        double fileSizeInMb = fileSizeInBytes / (1024.0 * 1024.0);
        System.out.println("文件大小：" + fileSizeInMb + " MB");

        // 创建文件对象
        File destFile = new File(uploadDirectory + fileName);

        // 计算上传进度
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        long totalBytesRead = 0;

        // 记录上次打印进度的时间
        long lastPrintTime = System.currentTimeMillis();

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(destFile)) {
            while ((bytesRead = inputStream.read(buffer))!= -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                // 计算当前时间与上次打印进度时间的差值
                long currentTime = System.currentTimeMillis();
                long timeDifference = currentTime - lastPrintTime;

                // 如果时间差大于 500 毫秒（0.5 秒），打印上传进度
                if (timeDifference >= 500) {
                    double progress = (double) totalBytesRead / fileSizeInBytes * 100;
                    System.out.println("上传进度: " + progress + "%");
                    lastPrintTime = currentTime;
                }
            }
        }

        return "上传成功";
    }
}