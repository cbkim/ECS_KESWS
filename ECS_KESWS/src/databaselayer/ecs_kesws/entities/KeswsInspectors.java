/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kim
 */
@Entity
@Table(name = "KESWS_INSPECTORS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeswsInspectors.findAll", query = "SELECT k FROM KeswsInspectors k"),
    @NamedQuery(name = "KeswsInspectors.findByKeswsInspectorsId", query = "SELECT k FROM KeswsInspectors k WHERE k.keswsInspectorsId = :keswsInspectorsId"),
    @NamedQuery(name = "KeswsInspectors.findByEcsId", query = "SELECT k FROM KeswsInspectors k WHERE k.ecsId = :ecsId"),
    @NamedQuery(name = "KeswsInspectors.findByEcsName", query = "SELECT k FROM KeswsInspectors k WHERE k.ecsName = :ecsName"),
    @NamedQuery(name = "KeswsInspectors.findByKeswsName", query = "SELECT k FROM KeswsInspectors k WHERE k.keswsName = :keswsName")})
public class KeswsInspectors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "KESWS_INSPECTORS_ID")
    private Integer keswsInspectorsId;
    @Column(name = "ECS_ID")
    private String ecsId;
    @Column(name = "ECS_NAME")
    private String ecsName;
    @Column(name = "KESWS_NAME")
    private String keswsName;

    public KeswsInspectors() {
    }

    public KeswsInspectors(Integer keswsInspectorsId) {
        this.keswsInspectorsId = keswsInspectorsId;
    }

    public Integer getKeswsInspectorsId() {
        return keswsInspectorsId;
    }

    public void setKeswsInspectorsId(Integer keswsInspectorsId) {
        this.keswsInspectorsId = keswsInspectorsId;
    }

    public String getEcsId() {
        return ecsId;
    }

    public void setEcsId(String ecsId) {
        this.ecsId = ecsId;
    }

    public String getEcsName() {
        return ecsName;
    }

    public void setEcsName(String ecsName) {
        this.ecsName = ecsName;
    }

    public String getKeswsName() {
        return keswsName;
    }

    public void setKeswsName(String keswsName) {
        this.keswsName = keswsName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keswsInspectorsId != null ? keswsInspectorsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeswsInspectors)) {
            return false;
        }
        KeswsInspectors other = (KeswsInspectors) object;
        if ((this.keswsInspectorsId == null && other.keswsInspectorsId != null) || (this.keswsInspectorsId != null && !this.keswsInspectorsId.equals(other.keswsInspectorsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaselayer.ecs_kesws.entities.KeswsInspectors[ keswsInspectorsId=" + keswsInspectorsId + " ]";
    }
    
}
