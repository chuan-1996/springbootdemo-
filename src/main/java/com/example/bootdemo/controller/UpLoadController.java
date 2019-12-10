package com.example.bootdemo.controller;

import com.example.bootdemo.entity.User;
import com.example.bootdemo.service.UserService;
import com.example.bootdemo.utils.isEmpty;
import com.example.bootdemo.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


/**
 * @author qq491
 */
@Controller
@RequestMapping("/file")
public class UpLoadController {

    @Autowired
    public UserService userService;

    @ResponseBody
    @RequestMapping(value = "/imageUpload" ,method = RequestMethod.POST)
    public ResultVo saveImage(@RequestPart(value = "file") MultipartFile file){
        System.out.println("uploading..");
        String filepath="E:/root/image/";
        if (file.isEmpty()) {
            return new ResultVo(0,"文件为空",null);
        }
        Subject subject = SecurityUtils.getSubject();
        if(isEmpty.isObjectNotEmpty(subject)) {
            User a = userService.getUser();
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if (!".jpg".equals(suffixName) && !".jpeg".equals(suffixName) && !".png".equals(suffixName) && !".bmp".equals(suffixName)
                    && !".gif".equals(suffixName)) {
                return new ResultVo(0,"上传失败:无效图片文件类型",null);
            }
            long fileSize = file.getSize();
            if(fileSize>1024*2048){
                return new ResultVo(0,"图片过大",null);
            }
            filepath=filepath+a.getImg()+".png";
            File dest = new File(filepath);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }System.out.println("上传完毕"+filepath);
            return new ResultVo(1,"上传完毕",a);

        }
        return new ResultVo(0,"服务器错误",null);
     }

}
