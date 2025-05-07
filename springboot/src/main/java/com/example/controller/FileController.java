package com.example.controller;

/**
 * @Auther: youMeng
 * @Date: 2025/4/14 - 04 - 14 - 19:46
 * @Description: com.example.controller
 * @version: 1.0
 */

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.example.common.Result;
import com.example.exception.CustomerException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理文件上传下载相关的接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    /**
     * 文件上传
     */

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 找到文件的位置
        String filePath = System.getProperty("user.dir") + "/files/";
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        byte[] bytes = file.getBytes();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();// 文件的原始名称
        // 写入文件
        FileUtil.writeBytes(bytes, filePath + fileName);
        String url = "http://localhost:8088/files/download/" + fileName;
        return Result.success(url);
    }

    /**
     * 文件下载
     * 下载路径:http://localhost:8088/files/download/ht1.png
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        // 找到文件的位置
        String filePath = System.getProperty("user.dir") + "/files/"; // 获取到当前项目的根路径 (code2025的绝对路径 C:\Users\12437\Desktop\小白\代码\code2025)
        String realPath = filePath + fileName; // C:\Users\12437\Desktop\小白\代码\code2025\files\ht1.png
        boolean exist = FileUtil.exist(realPath);
        if (!exist) {
            throw new CustomerException("文件不存在");
        }
        // 读取文件的字节流
        byte[] bytes = FileUtil.readBytes(realPath);
        ServletOutputStream os = response.getOutputStream();
        // 输出流对象把文件写出到客户端
        os.write(bytes);
        os.flush();
        os.close();
    }

    /**
     * wang-editor编辑器文件上传接口
     */
    @PostMapping("/wang/upload")
    public Map<String, Object> wngEditorUpload(MultipartFile file) {
        String flag = System.currentTimeMillis() + "";
        String fileName = file.getOriginalFilename();
        try {
            // 找到文件的位置
            String filePath = System.getProperty("user.dir") + "/files/";
            // 文件存储形式:时间戳-文件名
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
            System.out.println(fileName + "--上传成功");
            Thread.sleep(1L);
        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        String http = "http://localhost:8088/files/download/";
        Map<String, Object> resMap = new HashMap<>();
        // wangEditor 上传图片成功后, 需要返回的参数
        resMap.put("errno", 0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url", http + flag + "-" + fileName)));
        return resMap;
    }
}
