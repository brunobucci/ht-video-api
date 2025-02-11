package com.br.fiap.postech.ht_video_api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class HtVideoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtVideoApiApplication.class, args);
	}

}
