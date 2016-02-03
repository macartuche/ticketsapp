package entities;

import entities.ClientProvider;
import entities.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-03T11:52:32")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile ListAttribute<Person, Users> usersList;
    public static volatile SingularAttribute<Person, String> names;
    public static volatile SingularAttribute<Person, String> address;
    public static volatile SingularAttribute<Person, String> passport;
    public static volatile SingularAttribute<Person, String> phone;
    public static volatile SingularAttribute<Person, String> sex;
    public static volatile SingularAttribute<Person, String> photo;
    public static volatile SingularAttribute<Person, Integer> id;
    public static volatile ListAttribute<Person, ClientProvider> clientProviderList;
    public static volatile SingularAttribute<Person, String> email;
    public static volatile SingularAttribute<Person, String> lastname;

}