/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "detalleFactura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetailBilling.findAll", query = "SELECT d FROM DetailBilling d"),
    @NamedQuery(name = "DetailBilling.findById", query = "SELECT d FROM DetailBilling d WHERE d.id = :id"),
    @NamedQuery(name = "DetailBilling.findByQuantity", query = "SELECT d FROM DetailBilling d WHERE d.quantity = :quantity"),
    @NamedQuery(name = "DetailBilling.findByUnitaryPrice", query = "SELECT d FROM DetailBilling d WHERE d.unitaryPrice = :unitaryPrice"),
    @NamedQuery(name = "DetailBilling.findByTotal", query = "SELECT d FROM DetailBilling d WHERE d.total = :total")})
public class DetailBilling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidad", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal quantity;
//    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precioUnitario", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal unitaryPrice;
    @Column(name = "valorDescuento", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal valueDiscount;
    @Column(name = "porcentajeDescuento", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal percentageDiscount;
    @Column(name = "valorIva", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal valueIva;
    @Column(name = "porcentajeIva", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal percentageIva;
    @Column(name = "total", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal total;
    @Column(name = "totalconIva", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal totalWithTax;
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Billing billingId;
    
    @Column(name = "inicio")
    @Temporal(TemporalType.TIME)
    @Basic(optional = true)
    private Date timestart;
    
        
    @Column(name = "fin")
    @Temporal(TemporalType.TIME)
    @Basic(optional = true)
    private Date timeend;

    
    @Transient
    private String name;
    
    
    public DetailBilling() {
    }

    public DetailBilling(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitaryPrice() {
        return unitaryPrice;
    }

    public void setUnitaryPrice(BigDecimal unitaryPrice) {
        this.unitaryPrice = unitaryPrice;
    }

    public BigDecimal getValueDiscount() {
        return valueDiscount;
    }

    public void setValueDiscount(BigDecimal valueDiscount) {
        this.valueDiscount = valueDiscount;
    }

    public BigDecimal getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(BigDecimal percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public BigDecimal getValueIva() {
        return valueIva;
    }

    public void setValueIva(BigDecimal valueIva) {
        this.valueIva = valueIva;
    }

    public BigDecimal getPercentageIva() {
        return percentageIva;
    }

    public void setPercentageIva(BigDecimal percentageIva) {
        this.percentageIva = percentageIva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalWithTax() {
        return totalWithTax;
    }

    public void setTotalWithTax(BigDecimal totalWithTax) {
        this.totalWithTax = totalWithTax;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Billing getBillingId() {
        return billingId;
    }

    public void setBillingId(Billing billingId) {
        this.billingId = billingId;
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
        if (!(object instanceof DetailBilling)) {
            return false;
        }
        DetailBilling other = (DetailBilling) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DetailBilling[ id=" + id + " ]";
    }

    public String getProducto() {
        return productId.getName();
    }

    public String getCantidad() {
        return quantity.toString();
    }

    public String getPrecioUnitario() {
        return productId.getSaleprice().toString();
    }

    public String getDescuento() {
        return valueDiscount.toString();
    }

    public String getPrecioTotal() {
        return total.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 


    public Date getTimestart() {
        return timestart;
    }

    public void setTimestart(Date timestart) {
        this.timestart = timestart;
    }

    public Date getTimeend() {
        return timeend;
    }

    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }
     
    
    
}
