package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 业务实现层
 *
 * @author lijinzhou
 * @since 2017/12/16 21:10
 */
@SpringBootApplication
@EnableTransactionManagement//开启事物注解
@ImportResource({"classpath:dubbo-provider.xml"})
@MapperScan("com.blog.dao")
public class BlogServiceImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceImplApplication.class, args);
    }

}
