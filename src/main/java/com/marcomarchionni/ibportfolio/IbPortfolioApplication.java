package com.marcomarchionni.ibportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.marcomarchionni.ibportfolio")
@EnableScheduling
public class IbPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbPortfolioApplication.class, args);
	}
	// App
	/*@Bean
	public CommandLineRunner demo() {
		return args -> {
			IBReportParser parser = new IBReportParser();
			parser.parse("flex.xml");
			IBUpdate update = parser.getIBUpdate();
			update.print();
		};
	}*/
}
