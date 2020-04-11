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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Sergiu
 */
@Entity
@Table(name = "CHART_LINK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChartLink.findAll", query = "SELECT c FROM ChartLink c"),
    @NamedQuery(name = "ChartLink.findById", query = "SELECT c FROM ChartLink c WHERE c.id = :id"),
    @NamedQuery(name = "ChartLink.findByArtist", query = "SELECT c.chartId FROM ChartLink c WHERE c.artistId = :artistId"),
    @NamedQuery(name = "ChartLink.getRanking", query = "SELECT c.artistId FROM ChartLink c WHERE c.chartId = :chartId ORDER BY c.chartPosition ASC"),
    @NamedQuery(name = "ChartLink.findByChartPosition", query = "SELECT c FROM ChartLink c WHERE c.chartPosition = :chartPosition")})
public class ChartLink implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GenericGenerator(name="autoIncrement" , strategy="increment")
    @GeneratedValue(generator="autoIncrement")
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "CHART_POSITION")
    private BigInteger chartPosition;
    @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Artist artistId;
    @JoinColumn(name = "CHART_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Chart chartId;

    public ChartLink() {
    }

    public ChartLink(BigDecimal id) {
        this.id = id;
    }

    public ChartLink(BigDecimal id, BigInteger chartPosition) {
        this.id = id;
        this.chartPosition = chartPosition;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getChartPosition() {
        return chartPosition;
    }

    public void setChartPosition(BigInteger chartPosition) {
        this.chartPosition = chartPosition;
    }

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }

    public Chart getChartId() {
        return chartId;
    }

    public void setChartId(Chart chartId) {
        this.chartId = chartId;
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
        if (!(object instanceof ChartLink)) {
            return false;
        }
        ChartLink other = (ChartLink) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ChartLink[ id=" + id + " ]";
    }
    
}
