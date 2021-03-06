//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.21 at 03:24:53 AM EAT 
//


package xmlparser.kensws.condoc1_v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentHeader" type="{MDA_Common_Types}DocumentHeaderType"/>
 *         &lt;element name="DocumentDetails" type="{}DocumentDetailsType"/>
 *         &lt;element name="DocumentSummary" type="{MDA_Common_Types}DocumentSummaryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documentHeader",
    "documentDetails",
    "documentSummary"
})
@XmlRootElement(name = "ConsignmentDocument", namespace = "")
public class ConsignmentDocument {

    @XmlElement(name = "DocumentHeader", required = true)
    protected DocumentHeaderType documentHeader;
    @XmlElement(name = "DocumentDetails", required = true)
    protected DocumentDetailsType documentDetails;
    @XmlElement(name = "DocumentSummary", required = true)
    protected DocumentSummaryType documentSummary;

    /**
     * Gets the value of the documentHeader property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentHeaderType }
     *     
     */
    public DocumentHeaderType getDocumentHeader() {
        return documentHeader;
    }

    /**
     * Sets the value of the documentHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentHeaderType }
     *     
     */
    public void setDocumentHeader(DocumentHeaderType value) {
        this.documentHeader = value;
    }

    /**
     * Gets the value of the documentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentDetailsType }
     *     
     */
    public DocumentDetailsType getDocumentDetails() {
        return documentDetails;
    }

    /**
     * Sets the value of the documentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentDetailsType }
     *     
     */
    public void setDocumentDetails(DocumentDetailsType value) {
        this.documentDetails = value;
    }

    /**
     * Gets the value of the documentSummary property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentSummaryType }
     *     
     */
    public DocumentSummaryType getDocumentSummary() {
        return documentSummary;
    }

    /**
     * Sets the value of the documentSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentSummaryType }
     *     
     */
    public void setDocumentSummary(DocumentSummaryType value) {
        this.documentSummary = value;
    }

}
