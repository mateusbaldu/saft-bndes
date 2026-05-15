package avancado.poo.saft_bndes.controllers;

import org.springframework.data.domain.Pageable;
import avancado.poo.saft_bndes.dto.RegistroResponse;
import avancado.poo.saft_bndes.enums.EstadosBrasileiros;
import avancado.poo.saft_bndes.exceptions.EmptyFileException;
import avancado.poo.saft_bndes.models.OperacaoBndes;
import avancado.poo.saft_bndes.services.OperacaoBndesService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public ResponseEntity<Page<OperacaoBndes>> findBySetor(
            @PathVariable String setor, Pageable pageable) {
        return ResponseEntity.ok(service.findBySetor(setor, pageable));
      
    @GetMapping
    public ResponseEntity<Page<RegistroResponse>> buscarPorEstado(@RequestParam EstadosBrasileiros estado, Pageable pageable) {
        Page<RegistroResponse> operacoes = service.buscarPorEstado(estado, pageable);
        return ResponseEntity.ok(operacoes);
    }
}
