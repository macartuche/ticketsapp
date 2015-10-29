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
import entities.DetailBilling;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author macbookpro
 */
public class DetailBillingJpaController extends EntityManagerProj implements Serializable {

    public DetailBillingJpaController() {
        super();
    }

    public DetailBillingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetailBilling detailBilling) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Product productId = detailBilling.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                detailBilling.setProductId(productId);
            }
            Billing billingId = detailBilling.getBillingId();
            if (billingId != null) {
                billingId = em.getReference(billingId.getClass(), billingId.getId());
                detailBilling.setBillingId(billingId);
            }
            em.persist(detailBilling);
            if (productId != null) {
                productId.getDetailBillingList().add(detailBilling);
                productId = em.merge(productId);
            }
            if (billingId != null) {
                billingId.getDetailBillingList().add(detailBilling);
                billingId = em.merge(billingId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetailBilling(detailBilling.getId()) != null) {
                throw new PreexistingEntityException("DetailBilling " + detailBilling + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetailBilling detailBilling) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
           em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            DetailBilling persistentDetailBilling = em.find(DetailBilling.class, detailBilling.getId());
            Product productIdOld = persistentDetailBilling.getProductId();
            Product productIdNew = detailBilling.getProductId();
            Billing billingIdOld = persistentDetailBilling.getBillingId();
            Billing billingIdNew = detailBilling.getBillingId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                detailBilling.setProductId(productIdNew);
            }
            if (billingIdNew != null) {
                billingIdNew = em.getReference(billingIdNew.getClass(), billingIdNew.getId());
                detailBilling.setBillingId(billingIdNew);
            }
            detailBilling = em.merge(detailBilling);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getDetailBillingList().remove(detailBilling);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getDetailBillingList().add(detailBilling);
                productIdNew = em.merge(productIdNew);
            }
            if (billingIdOld != null && !billingIdOld.equals(billingIdNew)) {
                billingIdOld.getDetailBillingList().remove(detailBilling);
                billingIdOld = em.merge(billingIdOld);
            }
            if (billingIdNew != null && !billingIdNew.equals(billingIdOld)) {
                billingIdNew.getDetailBillingList().add(detailBilling);
                billingIdNew = em.merge(billingIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detailBilling.getId();
                if (findDetailBilling(id) == null) {
                    throw new NonexistentEntityException("The detailBilling with id " + id + " no longer exists.");
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
            DetailBilling detailBilling;
            try {
                detailBilling = em.getReference(DetailBilling.class, id);
                detailBilling.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detailBilling with id " + id + " no longer exists.", enfe);
            }
            Product productId = detailBilling.getProductId();
            if (productId != null) {
                productId.getDetailBillingList().remove(detailBilling);
                productId = em.merge(productId);
            }
            Billing billingId = detailBilling.getBillingId();
            if (billingId != null) {
                billingId.getDetailBillingList().remove(detailBilling);
                billingId = em.merge(billingId);
            }
            em.remove(detailBilling);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetailBilling> findDetailBillingEntities() {
        return findDetailBillingEntities(true, -1, -1);
    }

    public List<DetailBilling> findDetailBillingEntities(int maxResults, int firstResult) {
        return findDetailBillingEntities(false, maxResults, firstResult);
    }

    private List<DetailBilling> findDetailBillingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager
           em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetailBilling.class));
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

    public DetailBilling findDetailBilling(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetailBilling.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetailBillingCount() {
        EntityManager
           em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetailBilling> rt = cq.from(DetailBilling.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
