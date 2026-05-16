package avancado.poo.saft_bndes.repositories;

import avancado.poo.saft_bndes.models.OperacaoBndes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OperacaoBndesRepository extends JpaRepository<OperacaoBndes, Long> {
    Page<OperacaoBndes> findBySetor(String setor, Pageable pageable);
    Page<OperacaoBndes> findByEstado(String estado, Pageable pageable);
    Page<OperacaoBndes> findByValorGreaterThanEqual(BigDecimal valorAprovado, Pageable pageable);

    @Query("SELECT o.estado, SUM(o.valor) FROM OperacaoBndes o GROUP BY o.estado")
    List<Object[]> somarFinanciamentoPorEstado();
}
