package avancado.poo.saft_bndes.controllers;

import org.springframework.data.domain.Pageable;
import avancado.poo.saft_bndes.dto.RegistroResponse;
import avancado.poo.saft_bndes.enums.EstadosBrasileiros;
import avancado.poo.saft_bndes.exceptions.EmptyFileException;
import avancado.poo.saft_bndes.services.OperacaoBndesService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/operacoes")
public class OperacaoBndesController {
    private final OperacaoBndesService service;

    public OperacaoBndesController(OperacaoBndesService service) {
        this.service = service;
    }

    @PostMapping("/carga")
    public ResponseEntity<String> adicionarDados(@RequestParam("file") MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            throw new EmptyFileException("O arquivo CSV está vazio.");
        }

        Path tempFile = Files.createTempFile("carga-bndes-", ".csv");
        arquivo.transferTo(tempFile);

        service.adicionarDados(tempFile);

        return ResponseEntity.accepted().body("Arquivo está sendo processado em segundo plano.");
    }

    @GetMapping("/setor/{setor}")
    public ResponseEntity<Page<RegistroResponse>> findBySetor(
            @PathVariable String setor, Pageable pageable) {
        return ResponseEntity.ok(service.findBySetor(setor, pageable));
    }

    @GetMapping("/valor/{valorAprovado}")
    public ResponseEntity<Page<RegistroResponse>> findByValorAprovado(@PathVariable BigDecimal valorAprovado, Pageable pageable) {
        return ResponseEntity.ok(service.findByValorAprovado(valorAprovado, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<RegistroResponse>> buscarPorEstado(@RequestParam EstadosBrasileiros estado, Pageable pageable) {
        Page<RegistroResponse> operacoes = service.buscarPorEstado(estado, pageable);
        return ResponseEntity.ok(operacoes);
    }

    @GetMapping("/total-por-estado")
    public ResponseEntity<Map<String, BigDecimal>> totalPorEstado() {
        return ResponseEntity.ok(service.totalPorEstado());
    }
}

