/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "clienteProveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientProvider.findAll", query = "SELECT c FROM ClientProvider c"),
    @NamedQuery(name = "ClientProvider.findById", query = "SELECT c FROM ClientProvider c WHERE c.id = :id"),
    @NamedQuery(name = "ClientProvider.findByActiveclient", query = "SELECT c FROM ClientProvider c WHERE c.activeclient = :activeclient"),
    @NamedQuery(name = "ClientProvider.findByClient",
            query = "SELECT c FROM ClientProvider c WHERE c.client = :client"),
    @NamedQuery(name = "ClientProvider.findByClientCriteria",
            query = "SELECT c FROM ClientProvider c"
            + " WHERE c.client = :client "
            + " and (c.personId.passport like :criteria or "
            + "lower(c.personId.names) like :criteria or "
            + "lower(c.personId.lastname) like :criteria)"),
    @NamedQuery(name = "ClientProvider.findByNamesOrPassport",
            query = "SELECT c FROM ClientProvider c"
            + " WHERE c.personId.passport like :criteria or"
//            + " lower(c.personId.names) like :criteria or"
            + " lower(c.personId.lastname) like :criteria"),
    @NamedQuery(name = "ClientProvider.findByProvider", query = "SELECT c FROM ClientProvider c WHERE c.provider = :provider"),
    @NamedQuery(name = "ClientProvider.findByActiveprovider", query = "SELECT c FROM ClientProvider c WHERE c.activeprovider = :activeprovider")})
public class ClientProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "activeclient")
    private Boolean activeclient;
    @Column(name = "client")
    private Boolean client;
    @Column(name = "provider")
    private Boolean provider;
    @Column(name = "activeprovider")
    private Boolean activeprovider;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientProviderid")
    private List<Billing> billingList;
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Person personId;
     
   @Transient
   private String fullname;
   
    public ClientProvider() {
    }

    public ClientProvider(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActiveclient() {
        return activeclient;
    }

    public void setActiveclient(Boolean activeclient) {
        this.activeclient = activeclient;
    }

    public Boolean getClient() {
        return client;
    }

    public void setClient(Boolean client) {
        this.client = client;
    }

    public Boolean getProvider() {
        return provider;
    }

    public void setProvider(Boolean provider) {
        this.provider = provider;
    }

    public Boolean getActiveprovider() {
        return activeprovider;
    }

    public void setActiveprovider(Boolean activeprovider) {
        this.activeprovider = activeprovider;
    }

    @XmlTransient
    public List<Billing> getBillingList() {
        return billingList;
    }

    public void setBillingList(List<Billing> billingList) {
        this.billingList = billingList;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
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
        if (!(object instanceof ClientProvider)) {
            return false;
        }
        ClientProvider other = (ClientProvider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ClientProvider[ id=" + id + " ]";
    }

    public String getNombres() {
        return personId.getNames();
    }

    public String getApellidos() {
        return personId.getLastname();
    }

    public String getIdentificacion() {
        return personId.getPassport();
    }

    public String getEstado() {
        return (activeclient) ? "Activo" : "Inactivo";
    } 

    public String getFullname() {
        return personId.getNames()+" "+personId.getLastname();
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
