package com.mk.module.controller.common;

import com.mk.constant.MessageErrorConstant;
import com.mk.module.service.EmailService;
import com.mk.pojo.dto.LoginDto;
import com.mk.pojo.result.Result;
import com.mk.utils.AliOssUtil;
import com.mk.utils.ControllerScanner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
    private final EmailService emailService;
    private final AliOssUtil aliOssUtil;

    private final ControllerScanner controllerScanner;

    public CommonController(EmailService emailService, AliOssUtil aliOssUtil, ControllerScanner controllerScanner) {
        this.emailService = emailService;
        this.aliOssUtil = aliOssUtil;
        this.controllerScanner = controllerScanner;
    }

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

    /**
     * 获取所有controller包下的所有接口地址
     * @return
     */
    @GetMapping("/api-paths")
    public Result<Set<String>> getAllApiPath() {
        return Result.success(controllerScanner.getAllControllerEndpoints());
    }


    @GetMapping("/common/hello")
    public void hello () {

    }



}
