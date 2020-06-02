package com.navent.realestate.trace;

import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class B3HeadersInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	Optional.ofNullable(request.getHeader(b3Header)).ifPresent(h -> MDC.put(b3Header, h));
		}
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	MDC.remove(b3Header);
		}
    }
}
