/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Sergiu
 */
@Entity
@Table(name = "ALBUMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a"),
    @NamedQuery(name = "Album.findById", query = "SELECT a FROM Album a WHERE a.id = :id"),
    @NamedQuery(name = "Album.findByName", query = "SELECT a FROM Album a WHERE a.name = :name"),
    @NamedQuery(name = "Album.findByReleaseYear", query = "SELECT a FROM Album a WHERE a.releaseYear = :releaseYear"),
    @NamedQuery(name = "Album.findByArtist", query = "SELECT a FROM Album a WHERE a.artistId = :artistId")
})
public class Album implements Serializable {

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "albumId")
    private Genre genre;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GenericGenerator(name="autoIncrement" , strategy="increment")
    @GeneratedValue(generator="autoIncrement")
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "RELEASE_YEAR")
    private BigInteger releaseYear;
    @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Artist artistId;

    public Album() {
    }

    public Album(BigDecimal id) {
        this.id = id;
    }

    public Album(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Album(String name, int releaseYear, Artist artist)
    {
        this.name = name;
        this.releaseYear = new BigInteger(String.valueOf(releaseYear));
        this.artistId = artist;
    }
    
    public Album(int id, String name, int releaseYear, Artist artist)
    {
        this.id = new BigDecimal(id);
        this.name = name;
        this.releaseYear = new BigInteger(String.valueOf(releaseYear));
        this.artistId = artist;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(BigInteger releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Album[ id=" + id + " ]";
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    
}
