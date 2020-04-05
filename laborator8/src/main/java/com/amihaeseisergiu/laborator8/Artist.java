package com.amihaeseisergiu.laborator8;

public class Artist {
    
    private int id;
    private String name;
    private String country;
    
    public Artist(int id, String name, String country)
    {
        this.id = id;
        this.name = name;
        this.country = country;
    }
    
    @Override
    public String toString()
    {
        return getName() + " " + getCountry();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
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
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
