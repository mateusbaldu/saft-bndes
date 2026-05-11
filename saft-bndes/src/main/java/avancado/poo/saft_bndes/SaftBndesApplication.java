package avancado.poo.saft_bndes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SaftBndesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaftBndesApplication.class, args);
	}

}
