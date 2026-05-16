package avancado.poo.saft_bndes.repositories;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;

import avancado.poo.saft_bndes.models.OperacaoBndes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OperacaoBndesRepository extends JpaRepository<OperacaoBndes, Long> {
    Page<OperacaoBndes> findBySetor(String setor, Pageable pageable);
    Page<OperacaoBndes> findByEstado(String estado, Pageable pageable);
    Page<OperacaoBndes> findByValorGreaterThanEqual(BigDecimal valorAprovado, Pageable pageable);
}
