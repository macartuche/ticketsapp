/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import entities.Contracts;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author macbookpro
 */
public class ContractsJpaController  extends EntityManagerProj  implements Serializable {

    public ContractsJpaController() {
         super();
    } 
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contracts contracts) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contracts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contracts contracts) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contracts = em.merge(contracts);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contracts.getId();
                if (findContracts(id) == null) {
                    throw new NonexistentEntityException("The contracts with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contracts contracts;
            try {
                contracts = em.getReference(Contracts.class, id);
                contracts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contracts with id " + id + " no longer exists.", enfe);
            }
            em.remove(contracts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contracts> findContractsEntities() {
        return findContractsEntities(true, -1, -1);
    }

    public List<Contracts> findContractsEntities(int maxResults, int firstResult) {
        return findContractsEntities(false, maxResults, firstResult);
    }

    private List<Contracts> findContractsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contracts.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Contracts findContracts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contracts.class, id);
        } finally {
            em.close();
        }
    }

    public int getContractsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contracts> rt = cq.from(Contracts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
