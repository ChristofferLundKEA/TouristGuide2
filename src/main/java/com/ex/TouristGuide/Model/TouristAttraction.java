package com.ex.TouristGuide.Model;

import java.util.List;

public class TouristAttraction {
    private int touristAttractionId;
    private String name;
    private String description;
    private String city;
    private List<Tag> tags;
    private String originalName;

    public TouristAttraction() {}

    public TouristAttraction(int touristAttractionId,String name, String description, String city, List<Tag> tags) {
        this.touristAttractionId = touristAttractionId;
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
        this.originalName = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTouristAttractionId() {
        return touristAttractionId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getCity() {
        return city;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
