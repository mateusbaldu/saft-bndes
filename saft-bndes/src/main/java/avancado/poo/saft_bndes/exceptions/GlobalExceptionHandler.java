package avancado.poo.saft_bndes.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS dd/MM/yyyy");

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> Map.of(
                        "field", violation.getPropertyPath().toString(),
                        "message", violation.getMessage()))
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage() != null ? error.getDefaultMessage() : "Valor inválido"))
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "Erro de validação nos campos enviados.", errors);
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<Object> handleEmptyFileException(EmptyFileException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String invalidValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String message = "Valor '" + invalidValue + "' é inválido para o parâmetro '" + paramName + "'.";
        return buildResponse(HttpStatus.BAD_REQUEST, message, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor.", null);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, List<Map<String, String>> errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("timestamp", LocalDateTime.now().format(formatter));
        response.put("message", message);

        if (errors != null) {
            response.put("errors", errors);
        }

        return ResponseEntity.status(status).body(response);
    }
}
