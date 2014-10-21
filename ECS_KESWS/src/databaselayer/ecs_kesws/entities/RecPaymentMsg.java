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
@Table(name = "REC_PAYMENT_MSG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecPaymentMsg.findAll", query = "SELECT r FROM RecPaymentMsg r"),
    @NamedQuery(name = "RecPaymentMsg.findByReceivedPaymentMsgId", query = "SELECT r FROM RecPaymentMsg r WHERE r.receivedPaymentMsgId = :receivedPaymentMsgId"),
    @NamedQuery(name = "RecPaymentMsg.findByTimeReceived", query = "SELECT r FROM RecPaymentMsg r WHERE r.timeReceived = :timeReceived"),
    @NamedQuery(name = "RecPaymentMsg.findByAmountPaid", query = "SELECT r FROM RecPaymentMsg r WHERE r.amountPaid = :amountPaid"),
    @NamedQuery(name = "RecPaymentMsg.findByDatePaid", query = "SELECT r FROM RecPaymentMsg r WHERE r.datePaid = :datePaid"),
    @NamedQuery(name = "RecPaymentMsg.findByModeOfPayment", query = "SELECT r FROM RecPaymentMsg r WHERE r.modeOfPayment = :modeOfPayment"),
    @NamedQuery(name = "RecPaymentMsg.findByPrepaymentAccount", query = "SELECT r FROM RecPaymentMsg r WHERE r.prepaymentAccount = :prepaymentAccount"),
    @NamedQuery(name = "RecPaymentMsg.findByTransactionNumber", query = "SELECT r FROM RecPaymentMsg r WHERE r.transactionNumber = :transactionNumber"),
    @NamedQuery(name = "RecPaymentMsg.findByFilePath", query = "SELECT r FROM RecPaymentMsg r WHERE r.filePath = :filePath")})
public class RecPaymentMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RECEIVED_PAYMENT_MSG_ID")
    private Integer receivedPaymentMsgId;
    @Basic(optional = false)
    @Column(name = "TIME_RECEIVED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeReceived;
    @Column(name = "AMOUNT_PAID")
    private String amountPaid;
    @Column(name = "DATE_PAID")
    private String datePaid;
    @Column(name = "MODE_OF_PAYMENT")
    private String modeOfPayment;
    @Column(name = "PREPAYMENT_ACCOUNT")
    private String prepaymentAccount;
    @Column(name = "TRANSACTION_NUMBER")
    private String transactionNumber;
    @Column(name = "FILE_PATH")
    private String filePath;
    @JoinColumn(name = "SENT_PAYEMENT_MSG_PAYEMENT_MSG_ID", referencedColumnName = "PAYEMENT_MSG_ID")
    @ManyToOne(optional = false)
    private ResPaymentMsg sentPayementMsgPayementMsgId;
    @OneToMany(mappedBy = "recPaymentMsgReceivedPaymentMsgId")
    private Collection<TransactionLogs> transactionLogsCollection;

    public RecPaymentMsg() {
    }

    public RecPaymentMsg(Integer receivedPaymentMsgId) {
        this.receivedPaymentMsgId = receivedPaymentMsgId;
    }

    public RecPaymentMsg(Integer receivedPaymentMsgId, Date timeReceived) {
        this.receivedPaymentMsgId = receivedPaymentMsgId;
        this.timeReceived = timeReceived;
    }

    public Integer getReceivedPaymentMsgId() {
        return receivedPaymentMsgId;
    }

    public void setReceivedPaymentMsgId(Integer receivedPaymentMsgId) {
        this.receivedPaymentMsgId = receivedPaymentMsgId;
    }

    public Date getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(Date timeReceived) {
        this.timeReceived = timeReceived;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getPrepaymentAccount() {
        return prepaymentAccount;
    }

    public void setPrepaymentAccount(String prepaymentAccount) {
        this.prepaymentAccount = prepaymentAccount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ResPaymentMsg getSentPayementMsgPayementMsgId() {
        return sentPayementMsgPayementMsgId;
    }

    public void setSentPayementMsgPayementMsgId(ResPaymentMsg sentPayementMsgPayementMsgId) {
        this.sentPayementMsgPayementMsgId = sentPayementMsgPayementMsgId;
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
        hash += (receivedPaymentMsgId != null ? receivedPaymentMsgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecPaymentMsg)) {
            return false;
        }
        RecPaymentMsg other = (RecPaymentMsg) object;
        if ((this.receivedPaymentMsgId == null && other.receivedPaymentMsgId != null) || (this.receivedPaymentMsgId != null && !this.receivedPaymentMsgId.equals(other.receivedPaymentMsgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaselayer.ecs_kesws.entities.RecPaymentMsg[ receivedPaymentMsgId=" + receivedPaymentMsgId + " ]";
    }
    
}
