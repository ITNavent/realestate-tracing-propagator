package com.navent.realestate.trace;

public enum B3Header {
	REQ_ID("x-request-id"),
	TRACEID("x-b3-traceid"),
	SPANID("x-b3-spanid"),
	PARENTSPANID("x-b3-parentspanid"),
	SAMPLED("x-b3-sampled"),
	FLAGS("x-b3-flags"),
	B3("b3");

	private String name;
	
	private B3Header(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}