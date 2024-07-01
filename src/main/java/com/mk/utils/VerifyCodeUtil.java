package com.mk.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 验证码相关工具类
 */
@Component
@Slf4j
public class VerifyCodeUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 随机生成指定长度字符串验证码
     *
     * @param length 验证码长度
     */
    public String generateVerifyCode(int length) {
        String strRange = "1234567890";
        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < length; ++i) {
            char ch = strRange.charAt((new Random()).nextInt(strRange.length()));
            strBuilder.append(ch);
        }
        return strBuilder.toString();
    }

    /**
     * 校验验证码（可用作帐号登录、注册、修改信息等业务）
     * 思路：先检查redis中是否有key位对应email的键值对，没有代表验证码过期；如果有就判断用户输入的验证码与value是否相同，进而判断验证码是否正确。
     */
    public Integer checkVerifyCode(String email, String code) {
        int result = 1;
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String msgKey = "msg_" + email;
        String verifyCode = valueOperations.get(msgKey);
        /*校验验证码：0验证码错误、1验证码正确、2验证码过期*/
        log.info(verifyCode);
        if (verifyCode == null) {
            result = 2;
        } else if (!code.equals(verifyCode)) {
            result = 0;
        }
        // 如果验证码正确，则从redis删除
        if (result == 1) {
            stringRedisTemplate.delete(msgKey);
        }
        return result;
    }




}