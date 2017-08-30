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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByNick", query = "SELECT u FROM Users u WHERE u.nick = :nick"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByActive", query = "SELECT u FROM Users u WHERE u.active = :active"),
    @NamedQuery(name = "Users.findByIdOrNames",
            query = "SELECT u FROM Users u "
            + "WHERE u.personId.passport like :criteria or "
            + "lower(u.personId.names) like :criteria or "
            + "lower(u.personId.lastname) like :criteria")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombreUsuario")
    private byte[] nick;
    @Basic(optional = false)
    @Column(name = "clave")
    private String password;
    @Basic(optional = false)
    @Column(name = "activo")
    private Boolean active;
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Person personId;
    @Basic(optional = false)
    @Column(name = "rol")
    private String rol;

    @Column(name = "permisos")
    private String permissions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Billing> billingList;

    
    @Column(name = "frase",unique=true)
    private String phrase;
    
    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, byte[] nick, String password, Boolean active) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getNick() {
        return nick;
    }

    public void setNick(byte[] nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public String getNombres() {
        return this.personId.getNames() + " " + this.personId.getLastname();
    }

    public String getEmail() {
        return this.personId.getEmail();
    }

    public String getCorreo() {
        return this.personId.getEmail();
    }

    public String getUsuario() {
        return new String(nick);
    }

    public String getEstado() {
        return (active) ? "Activo" : "Inactivo";
    }

    public String getIdentificacion() {
        return personId.getPassport();
    }

    /*
     @Override
     public int hashCode() {
     int hash = 0;
     hash += (id != null ? id.hashCode() : 0);
     return hash;
     }

     @Override
     public boolean equals(Object object) {
     // TODO: Warning - this method won't work in the case the id fields are not set
     if (!(object instanceof Users)) {
     return false;
     }
     Users other = (Users) object;
     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
     return false;
     }
     return true;
     }

     */
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }


    public List<Billing> getBillingList() {
        return billingList;
    }

    public void setBillingList(List<Billing> billingList) {
        this.billingList = billingList;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
