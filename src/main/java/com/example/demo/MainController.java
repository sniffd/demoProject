package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

@Slf4j
@RestController
@Api("mainController")
@RequiredArgsConstructor
public class MainController {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("/core-api")
    @ApiOperation(value = "method that saves logs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "log saved",response = String.class)})
    public ResponseEntity<String> saveLog(@RequestBody @Valid LogRequest request) throws Exception {
        logRepository.save(request.toEntity());
        String requestString = objectMapper.writeValueAsString(request);
        log.info("{}", requestString);
        Path file = Paths.get("demo.log");
        Files.write(file, Collections.singletonList(requestString), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage()));
    }
}
