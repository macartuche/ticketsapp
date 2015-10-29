/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Product;
import entities.Billing;
import entities.Inventary;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author macbookpro
 */
public class InventaryJpaController implements Serializable {

    public InventaryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventary inventary) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product productId = inventary.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                inventary.setProductId(productId);
            }
            Billing billingId = inventary.getBillingId();
            if (billingId != null) {
                billingId = em.getReference(billingId.getClass(), billingId.getId());
                inventary.setBillingId(billingId);
            }
            em.persist(inventary);
            if (productId != null) {
                productId.getInventaryList().add(inventary);
                productId = em.merge(productId);
            }
            if (billingId != null) {
                billingId.getInventaryList().add(inventary);
                billingId = em.merge(billingId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInventary(inventary.getId()) != null) {
                throw new PreexistingEntityException("Inventary " + inventary + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventary inventary) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventary persistentInventary = em.find(Inventary.class, inventary.getId());
            Product productIdOld = persistentInventary.getProductId();
            Product productIdNew = inventary.getProductId();
            Billing billingIdOld = persistentInventary.getBillingId();
            Billing billingIdNew = inventary.getBillingId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                inventary.setProductId(productIdNew);
            }
            if (billingIdNew != null) {
                billingIdNew = em.getReference(billingIdNew.getClass(), billingIdNew.getId());
                inventary.setBillingId(billingIdNew);
            }
            inventary = em.merge(inventary);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getInventaryList().remove(inventary);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getInventaryList().add(inventary);
                productIdNew = em.merge(productIdNew);
            }
            if (billingIdOld != null && !billingIdOld.equals(billingIdNew)) {
                billingIdOld.getInventaryList().remove(inventary);
                billingIdOld = em.merge(billingIdOld);
            }
            if (billingIdNew != null && !billingIdNew.equals(billingIdOld)) {
                billingIdNew.getInventaryList().add(inventary);
                billingIdNew = em.merge(billingIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inventary.getId();
                if (findInventary(id) == null) {
                    throw new NonexistentEntityException("The inventary with id " + id + " no longer exists.");
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
            Inventary inventary;
            try {
                inventary = em.getReference(Inventary.class, id);
                inventary.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventary with id " + id + " no longer exists.", enfe);
            }
            Product productId = inventary.getProductId();
            if (productId != null) {
                productId.getInventaryList().remove(inventary);
                productId = em.merge(productId);
            }
            Billing billingId = inventary.getBillingId();
            if (billingId != null) {
                billingId.getInventaryList().remove(inventary);
                billingId = em.merge(billingId);
            }
            em.remove(inventary);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventary> findInventaryEntities() {
        return findInventaryEntities(true, -1, -1);
    }

    public List<Inventary> findInventaryEntities(int maxResults, int firstResult) {
        return findInventaryEntities(false, maxResults, firstResult);
    }

    private List<Inventary> findInventaryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventary.class));
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

    public Inventary findInventary(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventary.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventaryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventary> rt = cq.from(Inventary.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
