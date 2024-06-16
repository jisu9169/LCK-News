package com.sparta.lck_news.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class RequestLoggingAspect {

  @Before("execution(* com.sparta.lck_news.controller..*(..))")
  public void logBefore() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String method = request.getMethod();
      String requestURI = request.getRequestURI();
      log.info("HTTP Method: {}, Request URL: {}", method, requestURI);
    }
  }

}
