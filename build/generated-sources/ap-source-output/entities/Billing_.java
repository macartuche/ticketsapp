package entities;

import entities.Account;
import entities.ClientProvider;
import entities.DetailBilling;
import entities.Inventary;
import entities.Users;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-03T11:52:32")
@StaticMetamodel(Billing.class)
public class Billing_ { 

    public static volatile SingularAttribute<Billing, BigDecimal> baseiva0;
    public static volatile ListAttribute<Billing, Account> accountCollection;
    public static volatile SingularAttribute<Billing, String> additionalInformation;
    public static volatile SingularAttribute<Billing, Date> emissiondate;
    public static volatile SingularAttribute<Billing, String> sequential;
    public static volatile SingularAttribute<Billing, BigDecimal> discount;
    public static volatile SingularAttribute<Billing, BigDecimal> iva12;
    public static volatile SingularAttribute<Billing, String> type;
    public static volatile SingularAttribute<Billing, BigDecimal> baseiva12;
    public static volatile SingularAttribute<Billing, BigDecimal> iva0;
    public static volatile SingularAttribute<Billing, String> number;
    public static volatile SingularAttribute<Billing, String> shop_id;
    public static volatile SingularAttribute<Billing, BigDecimal> total;
    public static volatile SingularAttribute<Billing, ClientProvider> clientProviderid;
    public static volatile SingularAttribute<Billing, BigDecimal> percentageDiscount;
    public static volatile ListAttribute<Billing, Inventary> inventaryList;
    public static volatile SingularAttribute<Billing, BigDecimal> subtotal;
    public static volatile ListAttribute<Billing, DetailBilling> detailBillingList;
    public static volatile SingularAttribute<Billing, Integer> id;
    public static volatile SingularAttribute<Billing, String> state;
    public static volatile SingularAttribute<Billing, Users> user;
    public static volatile SingularAttribute<Billing, String> emissionpoint_id;

}