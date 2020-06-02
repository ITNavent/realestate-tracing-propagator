package com.navent.realestate.trace;

import org.slf4j.MDC;

import java.util.Optional;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class B3HeadersPropagator implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	Optional.ofNullable(MDC.get(b3Header)).ifPresent(v -> template.header(b3Header, v));
		}
	}
}
