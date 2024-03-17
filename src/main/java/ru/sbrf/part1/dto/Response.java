package ru.sbrf.part1.dto;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class Response {
    private HttpStatus status;
    private String dateHeader;
    private String content;
}
