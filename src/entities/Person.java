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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Person.FIND_ALL, query = "SELECT p FROM Person p"),
    @NamedQuery(name = Person.FIND_BY_ID, query = "SELECT p FROM Person p WHERE p.id = :id"),
    @NamedQuery(name = Person.FIND_BY_PASSPORT, query = "SELECT p FROM Person p WHERE p.passport = :passport"),
    @NamedQuery(name = Person.FIND_BY_NAME, query = "SELECT p FROM Person p WHERE p.names = :names"),
    @NamedQuery(name = Person.FIND_BY_LASTNAME, query = "SELECT p FROM Person p WHERE p.lastname = :lastname"),
    @NamedQuery(name = Person.FIND_BY_EMAIL, query = "SELECT p FROM Person p WHERE p.email = :email"),
    @NamedQuery(name = Person.FIND_BY_FULLNAME, query = "SELECT p FROM Person p "
            + "WHERE concat(p.lastname, ' ',  p.names ) = :names")
})
public class Person implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    public static final String FIND_ALL = "Person.findAll";
    public static final String FIND_BY_FULLNAME = "Person.findByFullName";
    public static final String FIND_BY_ID = "Person.findById";
    public static final String FIND_BY_PASSPORT = "Person.findByPassport";
    public static final String FIND_BY_NAME = "Person.findByName";
    public static final String FIND_BY_LASTNAME = "Person.findByNames";
    public static final String FIND_BY_EMAIL = "Person.findByEmail"; 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "identificacion")
    private String passport;
    @Column(name = "nombres")
    private String names;
    @Column(name = "apellidos")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "direccion")
    private String address;
    @Basic(optional = false)
    @Column(name = "sexo")
    private String sex;
    @Column(name = "telefono")
    private String phone;
    @Column(name = "foto")
    private String photo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private List<Users> usersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private List<ClientProvider> clientProviderList;


    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String sex) {
        this.id = id;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @XmlTransient
    public List<ClientProvider> getClientProviderList() {
        return clientProviderList;
    }

    public void setClientProviderList(List<ClientProvider> clientProviderList) {
        this.clientProviderList = clientProviderList;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Person[ id=" + id + " ]";
    }
    
}
