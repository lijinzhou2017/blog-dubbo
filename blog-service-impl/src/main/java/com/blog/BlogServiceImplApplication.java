package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author lijinzhou
 * @since 2017/12/16 21:10
 */
@SpringBootApplication
@ImportResource({ "classpath:dubbo-provider.xml" })
@MapperScan("com.blog.dao")
public class BlogServiceImplApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogServiceImplApplication.class, args);
	}

}
