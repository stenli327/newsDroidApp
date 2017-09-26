package com.hotlivenews.api.filter;

public enum ECategory implements IFilter {

    ALL("", "All"),
    BUSINESS("business", "Business"),
    ENTERTAINMENT("entertainment", "Entertainment"),
    GAMING("gaming", "Gaming"),
    GENERAL("general", "General"),
    MUSIC("music", "Music"),
    POLITICS("politics", "Politics"),
    SCIENCE_AND_NATURE("science-and-nature", "Science and nature"),
    SPORT("sport", "Sport"),
    TECHNOLOGY("technology", "Technology");

    private String label;
    private String description;

    private ECategory(String label, String description)
    {
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
        ECategory[] data = values();
        String[] result = new String[data.length];
        for(int i = 0; i < data.length; i++){
            ECategory item = data[i];
            result[i] = item.getDescription();
        }
        return result;
    }
}
