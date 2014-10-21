/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.RecPaymentMsg;
import java.util.ArrayList;
import java.util.Collection;
import databaselayer.ecs_kesws.entities.RecErrorMsg;
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import databaselayer.ecs_kesws.entities.TransactionLogs;
import databaselayer.ecs_kesws.entities.controllers.exceptions.IllegalOrphanException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kim
 */
public class ResPaymentMsgJpaController implements Serializable {

    public ResPaymentMsgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResPaymentMsg resPaymentMsg) {
        if (resPaymentMsg.getRecPaymentMsgCollection() == null) {
            resPaymentMsg.setRecPaymentMsgCollection(new ArrayList<RecPaymentMsg>());
        }
        if (resPaymentMsg.getRecErrorMsgCollection() == null) {
            resPaymentMsg.setRecErrorMsgCollection(new ArrayList<RecErrorMsg>());
        }
        if (resPaymentMsg.getTransactionLogsCollection() == null) {
            resPaymentMsg.setTransactionLogsCollection(new ArrayList<TransactionLogs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecCdFileMsg INBOXMSGInboxID = resPaymentMsg.getINBOXMSGInboxID();
            if (INBOXMSGInboxID != null) {
                INBOXMSGInboxID = em.getReference(INBOXMSGInboxID.getClass(), INBOXMSGInboxID.getRECCDFileID());
                resPaymentMsg.setINBOXMSGInboxID(INBOXMSGInboxID);
            }
            Collection<RecPaymentMsg> attachedRecPaymentMsgCollection = new ArrayList<RecPaymentMsg>();
            for (RecPaymentMsg recPaymentMsgCollectionRecPaymentMsgToAttach : resPaymentMsg.getRecPaymentMsgCollection()) {
                recPaymentMsgCollectionRecPaymentMsgToAttach = em.getReference(recPaymentMsgCollectionRecPaymentMsgToAttach.getClass(), recPaymentMsgCollectionRecPaymentMsgToAttach.getReceivedPaymentMsgId());
                attachedRecPaymentMsgCollection.add(recPaymentMsgCollectionRecPaymentMsgToAttach);
            }
            resPaymentMsg.setRecPaymentMsgCollection(attachedRecPaymentMsgCollection);
            Collection<RecErrorMsg> attachedRecErrorMsgCollection = new ArrayList<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgCollectionRecErrorMsgToAttach : resPaymentMsg.getRecErrorMsgCollection()) {
                recErrorMsgCollectionRecErrorMsgToAttach = em.getReference(recErrorMsgCollectionRecErrorMsgToAttach.getClass(), recErrorMsgCollectionRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgCollection.add(recErrorMsgCollectionRecErrorMsgToAttach);
            }
            resPaymentMsg.setRecErrorMsgCollection(attachedRecErrorMsgCollection);
            Collection<TransactionLogs> attachedTransactionLogsCollection = new ArrayList<TransactionLogs>();
            for (TransactionLogs transactionLogsCollectionTransactionLogsToAttach : resPaymentMsg.getTransactionLogsCollection()) {
                transactionLogsCollectionTransactionLogsToAttach = em.getReference(transactionLogsCollectionTransactionLogsToAttach.getClass(), transactionLogsCollectionTransactionLogsToAttach.getLogID());
                attachedTransactionLogsCollection.add(transactionLogsCollectionTransactionLogsToAttach);
            }
            resPaymentMsg.setTransactionLogsCollection(attachedTransactionLogsCollection);
            em.persist(resPaymentMsg);
            if (INBOXMSGInboxID != null) {
                INBOXMSGInboxID.getResPaymentMsgCollection().add(resPaymentMsg);
                INBOXMSGInboxID = em.merge(INBOXMSGInboxID);
            }
            for (RecPaymentMsg recPaymentMsgCollectionRecPaymentMsg : resPaymentMsg.getRecPaymentMsgCollection()) {
                ResPaymentMsg oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionRecPaymentMsg = recPaymentMsgCollectionRecPaymentMsg.getSentPayementMsgPayementMsgId();
                recPaymentMsgCollectionRecPaymentMsg.setSentPayementMsgPayementMsgId(resPaymentMsg);
                recPaymentMsgCollectionRecPaymentMsg = em.merge(recPaymentMsgCollectionRecPaymentMsg);
                if (oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionRecPaymentMsg != null) {
                    oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionRecPaymentMsg.getRecPaymentMsgCollection().remove(recPaymentMsgCollectionRecPaymentMsg);
                    oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionRecPaymentMsg = em.merge(oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionRecPaymentMsg);
                }
            }
            for (RecErrorMsg recErrorMsgCollectionRecErrorMsg : resPaymentMsg.getRecErrorMsgCollection()) {
                ResPaymentMsg oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionRecErrorMsg = recErrorMsgCollectionRecErrorMsg.getResPaymentMsgPayementMsgId();
                recErrorMsgCollectionRecErrorMsg.setResPaymentMsgPayementMsgId(resPaymentMsg);
                recErrorMsgCollectionRecErrorMsg = em.merge(recErrorMsgCollectionRecErrorMsg);
                if (oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionRecErrorMsg != null) {
                    oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionRecErrorMsg.getRecErrorMsgCollection().remove(recErrorMsgCollectionRecErrorMsg);
                    oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionRecErrorMsg = em.merge(oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionRecErrorMsg);
                }
            }
            for (TransactionLogs transactionLogsCollectionTransactionLogs : resPaymentMsg.getTransactionLogsCollection()) {
                ResPaymentMsg oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionTransactionLogs = transactionLogsCollectionTransactionLogs.getResPaymentMsgPayementMsgId();
                transactionLogsCollectionTransactionLogs.setResPaymentMsgPayementMsgId(resPaymentMsg);
                transactionLogsCollectionTransactionLogs = em.merge(transactionLogsCollectionTransactionLogs);
                if (oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionTransactionLogs != null) {
                    oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionTransactionLogs.getTransactionLogsCollection().remove(transactionLogsCollectionTransactionLogs);
                    oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionTransactionLogs = em.merge(oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionTransactionLogs);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResPaymentMsg resPaymentMsg) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResPaymentMsg persistentResPaymentMsg = em.find(ResPaymentMsg.class, resPaymentMsg.getPayementMsgId());
            RecCdFileMsg INBOXMSGInboxIDOld = persistentResPaymentMsg.getINBOXMSGInboxID();
            RecCdFileMsg INBOXMSGInboxIDNew = resPaymentMsg.getINBOXMSGInboxID();
            Collection<RecPaymentMsg> recPaymentMsgCollectionOld = persistentResPaymentMsg.getRecPaymentMsgCollection();
            Collection<RecPaymentMsg> recPaymentMsgCollectionNew = resPaymentMsg.getRecPaymentMsgCollection();
            Collection<RecErrorMsg> recErrorMsgCollectionOld = persistentResPaymentMsg.getRecErrorMsgCollection();
            Collection<RecErrorMsg> recErrorMsgCollectionNew = resPaymentMsg.getRecErrorMsgCollection();
            Collection<TransactionLogs> transactionLogsCollectionOld = persistentResPaymentMsg.getTransactionLogsCollection();
            Collection<TransactionLogs> transactionLogsCollectionNew = resPaymentMsg.getTransactionLogsCollection();
            List<String> illegalOrphanMessages = null;
            for (RecPaymentMsg recPaymentMsgCollectionOldRecPaymentMsg : recPaymentMsgCollectionOld) {
                if (!recPaymentMsgCollectionNew.contains(recPaymentMsgCollectionOldRecPaymentMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecPaymentMsg " + recPaymentMsgCollectionOldRecPaymentMsg + " since its sentPayementMsgPayementMsgId field is not nullable.");
                }
            }
            for (RecErrorMsg recErrorMsgCollectionOldRecErrorMsg : recErrorMsgCollectionOld) {
                if (!recErrorMsgCollectionNew.contains(recErrorMsgCollectionOldRecErrorMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecErrorMsg " + recErrorMsgCollectionOldRecErrorMsg + " since its resPaymentMsgPayementMsgId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (INBOXMSGInboxIDNew != null) {
                INBOXMSGInboxIDNew = em.getReference(INBOXMSGInboxIDNew.getClass(), INBOXMSGInboxIDNew.getRECCDFileID());
                resPaymentMsg.setINBOXMSGInboxID(INBOXMSGInboxIDNew);
            }
            Collection<RecPaymentMsg> attachedRecPaymentMsgCollectionNew = new ArrayList<RecPaymentMsg>();
            for (RecPaymentMsg recPaymentMsgCollectionNewRecPaymentMsgToAttach : recPaymentMsgCollectionNew) {
                recPaymentMsgCollectionNewRecPaymentMsgToAttach = em.getReference(recPaymentMsgCollectionNewRecPaymentMsgToAttach.getClass(), recPaymentMsgCollectionNewRecPaymentMsgToAttach.getReceivedPaymentMsgId());
                attachedRecPaymentMsgCollectionNew.add(recPaymentMsgCollectionNewRecPaymentMsgToAttach);
            }
            recPaymentMsgCollectionNew = attachedRecPaymentMsgCollectionNew;
            resPaymentMsg.setRecPaymentMsgCollection(recPaymentMsgCollectionNew);
            Collection<RecErrorMsg> attachedRecErrorMsgCollectionNew = new ArrayList<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgCollectionNewRecErrorMsgToAttach : recErrorMsgCollectionNew) {
                recErrorMsgCollectionNewRecErrorMsgToAttach = em.getReference(recErrorMsgCollectionNewRecErrorMsgToAttach.getClass(), recErrorMsgCollectionNewRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgCollectionNew.add(recErrorMsgCollectionNewRecErrorMsgToAttach);
            }
            recErrorMsgCollectionNew = attachedRecErrorMsgCollectionNew;
            resPaymentMsg.setRecErrorMsgCollection(recErrorMsgCollectionNew);
            Collection<TransactionLogs> attachedTransactionLogsCollectionNew = new ArrayList<TransactionLogs>();
            for (TransactionLogs transactionLogsCollectionNewTransactionLogsToAttach : transactionLogsCollectionNew) {
                transactionLogsCollectionNewTransactionLogsToAttach = em.getReference(transactionLogsCollectionNewTransactionLogsToAttach.getClass(), transactionLogsCollectionNewTransactionLogsToAttach.getLogID());
                attachedTransactionLogsCollectionNew.add(transactionLogsCollectionNewTransactionLogsToAttach);
            }
            transactionLogsCollectionNew = attachedTransactionLogsCollectionNew;
            resPaymentMsg.setTransactionLogsCollection(transactionLogsCollectionNew);
            resPaymentMsg = em.merge(resPaymentMsg);
            if (INBOXMSGInboxIDOld != null && !INBOXMSGInboxIDOld.equals(INBOXMSGInboxIDNew)) {
                INBOXMSGInboxIDOld.getResPaymentMsgCollection().remove(resPaymentMsg);
                INBOXMSGInboxIDOld = em.merge(INBOXMSGInboxIDOld);
            }
            if (INBOXMSGInboxIDNew != null && !INBOXMSGInboxIDNew.equals(INBOXMSGInboxIDOld)) {
                INBOXMSGInboxIDNew.getResPaymentMsgCollection().add(resPaymentMsg);
                INBOXMSGInboxIDNew = em.merge(INBOXMSGInboxIDNew);
            }
            for (RecPaymentMsg recPaymentMsgCollectionNewRecPaymentMsg : recPaymentMsgCollectionNew) {
                if (!recPaymentMsgCollectionOld.contains(recPaymentMsgCollectionNewRecPaymentMsg)) {
                    ResPaymentMsg oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionNewRecPaymentMsg = recPaymentMsgCollectionNewRecPaymentMsg.getSentPayementMsgPayementMsgId();
                    recPaymentMsgCollectionNewRecPaymentMsg.setSentPayementMsgPayementMsgId(resPaymentMsg);
                    recPaymentMsgCollectionNewRecPaymentMsg = em.merge(recPaymentMsgCollectionNewRecPaymentMsg);
                    if (oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionNewRecPaymentMsg != null && !oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionNewRecPaymentMsg.equals(resPaymentMsg)) {
                        oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionNewRecPaymentMsg.getRecPaymentMsgCollection().remove(recPaymentMsgCollectionNewRecPaymentMsg);
                        oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionNewRecPaymentMsg = em.merge(oldSentPayementMsgPayementMsgIdOfRecPaymentMsgCollectionNewRecPaymentMsg);
                    }
                }
            }
            for (RecErrorMsg recErrorMsgCollectionNewRecErrorMsg : recErrorMsgCollectionNew) {
                if (!recErrorMsgCollectionOld.contains(recErrorMsgCollectionNewRecErrorMsg)) {
                    ResPaymentMsg oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionNewRecErrorMsg = recErrorMsgCollectionNewRecErrorMsg.getResPaymentMsgPayementMsgId();
                    recErrorMsgCollectionNewRecErrorMsg.setResPaymentMsgPayementMsgId(resPaymentMsg);
                    recErrorMsgCollectionNewRecErrorMsg = em.merge(recErrorMsgCollectionNewRecErrorMsg);
                    if (oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionNewRecErrorMsg != null && !oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionNewRecErrorMsg.equals(resPaymentMsg)) {
                        oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionNewRecErrorMsg.getRecErrorMsgCollection().remove(recErrorMsgCollectionNewRecErrorMsg);
                        oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionNewRecErrorMsg = em.merge(oldResPaymentMsgPayementMsgIdOfRecErrorMsgCollectionNewRecErrorMsg);
                    }
                }
            }
            for (TransactionLogs transactionLogsCollectionOldTransactionLogs : transactionLogsCollectionOld) {
                if (!transactionLogsCollectionNew.contains(transactionLogsCollectionOldTransactionLogs)) {
                    transactionLogsCollectionOldTransactionLogs.setResPaymentMsgPayementMsgId(null);
                    transactionLogsCollectionOldTransactionLogs = em.merge(transactionLogsCollectionOldTransactionLogs);
                }
            }
            for (TransactionLogs transactionLogsCollectionNewTransactionLogs : transactionLogsCollectionNew) {
                if (!transactionLogsCollectionOld.contains(transactionLogsCollectionNewTransactionLogs)) {
                    ResPaymentMsg oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionNewTransactionLogs = transactionLogsCollectionNewTransactionLogs.getResPaymentMsgPayementMsgId();
                    transactionLogsCollectionNewTransactionLogs.setResPaymentMsgPayementMsgId(resPaymentMsg);
                    transactionLogsCollectionNewTransactionLogs = em.merge(transactionLogsCollectionNewTransactionLogs);
                    if (oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionNewTransactionLogs != null && !oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionNewTransactionLogs.equals(resPaymentMsg)) {
                        oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionNewTransactionLogs.getTransactionLogsCollection().remove(transactionLogsCollectionNewTransactionLogs);
                        oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionNewTransactionLogs = em.merge(oldResPaymentMsgPayementMsgIdOfTransactionLogsCollectionNewTransactionLogs);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = resPaymentMsg.getPayementMsgId();
                if (findResPaymentMsg(id) == null) {
                    throw new NonexistentEntityException("The resPaymentMsg with id " + id + " no longer exists.");
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
            ResPaymentMsg resPaymentMsg;
            try {
                resPaymentMsg = em.getReference(ResPaymentMsg.class, id);
                resPaymentMsg.getPayementMsgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resPaymentMsg with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RecPaymentMsg> recPaymentMsgCollectionOrphanCheck = resPaymentMsg.getRecPaymentMsgCollection();
            for (RecPaymentMsg recPaymentMsgCollectionOrphanCheckRecPaymentMsg : recPaymentMsgCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResPaymentMsg (" + resPaymentMsg + ") cannot be destroyed since the RecPaymentMsg " + recPaymentMsgCollectionOrphanCheckRecPaymentMsg + " in its recPaymentMsgCollection field has a non-nullable sentPayementMsgPayementMsgId field.");
            }
            Collection<RecErrorMsg> recErrorMsgCollectionOrphanCheck = resPaymentMsg.getRecErrorMsgCollection();
            for (RecErrorMsg recErrorMsgCollectionOrphanCheckRecErrorMsg : recErrorMsgCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResPaymentMsg (" + resPaymentMsg + ") cannot be destroyed since the RecErrorMsg " + recErrorMsgCollectionOrphanCheckRecErrorMsg + " in its recErrorMsgCollection field has a non-nullable resPaymentMsgPayementMsgId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RecCdFileMsg INBOXMSGInboxID = resPaymentMsg.getINBOXMSGInboxID();
            if (INBOXMSGInboxID != null) {
                INBOXMSGInboxID.getResPaymentMsgCollection().remove(resPaymentMsg);
                INBOXMSGInboxID = em.merge(INBOXMSGInboxID);
            }
            Collection<TransactionLogs> transactionLogsCollection = resPaymentMsg.getTransactionLogsCollection();
            for (TransactionLogs transactionLogsCollectionTransactionLogs : transactionLogsCollection) {
                transactionLogsCollectionTransactionLogs.setResPaymentMsgPayementMsgId(null);
                transactionLogsCollectionTransactionLogs = em.merge(transactionLogsCollectionTransactionLogs);
            }
            em.remove(resPaymentMsg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResPaymentMsg> findResPaymentMsgEntities() {
        return findResPaymentMsgEntities(true, -1, -1);
    }

    public List<ResPaymentMsg> findResPaymentMsgEntities(int maxResults, int firstResult) {
        return findResPaymentMsgEntities(false, maxResults, firstResult);
    }

    private List<ResPaymentMsg> findResPaymentMsgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResPaymentMsg.class));
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

    public ResPaymentMsg findResPaymentMsg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResPaymentMsg.class, id);
        } finally {
            em.close();
        }
    }

    public int getResPaymentMsgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResPaymentMsg> rt = cq.from(ResPaymentMsg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
