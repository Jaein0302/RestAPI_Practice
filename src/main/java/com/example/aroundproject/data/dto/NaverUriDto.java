package com.example.aroundproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
//응답 받는 데이터
public class NaverUriDto {

    private String message;

    private String code;

    private Result result;

    @Getter
    @Setter
    public static class Result {

        private String hash;

        private String url;

        private String orgUrl;

    }

}