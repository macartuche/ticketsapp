/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "contratos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contracts.findByDates", 
            query = "Select c from Contracts c where  :start>=c.dateStart "
                    + " and :end <= c.dateEnd ")
})
public class Contracts implements Serializable {

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
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contracts")
    private List<UserContract> usercontractList;

    public Contracts() {
    }

    public Contracts(Integer id) {
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

    public List<UserContract> getUsercontractList() {
        return usercontractList;
    }

    public void setUsercontractList(List<UserContract> usercontractList) {
        this.usercontractList = usercontractList;
    }
    
    public String getFechaInicio() {
        SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
        return parser.format(dateStart);
    }

    public String getFechaFin() {
        SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
        return parser.format(dateEnd);
    }
}
