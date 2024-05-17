package com.nataliia.koval.movieland.web.interceptor;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
@NonNullApi
public class LoggingInterceptor implements HandlerInterceptor {

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

        log.info(String.format("[%s] %s %s - Successful signing up for user '%s'", Thread.currentThread().getName(), requestId, request.getMethod(), email));

        MDC.remove("requestId");
        MDC.remove("userIdentifier");
    }
}
