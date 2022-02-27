package com.marcomarchionni.ibportfolio;

import com.marcomarchionni.ibportfolio.services.IBReportParser;
import com.marcomarchionni.ibportfolio.services.IBUpdate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IbPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbPortfolioApplication.class, args);
	}
	// App
	@Bean
	public CommandLineRunner demo() {
		return args -> {
			IBReportParser parser = new IBReportParser();
			parser.parse("flex.xml");
			IBUpdate update = parser.getIBUpdate();
			update.getPositions().forEach(System.out::println);
			update.getTrades().forEach(System.out::println);
		};
	}
}
