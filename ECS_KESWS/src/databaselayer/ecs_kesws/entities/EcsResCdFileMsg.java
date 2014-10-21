/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities;

import java.io.Serializable;
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

/**
 *
 * @author kim
 */
@Entity
@Table(name = "ECS_RES_CD_FILE_MSG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EcsResCdFileMsg.findAll", query = "SELECT e FROM EcsResCdFileMsg e"),
    @NamedQuery(name = "EcsResCdFileMsg.findByEcsResCdFileMsgId", query = "SELECT e FROM EcsResCdFileMsg e WHERE e.ecsResCdFileMsgId = :ecsResCdFileMsgId"),
    @NamedQuery(name = "EcsResCdFileMsg.findByEcsResCdFileMsgUcrNo", query = "SELECT e FROM EcsResCdFileMsg e WHERE e.ecsResCdFileMsgUcrNo = :ecsResCdFileMsgUcrNo")})
public class EcsResCdFileMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ECS_RES_CD_FILE_MSG_ID")
    private Integer ecsResCdFileMsgId;
    @Column(name = "ECS_RES_CD_FILE_MSG_UCR_NO")
    private String ecsResCdFileMsgUcrNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ecsResCdFileMsgEcsResCdFileMsgId")
    private Collection<RecErrorMsg> recErrorMsgCollection;

    public EcsResCdFileMsg() {
    }

    public EcsResCdFileMsg(Integer ecsResCdFileMsgId) {
        this.ecsResCdFileMsgId = ecsResCdFileMsgId;
    }

    public Integer getEcsResCdFileMsgId() {
        return ecsResCdFileMsgId;
    }

    public void setEcsResCdFileMsgId(Integer ecsResCdFileMsgId) {
        this.ecsResCdFileMsgId = ecsResCdFileMsgId;
    }

    public String getEcsResCdFileMsgUcrNo() {
        return ecsResCdFileMsgUcrNo;
    }

    public void setEcsResCdFileMsgUcrNo(String ecsResCdFileMsgUcrNo) {
        this.ecsResCdFileMsgUcrNo = ecsResCdFileMsgUcrNo;
    }

    @XmlTransient
    public Collection<RecErrorMsg> getRecErrorMsgCollection() {
        return recErrorMsgCollection;
    }

    public void setRecErrorMsgCollection(Collection<RecErrorMsg> recErrorMsgCollection) {
        this.recErrorMsgCollection = recErrorMsgCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ecsResCdFileMsgId != null ? ecsResCdFileMsgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EcsResCdFileMsg)) {
            return false;
        }
        EcsResCdFileMsg other = (EcsResCdFileMsg) object;
        if ((this.ecsResCdFileMsgId == null && other.ecsResCdFileMsgId != null) || (this.ecsResCdFileMsgId != null && !this.ecsResCdFileMsgId.equals(other.ecsResCdFileMsgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaselayer.ecs_kesws.entities.EcsResCdFileMsg[ ecsResCdFileMsgId=" + ecsResCdFileMsgId + " ]";
    }
    
}
