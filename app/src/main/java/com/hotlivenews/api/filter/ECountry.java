package com.hotlivenews.api.filter;

public enum ECountry implements IFilter{

    ALL("", "All"),
    AUSTRALIAN("au", "Australian"),
    GERMANY("de", "Germany"),
    UNITED_KINGDOM("gb", "United Kingdom"),
    INDIA("in", "India"),
    ITALY("it", "Italy"),
    USA("us", "USA");

    private String label;
    private String description;

    private ECountry(String label, String description){

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
        ECountry[] data = values();
        String[] result = new String[data.length];
        for(int i = 0; i < data.length; i++){
            ECountry item = data[i];
            result[i] = item.getDescription();
        }
        return result;
    }
}
