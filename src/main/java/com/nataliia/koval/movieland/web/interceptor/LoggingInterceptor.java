package com.nataliia.koval.movieland.web.interceptor;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@NonNullApi
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        String requestId = MDC.get("requestId");
        String userIdentifier = MDC.get("userIdentifier");

        String email = userIdentifier != null ? userIdentifier : "guest";

        logger.info(String.format("[%s] %s %s - Successful signing up for user '%s'", Thread.currentThread().getName(), requestId, request.getMethod(), email));

        MDC.remove("requestId");
        MDC.remove("userIdentifier");
    }
}
