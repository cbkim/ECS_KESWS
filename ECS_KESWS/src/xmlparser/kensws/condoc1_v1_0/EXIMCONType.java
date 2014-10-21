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
 * <p>Java class for EXIM_CON_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EXIM_CON_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{MDA_Common_Types}NameType"/>
 *         &lt;element name="TIN" type="{MDA_Common_Types}TINType"/>
 *         &lt;element name="MDARefNo" type="{MDA_Common_Types}MDARefNoType" minOccurs="0"/>
 *         &lt;element name="PhysicalAddress" type="{MDA_Common_Types}PhysicalAddressType"/>
 *         &lt;element name="PhyCountry" type="{MDA_Common_Types}PhyCountryDescType" minOccurs="0"/>
 *         &lt;element name="PostalAddress" type="{MDA_Common_Types}PostalAddressType" minOccurs="0"/>
 *         &lt;element name="PosCountry" type="{MDA_Common_Types}PosCountryType" minOccurs="0"/>
 *         &lt;element name="TeleFax" type="{MDA_Common_Types}TelephoneNumberType"/>
 *         &lt;element name="Email" type="{MDA_Common_Types}EMailType" minOccurs="0"/>
 *         &lt;element name="SectorofActivity" type="{MDA_Common_Types}SectorofActivityType" minOccurs="0"/>
 *         &lt;element name="WarehouseCode" type="{MDA_Common_Types}WarehouseCodeType" minOccurs="0"/>
 *         &lt;element name="WarehouseLocation" type="{MDA_Common_Types}WarehouseLocationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EXIM_CON_Type", propOrder = {
    "name",
    "tin",
    "mdaRefNo",
    "physicalAddress",
    "phyCountry",
    "postalAddress",
    "posCountry",
    "teleFax",
    "email",
    "sectorofActivity",
    "warehouseCode",
    "warehouseLocation"
})
public class EXIMCONType {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "TIN", required = true)
    protected String tin;
    @XmlElement(name = "MDARefNo")
    protected String mdaRefNo;
    @XmlElement(name = "PhysicalAddress", required = true)
    protected String physicalAddress;
    @XmlElement(name = "PhyCountry")
    protected String phyCountry;
    @XmlElement(name = "PostalAddress")
    protected String postalAddress;
    @XmlElement(name = "PosCountry")
    protected String posCountry;
    @XmlElement(name = "TeleFax", required = true)
    protected String teleFax;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "SectorofActivity")
    protected String sectorofActivity;
    @XmlElement(name = "WarehouseCode")
    protected String warehouseCode;
    @XmlElement(name = "WarehouseLocation")
    protected String warehouseLocation;

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
     * Gets the value of the mdaRefNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDARefNo() {
        return mdaRefNo;
    }

    /**
     * Sets the value of the mdaRefNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDARefNo(String value) {
        this.mdaRefNo = value;
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

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalAddress(String value) {
        this.postalAddress = value;
    }

    /**
     * Gets the value of the posCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosCountry() {
        return posCountry;
    }

    /**
     * Sets the value of the posCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosCountry(String value) {
        this.posCountry = value;
    }

    /**
     * Gets the value of the teleFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeleFax() {
        return teleFax;
    }

    /**
     * Sets the value of the teleFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeleFax(String value) {
        this.teleFax = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the sectorofActivity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectorofActivity() {
        return sectorofActivity;
    }

    /**
     * Sets the value of the sectorofActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectorofActivity(String value) {
        this.sectorofActivity = value;
    }

    /**
     * Gets the value of the warehouseCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarehouseCode() {
        return warehouseCode;
    }

    /**
     * Sets the value of the warehouseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarehouseCode(String value) {
        this.warehouseCode = value;
    }

    /**
     * Gets the value of the warehouseLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    /**
     * Sets the value of the warehouseLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarehouseLocation(String value) {
        this.warehouseLocation = value;
    }

}