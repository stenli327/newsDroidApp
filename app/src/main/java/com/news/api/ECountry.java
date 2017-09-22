package com.news.api;

public enum ECountry {

    australian("au"),
    GERMANY("de"),
    united_kingdom("gb"),
    INDIA("in"),
    ITALY("it"),
    USA("us");

    private String label;

    private ECountry(String label){
        label = label;
    }

    public String getLabel() {
        return label;
    }
}
