/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import databaselayer.ecs_kesws.entities.LogTypes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import databaselayer.ecs_kesws.entities.TransactionLogs;
import databaselayer.ecs_kesws.entities.controllers.exceptions.IllegalOrphanException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.PreexistingEntityException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kim
 */
public class LogTypesJpaController implements Serializable {

    public LogTypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LogTypes logTypes) throws PreexistingEntityException, Exception {
        if (logTypes.getTransactionLogses() == null) {
            logTypes.setTransactionLogses(new HashSet<TransactionLogs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<TransactionLogs> attachedTransactionLogses = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesTransactionLogsToAttach : logTypes.getTransactionLogses()) {
                transactionLogsesTransactionLogsToAttach = em.getReference(transactionLogsesTransactionLogsToAttach.getClass(), transactionLogsesTransactionLogsToAttach.getLogId());
                attachedTransactionLogses.add(transactionLogsesTransactionLogsToAttach);
            }
            logTypes.setTransactionLogses(attachedTransactionLogses);
            em.persist(logTypes);
            for (TransactionLogs transactionLogsesTransactionLogs : logTypes.getTransactionLogses()) {
                LogTypes oldLogTypesOfTransactionLogsesTransactionLogs = transactionLogsesTransactionLogs.getLogTypes();
                transactionLogsesTransactionLogs.setLogTypes(logTypes);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
                if (oldLogTypesOfTransactionLogsesTransactionLogs != null) {
                    oldLogTypesOfTransactionLogsesTransactionLogs.getTransactionLogses().remove(transactionLogsesTransactionLogs);
                    oldLogTypesOfTransactionLogsesTransactionLogs = em.merge(oldLogTypesOfTransactionLogsesTransactionLogs);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLogTypes(logTypes.getLogIdLevel()) != null) {
                throw new PreexistingEntityException("LogTypes " + logTypes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LogTypes logTypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LogTypes persistentLogTypes = em.find(LogTypes.class, logTypes.getLogIdLevel());
            Set<TransactionLogs> transactionLogsesOld = persistentLogTypes.getTransactionLogses();
            Set<TransactionLogs> transactionLogsesNew = logTypes.getTransactionLogses();
            List<String> illegalOrphanMessages = null;
            for (TransactionLogs transactionLogsesOldTransactionLogs : transactionLogsesOld) {
                if (!transactionLogsesNew.contains(transactionLogsesOldTransactionLogs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionLogs " + transactionLogsesOldTransactionLogs + " since its logTypes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<TransactionLogs> attachedTransactionLogsesNew = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesNewTransactionLogsToAttach : transactionLogsesNew) {
                transactionLogsesNewTransactionLogsToAttach = em.getReference(transactionLogsesNewTransactionLogsToAttach.getClass(), transactionLogsesNewTransactionLogsToAttach.getLogId());
                attachedTransactionLogsesNew.add(transactionLogsesNewTransactionLogsToAttach);
            }
            transactionLogsesNew = attachedTransactionLogsesNew;
            logTypes.setTransactionLogses(transactionLogsesNew);
            logTypes = em.merge(logTypes);
            for (TransactionLogs transactionLogsesNewTransactionLogs : transactionLogsesNew) {
                if (!transactionLogsesOld.contains(transactionLogsesNewTransactionLogs)) {
                    LogTypes oldLogTypesOfTransactionLogsesNewTransactionLogs = transactionLogsesNewTransactionLogs.getLogTypes();
                    transactionLogsesNewTransactionLogs.setLogTypes(logTypes);
                    transactionLogsesNewTransactionLogs = em.merge(transactionLogsesNewTransactionLogs);
                    if (oldLogTypesOfTransactionLogsesNewTransactionLogs != null && !oldLogTypesOfTransactionLogsesNewTransactionLogs.equals(logTypes)) {
                        oldLogTypesOfTransactionLogsesNewTransactionLogs.getTransactionLogses().remove(transactionLogsesNewTransactionLogs);
                        oldLogTypesOfTransactionLogsesNewTransactionLogs = em.merge(oldLogTypesOfTransactionLogsesNewTransactionLogs);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = logTypes.getLogIdLevel();
                if (findLogTypes(id) == null) {
                    throw new NonexistentEntityException("The logTypes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LogTypes logTypes;
            try {
                logTypes = em.getReference(LogTypes.class, id);
                logTypes.getLogIdLevel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logTypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<TransactionLogs> transactionLogsesOrphanCheck = logTypes.getTransactionLogses();
            for (TransactionLogs transactionLogsesOrphanCheckTransactionLogs : transactionLogsesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LogTypes (" + logTypes + ") cannot be destroyed since the TransactionLogs " + transactionLogsesOrphanCheckTransactionLogs + " in its transactionLogses field has a non-nullable logTypes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(logTypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LogTypes> findLogTypesEntities() {
        return findLogTypesEntities(true, -1, -1);
    }

    public List<LogTypes> findLogTypesEntities(int maxResults, int firstResult) {
        return findLogTypesEntities(false, maxResults, firstResult);
    }

    private List<LogTypes> findLogTypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LogTypes.class));
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

    public LogTypes findLogTypes(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LogTypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogTypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LogTypes> rt = cq.from(LogTypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
