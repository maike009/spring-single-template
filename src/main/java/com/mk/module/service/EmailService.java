package com.mk.module.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mk.constant.MessageErrorConstant;
import com.mk.constant.MessageSuccessConstant;
import com.mk.utils.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.time.Duration;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private VerifyCodeUtil verifyCodeUtil;
    public String sendEmail(String email){
        if (StringUtils.isBlank(email)) {
            throw new RuntimeException("未填写收件人邮箱");
        }
        // 定义Redis的key
        String key = "msg_" + email;

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String verifyCode = valueOperations.get(key);
        if (verifyCode == null) {
            // 随机生成一个6位数字型的字符串
            String code = verifyCodeUtil.generateVerifyCode(6);
            // 邮件对象（邮件模板，根据自身业务修改）
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("****注册邮箱验证码");
            message.setText("尊敬的用户您好!\n\n感谢您注册***。\n\n尊敬的: " + email + "您的校验验证码为: " + code + ",有效期5分钟，请不要把验证码信息泄露给其他人,如非本人请勿操作");
            message.setTo(email);

            try {
                // 对方看到的发送人（发件人的邮箱，根据实际业务进行修改，一般填写的是企业邮箱）
                message.setFrom(new InternetAddress(MimeUtility.encodeText("企业名称") + "<1**@163.com>").toString());
                // 发送邮件
                javaMailSender.send(message);
                // 将生成的验证码存入Redis数据库中，并设置过期时间
                valueOperations.set(key, code,  Duration.ofMinutes(5L));
                log.info("邮件发送成功");
                return MessageSuccessConstant.SEND_EMAIL_SUCCESS;
            } catch (Exception e) {
                log.error("邮件发送出现异常");
                log.error("异常信息为" + e.getMessage());
                log.error("异常堆栈信息为-->");
                log.error("邮件发送失败");
                return MessageErrorConstant.SEND_EMAIL_FAILED;
                //e.printStackTrace();
                //throw new RuntimeException(e);
            }
        } else {
            log.info("验证码已发送至您的邮箱，请注意查收");
            return MessageSuccessConstant.SEND_EMAIL_ALREADY;
        }
    }
}
