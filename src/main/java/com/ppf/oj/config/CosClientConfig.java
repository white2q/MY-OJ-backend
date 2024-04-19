package com.ppf.oj.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云对象存储客户端
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cos.client")
public class CosClientConfig {

    /**
     * accessKey
     */
    private String accessKeyId;

    /**
     * secretKey
     */
    private String accessKeySecret;

    private String endpoint;

    private String bucketName;

    @Bean
    public OSS cosClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}