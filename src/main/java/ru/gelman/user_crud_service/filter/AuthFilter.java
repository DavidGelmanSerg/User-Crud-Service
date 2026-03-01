package ru.gelman.user_crud_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    private static final String authTokenKey = "authToken";

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String serverAuthToken = (String) request.getSession().getAttribute(authTokenKey);
        String authToken = request.getHeader(authTokenKey);
        log.info("checking client auth");
        log.debug("server token from http session: {}, client token from request: {}", serverAuthToken, authToken);
        if (serverAuthToken != null && serverAuthToken.equals(authToken)) {
            log.info("client auth check successful");
            filterChain.doFilter(request, response);
        }

        log.info("client auth check failed");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "auth token check failed");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        boolean authRequest = url.startsWith("/auth");
        boolean registryRequest = method.equalsIgnoreCase("POST") && url.startsWith("/users");
        return authRequest || registryRequest;
    }
}
