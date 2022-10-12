package com.sparta.jwtBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing	//Spring Audit 기능을 활용하기 위해 (LocalDateTime)
@SpringBootApplication	//스프링을 자동 설정을 해주기 위한 어노테이션
public class JwtBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtBoardApplication.class, args);
	}

}
