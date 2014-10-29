/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import databaselayer.ecs_kesws.entities.RecPaymentMsg;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import databaselayer.ecs_kesws.entities.TransactionLogs;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kim
 */
public class RecPaymentMsgJpaController implements Serializable {

    public RecPaymentMsgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecPaymentMsg recPaymentMsg) {
        if (recPaymentMsg.getTransactionLogses() == null) {
            recPaymentMsg.setTransactionLogses(new HashSet<TransactionLogs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResPaymentMsg resPaymentMsg = recPaymentMsg.getResPaymentMsg();
            if (resPaymentMsg != null) {
                resPaymentMsg = em.getReference(resPaymentMsg.getClass(), resPaymentMsg.getPayementMsgId());
                recPaymentMsg.setResPaymentMsg(resPaymentMsg);
            }
            Set<TransactionLogs> attachedTransactionLogses = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesTransactionLogsToAttach : recPaymentMsg.getTransactionLogses()) {
                transactionLogsesTransactionLogsToAttach = em.getReference(transactionLogsesTransactionLogsToAttach.getClass(), transactionLogsesTransactionLogsToAttach.getLogId());
                attachedTransactionLogses.add(transactionLogsesTransactionLogsToAttach);
            }
            recPaymentMsg.setTransactionLogses(attachedTransactionLogses);
            em.persist(recPaymentMsg);
            if (resPaymentMsg != null) {
                resPaymentMsg.getRecPaymentMsgs().add(recPaymentMsg);
                resPaymentMsg = em.merge(resPaymentMsg);
            }
            for (TransactionLogs transactionLogsesTransactionLogs : recPaymentMsg.getTransactionLogses()) {
                RecPaymentMsg oldRecPaymentMsgOfTransactionLogsesTransactionLogs = transactionLogsesTransactionLogs.getRecPaymentMsg();
                transactionLogsesTransactionLogs.setRecPaymentMsg(recPaymentMsg);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
                if (oldRecPaymentMsgOfTransactionLogsesTransactionLogs != null) {
                    oldRecPaymentMsgOfTransactionLogsesTransactionLogs.getTransactionLogses().remove(transactionLogsesTransactionLogs);
                    oldRecPaymentMsgOfTransactionLogsesTransactionLogs = em.merge(oldRecPaymentMsgOfTransactionLogsesTransactionLogs);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecPaymentMsg recPaymentMsg) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecPaymentMsg persistentRecPaymentMsg = em.find(RecPaymentMsg.class, recPaymentMsg.getReceivedPaymentMsgId());
            ResPaymentMsg resPaymentMsgOld = persistentRecPaymentMsg.getResPaymentMsg();
            ResPaymentMsg resPaymentMsgNew = recPaymentMsg.getResPaymentMsg();
            Set<TransactionLogs> transactionLogsesOld = persistentRecPaymentMsg.getTransactionLogses();
            Set<TransactionLogs> transactionLogsesNew = recPaymentMsg.getTransactionLogses();
            if (resPaymentMsgNew != null) {
                resPaymentMsgNew = em.getReference(resPaymentMsgNew.getClass(), resPaymentMsgNew.getPayementMsgId());
                recPaymentMsg.setResPaymentMsg(resPaymentMsgNew);
            }
            Set<TransactionLogs> attachedTransactionLogsesNew = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesNewTransactionLogsToAttach : transactionLogsesNew) {
                transactionLogsesNewTransactionLogsToAttach = em.getReference(transactionLogsesNewTransactionLogsToAttach.getClass(), transactionLogsesNewTransactionLogsToAttach.getLogId());
                attachedTransactionLogsesNew.add(transactionLogsesNewTransactionLogsToAttach);
            }
            transactionLogsesNew = attachedTransactionLogsesNew;
            recPaymentMsg.setTransactionLogses(transactionLogsesNew);
            recPaymentMsg = em.merge(recPaymentMsg);
            if (resPaymentMsgOld != null && !resPaymentMsgOld.equals(resPaymentMsgNew)) {
                resPaymentMsgOld.getRecPaymentMsgs().remove(recPaymentMsg);
                resPaymentMsgOld = em.merge(resPaymentMsgOld);
            }
            if (resPaymentMsgNew != null && !resPaymentMsgNew.equals(resPaymentMsgOld)) {
                resPaymentMsgNew.getRecPaymentMsgs().add(recPaymentMsg);
                resPaymentMsgNew = em.merge(resPaymentMsgNew);
            }
            for (TransactionLogs transactionLogsesOldTransactionLogs : transactionLogsesOld) {
                if (!transactionLogsesNew.contains(transactionLogsesOldTransactionLogs)) {
                    transactionLogsesOldTransactionLogs.setRecPaymentMsg(null);
                    transactionLogsesOldTransactionLogs = em.merge(transactionLogsesOldTransactionLogs);
                }
            }
            for (TransactionLogs transactionLogsesNewTransactionLogs : transactionLogsesNew) {
                if (!transactionLogsesOld.contains(transactionLogsesNewTransactionLogs)) {
                    RecPaymentMsg oldRecPaymentMsgOfTransactionLogsesNewTransactionLogs = transactionLogsesNewTransactionLogs.getRecPaymentMsg();
                    transactionLogsesNewTransactionLogs.setRecPaymentMsg(recPaymentMsg);
                    transactionLogsesNewTransactionLogs = em.merge(transactionLogsesNewTransactionLogs);
                    if (oldRecPaymentMsgOfTransactionLogsesNewTransactionLogs != null && !oldRecPaymentMsgOfTransactionLogsesNewTransactionLogs.equals(recPaymentMsg)) {
                        oldRecPaymentMsgOfTransactionLogsesNewTransactionLogs.getTransactionLogses().remove(transactionLogsesNewTransactionLogs);
                        oldRecPaymentMsgOfTransactionLogsesNewTransactionLogs = em.merge(oldRecPaymentMsgOfTransactionLogsesNewTransactionLogs);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recPaymentMsg.getReceivedPaymentMsgId();
                if (findRecPaymentMsg(id) == null) {
                    throw new NonexistentEntityException("The recPaymentMsg with id " + id + " no longer exists.");
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
            RecPaymentMsg recPaymentMsg;
            try {
                recPaymentMsg = em.getReference(RecPaymentMsg.class, id);
                recPaymentMsg.getReceivedPaymentMsgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recPaymentMsg with id " + id + " no longer exists.", enfe);
            }
            ResPaymentMsg resPaymentMsg = recPaymentMsg.getResPaymentMsg();
            if (resPaymentMsg != null) {
                resPaymentMsg.getRecPaymentMsgs().remove(recPaymentMsg);
                resPaymentMsg = em.merge(resPaymentMsg);
            }
            Set<TransactionLogs> transactionLogses = recPaymentMsg.getTransactionLogses();
            for (TransactionLogs transactionLogsesTransactionLogs : transactionLogses) {
                transactionLogsesTransactionLogs.setRecPaymentMsg(null);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
            }
            em.remove(recPaymentMsg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecPaymentMsg> findRecPaymentMsgEntities() {
        return findRecPaymentMsgEntities(true, -1, -1);
    }

    public List<RecPaymentMsg> findRecPaymentMsgEntities(int maxResults, int firstResult) {
        return findRecPaymentMsgEntities(false, maxResults, firstResult);
    }

    private List<RecPaymentMsg> findRecPaymentMsgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecPaymentMsg.class));
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

    public RecPaymentMsg findRecPaymentMsg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecPaymentMsg.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecPaymentMsgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecPaymentMsg> rt = cq.from(RecPaymentMsg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
