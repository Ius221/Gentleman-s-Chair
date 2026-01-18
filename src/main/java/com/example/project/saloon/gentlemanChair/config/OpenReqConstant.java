package com.example.project.saloon.gentlemanChair.config;

public class OpenReqConstant {
    public static String[] getAllOpenRequest() {
        String[] allOpen = {
                "/auth/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/webjars/**"
        };
        return allOpen;
    }
}
