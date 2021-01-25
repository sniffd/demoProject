package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest
@ExtendWith(SpringExtension.class)
class MainControllerTest {

    @MockBean
    LogRepository logRepository;

    MainController controller;
    @MockBean
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        controller = new MainController(logRepository, objectMapper);
    }

    @Test
    void testThatLogIsSavedToDb() throws Exception {
        LogRequest request = new LogRequest("test", "test", "test", Timestamp.from(Instant.now()));
        when(logRepository.save(any(LogEntity.class))).thenReturn(null);
        ResponseEntity<String> voidResponseEntity = controller.saveLog(request);
        assertThat(voidResponseEntity).isNotNull();
        assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(logRepository).save(any());
    }

    @Test
    void messageIsNullTest() throws Exception {
        LogRequest request = new LogRequest(null, "test", "test", Timestamp.from(Instant.now()));
        ResponseEntity<String> voidResponseEntity = controller.saveLog(request);
        assertThat(voidResponseEntity).isNotNull();
        assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}