//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.21 at 03:24:53 AM EAT 
//


package xmlparser.kensws.condoc1_v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApprovalDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApprovalDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApprovalHistory" type="{MDA_Common_Types}ApprovalHistoryType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApprovalDetailsType", namespace = "", propOrder = {
    "approvalHistory"
})
public class ApprovalDetailsType {

    @XmlElement(name = "ApprovalHistory", required = true)
    protected List<ApprovalHistoryType> approvalHistory;

    /**
     * Gets the value of the approvalHistory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the approvalHistory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApprovalHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApprovalHistoryType }
     * 
     * 
     */
    public List<ApprovalHistoryType> getApprovalHistory() {
        if (approvalHistory == null) {
            approvalHistory = new ArrayList<ApprovalHistoryType>();
        }
        return this.approvalHistory;
    }

}
