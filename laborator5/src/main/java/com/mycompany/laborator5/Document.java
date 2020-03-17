package com.mycompany.laborator5;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Document implements Serializable {
    
    private String id;
    private String name;
    private String location;
    private Map<String, Object> tags = new HashMap<>();
    
    public Document(String id, String name, String location)
    {
        this.id = id;
        this.name = name;
        this.location = location;
    }
    
    public void addTag(String key, Object obj)
    {
        getTags().put(key, obj);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the tags
     */
    public Map<String, Object> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(Map<String, Object> tags) {
        this.tags = tags;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(id);
        builder.append(" ");
        builder.append(name);
        builder.append(" ");
        builder.append(location);
        return builder.toString();
    }
}
