/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author macbookpro
 */
public class EntityManagerProj {

    protected EntityManagerFactory emf;
    protected EntityManager em;
    private static final String PERSISTENCE_UNIT_NAME = "pabloAppPU";

    public EntityManagerProj() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//        emf = Persistence.createEntityManagerFactory("pabloAppPU");
        em = emf.createEntityManager();

//        EntityManager entityManager = Persistence.createEntityManagerFactory("CustomerLibraryPU").createEntityManager();
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
