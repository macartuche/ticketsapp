/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Billing.findAll", query = "SELECT b FROM Billing b order by b.number"),
    @NamedQuery(name = "Billing.findById", query = "SELECT b FROM Billing b WHERE b.id = :id"),
    @NamedQuery(name = "Billing.findByType", query = "SELECT b FROM Billing b WHERE b.type = :type"),
    @NamedQuery(name = "Billing.findBySubtotal", query = "SELECT b FROM Billing b WHERE b.subtotal = :subtotal"),
    @NamedQuery(name = "Billing.findByFilter",
            query = "SELECT b FROM Billing b"
            + " WHERE b.emissiondate between :startDate"
            + " and :endDate"
            + " and (b.clientProviderid.personId.passport like :rucci or"
            + " lower(b.number) like :numReceipt)  order by b.emissiondate desc"),
    @NamedQuery(name = "Billing.findByFilter2",
            query = "SELECT b FROM Billing b"
            + " WHERE b.emissiondate between :startDate"
            + " and :endDate"
            + " order by b.emissiondate desc"),    
    @NamedQuery(name = "Billing.findByState", query = "SELECT b FROM Billing b WHERE b.state = :state"),
    @NamedQuery(name = "Billing.findByCriteria",
            query = "SELECT b FROM Billing b"
            + " WHERE b.emissiondate between :startDate"
            + " and :endDate"
            + " and (b.clientProviderid.personId.passport like :criteria or"
            + " lower(b.clientProviderid.personId.lastname ) like :criteria) order by b.emissiondate desc"),
    @NamedQuery(name = "Billing.findByTotal", query = "SELECT b FROM Billing b WHERE b.total = :total"),
    @NamedQuery(name = "Billing.findByTicketOrPlaca",
            query = "SELECT b FROM Billing b"
            + " WHERE b.emissiondate between :from and :until "
            + " and ( b.number like :criteria or lower(b.additionalInformation) like  :criteria)  "
            + " order by b.emissiondate desc")
})
@SuppressWarnings("ValidAttributes")
public class Billing implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billingId")
    private List<Account> accountCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fechaEmision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissiondate;
    @Column(name = "tipo")
    private String type;
    @Column(name = "estado")
    private String state;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal subtotal;
    @Column(name = "baseiva0", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal baseiva0;
    @Column(name = "iva0", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal iva0;
    @Column(name = "baseiva12", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal baseiva12;
    @Column(name = "iva12", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal iva12;
    @Column(name = "porcentajeDescuento", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal percentageDiscount;
    @Column(name = "descuento", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal discount;
    @Column(name = "total", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal total;
    @Column(name = "numero")
    private String number;
    @Column(name = "secuencial")
    private String sequential;
    @Column(name = "puntoEmision")
    private String emissionpoint_id;
    @Column(name = "tiendaid")
    private String shop_id;
    @JoinColumn(name = "clientdProveedor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientProvider clientProviderid;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billingId")
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "billingId", fetch = FetchType.EAGER)
    private List<DetailBilling> detailBillingList;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users user;

    @Transient
    private String clienteName;

    @Transient
    private String passportClient;

    @Column(name = "adicional")
    @Basic(optional = true)
    private String additionalInformation;
     
    
    @Column(name = "emisor")
    private Integer emitterPerson;
    
    @Column(name = "cobrador")
    private Integer collectorPerson;

    
    @Transient
    private String emisor;
    
    @Transient 
    private String cobrador;
    
    public Billing() {
        detailBillingList = new ArrayList<>();
        accountCollection = new ArrayList<>();
    }

    public Billing(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getEmissiondate() {
        return emissiondate;
    }

    public void setEmissiondate(Date emissiondate) {
        this.emissiondate = emissiondate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getBaseiva0() {
        return baseiva0;
    }

    public void setBaseiva0(BigDecimal baseiva0) {
        this.baseiva0 = baseiva0;
    }

    public BigDecimal getIva0() {
        return iva0;
    }

    public void setIva0(BigDecimal iva0) {
        this.iva0 = iva0;
    }

    public BigDecimal getBaseiva12() {
        return baseiva12;
    }

    public void setBaseiva12(BigDecimal baseiva12) {
        this.baseiva12 = baseiva12;
    }

    public BigDecimal getIva12() {
        return iva12;
    }

    public void setIva12(BigDecimal iva12) {
        this.iva12 = iva12;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSequential() {
        return sequential;
    }

    public void setSequential(String sequential) {
        this.sequential = sequential;
    }

    public BigDecimal getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(BigDecimal percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public String getEmissionpoint_id() {
        return emissionpoint_id;
    }

    public void setEmissionpoint_id(String emissionpoint_id) {
        this.emissionpoint_id = emissionpoint_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public ClientProvider getClientProviderid() {
        return clientProviderid;
    }

    public void setClientProviderid(ClientProvider clientProviderid) {
        this.clientProviderid = clientProviderid;
    }

    @XmlTransient
    public List<DetailBilling> getDetailBillingList() {
        return detailBillingList;
    }

    public void setDetailBillingList(List<DetailBilling> detailBillingList) {
        this.detailBillingList = detailBillingList;
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
        if (!(object instanceof Billing)) {
            return false;
        }
        Billing other = (Billing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Billing[ id=" + id + " ]";
    }

    public String getFecha() {
        SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return parser.format(emissiondate);
    }

    public String getCliente() {
        return clientProviderid.getFullname();
    }

    public String getNumero() {
        return number;
    }

    public String getEstado() {
        return state;
    }

    public String getFactura() {
        return number;
    }

    public String getIdentificacion() {
        return clientProviderid.getIdentificacion();
    }

    @XmlTransient
    public List<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(List<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }

    public String getClienteName() {
        return clienteName;
    }

    public void setClienteName(String clienteName) {
        this.clienteName = clienteName;
    }

    public String getPassportClient() {
        return passportClient;
    }

    public void setPassportClient(String passportClient) {
        this.passportClient = passportClient;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getInicio() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return (!detailBillingList.isEmpty()) ? format.format(detailBillingList.get(0).getTimestart()) : "";
    }

    
    public String getFin() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String fin="";
        if(!detailBillingList.isEmpty()){
            if(detailBillingList.get(0).getTimeend()!=null){
                fin = format.format(detailBillingList.get(0).getTimeend());
            }
        }
        return fin;
         
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
    
    
    public String getPlaca(){
        return this.additionalInformation;
    }

    public Integer getEmitterPerson() {
        return emitterPerson;
    }

    public void setEmitterPerson(Integer emitterPerson) {
        this.emitterPerson = emitterPerson;
    }

    public Integer getCollectorPerson() {
        return collectorPerson;
    }

    public void setCollectorPerson(Integer collectorPerson) {
        this.collectorPerson = collectorPerson;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getCobrador() {
        return cobrador;
    }

    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
    }
}
