package avancado.poo.saft_bndes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistroRequest(
        String nome_empresa,
        String setor,
        BigDecimal valor,
        String estado,
        LocalDate data_contratacao
) {
}
