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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TransportType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransportType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ModeOfTransport" type="{MDA_Common_Types}ModeOfTransportType"/>
 *         &lt;element name="ModeOfTransportDesc" type="{MDA_Common_Types}ModeOfTransportDescType" minOccurs="0"/>
 *         &lt;element name="VesselName" type="{MDA_Common_Types}VesselNameType" minOccurs="0"/>
 *         &lt;element name="VoyageNo" type="{MDA_Common_Types}VoyageNoType" minOccurs="0"/>
 *         &lt;element name="ShipmentDate" type="{MDA_Common_Types}DateType" minOccurs="0"/>
 *         &lt;element name="Carrier" type="{MDA_Common_Types}CarrierType" minOccurs="0"/>
 *         &lt;element name="ManifestNo" type="{MDA_Common_Types}ManifestNoType" minOccurs="0"/>
 *         &lt;element name="BLAWB" type="{MDA_Common_Types}BLAWBType" minOccurs="0"/>
 *         &lt;element name="MarksAndNumbers" type="{MDA_Common_Types}MarksAndNumbersType" minOccurs="0"/>
 *         &lt;element name="PortOfArrival" type="{MDA_Common_Types}PortType"/>
 *         &lt;element name="PortOfArrivalDesc" type="{MDA_Common_Types}PortDescType" minOccurs="0"/>
 *         &lt;element name="PortOfDeparture" type="{MDA_Common_Types}PortType" minOccurs="0"/>
 *         &lt;element name="PortOfDepartureDesc" type="{MDA_Common_Types}PortDescType" minOccurs="0"/>
 *         &lt;element name="CustomsOffice" type="{MDA_Common_Types}CustomsOfficeType"/>
 *         &lt;element name="CustomsOfficeDesc" type="{MDA_Common_Types}CustomsOfficeType" minOccurs="0"/>
 *         &lt;element name="FreightStation" type="{MDA_Common_Types}FreightStationType"/>
 *         &lt;element name="FreightStationDesc" type="{MDA_Common_Types}FreightStationDescType" minOccurs="0"/>
 *         &lt;element name="CargoTypeIndicator" type="{MDA_Common_Types}CargoTypeIndicatorType"/>
 *         &lt;element name="ContainerDetails" type="{MDA_Common_Types}ContainerDetailsType" minOccurs="0"/>
 *         &lt;element name="InlandTransportCo" type="{MDA_Common_Types}InlandTransportCoType" minOccurs="0"/>
 *         &lt;element name="InlandTransportCoRefNo" type="{MDA_Common_Types}InlandTransportCoRefNoType" minOccurs="0"/>
 *         &lt;element name="KEPHISCollectionOffice" type="{MDA_Common_Types}KEPHISCollectionOfficeType" minOccurs="0"/>
 *         &lt;element name="CertificationCategory" type="{MDA_Common_Types}CertificationCategoryType" minOccurs="0"/>
 *         &lt;element name="PlaceOfApplication" type="{MDA_Common_Types}PlaceOfApplicationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportType", propOrder = {
    "modeOfTransport",
    "modeOfTransportDesc",
    "vesselName",
    "voyageNo",
    "shipmentDate",
    "carrier",
    "manifestNo",
    "blawb",
    "marksAndNumbers",
    "portOfArrival",
    "portOfArrivalDesc",
    "portOfDeparture",
    "portOfDepartureDesc",
    "customsOffice",
    "customsOfficeDesc",
    "freightStation",
    "freightStationDesc",
    "cargoTypeIndicator",
    "containerDetails",
    "inlandTransportCo",
    "inlandTransportCoRefNo",
    "kephisCollectionOffice",
    "certificationCategory",
    "placeOfApplication"
})
public class TransportType {

    @XmlElement(name = "ModeOfTransport", required = true)
    protected String modeOfTransport;
    @XmlElement(name = "ModeOfTransportDesc")
    protected String modeOfTransportDesc;
    @XmlElement(name = "VesselName")
    protected String vesselName;
    @XmlElement(name = "VoyageNo")
    protected String voyageNo;
    @XmlElement(name = "ShipmentDate")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String shipmentDate;
    @XmlElement(name = "Carrier")
    protected String carrier;
    @XmlElement(name = "ManifestNo")
    protected String manifestNo;
    @XmlElement(name = "BLAWB")
    protected String blawb;
    @XmlElement(name = "MarksAndNumbers")
    protected String marksAndNumbers;
    @XmlElement(name = "PortOfArrival", required = true)
    protected String portOfArrival;
    @XmlElement(name = "PortOfArrivalDesc")
    protected String portOfArrivalDesc;
    @XmlElement(name = "PortOfDeparture")
    protected String portOfDeparture;
    @XmlElement(name = "PortOfDepartureDesc")
    protected String portOfDepartureDesc;
    @XmlElement(name = "CustomsOffice", required = true)
    protected String customsOffice;
    @XmlElement(name = "CustomsOfficeDesc")
    protected String customsOfficeDesc;
    @XmlElement(name = "FreightStation", required = true)
    protected String freightStation;
    @XmlElement(name = "FreightStationDesc")
    protected String freightStationDesc;
    @XmlElement(name = "CargoTypeIndicator", required = true)
    protected String cargoTypeIndicator;
    @XmlElement(name = "ContainerDetails")
    protected ContainerDetailsType containerDetails;
    @XmlElement(name = "InlandTransportCo")
    protected String inlandTransportCo;
    @XmlElement(name = "InlandTransportCoRefNo")
    protected String inlandTransportCoRefNo;
    @XmlElement(name = "KEPHISCollectionOffice")
    protected String kephisCollectionOffice;
    @XmlElement(name = "CertificationCategory")
    protected String certificationCategory;
    @XmlElement(name = "PlaceOfApplication")
    protected String placeOfApplication;

    /**
     * Gets the value of the modeOfTransport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeOfTransport() {
        return modeOfTransport;
    }

    /**
     * Sets the value of the modeOfTransport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeOfTransport(String value) {
        this.modeOfTransport = value;
    }

    /**
     * Gets the value of the modeOfTransportDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeOfTransportDesc() {
        return modeOfTransportDesc;
    }

    /**
     * Sets the value of the modeOfTransportDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeOfTransportDesc(String value) {
        this.modeOfTransportDesc = value;
    }

    /**
     * Gets the value of the vesselName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVesselName() {
        return vesselName;
    }

    /**
     * Sets the value of the vesselName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVesselName(String value) {
        this.vesselName = value;
    }

    /**
     * Gets the value of the voyageNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoyageNo() {
        return voyageNo;
    }

    /**
     * Sets the value of the voyageNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoyageNo(String value) {
        this.voyageNo = value;
    }

    /**
     * Gets the value of the shipmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentDate() {
        return shipmentDate;
    }

    /**
     * Sets the value of the shipmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentDate(String value) {
        this.shipmentDate = value;
    }

    /**
     * Gets the value of the carrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Sets the value of the carrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrier(String value) {
        this.carrier = value;
    }

    /**
     * Gets the value of the manifestNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManifestNo() {
        return manifestNo;
    }

    /**
     * Sets the value of the manifestNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManifestNo(String value) {
        this.manifestNo = value;
    }

    /**
     * Gets the value of the blawb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLAWB() {
        return blawb;
    }

    /**
     * Sets the value of the blawb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLAWB(String value) {
        this.blawb = value;
    }

    /**
     * Gets the value of the marksAndNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarksAndNumbers() {
        return marksAndNumbers;
    }

    /**
     * Sets the value of the marksAndNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarksAndNumbers(String value) {
        this.marksAndNumbers = value;
    }

    /**
     * Gets the value of the portOfArrival property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortOfArrival() {
        return portOfArrival;
    }

    /**
     * Sets the value of the portOfArrival property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortOfArrival(String value) {
        this.portOfArrival = value;
    }

    /**
     * Gets the value of the portOfArrivalDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortOfArrivalDesc() {
        return portOfArrivalDesc;
    }

    /**
     * Sets the value of the portOfArrivalDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortOfArrivalDesc(String value) {
        this.portOfArrivalDesc = value;
    }

    /**
     * Gets the value of the portOfDeparture property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortOfDeparture() {
        return portOfDeparture;
    }

    /**
     * Sets the value of the portOfDeparture property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortOfDeparture(String value) {
        this.portOfDeparture = value;
    }

    /**
     * Gets the value of the portOfDepartureDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortOfDepartureDesc() {
        return portOfDepartureDesc;
    }

    /**
     * Sets the value of the portOfDepartureDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortOfDepartureDesc(String value) {
        this.portOfDepartureDesc = value;
    }

    /**
     * Gets the value of the customsOffice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsOffice() {
        return customsOffice;
    }

    /**
     * Sets the value of the customsOffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomsOffice(String value) {
        this.customsOffice = value;
    }

    /**
     * Gets the value of the customsOfficeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsOfficeDesc() {
        return customsOfficeDesc;
    }

    /**
     * Sets the value of the customsOfficeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomsOfficeDesc(String value) {
        this.customsOfficeDesc = value;
    }

    /**
     * Gets the value of the freightStation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreightStation() {
        return freightStation;
    }

    /**
     * Sets the value of the freightStation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreightStation(String value) {
        this.freightStation = value;
    }

    /**
     * Gets the value of the freightStationDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreightStationDesc() {
        return freightStationDesc;
    }

    /**
     * Sets the value of the freightStationDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreightStationDesc(String value) {
        this.freightStationDesc = value;
    }

    /**
     * Gets the value of the cargoTypeIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCargoTypeIndicator() {
        return cargoTypeIndicator;
    }

    /**
     * Sets the value of the cargoTypeIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCargoTypeIndicator(String value) {
        this.cargoTypeIndicator = value;
    }

    /**
     * Gets the value of the containerDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ContainerDetailsType }
     *     
     */
    public ContainerDetailsType getContainerDetails() {
        return containerDetails;
    }

    /**
     * Sets the value of the containerDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContainerDetailsType }
     *     
     */
    public void setContainerDetails(ContainerDetailsType value) {
        this.containerDetails = value;
    }

    /**
     * Gets the value of the inlandTransportCo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInlandTransportCo() {
        return inlandTransportCo;
    }

    /**
     * Sets the value of the inlandTransportCo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInlandTransportCo(String value) {
        this.inlandTransportCo = value;
    }

    /**
     * Gets the value of the inlandTransportCoRefNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInlandTransportCoRefNo() {
        return inlandTransportCoRefNo;
    }

    /**
     * Sets the value of the inlandTransportCoRefNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInlandTransportCoRefNo(String value) {
        this.inlandTransportCoRefNo = value;
    }

    /**
     * Gets the value of the kephisCollectionOffice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEPHISCollectionOffice() {
        return kephisCollectionOffice;
    }

    /**
     * Sets the value of the kephisCollectionOffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEPHISCollectionOffice(String value) {
        this.kephisCollectionOffice = value;
    }

    /**
     * Gets the value of the certificationCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificationCategory() {
        return certificationCategory;
    }

    /**
     * Sets the value of the certificationCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificationCategory(String value) {
        this.certificationCategory = value;
    }

    /**
     * Gets the value of the placeOfApplication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaceOfApplication() {
        return placeOfApplication;
    }

    /**
     * Sets the value of the placeOfApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaceOfApplication(String value) {
        this.placeOfApplication = value;
    }

}
