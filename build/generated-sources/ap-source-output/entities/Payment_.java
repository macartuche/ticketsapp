package entities;

import entities.Account;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-03T11:52:32")
@StaticMetamodel(Payment.class)
public class Payment_ { 

    public static volatile SingularAttribute<Payment, Account> accountId;
    public static volatile SingularAttribute<Payment, BigDecimal> balance;
    public static volatile SingularAttribute<Payment, Integer> id;
    public static volatile SingularAttribute<Payment, BigDecimal> value;
    public static volatile SingularAttribute<Payment, Date> datePayment;

}