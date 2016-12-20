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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "contratosUsuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserContract.findBylicensePlateAndDate", 
            query = "Select uc from UserContract uc "
                    + "where uc.licensePlate=:licensePlate "
                    + "and :currentDate between uc.contracts.dateStart "
                    + "and uc.contracts.dateEnd")
})
public class UserContract implements Serializable {
    
    //public static String 
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date dateStart;
    
    @Basic(optional = false)
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    
    @Basic(optional = false)
    @Column(name = "valor")
    private BigDecimal value;
    
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientProvider client;
    
    @JoinColumn(name = "contractId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contracts contracts;
    
    
    @Column(name = "placa")
    private String licensePlate;
    
    public UserContract() {
    }

    public UserContract(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public ClientProvider getClient() {
        return client;
    }

    public void setClient(ClientProvider client) {
        this.client = client;
    }
    
    public void setContract(Contracts contracts) {
        this.contracts = contracts;
    }

    public Contracts getContracts() {
        return contracts;
    }

    public void setContracts(Contracts contracts) {
        this.contracts = contracts;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
