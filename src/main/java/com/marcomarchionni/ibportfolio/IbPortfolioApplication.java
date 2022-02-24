package com.marcomarchionni.ibportfolio;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.services.IBReportParser;
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
			Position pos = new Position();
			pos.setConId("00033444");
			pos.setMarketPrice("207.5");
			pos.setCostBasisPrice("200.45");
			pos.setQuantity("3");
			pos.setTicker("AAPL");
			pos.setMultiplier("1");
			System.out.println(pos.toString());
			//repo.saveAndFlush(pos);
			IBReportParser parser = new IBReportParser();
			parser.parse("flex.xml");
			parser.printAll();

		};
	}
}
