package com.ef.log;

public enum DurationType {
	hourly("hourly"), daily("daily");
	
	private final String text;
	
	DurationType(String text) {
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
