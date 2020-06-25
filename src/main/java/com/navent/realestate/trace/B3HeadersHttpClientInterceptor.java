package com.navent.realestate.trace;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.MDC;

import java.io.IOException;

public class B3HeadersHttpClientInterceptor implements HttpRequestInterceptor {

	private B3HeadersService b3HeadersService;

	public B3HeadersHttpClientInterceptor(B3HeadersService b3HeadersService) {
		this.b3HeadersService = b3HeadersService;
	}

	@Override
	public void process(HttpRequest req, HttpContext ctx) throws HttpException, IOException {
		b3HeadersService.mdcHeadersToMap(MDC.getMDCAdapter()).forEach((k, v) -> {
			req.addHeader(k, v);
		});
	}
}
