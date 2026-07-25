package io.github.kimukenyuu.asobou;

import org.springframework.boot.SpringApplication;

public class TestAsobouApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(AsobouApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
