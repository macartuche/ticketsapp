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
import entities.Person;
import entities.Billing;
import entities.ClientProvider;
import entities.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author macbookpro
 */
public class ClientProviderJpaController extends EntityManagerProj implements Serializable {

    public ClientProviderJpaController() {
        super();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientProvider clientProvider) {
        if (clientProvider.getBillingList() == null) {
            clientProvider.setBillingList(new ArrayList<Billing>());
        }
        EntityManager em = null;
        try {
//            em = super.getEmf().createEntityManager
            em = super.getEm();
            em.getTransaction().begin();
            Person personId = clientProvider.getPersonId();
            if (personId != null) {
                personId = em.getReference(personId.getClass(), personId.getId());
                clientProvider.setPersonId(personId);
            }
            List<Billing> attachedBillingList = new ArrayList<Billing>();
            for (Billing billingListBillingToAttach : clientProvider.getBillingList()) {
                billingListBillingToAttach = em.getReference(billingListBillingToAttach.getClass(), billingListBillingToAttach.getId());
                attachedBillingList.add(billingListBillingToAttach);
            }
            clientProvider.setBillingList(attachedBillingList);
            em.persist(clientProvider);
            if (personId != null) {
                personId.getClientProviderList().add(clientProvider);
                personId = em.merge(personId);
            }
            for (Billing billingListBilling : clientProvider.getBillingList()) {
                ClientProvider oldClientProvideridOfBillingListBilling = billingListBilling.getClientProviderid();
                billingListBilling.setClientProviderid(clientProvider);
                billingListBilling = em.merge(billingListBilling);
                if (oldClientProvideridOfBillingListBilling != null) {
                    oldClientProvideridOfBillingListBilling.getBillingList().remove(billingListBilling);
                    oldClientProvideridOfBillingListBilling = em.merge(oldClientProvideridOfBillingListBilling);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
//                em.close();
            }
        }
    }

    public void edit(ClientProvider clientProvider) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
//            em = super.getEmf().createEntityManager();
            em = super.getEm();
            em.getTransaction().begin();
            ClientProvider persistentClientProvider = em.find(ClientProvider.class, clientProvider.getId());
            Person personIdOld = persistentClientProvider.getPersonId();
            Person personIdNew = clientProvider.getPersonId();
            List<Billing> billingListOld = persistentClientProvider.getBillingList();
            List<Billing> billingListNew = clientProvider.getBillingList();
            List<String> illegalOrphanMessages = null;
            for (Billing billingListOldBilling : billingListOld) {
                if (!billingListNew.contains(billingListOldBilling)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Billing " + billingListOldBilling + " since its clientProviderid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personIdNew != null) {
                personIdNew = em.getReference(personIdNew.getClass(), personIdNew.getId());
                clientProvider.setPersonId(personIdNew);
            }
            List<Billing> attachedBillingListNew = new ArrayList<Billing>();
            for (Billing billingListNewBillingToAttach : billingListNew) {
                billingListNewBillingToAttach = em.getReference(billingListNewBillingToAttach.getClass(), billingListNewBillingToAttach.getId());
                attachedBillingListNew.add(billingListNewBillingToAttach);
            }
            billingListNew = attachedBillingListNew;
            clientProvider.setBillingList(billingListNew);
            clientProvider = em.merge(clientProvider);
            if (personIdOld != null && !personIdOld.equals(personIdNew)) {
                personIdOld.getClientProviderList().remove(clientProvider);
                personIdOld = em.merge(personIdOld);
            }
            if (personIdNew != null && !personIdNew.equals(personIdOld)) {
                personIdNew.getClientProviderList().add(clientProvider);
                personIdNew = em.merge(personIdNew);
            }
            for (Billing billingListNewBilling : billingListNew) {
                if (!billingListOld.contains(billingListNewBilling)) {
                    ClientProvider oldClientProvideridOfBillingListNewBilling = billingListNewBilling.getClientProviderid();
                    billingListNewBilling.setClientProviderid(clientProvider);
                    billingListNewBilling = em.merge(billingListNewBilling);
                    if (oldClientProvideridOfBillingListNewBilling != null && !oldClientProvideridOfBillingListNewBilling.equals(clientProvider)) {
                        oldClientProvideridOfBillingListNewBilling.getBillingList().remove(billingListNewBilling);
                        oldClientProvideridOfBillingListNewBilling = em.merge(oldClientProvideridOfBillingListNewBilling);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientProvider.getId();
                if (findClientProvider(id) == null) {
                    throw new NonexistentEntityException("The clientProvider with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
//                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
//            em = super.getEmf().createEntityManager();
            em = super.getEm();
            em.getTransaction().begin();
            ClientProvider clientProvider;
            try {
                clientProvider = em.getReference(ClientProvider.class, id);
                clientProvider.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientProvider with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Billing> billingListOrphanCheck = clientProvider.getBillingList();
            for (Billing billingListOrphanCheckBilling : billingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClientProvider (" + clientProvider + ") cannot be destroyed since the Billing " + billingListOrphanCheckBilling + " in its billingList field has a non-nullable clientProviderid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Person personId = clientProvider.getPersonId();
            if (personId != null) {
                personId.getClientProviderList().remove(clientProvider);
                personId = em.merge(personId);
            }
            em.remove(clientProvider);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
//                em.close();
            }
        }
    }

    public List<ClientProvider> findClientProviderEntities() {
        return findClientProviderEntities(true, -1, -1);
    }

    public List<ClientProvider> findClientProviderEntities(int maxResults, int firstResult) {
        return findClientProviderEntities(false, maxResults, firstResult);
    }

    private List<ClientProvider> findClientProviderEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = super.getEmf().createEntityManager();
        em = super.getEm();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientProvider.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
//            em.close();
        }
    }

    public ClientProvider findClientProvider(Integer id) {
//        EntityManager em = super.getEmf().createEntityManager();
        em = super.getEm();
        try {
            return em.find(ClientProvider.class, id);
        } finally {
//            em.close();
        }
    }

    public int getClientProviderCount() {
//        EntityManager em = super.getEmf().createEntityManager();
        em = super.getEm();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientProvider> rt = cq.from(ClientProvider.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
//            em.close();
        }
    }

    public List<ClientProvider> getClients(String query, Map<String, Object> filters) {
//        EntityManager em = super.getEmf().createEntityManager();
        em = super.getEm();
        try {

            Query q = em.createNamedQuery(query);
            for (Map.Entry<String, Object> entrySet : filters.entrySet()) {
                String key = entrySet.getKey();
                Object value = entrySet.getValue();
                q.setParameter(key, value);
            }
            return q.getResultList();
        } finally {
//            em.close();
        }

    }

    public List<ClientProvider> namedQuery(String query, Map<String, Object> filters) {
//        EntityManager em = super.getEmf().createEntityManager();
         em = super.getEm();
        try {

            Query q = em.createNamedQuery(query);
            for (Map.Entry<String, Object> entrySet : filters.entrySet()) {
                String key = entrySet.getKey();
                Object value = entrySet.getValue();
                q.setParameter(key, value);
            }
            return q.getResultList();
        } finally {
//            em.close();
        }

    }

}
