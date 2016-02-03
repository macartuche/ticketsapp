package entities;

import entities.DetailBilling;
import entities.Family;
import entities.Inventary;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-03T11:52:32")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, BigDecimal> percentageIva;
    public static volatile SingularAttribute<Product, String> code;
    public static volatile SingularAttribute<Product, String> color;
    public static volatile SingularAttribute<Product, String> sex;
    public static volatile SingularAttribute<Product, BigDecimal> saleprice;
    public static volatile SingularAttribute<Product, Boolean> active;
    public static volatile SingularAttribute<Product, BigDecimal> purchaseprice;
    public static volatile SingularAttribute<Product, BigDecimal> minvalue;
    public static volatile SingularAttribute<Product, String> quality;
    public static volatile SingularAttribute<Product, String> measure;
    public static volatile ListAttribute<Product, Inventary> inventaryList;
    public static volatile SingularAttribute<Product, String> size;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile ListAttribute<Product, DetailBilling> detailBillingList;
    public static volatile SingularAttribute<Product, String> model;
    public static volatile SingularAttribute<Product, Integer> id;
    public static volatile SingularAttribute<Product, BigDecimal> stock;
    public static volatile SingularAttribute<Product, Family> family;

}