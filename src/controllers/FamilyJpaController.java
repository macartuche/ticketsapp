/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import entities.Family;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author macbookpro
 */
public class FamilyJpaController  extends EntityManagerProj implements Serializable {

    public FamilyJpaController() {
          super();
    } 

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Family family) {
        EntityManager em = null;
        try {
             em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            em.persist(family);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Family family) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
         em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            family = em.merge(family);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = family.getId();
                if (findFamily(id) == null) {
                    throw new NonexistentEntityException("The family with id " + id + " no longer exists.");
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
           em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Family family;
            try {
                family = em.getReference(Family.class, id);
                family.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The family with id " + id + " no longer exists.", enfe);
            }
            em.remove(family);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Family> findFamilyEntities() {
        return findFamilyEntities(true, -1, -1);
    }

    public List<Family> findFamilyEntities(int maxResults, int firstResult) {
        return findFamilyEntities(false, maxResults, firstResult);
    }

    private List<Family> findFamilyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager    em = super.getEmf().createEntityManager(); 
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Family.class));
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

    public Family findFamily(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Family.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamilyCount() {
        EntityManager    em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Family> rt = cq.from(Family.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
