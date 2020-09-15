package com.navent.realestate.trace;

import com.newrelic.api.agent.NewRelic;

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
        for (int i = 0; i < TracingHeader.values().length; i++) {
        	TracingHeader tracingHeader = TracingHeader.values()[i];
        	Optional.ofNullable(request.getHeader(tracingHeader.getName())).ifPresent(h -> {
        		mdc.put(tracingHeader.getName(), h);
        		if(tracingHeader.isSendToNewrelic()) {
        			NewRelic.addCustomParameter(tracingHeader.getName(), h);
        		}
        	});
		}
        log.debug("MDC after fill {} ", mdc.getCopyOfContextMap());
    }

    public void cleanMDC(MDCAdapter mdc) {
        for (int i = 0; i < TracingHeader.values().length; i++) {
        	String b3Header = TracingHeader.values()[i].getName();
        	mdc.remove(b3Header);
		}
    }

    public Map<String, String> mdcHeadersToMap(MDCAdapter mdc) {
    	Map<String, String> headers = new HashMap<String, String>(TracingHeader.values().length);
    	for (int i = 0; i < TracingHeader.values().length; i++) {
        	String b3Header = TracingHeader.values()[i].getName();
        	Optional.ofNullable(mdc.get(b3Header)).ifPresent(v -> headers.put(b3Header, v));
    	}
    	log.debug("MDC headers map {} ", headers);
    	return headers;
    }

    public MultiValueMap<String, String> mdcHeadersToMultiValueMap(MDCAdapter mdc) {
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(TracingHeader.values().length);
    	for (int i = 0; i < TracingHeader.values().length; i++) {
        	String b3Header = TracingHeader.values()[i].getName();
        	Optional.ofNullable(mdc.get(b3Header)).ifPresent(v -> headers.put(b3Header, Arrays.asList(v)));
    	}
    	log.debug("MDC headers map {} ", headers);
    	return headers;
    }
}
