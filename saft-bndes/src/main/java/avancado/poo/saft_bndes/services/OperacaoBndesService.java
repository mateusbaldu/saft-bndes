package avancado.poo.saft_bndes.services;

import avancado.poo.saft_bndes.dto.RegistroResponse;
import avancado.poo.saft_bndes.enums.EstadosBrasileiros;
import avancado.poo.saft_bndes.mappers.OperacaoBndesMapper;
import avancado.poo.saft_bndes.models.OperacaoBndes;
import avancado.poo.saft_bndes.repositories.OperacaoBndesRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperacaoBndesService {
    private final OperacaoBndesRepository repository;
    private final OperacaoBndesMapper mapper;

    public OperacaoBndesService(OperacaoBndesRepository repository, OperacaoBndesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Async
    public void adicionarDados(Path arquivo) {
        final int TAMANHO_DO_LOTE = 1000;
        int linhasSalvas = 0;

        try (Reader reader = Files.newBufferedReader(arquivo, StandardCharsets.ISO_8859_1)) {
            CsvToBean<OperacaoBndes> csvToBean = new CsvToBeanBuilder<OperacaoBndes>(reader)
                    .withType(OperacaoBndes.class)
                    .withSeparator(';')
                    .build();

            Iterator<OperacaoBndes> iterador = csvToBean.iterator();

            List<OperacaoBndes> loteTemporario = new ArrayList<>();

            while (iterador.hasNext()) {
                OperacaoBndes operacao = iterador.next();

                if (operacao.getValor() == null) {
                    continue;
                }

                if (operacao.getEstado() != null) {
                    operacao.setEstado(operacao.getEstado().trim());
                }

                loteTemporario.add(operacao);

                if (loteTemporario.size() == TAMANHO_DO_LOTE) {
                    repository.saveAll(loteTemporario);
                    linhasSalvas += loteTemporario.size();
                    loteTemporario.clear();
                }
            }

            if (!loteTemporario.isEmpty()) {
                repository.saveAll(loteTemporario);
                linhasSalvas += loteTemporario.size();
            }

            System.out.println("Carga finalizada. " + linhasSalvas + " linhas salvas.");
        } catch (Exception e) {
            System.out.println("Erro ao processar arquivo CSV: " + e.getMessage());
        } finally {
            try {
                Files.deleteIfExists(arquivo);
            } catch (IOException e) {
                System.out.println("Não foi possível deletar arquivo temporário: " + arquivo);
            }
        }
    }
  
    public RegistroResponse buscarPorId(Long id) {
        OperacaoBndes operacao = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Operação com ID " + id + " não encontrada."));
        return mapper.toResponse(operacao);
    }

    public Page<RegistroResponse> findBySetor(String setor, Pageable pageable) {
        Page<OperacaoBndes> resultado = repository.findBySetor(setor, pageable);
        if (resultado.isEmpty()) {
            throw new EntityNotFoundException("Setor '" + setor + "' não encontrado.");
        }
        return resultado.map(mapper::toResponse);
    }

    public Page<RegistroResponse> buscarPorEstado(EstadosBrasileiros estado, Pageable pageable) {
        return repository.findByEstado(estado.name(), pageable)
                .map(mapper::toResponse);
    }

    public Map<String, BigDecimal> totalPorEstado() {
        return repository.somarFinanciamentoPorEstado().stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (BigDecimal) row[1],
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }
}

