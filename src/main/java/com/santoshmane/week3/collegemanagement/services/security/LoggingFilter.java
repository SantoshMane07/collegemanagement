package com.santoshmane.week3.collegemanagement.services.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    private final String CLASS_NAME = "LoggingFilter";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logRequest(request);
        filterChain.doFilter(request, response);
        logResponse(response);
    }

    private void logRequest(HttpServletRequest request) {
        log.info("Incoming Request: {} {} from {} inside {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr(),CLASS_NAME);
    }

    private void logResponse(HttpServletResponse response) {
        log.info("Outgoing Response: status={} inside {}", response.getStatus(),CLASS_NAME );
    }
}
