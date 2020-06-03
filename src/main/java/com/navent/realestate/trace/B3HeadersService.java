package com.navent.realestate.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.MDCAdapter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public class B3HeadersService {

	private Logger log = LoggerFactory.getLogger(getClass());

    public void fillMDC(HttpServletRequest request, MDCAdapter mdc) {
    	log.debug("MDC before fill {} ", mdc.getCopyOfContextMap());
        for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	Optional.ofNullable(request.getHeader(b3Header)).ifPresent(h -> mdc.put(b3Header, h));
		}
        log.debug("MDC after fill {} ", mdc.getCopyOfContextMap());
    }

    public void cleanMDC(MDCAdapter mdc) {
        for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	mdc.remove(b3Header);
		}
    }

    public Map<String, String> mdcHeadersToMap(MDCAdapter mdc) {
    	Map<String, String> headers = new HashMap<String, String>(B3Header.values().length);
    	for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	Optional.ofNullable(mdc.get(b3Header)).ifPresent(v -> headers.put(b3Header, v));
    	}
    	log.debug("MDC headers map {} ", headers);
    	return headers;
    }

    public MultiValueMap<String, String> mdcHeadersToMultiValueMap(MDCAdapter mdc) {
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(B3Header.values().length);
    	for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	Optional.ofNullable(mdc.get(b3Header)).ifPresent(v -> headers.put(b3Header, Arrays.asList(v)));
    	}
    	log.debug("MDC headers map {} ", headers);
    	return headers;
    }
}
