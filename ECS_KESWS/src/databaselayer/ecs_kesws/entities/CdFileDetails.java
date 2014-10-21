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
@Table(name = "CD_FILE_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CdFileDetails.findAll", query = "SELECT c FROM CdFileDetails c"),
    @NamedQuery(name = "CdFileDetails.findById", query = "SELECT c FROM CdFileDetails c WHERE c.id = :id"),
    @NamedQuery(name = "CdFileDetails.findByWeight", query = "SELECT c FROM CdFileDetails c WHERE c.weight = :weight"),
    @NamedQuery(name = "CdFileDetails.findByIpcIdCode", query = "SELECT c FROM CdFileDetails c WHERE c.ipcIdCode = :ipcIdCode")})
public class CdFileDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Weight")
    private Double weight;
    @Column(name = "IPC_ID_CODE")
    private String ipcIdCode;
    @JoinColumn(name = "PRICE_LIST_INT_IPC_DOCUMENT_MAP_Pricelist_IPC_MAP_ID_Ref", referencedColumnName = "Pricelist_IPC_MAP_ID")
    @ManyToOne(optional = false)
    private PricelistInternalProductcodeDocumentMap pRICELISTINTIPCDOCUMENTMAPPricelistIPCMAPIDRef;
    @JoinColumn(name = "DOCUMENT_TYPES_id_Ref", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EcsDocumentTypes dOCUMENTTYPESidRef;
    @JoinColumn(name = "REC_CD_FILE_MSG_REC_CD_FILE_ID_Ref", referencedColumnName = "REC_CD_File_ID")
    @ManyToOne(optional = false)
    private RecCdFileMsg rECCDFILEMSGRECCDFILEIDRef;

    public CdFileDetails() {
    }

    public CdFileDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getIpcIdCode() {
        return ipcIdCode;
    }

    public void setIpcIdCode(String ipcIdCode) {
        this.ipcIdCode = ipcIdCode;
    }

    public PricelistInternalProductcodeDocumentMap getPRICELISTINTIPCDOCUMENTMAPPricelistIPCMAPIDRef() {
        return pRICELISTINTIPCDOCUMENTMAPPricelistIPCMAPIDRef;
    }

    public void setPRICELISTINTIPCDOCUMENTMAPPricelistIPCMAPIDRef(PricelistInternalProductcodeDocumentMap pRICELISTINTIPCDOCUMENTMAPPricelistIPCMAPIDRef) {
        this.pRICELISTINTIPCDOCUMENTMAPPricelistIPCMAPIDRef = pRICELISTINTIPCDOCUMENTMAPPricelistIPCMAPIDRef;
    }

    public EcsDocumentTypes getDOCUMENTTYPESidRef() {
        return dOCUMENTTYPESidRef;
    }

    public void setDOCUMENTTYPESidRef(EcsDocumentTypes dOCUMENTTYPESidRef) {
        this.dOCUMENTTYPESidRef = dOCUMENTTYPESidRef;
    }

    public RecCdFileMsg getRECCDFILEMSGRECCDFILEIDRef() {
        return rECCDFILEMSGRECCDFILEIDRef;
    }

    public void setRECCDFILEMSGRECCDFILEIDRef(RecCdFileMsg rECCDFILEMSGRECCDFILEIDRef) {
        this.rECCDFILEMSGRECCDFILEIDRef = rECCDFILEMSGRECCDFILEIDRef;
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
        if (!(object instanceof CdFileDetails)) {
            return false;
        }
        CdFileDetails other = (CdFileDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaselayer.ecs_kesws.entities.CdFileDetails[ id=" + id + " ]";
    }
    
}
