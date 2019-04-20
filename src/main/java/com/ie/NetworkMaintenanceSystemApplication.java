package com.ie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Mapper接口不用写@Mapper，直接使用@MapperScan进行包扫描
@MapperScan("com.ie.dao")
public class NetworkMaintenanceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkMaintenanceSystemApplication.class, args);
	}
}
