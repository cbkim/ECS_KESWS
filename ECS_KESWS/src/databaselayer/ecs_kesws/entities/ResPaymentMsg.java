/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kim
 */
@Entity
@Table(name = "RES_PAYMENT_MSG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResPaymentMsg.findAll", query = "SELECT r FROM ResPaymentMsg r"),
    @NamedQuery(name = "ResPaymentMsg.findByPayementMsgId", query = "SELECT r FROM ResPaymentMsg r WHERE r.payementMsgId = :payementMsgId"),
    @NamedQuery(name = "ResPaymentMsg.findByTimeSent", query = "SELECT r FROM ResPaymentMsg r WHERE r.timeSent = :timeSent"),
    @NamedQuery(name = "ResPaymentMsg.findByAmount", query = "SELECT r FROM ResPaymentMsg r WHERE r.amount = :amount"),
    @NamedQuery(name = "ResPaymentMsg.findByCurrency", query = "SELECT r FROM ResPaymentMsg r WHERE r.currency = :currency"),
    @NamedQuery(name = "ResPaymentMsg.findByRevenueCode", query = "SELECT r FROM ResPaymentMsg r WHERE r.revenueCode = :revenueCode"),
    @NamedQuery(name = "ResPaymentMsg.findByFilePath", query = "SELECT r FROM ResPaymentMsg r WHERE r.filePath = :filePath"),
    @NamedQuery(name = "ResPaymentMsg.findBySentAck", query = "SELECT r FROM ResPaymentMsg r WHERE r.sentAck = :sentAck")})
public class ResPaymentMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYEMENT_MSG_ID")
    private Integer payementMsgId;
    @Column(name = "TIME_SENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSent;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "REVENUE_CODE")
    private String revenueCode;
    @Column(name = "FILE_PATH")
    private String filePath;
    @Column(name = "SENT_ACK")
    private Integer sentAck;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sentPayementMsgPayementMsgId")
    private Collection<RecPaymentMsg> recPaymentMsgCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resPaymentMsgPayementMsgId")
    private Collection<RecErrorMsg> recErrorMsgCollection;
    @JoinColumn(name = "INBOX_MSG_Inbox_ID", referencedColumnName = "REC_CD_File_ID")
    @ManyToOne(optional = false)
    private RecCdFileMsg iNBOXMSGInboxID;
    @OneToMany(mappedBy = "resPaymentMsgPayementMsgId")
    private Collection<TransactionLogs> transactionLogsCollection;

    public ResPaymentMsg() {
    }

    public ResPaymentMsg(Integer payementMsgId) {
        this.payementMsgId = payementMsgId;
    }

    public Integer getPayementMsgId() {
        return payementMsgId;
    }

    public void setPayementMsgId(Integer payementMsgId) {
        this.payementMsgId = payementMsgId;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRevenueCode() {
        return revenueCode;
    }

    public void setRevenueCode(String revenueCode) {
        this.revenueCode = revenueCode;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getSentAck() {
        return sentAck;
    }

    public void setSentAck(Integer sentAck) {
        this.sentAck = sentAck;
    }

    @XmlTransient
    public Collection<RecPaymentMsg> getRecPaymentMsgCollection() {
        return recPaymentMsgCollection;
    }

    public void setRecPaymentMsgCollection(Collection<RecPaymentMsg> recPaymentMsgCollection) {
        this.recPaymentMsgCollection = recPaymentMsgCollection;
    }

    @XmlTransient
    public Collection<RecErrorMsg> getRecErrorMsgCollection() {
        return recErrorMsgCollection;
    }

    public void setRecErrorMsgCollection(Collection<RecErrorMsg> recErrorMsgCollection) {
        this.recErrorMsgCollection = recErrorMsgCollection;
    }

    public RecCdFileMsg getINBOXMSGInboxID() {
        return iNBOXMSGInboxID;
    }

    public void setINBOXMSGInboxID(RecCdFileMsg iNBOXMSGInboxID) {
        this.iNBOXMSGInboxID = iNBOXMSGInboxID;
    }

    @XmlTransient
    public Collection<TransactionLogs> getTransactionLogsCollection() {
        return transactionLogsCollection;
    }

    public void setTransactionLogsCollection(Collection<TransactionLogs> transactionLogsCollection) {
        this.transactionLogsCollection = transactionLogsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payementMsgId != null ? payementMsgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResPaymentMsg)) {
            return false;
        }
        ResPaymentMsg other = (ResPaymentMsg) object;
        if ((this.payementMsgId == null && other.payementMsgId != null) || (this.payementMsgId != null && !this.payementMsgId.equals(other.payementMsgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaselayer.ecs_kesws.entities.ResPaymentMsg[ payementMsgId=" + payementMsgId + " ]";
    }
    
}
