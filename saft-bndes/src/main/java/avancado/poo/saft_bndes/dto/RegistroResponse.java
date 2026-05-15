package avancado.poo.saft_bndes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistroResponse(
        Long id,
        String nomeEmpresa,
        String setor,
        BigDecimal valor,
        String estado,
        LocalDate data
) {
}