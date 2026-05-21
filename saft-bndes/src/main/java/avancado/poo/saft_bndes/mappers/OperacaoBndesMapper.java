package avancado.poo.saft_bndes.mappers;

import avancado.poo.saft_bndes.dto.RegistroResponse;
import avancado.poo.saft_bndes.models.OperacaoBndes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperacaoBndesMapper {
    RegistroResponse toResponse(OperacaoBndes operacao);
}
