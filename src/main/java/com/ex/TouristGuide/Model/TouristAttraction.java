package com.ex.TouristGuide.Model;

import java.util.List;

public class TouristAttraction {

    private Long id;
    private String name;
    private String description;
    private String city; // fix to match enum
    private List<Tag> tags;

    public TouristAttraction() {}

    public TouristAttraction(String name, String description, String city, List<Tag> tags) {

        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
