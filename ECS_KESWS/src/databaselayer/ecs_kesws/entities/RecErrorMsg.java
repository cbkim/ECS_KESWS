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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kim
 */
@Entity
@Table(name = "REC_ERROR_MSG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecErrorMsg.findAll", query = "SELECT r FROM RecErrorMsg r"),
    @NamedQuery(name = "RecErrorMsg.findByRecErrorMsgId", query = "SELECT r FROM RecErrorMsg r WHERE r.recErrorMsgId = :recErrorMsgId"),
    @NamedQuery(name = "RecErrorMsg.findByRecErrorMsgTime", query = "SELECT r FROM RecErrorMsg r WHERE r.recErrorMsgTime = :recErrorMsgTime"),
    @NamedQuery(name = "RecErrorMsg.findByFilePath", query = "SELECT r FROM RecErrorMsg r WHERE r.filePath = :filePath")})
public class RecErrorMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "REC_ERROR_MSG_ID")
    private Integer recErrorMsgId;
    @Column(name = "REC_ERROR_MSG_TIME")
    private String recErrorMsgTime;
    @Column(name = "FILE_PATH")
    private String filePath;
    @JoinColumn(name = "ECS_RES_CD_FILE_MSG_ECS_RES_CD_FILE_MSG_ID", referencedColumnName = "ECS_RES_CD_FILE_MSG_ID")
    @ManyToOne(optional = false)
    private EcsResCdFileMsg ecsResCdFileMsgEcsResCdFileMsgId;
    @JoinColumn(name = "RES_PAYMENT_MSG_PAYEMENT_MSG_ID", referencedColumnName = "PAYEMENT_MSG_ID")
    @ManyToOne(optional = false)
    private ResPaymentMsg resPaymentMsgPayementMsgId;
    @JoinColumn(name = "RES_CD_FILE_MSG_RES_CD_FILE_ID", referencedColumnName = "RES_CD_FILE_ID")
    @ManyToOne(optional = false)
    private ResCdFileMsg resCdFileMsgResCdFileId;

    public RecErrorMsg() {
    }

    public RecErrorMsg(Integer recErrorMsgId) {
        this.recErrorMsgId = recErrorMsgId;
    }

    public Integer getRecErrorMsgId() {
        return recErrorMsgId;
    }

    public void setRecErrorMsgId(Integer recErrorMsgId) {
        this.recErrorMsgId = recErrorMsgId;
    }

    public String getRecErrorMsgTime() {
        return recErrorMsgTime;
    }

    public void setRecErrorMsgTime(String recErrorMsgTime) {
        this.recErrorMsgTime = recErrorMsgTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public EcsResCdFileMsg getEcsResCdFileMsgEcsResCdFileMsgId() {
        return ecsResCdFileMsgEcsResCdFileMsgId;
    }

    public void setEcsResCdFileMsgEcsResCdFileMsgId(EcsResCdFileMsg ecsResCdFileMsgEcsResCdFileMsgId) {
        this.ecsResCdFileMsgEcsResCdFileMsgId = ecsResCdFileMsgEcsResCdFileMsgId;
    }

    public ResPaymentMsg getResPaymentMsgPayementMsgId() {
        return resPaymentMsgPayementMsgId;
    }

    public void setResPaymentMsgPayementMsgId(ResPaymentMsg resPaymentMsgPayementMsgId) {
        this.resPaymentMsgPayementMsgId = resPaymentMsgPayementMsgId;
    }

    public ResCdFileMsg getResCdFileMsgResCdFileId() {
        return resCdFileMsgResCdFileId;
    }

    public void setResCdFileMsgResCdFileId(ResCdFileMsg resCdFileMsgResCdFileId) {
        this.resCdFileMsgResCdFileId = resCdFileMsgResCdFileId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recErrorMsgId != null ? recErrorMsgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecErrorMsg)) {
            return false;
        }
        RecErrorMsg other = (RecErrorMsg) object;
        if ((this.recErrorMsgId == null && other.recErrorMsgId != null) || (this.recErrorMsgId != null && !this.recErrorMsgId.equals(other.recErrorMsgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaselayer.ecs_kesws.entities.RecErrorMsg[ recErrorMsgId=" + recErrorMsgId + " ]";
    }
    
}
