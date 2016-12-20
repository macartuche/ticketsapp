/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import entities.Contracts;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.UserContract;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author macbookpro
 */
public class ContractsJpaController2 implements Serializable {

    public ContractsJpaController2(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contracts contracts) {
        if (contracts.getUsercontractList() == null) {
            contracts.setUsercontractList(new ArrayList<UserContract>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UserContract> attachedUsercontractList = new ArrayList<UserContract>();
            for (UserContract usercontractListUserContractToAttach : contracts.getUsercontractList()) {
                usercontractListUserContractToAttach = em.getReference(usercontractListUserContractToAttach.getClass(), usercontractListUserContractToAttach.getId());
                attachedUsercontractList.add(usercontractListUserContractToAttach);
            }
            contracts.setUsercontractList(attachedUsercontractList);
            em.persist(contracts);
            for (UserContract usercontractListUserContract : contracts.getUsercontractList()) {
                Contracts oldContractsOfUsercontractListUserContract = usercontractListUserContract.getContracts();
                usercontractListUserContract.setContracts(contracts);
                usercontractListUserContract = em.merge(usercontractListUserContract);
                if (oldContractsOfUsercontractListUserContract != null) {
                    oldContractsOfUsercontractListUserContract.getUsercontractList().remove(usercontractListUserContract);
                    oldContractsOfUsercontractListUserContract = em.merge(oldContractsOfUsercontractListUserContract);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contracts contracts) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contracts persistentContracts = em.find(Contracts.class, contracts.getId());
            List<UserContract> usercontractListOld = persistentContracts.getUsercontractList();
            List<UserContract> usercontractListNew = contracts.getUsercontractList();
            List<String> illegalOrphanMessages = null;
            for (UserContract usercontractListOldUserContract : usercontractListOld) {
                if (!usercontractListNew.contains(usercontractListOldUserContract)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserContract " + usercontractListOldUserContract + " since its contracts field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<UserContract> attachedUsercontractListNew = new ArrayList<UserContract>();
            for (UserContract usercontractListNewUserContractToAttach : usercontractListNew) {
                usercontractListNewUserContractToAttach = em.getReference(usercontractListNewUserContractToAttach.getClass(), usercontractListNewUserContractToAttach.getId());
                attachedUsercontractListNew.add(usercontractListNewUserContractToAttach);
            }
            usercontractListNew = attachedUsercontractListNew;
            contracts.setUsercontractList(usercontractListNew);
            contracts = em.merge(contracts);
            for (UserContract usercontractListNewUserContract : usercontractListNew) {
                if (!usercontractListOld.contains(usercontractListNewUserContract)) {
                    Contracts oldContractsOfUsercontractListNewUserContract = usercontractListNewUserContract.getContracts();
                    usercontractListNewUserContract.setContracts(contracts);
                    usercontractListNewUserContract = em.merge(usercontractListNewUserContract);
                    if (oldContractsOfUsercontractListNewUserContract != null && !oldContractsOfUsercontractListNewUserContract.equals(contracts)) {
                        oldContractsOfUsercontractListNewUserContract.getUsercontractList().remove(usercontractListNewUserContract);
                        oldContractsOfUsercontractListNewUserContract = em.merge(oldContractsOfUsercontractListNewUserContract);
                    }
                }
            }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<UserContract> usercontractListOrphanCheck = contracts.getUsercontractList();
            for (UserContract usercontractListOrphanCheckUserContract : usercontractListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contracts (" + contracts + ") cannot be destroyed since the UserContract " + usercontractListOrphanCheckUserContract + " in its usercontractList field has a non-nullable contracts field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
