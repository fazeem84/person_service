package com.fazeem.personservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Slf4j
public class PersonserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonserviceApplication.class, args);
	}



	@Bean
	@Profile("test")
	public FlywayMigrationStrategy cleanMigrateStrategy() {
		log.info("****************************Flyway Testing Clean************");
		return flyway ->{	flyway.clean();	flyway.migrate();};

	}

}
