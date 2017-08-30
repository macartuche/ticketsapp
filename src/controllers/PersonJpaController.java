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
import entities.Users;
import java.util.ArrayList;
import java.util.List;
import entities.ClientProvider;
import entities.Person;
import javax.persistence.EntityManager;

/**
 *
 * @author macbookpro
 */
public class PersonJpaController  extends EntityManagerProj  implements Serializable {

    public PersonJpaController() {
        super();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Person person) {
        if (person.getUsersList() == null) {
            person.setUsersList(new ArrayList<Users>());
        }
        if (person.getClientProviderList() == null) {
            person.setClientProviderList(new ArrayList<ClientProvider>());
        }
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : person.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getId());
                attachedUsersList.add(usersListUsersToAttach);
            }
            person.setUsersList(attachedUsersList);
            List<ClientProvider> attachedClientProviderList = new ArrayList<ClientProvider>();
            for (ClientProvider clientProviderListClientProviderToAttach : person.getClientProviderList()) {
                clientProviderListClientProviderToAttach = em.getReference(clientProviderListClientProviderToAttach.getClass(), clientProviderListClientProviderToAttach.getId());
                attachedClientProviderList.add(clientProviderListClientProviderToAttach);
            }
            person.setClientProviderList(attachedClientProviderList);
            em.persist(person);
            for (Users usersListUsers : person.getUsersList()) {
                Person oldPersonIdOfUsersListUsers = usersListUsers.getPersonId();
                usersListUsers.setPersonId(person);
                usersListUsers = em.merge(usersListUsers);
                if (oldPersonIdOfUsersListUsers != null) {
                    oldPersonIdOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldPersonIdOfUsersListUsers = em.merge(oldPersonIdOfUsersListUsers);
                }
            }
            for (ClientProvider clientProviderListClientProvider : person.getClientProviderList()) {
                Person oldPersonIdOfClientProviderListClientProvider = clientProviderListClientProvider.getPersonId();
                clientProviderListClientProvider.setPersonId(person);
                clientProviderListClientProvider = em.merge(clientProviderListClientProvider);
                if (oldPersonIdOfClientProviderListClientProvider != null) {
                    oldPersonIdOfClientProviderListClientProvider.getClientProviderList().remove(clientProviderListClientProvider);
                    oldPersonIdOfClientProviderListClientProvider = em.merge(oldPersonIdOfClientProviderListClientProvider);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Person person) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Person persistentPerson = em.find(Person.class, person.getId());
            List<Users> usersListOld = persistentPerson.getUsersList();
            List<Users> usersListNew = person.getUsersList();
            List<ClientProvider> clientProviderListOld = persistentPerson.getClientProviderList();
            List<ClientProvider> clientProviderListNew = person.getClientProviderList();
            List<String> illegalOrphanMessages = null;
//            for (Users usersListOldUsers : usersListOld) {
//                if (!usersListNew.contains(usersListOldUsers)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Users " + usersListOldUsers + " since its personId field is not nullable.");
//                }
//            }
            for (ClientProvider clientProviderListOldClientProvider : clientProviderListOld) {
                if (!clientProviderListNew.contains(clientProviderListOldClientProvider)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientProvider " + clientProviderListOldClientProvider + " since its personId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getId());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            person.setUsersList(usersListNew);
            List<ClientProvider> attachedClientProviderListNew = new ArrayList<ClientProvider>();
            for (ClientProvider clientProviderListNewClientProviderToAttach : clientProviderListNew) {
                clientProviderListNewClientProviderToAttach = em.getReference(clientProviderListNewClientProviderToAttach.getClass(), clientProviderListNewClientProviderToAttach.getId());
                attachedClientProviderListNew.add(clientProviderListNewClientProviderToAttach);
            }
            clientProviderListNew = attachedClientProviderListNew;
            person.setClientProviderList(clientProviderListNew);
            person = em.merge(person);
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Person oldPersonIdOfUsersListNewUsers = usersListNewUsers.getPersonId();
                    usersListNewUsers.setPersonId(person);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldPersonIdOfUsersListNewUsers != null && !oldPersonIdOfUsersListNewUsers.equals(person)) {
                        oldPersonIdOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldPersonIdOfUsersListNewUsers = em.merge(oldPersonIdOfUsersListNewUsers);
                    }
                }
            }
            for (ClientProvider clientProviderListNewClientProvider : clientProviderListNew) {
                if (!clientProviderListOld.contains(clientProviderListNewClientProvider)) {
                    Person oldPersonIdOfClientProviderListNewClientProvider = clientProviderListNewClientProvider.getPersonId();
                    clientProviderListNewClientProvider.setPersonId(person);
                    clientProviderListNewClientProvider = em.merge(clientProviderListNewClientProvider);
                    if (oldPersonIdOfClientProviderListNewClientProvider != null && !oldPersonIdOfClientProviderListNewClientProvider.equals(person)) {
                        oldPersonIdOfClientProviderListNewClientProvider.getClientProviderList().remove(clientProviderListNewClientProvider);
                        oldPersonIdOfClientProviderListNewClientProvider = em.merge(oldPersonIdOfClientProviderListNewClientProvider);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = person.getId();
                if (findPerson(id) == null) {
                    throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
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
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Users> usersListOrphanCheck = person.getUsersList();
            for (Users usersListOrphanCheckUsers : usersListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Users " + usersListOrphanCheckUsers + " in its usersList field has a non-nullable personId field.");
            }
            List<ClientProvider> clientProviderListOrphanCheck = person.getClientProviderList();
            for (ClientProvider clientProviderListOrphanCheckClientProvider : clientProviderListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the ClientProvider " + clientProviderListOrphanCheckClientProvider + " in its clientProviderList field has a non-nullable personId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> findPersonEntities() {
        return findPersonEntities(true, -1, -1);
    }

    public List<Person> findPersonEntities(int maxResults, int firstResult) {
        return findPersonEntities(false, maxResults, firstResult);
    }

    private List<Person> findPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
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

    public Person findPerson(Integer id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Person> rt = cq.from(Person.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
