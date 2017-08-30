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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "configuraciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configurations.findAll", query = "SELECT c FROM Configurations c"),
    @NamedQuery(name = "Configurations.findById", query = "SELECT c FROM Configurations c WHERE c.id = :id"),
    @NamedQuery(name = "Configurations.findByName", query = "SELECT c FROM Configurations c WHERE c.name = :name"),
    @NamedQuery(name = "Configurations.findByValue", query = "SELECT c FROM Configurations c WHERE c.value = :value"),
    @NamedQuery(name = "Configurations.findByCode", query = "SELECT c FROM Configurations c WHERE c.code = :code")})
public class Configurations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String name;
    @Basic(optional = false)
    @Column(name = "valor")
    private String value;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String code;
 
    @Column(name = "activo")
    private Boolean active;

    public Configurations() {
    }

    public Configurations(Integer id) {
        this.id = id;
    }

    public Configurations(Integer id, String name, String value, String code) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(object instanceof Configurations)) {
            return false;
        }
        Configurations other = (Configurations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Configurations[ id=" + id + " ]";
    }

    public String getCodigo() {
        return code;
    }

    public String getValor() {
        return value;
    }

    public String getNombre() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEstado() {
        return (active) ? "Activo" : "Inactivo";
    }

}
