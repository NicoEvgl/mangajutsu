package com.mangajutsu.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mangajutsu.webclient")
public class MangajutsuWebclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MangajutsuWebclientApplication.class, args);
	}

}
