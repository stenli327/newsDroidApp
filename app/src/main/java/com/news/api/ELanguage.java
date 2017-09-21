package com.news.api;


public enum ELanguage {

    ENGLISH("en"),
    GERMAN("de"),
    FRENCH("fr");

    private String label;

    private ELanguage(String label){
        label = label;
    }

    public String getLabel() {
        return label;
    }
}
