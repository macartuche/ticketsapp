/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import entities.Configurations;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
public class ConfigurationsJpaController extends EntityManagerProj implements Serializable {

    public ConfigurationsJpaController() {
        super();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Configurations configurations) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            em.persist(configurations);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConfigurations(configurations.getId()) != null) {
                throw new PreexistingEntityException("Configurations " + configurations + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Configurations configurations) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            configurations = em.merge(configurations);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = configurations.getId();
                if (findConfigurations(id) == null) {
                    throw new NonexistentEntityException("The configurations with id " + id + " no longer exists.");
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
            Configurations configurations;
            try {
                configurations = em.getReference(Configurations.class, id);
                configurations.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configurations with id " + id + " no longer exists.", enfe);
            }
            em.remove(configurations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Configurations> findConfigurationsEntities() {
        return findConfigurationsEntities(true, -1, -1);
    }

    public List<Configurations> findConfigurationsEntities(int maxResults, int firstResult) {
        return findConfigurationsEntities(false, maxResults, firstResult);
    }

    private List<Configurations> findConfigurationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Configurations.class));
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

    public Configurations findConfigurations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Configurations.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigurationsCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Configurations> rt = cq.from(Configurations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Configurations> namedQuery(String query, Map<String, Object> filters) {
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
