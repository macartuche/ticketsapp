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
import java.util.ArrayList;
import java.util.List;
import entities.UserContract;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author macbookpro
 */
public class ClientProviderJpaController1 implements Serializable {

    public ClientProviderJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientProvider clientProvider) {
        if (clientProvider.getBillingList() == null) {
            clientProvider.setBillingList(new ArrayList<Billing>());
        }
        if (clientProvider.getUsercontractList() == null) {
            clientProvider.setUsercontractList(new ArrayList<UserContract>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
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
            List<UserContract> attachedUsercontractList = new ArrayList<UserContract>();
            for (UserContract usercontractListUserContractToAttach : clientProvider.getUsercontractList()) {
                usercontractListUserContractToAttach = em.getReference(usercontractListUserContractToAttach.getClass(), usercontractListUserContractToAttach.getId());
                attachedUsercontractList.add(usercontractListUserContractToAttach);
            }
            clientProvider.setUsercontractList(attachedUsercontractList);
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
            for (UserContract usercontractListUserContract : clientProvider.getUsercontractList()) {
                ClientProvider oldClientOfUsercontractListUserContract = usercontractListUserContract.getClient();
                usercontractListUserContract.setClient(clientProvider);
                usercontractListUserContract = em.merge(usercontractListUserContract);
                if (oldClientOfUsercontractListUserContract != null) {
                    oldClientOfUsercontractListUserContract.getUsercontractList().remove(usercontractListUserContract);
                    oldClientOfUsercontractListUserContract = em.merge(oldClientOfUsercontractListUserContract);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientProvider clientProvider) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientProvider persistentClientProvider = em.find(ClientProvider.class, clientProvider.getId());
            Person personIdOld = persistentClientProvider.getPersonId();
            Person personIdNew = clientProvider.getPersonId();
            List<Billing> billingListOld = persistentClientProvider.getBillingList();
            List<Billing> billingListNew = clientProvider.getBillingList();
            List<UserContract> usercontractListOld = persistentClientProvider.getUsercontractList();
            List<UserContract> usercontractListNew = clientProvider.getUsercontractList();
            List<String> illegalOrphanMessages = null;
            for (Billing billingListOldBilling : billingListOld) {
                if (!billingListNew.contains(billingListOldBilling)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Billing " + billingListOldBilling + " since its clientProviderid field is not nullable.");
                }
            }
            for (UserContract usercontractListOldUserContract : usercontractListOld) {
                if (!usercontractListNew.contains(usercontractListOldUserContract)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserContract " + usercontractListOldUserContract + " since its client field is not nullable.");
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
            List<UserContract> attachedUsercontractListNew = new ArrayList<UserContract>();
            for (UserContract usercontractListNewUserContractToAttach : usercontractListNew) {
                usercontractListNewUserContractToAttach = em.getReference(usercontractListNewUserContractToAttach.getClass(), usercontractListNewUserContractToAttach.getId());
                attachedUsercontractListNew.add(usercontractListNewUserContractToAttach);
            }
            usercontractListNew = attachedUsercontractListNew;
            clientProvider.setUsercontractList(usercontractListNew);
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
            for (UserContract usercontractListNewUserContract : usercontractListNew) {
                if (!usercontractListOld.contains(usercontractListNewUserContract)) {
                    ClientProvider oldClientOfUsercontractListNewUserContract = usercontractListNewUserContract.getClient();
                    usercontractListNewUserContract.setClient(clientProvider);
                    usercontractListNewUserContract = em.merge(usercontractListNewUserContract);
                    if (oldClientOfUsercontractListNewUserContract != null && !oldClientOfUsercontractListNewUserContract.equals(clientProvider)) {
                        oldClientOfUsercontractListNewUserContract.getUsercontractList().remove(usercontractListNewUserContract);
                        oldClientOfUsercontractListNewUserContract = em.merge(oldClientOfUsercontractListNewUserContract);
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
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
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
            List<UserContract> usercontractListOrphanCheck = clientProvider.getUsercontractList();
            for (UserContract usercontractListOrphanCheckUserContract : usercontractListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClientProvider (" + clientProvider + ") cannot be destroyed since the UserContract " + usercontractListOrphanCheckUserContract + " in its usercontractList field has a non-nullable client field.");
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
                em.close();
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
        EntityManager em = getEntityManager();
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
            em.close();
        }
    }

    public ClientProvider findClientProvider(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientProvider.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientProviderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientProvider> rt = cq.from(ClientProvider.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
