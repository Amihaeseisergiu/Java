/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Sergiu
 */
@Entity
@Table(name = "ARTISTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a"),
    @NamedQuery(name = "Artist.findById", query = "SELECT a FROM Artist a WHERE a.id = :id"),
    @NamedQuery(name = "Artist.findByName", query = "SELECT a FROM Artist a WHERE a.name = :name"),
    @NamedQuery(name = "Artist.findByCountry", query = "SELECT a FROM Artist a WHERE a.country = :country")})
public class Artist implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artistId")
    private Collection<ChartLink> chartLinkCollection;

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
    @Column(name = "COUNTRY")
    private String country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artistId")
    private Collection<Album> albumCollection;

    public Artist() {
    }

    public Artist(BigDecimal id) {
        this.id = id;
    }

    public Artist(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Artist(String name, String country)
    {
        this.name = name;
        this.country = country;
    }
    
    public Artist(int id, String name, String country)
    {
        this.id = new BigDecimal(id);
        this.name = name;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @XmlTransient
    public Collection<Album> getAlbumCollection() {
        return albumCollection;
    }

    public void setAlbumCollection(Collection<Album> albumCollection) {
        this.albumCollection = albumCollection;
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
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Artist[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ChartLink> getChartLinkCollection() {
        return chartLinkCollection;
    }

    public void setChartLinkCollection(Collection<ChartLink> chartLinkCollection) {
        this.chartLinkCollection = chartLinkCollection;
    }
    
}
