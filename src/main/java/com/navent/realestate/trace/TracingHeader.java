package com.navent.realestate.trace;

public enum TracingHeader {
	REQ_ID("x-request-id", true),
	TRACEID("x-b3-traceid", false),
	SPANID("x-b3-spanid", false),
	PARENTSPANID("x-b3-parentspanid", false),
	SAMPLED("x-b3-sampled", false),
	FLAGS("x-b3-flags", false),
	B3("b3", false),
	X_UUID("x-uuid", true),
	TRACEPARENT("traceparent", false);

	private String name;
	private boolean sendToNewrelic;
	
	private TracingHeader(String name, boolean sendToNewrelic) {
		this.name = name;
		this.sendToNewrelic = sendToNewrelic;
	}
	
	public String getName() {
		return name;
	}

	public boolean isSendToNewrelic() {
		return sendToNewrelic;
	}
}