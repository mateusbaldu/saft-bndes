package avancado.poo.saft_bndes;

import org.springframework.data.domain.Pageable;

import avancado.poo.saft_bndes.models.OperacaoBndes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import avancado.poo.saft_bndes.repositories.OperacaoBndesRepository;

@SpringBootApplication
@EnableAsync
public class SaftBndesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaftBndesApplication.class, args);
	}


}
