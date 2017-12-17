package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ImportResource({ "classpath:dubbo/*.xml" })
public class BlogAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAdminApplication.class, args);
	}

}
