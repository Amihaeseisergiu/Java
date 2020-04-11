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
@Table(name = "CHARTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chart.findAll", query = "SELECT c FROM Chart c"),
    @NamedQuery(name = "Chart.findById", query = "SELECT c FROM Chart c WHERE c.id = :id"),
    @NamedQuery(name = "Chart.findByName", query = "SELECT c FROM Chart c WHERE c.name = :name")})
public class Chart implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GenericGenerator(name="autoIncrement" , strategy="increment")
    @GeneratedValue(generator="autoIncrement")
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chartId")
    private Collection<ChartLink> chartLinkCollection;

    public Chart() {
    }

    public Chart(BigDecimal id) {
        this.id = id;
    }
    
    public Chart(String name)
    {
        this.name = name;
    }
    
    public Chart(int id, String name)
    {
        this.id = new BigDecimal(id);
        this.name = name;
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

    @XmlTransient
    public Collection<ChartLink> getChartLinkCollection() {
        return chartLinkCollection;
    }

    public void setChartLinkCollection(Collection<ChartLink> chartLinkCollection) {
        this.chartLinkCollection = chartLinkCollection;
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
        if (!(object instanceof Chart)) {
            return false;
        }
        Chart other = (Chart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Chart[ id=" + id + " ]";
    }
    
}
