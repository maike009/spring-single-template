package com.mk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.alioss")
@Data
public class AliOssProperties {
    /**
     * OSS服务的访问域名
     */
    private String endpoint;

    /**
     * 阿里云账户的Access Key ID
     */
    private String accessKeyId;

    /**
     * 阿里云账户的Access Key Secret
     */
    private String accessKeySecret;

    /**
     * OSS存储空间的名称
     */
    private String bucketName;
}
