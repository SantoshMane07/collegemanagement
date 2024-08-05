package com.santoshmane.week3.collegemanagement.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

    @Data
    public class ApiResponse<T> {
        private ApiError apiError;
        private T data;
        @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
        private LocalDateTime timeStamp;

        public ApiResponse(){
            this.timeStamp = LocalDateTime.now();
        }

        public ApiResponse(ApiError apiError){
            this();
            this.apiError = apiError;
        }

        public ApiResponse(T data){
            this();
            this.data = data;
        }

    }
