package com.amihaeseisergiu.laborator8;

import java.util.List;

public class Chart {
    
    private int id;
    private List<Album> albums;
    
    public Chart(int id, List<Album> albums)
    {
        this.id = id;
        this.albums = albums;
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
     * @return the albums
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * @param albums the albums to set
     */
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
