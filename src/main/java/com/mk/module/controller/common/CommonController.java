package com.mk.module.controller.common;

import com.mk.constant.MessageErrorConstant;
import com.mk.module.service.EmailService;
import com.mk.pojo.dto.LoginDto;
import com.mk.pojo.result.Result;
import com.mk.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/email")
    @ApiOperation("发送邮箱验证码")
    public Result<String> sendEmail (@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String msg = emailService.sendEmail(email);
        log.info("用户的邮件为：{}",email);
        return Result.success(msg);
    }

    /**
     * 文件上传
     * @param files
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<List<String>> upload(@RequestParam List<MultipartFile> files){
        log.info("文件上传：{}",files);
        List<String> fileList = new ArrayList<>();
        try {
            for (MultipartFile f: files) {
//                原始文件

                String originalFilename = f.getOriginalFilename();
                //截取原始文件名的后缀（jpg/png...）
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String newFilename = UUID.randomUUID().toString()+"-"+ formatDate + extension;
                String filePath = aliOssUtil.upload(f.getBytes(), newFilename);
                fileList.add(filePath);
            }
            return Result.success(fileList);
        } catch (IOException e) {
            log.info("文件上传失败：{}",e.getMessage());
        }
        return Result.error(MessageErrorConstant.UPLOAD_FAILED);
    }



}
