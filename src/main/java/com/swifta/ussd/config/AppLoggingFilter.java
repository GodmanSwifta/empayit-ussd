package com.swifta.ussd.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppLoggingFilter extends OncePerRequestFilter {

    private final List<String> ignoredPaths = Arrays.asList("/v3/api-docs/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return ignoredPaths.stream().anyMatch(url -> pathMatcher.match(url, requestUri));
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest httpServletRequest,
                                    @Nonnull HttpServletResponse httpServletResponse,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {

        if (isAsyncDispatch(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        ContentCachingRequestWrapper request = wrapRequest(httpServletRequest);
        ContentCachingResponseWrapper response = wrapResponse(httpServletResponse);
        try {
            filterChain.doFilter(request, response);
        } finally {
            logRequest(request);
            logResponse(response);
            response.copyBodyToResponse();
        }
    }

    @SneakyThrows
    private void logResponse(ContentCachingResponseWrapper response) {
        log.info("****************************App Response******************************************");
        log.info("Status code  : {}", response.getStatus());
        log.info("Status text  : {}", response.getStatus());
        log.info("Headers      : {}", buildHeadersMap(response));
        log.info("Response body: {}", new BufferedReader(
                new InputStreamReader(response.getContentInputStream(), response.getCharacterEncoding()))
                .lines()
                .collect(Collectors.joining("\n")));
        log.info("****************************App Response************************************************");

    }

    @SneakyThrows
    private void logRequest(ContentCachingRequestWrapper request) {
        log.info("****************************App Request************************************************");
        log.info("URI         : {}", request.getRequestURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", buildHeadersMap(request));
        log.info("Request body: {}", new String(request.getContentAsByteArray(), request.getCharacterEncoding()));
        log.info("****************************App Request************************************************");

    }

    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }

    private Map<String, String> buildHeadersMap(ContentCachingRequestWrapper request) {
        Map<String, String> map = new HashMap<>();
        Collections.list(request.getHeaderNames())
                .forEach(headerName ->
                        Collections.list(request.getHeaders(headerName))
                                .forEach(headerValue -> map.put(headerName, headerValue)));
        return map;
    }

    private Map<String, String> buildHeadersMap(ContentCachingResponseWrapper response) {
        Map<String, String> map = new HashMap<>();
        response.getHeaderNames()
                .forEach(headerName ->
                        response.getHeaders(headerName)
                                .forEach(headerValue -> map.put(headerName, headerValue)));
        return map;
    }
}
