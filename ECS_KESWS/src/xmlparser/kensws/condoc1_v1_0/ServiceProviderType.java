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
 * <p>Java class for ServiceProviderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceProviderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApplicationCode" type="{MDA_Common_Types}ApplicationCodeType" minOccurs="0"/>
 *         &lt;element name="Name" type="{MDA_Common_Types}NameType"/>
 *         &lt;element name="TIN" type="{MDA_Common_Types}TINType" minOccurs="0"/>
 *         &lt;element name="PhysicalAddress" type="{MDA_Common_Types}PhysicalAddressType" minOccurs="0"/>
 *         &lt;element name="PhyCountry" type="{MDA_Common_Types}PhyCountryType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceProviderType", propOrder = {
    "applicationCode",
    "name",
    "tin",
    "physicalAddress",
    "phyCountry"
})
public class ServiceProviderType {

    @XmlElement(name = "ApplicationCode")
    protected String applicationCode;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "TIN")
    protected String tin;
    @XmlElement(name = "PhysicalAddress")
    protected String physicalAddress;
    @XmlElement(name = "PhyCountry")
    protected String phyCountry;

    /**
     * Gets the value of the applicationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationCode() {
        return applicationCode;
    }

    /**
     * Sets the value of the applicationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationCode(String value) {
        this.applicationCode = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the tin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIN() {
        return tin;
    }

    /**
     * Sets the value of the tin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIN(String value) {
        this.tin = value;
    }

    /**
     * Gets the value of the physicalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    /**
     * Sets the value of the physicalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhysicalAddress(String value) {
        this.physicalAddress = value;
    }

    /**
     * Gets the value of the phyCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhyCountry() {
        return phyCountry;
    }

    /**
     * Sets the value of the phyCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhyCountry(String value) {
        this.phyCountry = value;
    }

}