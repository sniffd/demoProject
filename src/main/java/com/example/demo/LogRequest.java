package com.example.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@ApiModel("Log")
@AllArgsConstructor
public class LogRequest {

    @ApiModelProperty(value = "hui", name = "message")
    @NotEmpty(message = "message cannot be empty")
    private String message;
    @NotEmpty(message = "type cannot be empty")
    private String type;
    @NotEmpty(message = "level cannot be empty")
    private String level;
    @NotNull(message = "time cannot be empty")
    private Timestamp time;

    public LogEntity toEntity() {
        return new LogEntity(message, type, level, time);
    }
}
