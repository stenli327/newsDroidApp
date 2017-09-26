package com.hotlivenews.api.filter;


public enum ELanguage implements IFilter{

    ALL("","All"),
    ENGLISH("en", "English"),
    GERMAN("de", "German"),
    FRENCH("fr", "French");

    private String label;
    private String description;

    private ELanguage(String label, String description){
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public static String[] getDescriptionArray(){
        ELanguage[] data = values();
        String[] result = new String[data.length];
        for(int i = 0; i < data.length; i++){
            ELanguage item = data[i];
            result[i] = item.getDescription();
        }
        return result;
    }
}
