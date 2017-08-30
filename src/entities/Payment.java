/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "pagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"),
    @NamedQuery(name = "Payment.findByAccountId", query = "SELECT p FROM Payment p WHERE p.accountId = :accountId"),
    @NamedQuery(name = "Payment.findByDatePayment", query = "SELECT p FROM Payment p WHERE p.datePayment = :datePayment"),
    @NamedQuery(name = "Payment.findByBalance", query = "SELECT p FROM Payment p WHERE p.balance = :balance"),
    @NamedQuery(name = "Payment.findByValue", query = "SELECT p FROM Payment p WHERE p.value = :value")})
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @OneToOne
    @JoinColumn(name="cuenta_id") 
    private Account accountId;
    
    @Basic(optional = false)
    @Column(name = "fechaPago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePayment;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "saldo")
    private BigDecimal balance;
    @Basic(optional = false)
    @Column(name = "valor")
    private BigDecimal value;

    public Payment() {
    }

    public Payment(Integer id) {
        this.id = id;
    }

    public Payment(Integer id, Account accountId, Date datePayment, BigDecimal balance, BigDecimal value) {
        this.id = id;
        this.accountId = accountId;
        this.datePayment = datePayment;
        this.balance = balance;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Payment[ id=" + id + " ]";
    }
    
    
    public String  getFechaPago(){
        SimpleDateFormat  parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return parser.format(datePayment);
    }
    
    public BigDecimal getValor(){
        return value;
    }
    
    public BigDecimal getSaldo(){
        return balance;
    }
}
