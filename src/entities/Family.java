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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "familia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Family.findAll", query = "SELECT f FROM Family f"),
    @NamedQuery(name = "Family.findById", query = "SELECT f FROM Family f WHERE f.id = :id"),
    @NamedQuery(name = "Family.findByName", query = "SELECT f FROM Family f WHERE lower(f.name)  like :name"),
    @NamedQuery(name = "Family.findByDescription", query = "SELECT f FROM Family f WHERE f.description = :description")})

public class Family implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String name;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String  description;
    
    @Column(name = "talla")
    private Boolean  size;
    
    @Column(name = "color")
    private Boolean  color;
    
    @Column(name = "genero")
    private Boolean  sex;
    
   @Column(name = "medida")
    private Boolean  measure;
 
    @Column(name = "calidad")
    private Boolean  quality;
    
    @Column(name = "modelo")
    private Boolean  model;
     
    @Column(name = "activo")
    private Boolean  active;
    
    @Transient
    private Boolean edit;
    
    public Family() {
        edit = false;
    }

    public Family(Integer id) {
        this.id = id;
    }

    public Family(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Family)) {
            return false;
        }
        Family other = (Family) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name; 
   }

    public Boolean getSize() {
        return size;
    }

    public void setSize(Boolean size) {
        this.size = size;
    }

    public Boolean getColor() {
        return color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getMeasure() {
        return measure;
    }

    public void setMeasure(Boolean measure) {
        this.measure = measure;
    }

    public Boolean getQuality() {
        return quality;
    }

    public void setQuality(Boolean quality) {
        this.quality = quality;
    }

    public Boolean getModel() {
        return model;
    }

    public void setModel(Boolean model) {
        this.model = model;
    }
    
    public String getMedida(){
        return (this.measure)? "Si" : "No";
    }
    
    public String getTalla(){
        return (this.size)? "Si" : "No";
    }
    
    public String getColor1(){
      return (this.color)? "Si" : "No";
    }
    
    public String getCalidad(){
        return (this.quality)? "Si" : "No";
    }
    
    public String getModelo(){
        return (this.model)? "Si" : "No";
    }
    
    public String getGenero(){
        return (this.sex)? "Si" : "No";
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
   
    public String getEstado(){
        return (this.active)? "Activo" : "Inactivo";
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }
    
}
