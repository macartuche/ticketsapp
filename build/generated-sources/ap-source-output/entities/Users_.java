package entities;

import entities.Billing;
import entities.Person;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-03T11:52:32")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, byte[]> nick;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile ListAttribute<Users, Billing> billingList;
    public static volatile SingularAttribute<Users, String> permissions;
    public static volatile SingularAttribute<Users, Boolean> active;
    public static volatile SingularAttribute<Users, Person> personId;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> rol;

}