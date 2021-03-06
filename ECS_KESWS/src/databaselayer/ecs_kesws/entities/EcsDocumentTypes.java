package databaselayer.ecs_kesws.entities;
// Generated Oct 29, 2014 3:56:07 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EcsDocumentTypes generated by hbm2java
 */
@Entity
@Table(name="ECS_DOCUMENT_TYPES"
    ,catalog="ECS_KESWS"
)
public class EcsDocumentTypes  implements java.io.Serializable {


     private int id;
     private String documentName;
     private Set<CdFileDetails> cdFileDetailses = new HashSet<CdFileDetails>(0);
     private Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMaps = new HashSet<PricelistInternalProductcodeDocumentMap>(0);

    public EcsDocumentTypes() {
    }

	
    public EcsDocumentTypes(int id) {
        this.id = id;
    }
    public EcsDocumentTypes(int id, String documentName, Set<CdFileDetails> cdFileDetailses, Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMaps) {
       this.id = id;
       this.documentName = documentName;
       this.cdFileDetailses = cdFileDetailses;
       this.pricelistInternalProductcodeDocumentMaps = pricelistInternalProductcodeDocumentMaps;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="Document_Name", length=45)
    public String getDocumentName() {
        return this.documentName;
    }
    
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="ecsDocumentTypes")
    public Set<CdFileDetails> getCdFileDetailses() {
        return this.cdFileDetailses;
    }
    
    public void setCdFileDetailses(Set<CdFileDetails> cdFileDetailses) {
        this.cdFileDetailses = cdFileDetailses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="ecsDocumentTypes")
    public Set<PricelistInternalProductcodeDocumentMap> getPricelistInternalProductcodeDocumentMaps() {
        return this.pricelistInternalProductcodeDocumentMaps;
    }
    
    public void setPricelistInternalProductcodeDocumentMaps(Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMaps) {
        this.pricelistInternalProductcodeDocumentMaps = pricelistInternalProductcodeDocumentMaps;
    }




}


