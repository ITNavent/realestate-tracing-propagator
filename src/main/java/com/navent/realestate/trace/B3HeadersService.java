package com.navent.realestate.trace;

import org.slf4j.spi.MDCAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public class B3HeadersService {

    public void fillMDC(HttpServletRequest request, MDCAdapter mdc) {
        for (int i = 0; i < B3Header.values().length; i++) {
        	String b3Header = B3Header.values()[i].getName();
        	Optional.ofNullable(request.getHeader(b3Header)).ifPresent(h -> mdc.put(b3Header, h));
		}
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
    	return headers;
    }
}
