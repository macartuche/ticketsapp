/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.ClientProvider;
import entities.Contracts;
import entities.UserContract;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author macbookpro
 */
public class UserContractJpaController extends EntityManagerProj implements Serializable {

    public UserContractJpaController() {
        super();
    } 
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserContract userContract) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientProvider client = userContract.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getId());
                userContract.setClient(client);
            }
            Contracts contracts = userContract.getContracts();
            if (contracts != null) {
                contracts = em.getReference(contracts.getClass(), contracts.getId());
                userContract.setContracts(contracts);
            }
            em.persist(userContract);
            if (client != null) {
                client.getUsercontractList().add(userContract);
                client = em.merge(client);
            }
            if (contracts != null) {
                contracts.getUsercontractList().add(userContract);
                contracts = em.merge(contracts);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserContract userContract) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserContract persistentUserContract = em.find(UserContract.class, userContract.getId());
            ClientProvider clientOld = persistentUserContract.getClient();
            ClientProvider clientNew = userContract.getClient();
            Contracts contractsOld = persistentUserContract.getContracts();
            Contracts contractsNew = userContract.getContracts();
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getId());
                userContract.setClient(clientNew);
            }
            if (contractsNew != null) {
                contractsNew = em.getReference(contractsNew.getClass(), contractsNew.getId());
                userContract.setContracts(contractsNew);
            }
            userContract = em.merge(userContract);
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.getUsercontractList().remove(userContract);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.getUsercontractList().add(userContract);
                clientNew = em.merge(clientNew);
            }
            if (contractsOld != null && !contractsOld.equals(contractsNew)) {
                contractsOld.getUsercontractList().remove(userContract);
                contractsOld = em.merge(contractsOld);
            }
            if (contractsNew != null && !contractsNew.equals(contractsOld)) {
                contractsNew.getUsercontractList().add(userContract);
                contractsNew = em.merge(contractsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userContract.getId();
                if (findUserContract(id) == null) {
                    throw new NonexistentEntityException("The userContract with id " + id + " no longer exists.");
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
            UserContract userContract;
            try {
                userContract = em.getReference(UserContract.class, id);
                userContract.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userContract with id " + id + " no longer exists.", enfe);
            }
            ClientProvider client = userContract.getClient();
            if (client != null) {
                client.getUsercontractList().remove(userContract);
                client = em.merge(client);
            }
            Contracts contracts = userContract.getContracts();
            if (contracts != null) {
                contracts.getUsercontractList().remove(userContract);
                contracts = em.merge(contracts);
            }
            em.remove(userContract);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserContract> findUserContractEntities() {
        return findUserContractEntities(true, -1, -1);
    }

    public List<UserContract> findUserContractEntities(int maxResults, int firstResult) {
        return findUserContractEntities(false, maxResults, firstResult);
    }

    private List<UserContract> findUserContractEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserContract.class));
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

    public UserContract findUserContract(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserContract.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserContractCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserContract> rt = cq.from(UserContract.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
