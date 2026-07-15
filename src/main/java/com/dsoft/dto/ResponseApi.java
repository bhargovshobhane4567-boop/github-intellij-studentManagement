package com.dsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Builder
@Data
@AllArgsConstructor
public class ResponseApi <T> {

    private  boolean success;
    private String message;
    private  T data;
    private int status;
    private LocalTime timeStamp;

}
