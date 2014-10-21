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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        if (recPaymentMsg.getTransactionLogsCollection() == null) {
            recPaymentMsg.setTransactionLogsCollection(new ArrayList<TransactionLogs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResPaymentMsg sentPayementMsgPayementMsgId = recPaymentMsg.getSentPayementMsgPayementMsgId();
            if (sentPayementMsgPayementMsgId != null) {
                sentPayementMsgPayementMsgId = em.getReference(sentPayementMsgPayementMsgId.getClass(), sentPayementMsgPayementMsgId.getPayementMsgId());
                recPaymentMsg.setSentPayementMsgPayementMsgId(sentPayementMsgPayementMsgId);
            }
            Collection<TransactionLogs> attachedTransactionLogsCollection = new ArrayList<TransactionLogs>();
            for (TransactionLogs transactionLogsCollectionTransactionLogsToAttach : recPaymentMsg.getTransactionLogsCollection()) {
                transactionLogsCollectionTransactionLogsToAttach = em.getReference(transactionLogsCollectionTransactionLogsToAttach.getClass(), transactionLogsCollectionTransactionLogsToAttach.getLogID());
                attachedTransactionLogsCollection.add(transactionLogsCollectionTransactionLogsToAttach);
            }
            recPaymentMsg.setTransactionLogsCollection(attachedTransactionLogsCollection);
            em.persist(recPaymentMsg);
            if (sentPayementMsgPayementMsgId != null) {
                sentPayementMsgPayementMsgId.getRecPaymentMsgCollection().add(recPaymentMsg);
                sentPayementMsgPayementMsgId = em.merge(sentPayementMsgPayementMsgId);
            }
            for (TransactionLogs transactionLogsCollectionTransactionLogs : recPaymentMsg.getTransactionLogsCollection()) {
                RecPaymentMsg oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionTransactionLogs = transactionLogsCollectionTransactionLogs.getRecPaymentMsgReceivedPaymentMsgId();
                transactionLogsCollectionTransactionLogs.setRecPaymentMsgReceivedPaymentMsgId(recPaymentMsg);
                transactionLogsCollectionTransactionLogs = em.merge(transactionLogsCollectionTransactionLogs);
                if (oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionTransactionLogs != null) {
                    oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionTransactionLogs.getTransactionLogsCollection().remove(transactionLogsCollectionTransactionLogs);
                    oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionTransactionLogs = em.merge(oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionTransactionLogs);
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
            ResPaymentMsg sentPayementMsgPayementMsgIdOld = persistentRecPaymentMsg.getSentPayementMsgPayementMsgId();
            ResPaymentMsg sentPayementMsgPayementMsgIdNew = recPaymentMsg.getSentPayementMsgPayementMsgId();
            Collection<TransactionLogs> transactionLogsCollectionOld = persistentRecPaymentMsg.getTransactionLogsCollection();
            Collection<TransactionLogs> transactionLogsCollectionNew = recPaymentMsg.getTransactionLogsCollection();
            if (sentPayementMsgPayementMsgIdNew != null) {
                sentPayementMsgPayementMsgIdNew = em.getReference(sentPayementMsgPayementMsgIdNew.getClass(), sentPayementMsgPayementMsgIdNew.getPayementMsgId());
                recPaymentMsg.setSentPayementMsgPayementMsgId(sentPayementMsgPayementMsgIdNew);
            }
            Collection<TransactionLogs> attachedTransactionLogsCollectionNew = new ArrayList<TransactionLogs>();
            for (TransactionLogs transactionLogsCollectionNewTransactionLogsToAttach : transactionLogsCollectionNew) {
                transactionLogsCollectionNewTransactionLogsToAttach = em.getReference(transactionLogsCollectionNewTransactionLogsToAttach.getClass(), transactionLogsCollectionNewTransactionLogsToAttach.getLogID());
                attachedTransactionLogsCollectionNew.add(transactionLogsCollectionNewTransactionLogsToAttach);
            }
            transactionLogsCollectionNew = attachedTransactionLogsCollectionNew;
            recPaymentMsg.setTransactionLogsCollection(transactionLogsCollectionNew);
            recPaymentMsg = em.merge(recPaymentMsg);
            if (sentPayementMsgPayementMsgIdOld != null && !sentPayementMsgPayementMsgIdOld.equals(sentPayementMsgPayementMsgIdNew)) {
                sentPayementMsgPayementMsgIdOld.getRecPaymentMsgCollection().remove(recPaymentMsg);
                sentPayementMsgPayementMsgIdOld = em.merge(sentPayementMsgPayementMsgIdOld);
            }
            if (sentPayementMsgPayementMsgIdNew != null && !sentPayementMsgPayementMsgIdNew.equals(sentPayementMsgPayementMsgIdOld)) {
                sentPayementMsgPayementMsgIdNew.getRecPaymentMsgCollection().add(recPaymentMsg);
                sentPayementMsgPayementMsgIdNew = em.merge(sentPayementMsgPayementMsgIdNew);
            }
            for (TransactionLogs transactionLogsCollectionOldTransactionLogs : transactionLogsCollectionOld) {
                if (!transactionLogsCollectionNew.contains(transactionLogsCollectionOldTransactionLogs)) {
                    transactionLogsCollectionOldTransactionLogs.setRecPaymentMsgReceivedPaymentMsgId(null);
                    transactionLogsCollectionOldTransactionLogs = em.merge(transactionLogsCollectionOldTransactionLogs);
                }
            }
            for (TransactionLogs transactionLogsCollectionNewTransactionLogs : transactionLogsCollectionNew) {
                if (!transactionLogsCollectionOld.contains(transactionLogsCollectionNewTransactionLogs)) {
                    RecPaymentMsg oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionNewTransactionLogs = transactionLogsCollectionNewTransactionLogs.getRecPaymentMsgReceivedPaymentMsgId();
                    transactionLogsCollectionNewTransactionLogs.setRecPaymentMsgReceivedPaymentMsgId(recPaymentMsg);
                    transactionLogsCollectionNewTransactionLogs = em.merge(transactionLogsCollectionNewTransactionLogs);
                    if (oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionNewTransactionLogs != null && !oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionNewTransactionLogs.equals(recPaymentMsg)) {
                        oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionNewTransactionLogs.getTransactionLogsCollection().remove(transactionLogsCollectionNewTransactionLogs);
                        oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionNewTransactionLogs = em.merge(oldRecPaymentMsgReceivedPaymentMsgIdOfTransactionLogsCollectionNewTransactionLogs);
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
            ResPaymentMsg sentPayementMsgPayementMsgId = recPaymentMsg.getSentPayementMsgPayementMsgId();
            if (sentPayementMsgPayementMsgId != null) {
                sentPayementMsgPayementMsgId.getRecPaymentMsgCollection().remove(recPaymentMsg);
                sentPayementMsgPayementMsgId = em.merge(sentPayementMsgPayementMsgId);
            }
            Collection<TransactionLogs> transactionLogsCollection = recPaymentMsg.getTransactionLogsCollection();
            for (TransactionLogs transactionLogsCollectionTransactionLogs : transactionLogsCollection) {
                transactionLogsCollectionTransactionLogs.setRecPaymentMsgReceivedPaymentMsgId(null);
                transactionLogsCollectionTransactionLogs = em.merge(transactionLogsCollectionTransactionLogs);
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
