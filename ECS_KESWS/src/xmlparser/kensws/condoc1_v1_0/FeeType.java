//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.21 at 03:15:08 AM EAT 
//


package xmlparser.kensws.condoc1_v1_0;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for FeeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FeeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Currency" type="{MDA_Common_Types}CurrencyCodeType" minOccurs="0"/>
 *         &lt;element name="AmountFCY" type="{MDA_Common_Types}FCYType" minOccurs="0"/>
 *         &lt;element name="AmountNCY" type="{MDA_Common_Types}NCYType" minOccurs="0"/>
 *         &lt;element name="PaymentMode" type="{MDA_Common_Types}PaymentModeType" minOccurs="0"/>
 *         &lt;element name="ReceiptNumber" type="{MDA_Common_Types}ReceiptNumberType" minOccurs="0"/>
 *         &lt;element name="ReceiptDate" type="{MDA_Common_Types}DateType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeeType", propOrder = {
    "currency",
    "amountFCY",
    "amountNCY",
    "paymentMode",
    "receiptNumber",
    "receiptDate"
})
public class FeeType {

    @XmlElement(name = "Currency")
    protected String currency;
    @XmlElement(name = "AmountFCY")
    protected BigDecimal amountFCY;
    @XmlElement(name = "AmountNCY")
    protected BigDecimal amountNCY;
    @XmlElement(name = "PaymentMode")
    protected String paymentMode;
    @XmlElement(name = "ReceiptNumber")
    protected String receiptNumber;
    @XmlElement(name = "ReceiptDate")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String receiptDate;

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the amountFCY property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountFCY() {
        return amountFCY;
    }

    /**
     * Sets the value of the amountFCY property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountFCY(BigDecimal value) {
        this.amountFCY = value;
    }

    /**
     * Gets the value of the amountNCY property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountNCY() {
        return amountNCY;
    }

    /**
     * Sets the value of the amountNCY property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountNCY(BigDecimal value) {
        this.amountNCY = value;
    }

    /**
     * Gets the value of the paymentMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * Sets the value of the paymentMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMode(String value) {
        this.paymentMode = value;
    }

    /**
     * Gets the value of the receiptNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptNumber() {
        return receiptNumber;
    }

    /**
     * Sets the value of the receiptNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptNumber(String value) {
        this.receiptNumber = value;
    }

    /**
     * Gets the value of the receiptDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptDate() {
        return receiptDate;
    }

    /**
     * Sets the value of the receiptDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptDate(String value) {
        this.receiptDate = value;
    }

}
