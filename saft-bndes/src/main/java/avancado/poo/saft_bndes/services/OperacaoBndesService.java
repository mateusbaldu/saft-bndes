package avancado.poo.saft_bndes.services;

import org.springframework.data.domain.Pageable;
import avancado.poo.saft_bndes.models.OperacaoBndes;
import avancado.poo.saft_bndes.repositories.OperacaoBndesRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OperacaoBndesService {
    private final OperacaoBndesRepository repository;

    public OperacaoBndesService(OperacaoBndesRepository repository) {
        this.repository = repository;
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
    public Page <OperacaoBndes> findBySetor(String setor, Pageable pageable){
        Page<OperacaoBndes> resultado = repository.findBySetor(setor, pageable);
        if (resultado.isEmpty()){
            throw new EntityNotFoundException("Setor '" + setor + "' não encontrado.");
        }
        return resultado;
    }
}
