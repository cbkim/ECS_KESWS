//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.21 at 03:15:08 AM EAT 
//


package xmlparser.kensws.condoc1_v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UCRReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UCRReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UCRRefNo" type="{MDA_Common_Types}UCRRefNo"/>
 *         &lt;element name="UCRRelation" type="{MDA_Common_Types}UCRRelation"/>
 *         &lt;element name="UCRRelationDesc" type="{MDA_Common_Types}UCRRelationDesc" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UCRReferenceType", propOrder = {
    "ucrRefNo",
    "ucrRelation",
    "ucrRelationDesc"
})
public class UCRReferenceType {

    @XmlElement(name = "UCRRefNo", required = true)
    protected String ucrRefNo;
    @XmlElement(name = "UCRRelation", required = true)
    protected String ucrRelation;
    @XmlElement(name = "UCRRelationDesc")
    protected String ucrRelationDesc;

    /**
     * Gets the value of the ucrRefNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCRRefNo() {
        return ucrRefNo;
    }

    /**
     * Sets the value of the ucrRefNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCRRefNo(String value) {
        this.ucrRefNo = value;
    }

    /**
     * Gets the value of the ucrRelation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCRRelation() {
        return ucrRelation;
    }

    /**
     * Sets the value of the ucrRelation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCRRelation(String value) {
        this.ucrRelation = value;
    }

    /**
     * Gets the value of the ucrRelationDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCRRelationDesc() {
        return ucrRelationDesc;
    }

    /**
     * Sets the value of the ucrRelationDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCRRelationDesc(String value) {
        this.ucrRelationDesc = value;
    }

}
