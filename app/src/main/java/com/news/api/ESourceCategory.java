package com.news.api;

public enum ESourceCategory {

    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GAMING("gaming"),
    GENERAL("general"),
    MUSIC("music"),
    POLITICS("politics"),
    SCIENCE_AND_NATURE("science-and-nature"),
    SPORT("sport"),
    TECHNOLOGY("technology");

    private String label;

    private ESourceCategory(String label){
        label = label;
    }

    public String getLabel() {
        return label;
    }
}
