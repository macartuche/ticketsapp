package entities;

import entities.Billing;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-03T11:52:32")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, BigDecimal> total;
    public static volatile SingularAttribute<Account, Date> dateCreation;
    public static volatile SingularAttribute<Account, Billing> billingId;
    public static volatile SingularAttribute<Account, BigDecimal> balance;
    public static volatile SingularAttribute<Account, Integer> id;
    public static volatile SingularAttribute<Account, String> state;

}