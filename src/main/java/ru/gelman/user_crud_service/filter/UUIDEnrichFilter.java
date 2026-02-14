package ru.gelman.user_crud_service.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class UUIDEnrichFilter implements Filter {
    private static final String UUID_HEADER_NAME = "x-uuid";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uuid = UUID.randomUUID().toString();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
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
