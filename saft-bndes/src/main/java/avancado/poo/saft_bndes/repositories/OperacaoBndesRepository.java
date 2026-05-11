package avancado.poo.saft_bndes.repositories;

import avancado.poo.saft_bndes.models.OperacaoBndes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoBndesRepository extends JpaRepository<OperacaoBndes, Long> {
}
