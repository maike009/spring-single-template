package com.mk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.jwt")
@Data
public class JwtProperties {

    /**
     * 用户jwt秘钥
     */
    private String userSecretKey;
    /**
     * 用户jwt过期时间
     */
    private Long userExpire;
    /**
     * 用户请求头token名称
     */
    private String userTokenName;

    /**
     * 管理员jwt秘钥
     */
    private String adminSecretKey;
    /**
     * 管理员jwt过期时间
     */
    private Long adminExpire;
    /**
     * 管理员请求头token名称
     */
    private String adminTokenName;
}
