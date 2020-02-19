package com.example.projektKoncowy;

import com.example.projektKoncowy.model.Furniture;
import com.example.projektKoncowy.model.TypeOfFurniture;
import com.example.projektKoncowy.repository.FurnitureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjektKoncowyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjektKoncowyApplication.class, args);
	}


	@Bean
	public CommandLineRunner dataLoader(FurnitureRepository repo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				repo.save(new Furniture("ONE", "MADRIT", TypeOfFurniture.CHAIRS));
				repo.save(new Furniture("TWO", "ROMA", TypeOfFurniture.TABLES));
				repo.save(new Furniture("THREE", "BARCELONA", TypeOfFurniture.ARMCHAIRS));
				repo.save(new Furniture("CARN", "MEDIOLAN", TypeOfFurniture.SOFAS));

			}
		};
	}
}
