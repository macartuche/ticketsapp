/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import entities.Billing;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.ClientProvider;
import entities.DetailBilling;
import java.util.ArrayList;
import java.util.List;
import entities.Inventary;
import entities.Users;
import java.util.Date;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author macbookpro
 */
public class BillingJpaController extends EntityManagerProj implements Serializable {

    public BillingJpaController() {
        super();
    }

//    public BillingJpaController(EntityManagerFactory emf) {
//        this.emf = emf;
//    }
//    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Billing billing) {
        if (billing.getDetailBillingList() == null) {
            billing.setDetailBillingList(new ArrayList<DetailBilling>());
        }
        if (billing.getInventaryList() == null) {
            billing.setInventaryList(new ArrayList<Inventary>());
        }
        EntityManager em = null;
        try {
//            em = getEntityManager();
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            ClientProvider clientProviderid = billing.getClientProviderid();
            if (clientProviderid != null) {
                clientProviderid = em.getReference(clientProviderid.getClass(), clientProviderid.getId());
                billing.setClientProviderid(clientProviderid);
            }
            List<DetailBilling> attachedDetailBillingList = new ArrayList<DetailBilling>();
            for (DetailBilling detailBillingListDetailBillingToAttach : billing.getDetailBillingList()) {
                detailBillingListDetailBillingToAttach = em.getReference(detailBillingListDetailBillingToAttach.getClass(), detailBillingListDetailBillingToAttach.getId());
                attachedDetailBillingList.add(detailBillingListDetailBillingToAttach);
            }
            billing.setDetailBillingList(attachedDetailBillingList);
            List<Inventary> attachedInventaryList = new ArrayList<Inventary>();
            for (Inventary inventaryListInventaryToAttach : billing.getInventaryList()) {
                inventaryListInventaryToAttach = em.getReference(inventaryListInventaryToAttach.getClass(), inventaryListInventaryToAttach.getId());
                attachedInventaryList.add(inventaryListInventaryToAttach);
            }
            billing.setInventaryList(attachedInventaryList);
            em.persist(billing);
            if (clientProviderid != null) {
                clientProviderid.getBillingList().add(billing);
                clientProviderid = em.merge(clientProviderid);
            }
            for (DetailBilling detailBillingListDetailBilling : billing.getDetailBillingList()) {
                Billing oldBillingIdOfDetailBillingListDetailBilling = detailBillingListDetailBilling.getBillingId();
                detailBillingListDetailBilling.setBillingId(billing);
                detailBillingListDetailBilling = em.merge(detailBillingListDetailBilling);
                if (oldBillingIdOfDetailBillingListDetailBilling != null) {
                    oldBillingIdOfDetailBillingListDetailBilling.getDetailBillingList().remove(detailBillingListDetailBilling);
                    oldBillingIdOfDetailBillingListDetailBilling = em.merge(oldBillingIdOfDetailBillingListDetailBilling);
                }
            }
            for (Inventary inventaryListInventary : billing.getInventaryList()) {
                Billing oldBillingIdOfInventaryListInventary = inventaryListInventary.getBillingId();
                inventaryListInventary.setBillingId(billing);
                inventaryListInventary = em.merge(inventaryListInventary);
                if (oldBillingIdOfInventaryListInventary != null) {
                    oldBillingIdOfInventaryListInventary.getInventaryList().remove(inventaryListInventary);
                    oldBillingIdOfInventaryListInventary = em.merge(oldBillingIdOfInventaryListInventary);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Billing billing) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
//            em = getEntityManager();
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Billing persistentBilling = em.find(Billing.class, billing.getId());
            ClientProvider clientProvideridOld = persistentBilling.getClientProviderid();
            ClientProvider clientProvideridNew = billing.getClientProviderid();
            List<DetailBilling> detailBillingListOld = persistentBilling.getDetailBillingList();
            List<DetailBilling> detailBillingListNew = billing.getDetailBillingList();
            List<Inventary> inventaryListOld = persistentBilling.getInventaryList();
            List<Inventary> inventaryListNew = billing.getInventaryList();
            List<String> illegalOrphanMessages = null;
            for (DetailBilling detailBillingListOldDetailBilling : detailBillingListOld) {
                if (!detailBillingListNew.contains(detailBillingListOldDetailBilling)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetailBilling " + detailBillingListOldDetailBilling + " since its billingId field is not nullable.");
                }
            }
            for (Inventary inventaryListOldInventary : inventaryListOld) {
                if (!inventaryListNew.contains(inventaryListOldInventary)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventary " + inventaryListOldInventary + " since its billingId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientProvideridNew != null) {
                clientProvideridNew = em.getReference(clientProvideridNew.getClass(), clientProvideridNew.getId());
                billing.setClientProviderid(clientProvideridNew);
            }
            List<DetailBilling> attachedDetailBillingListNew = new ArrayList<DetailBilling>();
            for (DetailBilling detailBillingListNewDetailBillingToAttach : detailBillingListNew) {
                detailBillingListNewDetailBillingToAttach = em.getReference(detailBillingListNewDetailBillingToAttach.getClass(), detailBillingListNewDetailBillingToAttach.getId());
                attachedDetailBillingListNew.add(detailBillingListNewDetailBillingToAttach);
            }
            detailBillingListNew = attachedDetailBillingListNew;
            billing.setDetailBillingList(detailBillingListNew);
            List<Inventary> attachedInventaryListNew = new ArrayList<Inventary>();
            for (Inventary inventaryListNewInventaryToAttach : inventaryListNew) {
                inventaryListNewInventaryToAttach = em.getReference(inventaryListNewInventaryToAttach.getClass(), inventaryListNewInventaryToAttach.getId());
                attachedInventaryListNew.add(inventaryListNewInventaryToAttach);
            }
            inventaryListNew = attachedInventaryListNew;
            billing.setInventaryList(inventaryListNew);
            billing = em.merge(billing);
            if (clientProvideridOld != null && !clientProvideridOld.equals(clientProvideridNew)) {
                clientProvideridOld.getBillingList().remove(billing);
                clientProvideridOld = em.merge(clientProvideridOld);
            }
            if (clientProvideridNew != null && !clientProvideridNew.equals(clientProvideridOld)) {
                clientProvideridNew.getBillingList().add(billing);
                clientProvideridNew = em.merge(clientProvideridNew);
            }
            for (DetailBilling detailBillingListNewDetailBilling : detailBillingListNew) {
                if (!detailBillingListOld.contains(detailBillingListNewDetailBilling)) {
                    Billing oldBillingIdOfDetailBillingListNewDetailBilling = detailBillingListNewDetailBilling.getBillingId();
                    detailBillingListNewDetailBilling.setBillingId(billing);
                    detailBillingListNewDetailBilling = em.merge(detailBillingListNewDetailBilling);
                    if (oldBillingIdOfDetailBillingListNewDetailBilling != null && !oldBillingIdOfDetailBillingListNewDetailBilling.equals(billing)) {
                        oldBillingIdOfDetailBillingListNewDetailBilling.getDetailBillingList().remove(detailBillingListNewDetailBilling);
                        oldBillingIdOfDetailBillingListNewDetailBilling = em.merge(oldBillingIdOfDetailBillingListNewDetailBilling);
                    }
                }
            }
            for (Inventary inventaryListNewInventary : inventaryListNew) {
                if (!inventaryListOld.contains(inventaryListNewInventary)) {
                    Billing oldBillingIdOfInventaryListNewInventary = inventaryListNewInventary.getBillingId();
                    inventaryListNewInventary.setBillingId(billing);
                    inventaryListNewInventary = em.merge(inventaryListNewInventary);
                    if (oldBillingIdOfInventaryListNewInventary != null && !oldBillingIdOfInventaryListNewInventary.equals(billing)) {
                        oldBillingIdOfInventaryListNewInventary.getInventaryList().remove(inventaryListNewInventary);
                        oldBillingIdOfInventaryListNewInventary = em.merge(oldBillingIdOfInventaryListNewInventary);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = billing.getId();
                if (findBilling(id) == null) {
                    throw new NonexistentEntityException("The billing with id " + id + " no longer exists.");
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
            Billing billing;
            try {
                billing = em.getReference(Billing.class, id);
                billing.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The billing with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetailBilling> detailBillingListOrphanCheck = billing.getDetailBillingList();
            for (DetailBilling detailBillingListOrphanCheckDetailBilling : detailBillingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Billing (" + billing + ") cannot be destroyed since the DetailBilling " + detailBillingListOrphanCheckDetailBilling + " in its detailBillingList field has a non-nullable billingId field.");
            }
            List<Inventary> inventaryListOrphanCheck = billing.getInventaryList();
            for (Inventary inventaryListOrphanCheckInventary : inventaryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Billing (" + billing + ") cannot be destroyed since the Inventary " + inventaryListOrphanCheckInventary + " in its inventaryList field has a non-nullable billingId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ClientProvider clientProviderid = billing.getClientProviderid();
            if (clientProviderid != null) {
                clientProviderid.getBillingList().remove(billing);
                clientProviderid = em.merge(clientProviderid);
            }
            em.remove(billing);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Billing> findBillingEntities() {
        return findBillingEntities(true, -1, -1);
    }

    public List<Billing> findBillingEntities(int maxResults, int firstResult) {
        return findBillingEntities(false, maxResults, firstResult);
    }

    private List<Billing> findBillingEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
        em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Billing.class));
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

    public Billing findBilling(Integer id) {
//        EntityManager em = getEntityManager();
        em = super.getEmf().createEntityManager();
        try {
            return em.find(Billing.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillingCount() {
//        EntityManager em = getEntityManager();
        em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Billing> rt = cq.from(Billing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Billing> namedQuery(String query, Map<String, Object> filters) {
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

    public List<Billing> findByDayAndCollector(Date date, Users user ) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            Query q = em.createQuery("Select b from Billing b where b.emissiondate >=:fecha and b.user=:user and b.state=:state order by b.emissiondate desc");
            q.setParameter("fecha", date);
            q.setParameter("user", user);
            q.setParameter("state", "Pagada");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    
    public List<Billing> findByDayAndCollector2(Date date, Users user ) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            Query q = em.createQuery("Select b from Billing b where b.emissiondate >=:fecha and b.user=:user  order by b.emissiondate desc");
            q.setParameter("fecha", date);
            q.setParameter("user", user); 
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void createBilling(Billing entity) {
        EntityManager entitymanager;
        try {
            entitymanager = super.getEmf().createEntityManager();
            entitymanager.getTransaction().begin();
            entitymanager.persist(entity);
            entitymanager.getTransaction().commit();
            entitymanager.close();
        } catch (Exception e) {
            System.out.println("ERROR >>>");
        }
    }

    public void updateBilling(Billing entity) {
        EntityManager em = super.getEmf().createEntityManager();
        em.merge(entity);
    }

}
