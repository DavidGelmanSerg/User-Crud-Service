package ru.gelman.user_crud_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class UUIDEnrichFilter extends OncePerRequestFilter {
    private static final String UUID_HEADER_NAME = "x-uuid";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        try {
            request.setAttribute(UUID_HEADER_NAME, uuid);
            response.setHeader(UUID_HEADER_NAME, uuid);
            MDC.put(UUID_HEADER_NAME, uuid);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
