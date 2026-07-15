package com.dsoft.mapper;

import com.dsoft.dto.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ApiMapper<T> {

    public ResponseApi<T> mapToApiMapper(T response,String msg)
   {

        return ResponseApi.<T>builder()
               .success(true)
               .status(HttpStatus.OK.value())
               .message(msg)
               .data(response)
               .timeStamp(LocalTime.now())
               .build();
   }
}
