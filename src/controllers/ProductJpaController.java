/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.DetailBilling;
import java.util.ArrayList;
import java.util.List;
import entities.Inventary;
import entities.Product;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author macbookpro
 */
public class ProductJpaController extends EntityManagerProj implements Serializable {

    public ProductJpaController() {
        super();
    }

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

//    public EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }

    public void create(Product product) {
        if (product.getDetailBillingList() == null) {
            product.setDetailBillingList(new ArrayList<DetailBilling>());
        }
        if (product.getInventaryList() == null) {
            product.setInventaryList(new ArrayList<Inventary>());
        }
        EntityManager em = null;
        try {
//            em = getEntityManager();
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            List<DetailBilling> attachedDetailBillingList = new ArrayList<DetailBilling>();
            for (DetailBilling detailBillingListDetailBillingToAttach : product.getDetailBillingList()) {
                detailBillingListDetailBillingToAttach = em.getReference(detailBillingListDetailBillingToAttach.getClass(), detailBillingListDetailBillingToAttach.getId());
                attachedDetailBillingList.add(detailBillingListDetailBillingToAttach);
            }
            product.setDetailBillingList(attachedDetailBillingList);
            List<Inventary> attachedInventaryList = new ArrayList<Inventary>();
            for (Inventary inventaryListInventaryToAttach : product.getInventaryList()) {
                inventaryListInventaryToAttach = em.getReference(inventaryListInventaryToAttach.getClass(), inventaryListInventaryToAttach.getId());
                attachedInventaryList.add(inventaryListInventaryToAttach);
            }
            product.setInventaryList(attachedInventaryList);
            em.persist(product);
            for (DetailBilling detailBillingListDetailBilling : product.getDetailBillingList()) {
                Product oldProductIdOfDetailBillingListDetailBilling = detailBillingListDetailBilling.getProductId();
                detailBillingListDetailBilling.setProductId(product);
                detailBillingListDetailBilling = em.merge(detailBillingListDetailBilling);
                if (oldProductIdOfDetailBillingListDetailBilling != null) {
                    oldProductIdOfDetailBillingListDetailBilling.getDetailBillingList().remove(detailBillingListDetailBilling);
                    oldProductIdOfDetailBillingListDetailBilling = em.merge(oldProductIdOfDetailBillingListDetailBilling);
                }
            }
            for (Inventary inventaryListInventary : product.getInventaryList()) {
                Product oldProductIdOfInventaryListInventary = inventaryListInventary.getProductId();
                inventaryListInventary.setProductId(product);
                inventaryListInventary = em.merge(inventaryListInventary);
                if (oldProductIdOfInventaryListInventary != null) {
                    oldProductIdOfInventaryListInventary.getInventaryList().remove(inventaryListInventary);
                    oldProductIdOfInventaryListInventary = em.merge(oldProductIdOfInventaryListInventary);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
//            em = getEntityManager();
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getId());
            List<DetailBilling> detailBillingListOld = persistentProduct.getDetailBillingList();
            List<DetailBilling> detailBillingListNew = product.getDetailBillingList();
            List<Inventary> inventaryListOld = persistentProduct.getInventaryList();
            List<Inventary> inventaryListNew = product.getInventaryList();
            List<String> illegalOrphanMessages = null;
            for (DetailBilling detailBillingListOldDetailBilling : detailBillingListOld) {
                if (!detailBillingListNew.contains(detailBillingListOldDetailBilling)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetailBilling " + detailBillingListOldDetailBilling + " since its productId field is not nullable.");
                }
            }
            for (Inventary inventaryListOldInventary : inventaryListOld) {
                if (!inventaryListNew.contains(inventaryListOldInventary)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventary " + inventaryListOldInventary + " since its productId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DetailBilling> attachedDetailBillingListNew = new ArrayList<DetailBilling>();
            for (DetailBilling detailBillingListNewDetailBillingToAttach : detailBillingListNew) {
                detailBillingListNewDetailBillingToAttach = em.getReference(detailBillingListNewDetailBillingToAttach.getClass(), detailBillingListNewDetailBillingToAttach.getId());
                attachedDetailBillingListNew.add(detailBillingListNewDetailBillingToAttach);
            }
            detailBillingListNew = attachedDetailBillingListNew;
            product.setDetailBillingList(detailBillingListNew);
            List<Inventary> attachedInventaryListNew = new ArrayList<Inventary>();
            for (Inventary inventaryListNewInventaryToAttach : inventaryListNew) {
                inventaryListNewInventaryToAttach = em.getReference(inventaryListNewInventaryToAttach.getClass(), inventaryListNewInventaryToAttach.getId());
                attachedInventaryListNew.add(inventaryListNewInventaryToAttach);
            }
            inventaryListNew = attachedInventaryListNew;
            product.setInventaryList(inventaryListNew);
            product = em.merge(product);
            for (DetailBilling detailBillingListNewDetailBilling : detailBillingListNew) {
                if (!detailBillingListOld.contains(detailBillingListNewDetailBilling)) {
                    Product oldProductIdOfDetailBillingListNewDetailBilling = detailBillingListNewDetailBilling.getProductId();
                    detailBillingListNewDetailBilling.setProductId(product);
                    detailBillingListNewDetailBilling = em.merge(detailBillingListNewDetailBilling);
                    if (oldProductIdOfDetailBillingListNewDetailBilling != null && !oldProductIdOfDetailBillingListNewDetailBilling.equals(product)) {
                        oldProductIdOfDetailBillingListNewDetailBilling.getDetailBillingList().remove(detailBillingListNewDetailBilling);
                        oldProductIdOfDetailBillingListNewDetailBilling = em.merge(oldProductIdOfDetailBillingListNewDetailBilling);
                    }
                }
            }
            for (Inventary inventaryListNewInventary : inventaryListNew) {
                if (!inventaryListOld.contains(inventaryListNewInventary)) {
                    Product oldProductIdOfInventaryListNewInventary = inventaryListNewInventary.getProductId();
                    inventaryListNewInventary.setProductId(product);
                    inventaryListNewInventary = em.merge(inventaryListNewInventary);
                    if (oldProductIdOfInventaryListNewInventary != null && !oldProductIdOfInventaryListNewInventary.equals(product)) {
                        oldProductIdOfInventaryListNewInventary.getInventaryList().remove(inventaryListNewInventary);
                        oldProductIdOfInventaryListNewInventary = em.merge(oldProductIdOfInventaryListNewInventary);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = product.getId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
//            em = getEntityManager();
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetailBilling> detailBillingListOrphanCheck = product.getDetailBillingList();
            for (DetailBilling detailBillingListOrphanCheckDetailBilling : detailBillingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the DetailBilling " + detailBillingListOrphanCheckDetailBilling + " in its detailBillingList field has a non-nullable productId field.");
            }
            List<Inventary> inventaryListOrphanCheck = product.getInventaryList();
            for (Inventary inventaryListOrphanCheckInventary : inventaryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Inventary " + inventaryListOrphanCheckInventary + " in its inventaryList field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
        em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id) {
//        EntityManager em = getEntityManager();
        em = super.getEmf().createEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
//        EntityManager em = getEntityManager();
        em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Product> namedQuery(String query, Map<String, Object> filters) {
        EntityManager em = super.getEmf().createEntityManager();
        try {

            Query q = em.createNamedQuery(query);
            for (Map.Entry<String, Object> entrySet : filters.entrySet()) {
                String key = entrySet.getKey();
                Object value = entrySet.getValue();
                q.setParameter(key, value);
            }
            return q.getResultList();
        } finally {
            em.close();
        }

    }

}
