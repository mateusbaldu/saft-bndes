package avancado.poo.saft_bndes.mappers;

import avancado.poo.saft_bndes.dto.RegistroRequest;
import avancado.poo.saft_bndes.dto.RegistroResponse;
import avancado.poo.saft_bndes.models.OperacaoBndes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperacaoBndesMapper {
    RegistroResponse toResponse(OperacaoBndes operacao);

    @Mapping(source = "nome_empresa", target = "nomeEmpresa")
    @Mapping(source = "data_contratacao", target = "data")
    OperacaoBndes toModel(RegistroRequest novoRegistro);
}
