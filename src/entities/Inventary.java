/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "inventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventary.findAll", query = "SELECT i FROM Inventary i"),
    @NamedQuery(name = "Inventary.findById", query = "SELECT i FROM Inventary i WHERE i.id = :id"),
    @NamedQuery(name = "Inventary.findByType", query = "SELECT i FROM Inventary i WHERE i.type = :type"),
    @NamedQuery(name = "Inventary.findByQuantity", query = "SELECT i FROM Inventary i WHERE i.quantity = :quantity"),
    @NamedQuery(name = "Inventary.findByPrice", query = "SELECT i FROM Inventary i WHERE i.price = :price"),
    @NamedQuery(name = "Inventary.findByTotal", query = "SELECT i FROM Inventary i WHERE i.total = :total"),
    @NamedQuery(name = "Inventary.findByInventarycol", query = "SELECT i FROM Inventary i WHERE i.inventarycol = :inventarycol"),
    @NamedQuery(name = "Inventary.findByDate", query = "SELECT i FROM Inventary i WHERE i.date = :date")})
public class Inventary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo")
    private String type;
    @Column(name = "cantidad")
    private String quantity;
    @Column(name = "precio")
    private String price;
    @Column(name = "total")
    private String total;
    @Column(name = "inventariocol")
    private String inventarycol;
    @Column(name = "fecha")
    private String date;
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Billing billingId;

    public Inventary() {
    }

    public Inventary(Integer id) {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getInventarycol() {
        return inventarycol;
    }

    public void setInventarycol(String inventarycol) {
        this.inventarycol = inventarycol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        if (!(object instanceof Inventary)) {
            return false;
        }
        Inventary other = (Inventary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Inventary[ id=" + id + " ]";
    }
    
}
