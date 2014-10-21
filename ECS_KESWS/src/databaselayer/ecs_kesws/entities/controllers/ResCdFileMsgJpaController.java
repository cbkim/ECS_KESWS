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
import databaselayer.ecs_kesws.entities.MessageTypes;
import databaselayer.ecs_kesws.entities.RecErrorMsg;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
import java.util.ArrayList;
import java.util.Collection;
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
public class ResCdFileMsgJpaController implements Serializable {

    public ResCdFileMsgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResCdFileMsg resCdFileMsg) {
        if (resCdFileMsg.getRecErrorMsgCollection() == null) {
            resCdFileMsg.setRecErrorMsgCollection(new ArrayList<RecErrorMsg>());
        }
        if (resCdFileMsg.getTransactionLogsCollection() == null) {
            resCdFileMsg.setTransactionLogsCollection(new ArrayList<TransactionLogs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecCdFileMsg recCdFileMsgRecCdFileId = resCdFileMsg.getRecCdFileMsgRecCdFileId();
            if (recCdFileMsgRecCdFileId != null) {
                recCdFileMsgRecCdFileId = em.getReference(recCdFileMsgRecCdFileId.getClass(), recCdFileMsgRecCdFileId.getRECCDFileID());
                resCdFileMsg.setRecCdFileMsgRecCdFileId(recCdFileMsgRecCdFileId);
            }
            MessageTypes messageTypesMessageTypeId = resCdFileMsg.getMessageTypesMessageTypeId();
            if (messageTypesMessageTypeId != null) {
                messageTypesMessageTypeId = em.getReference(messageTypesMessageTypeId.getClass(), messageTypesMessageTypeId.getMessageTypeId());
                resCdFileMsg.setMessageTypesMessageTypeId(messageTypesMessageTypeId);
            }
            Collection<RecErrorMsg> attachedRecErrorMsgCollection = new ArrayList<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgCollectionRecErrorMsgToAttach : resCdFileMsg.getRecErrorMsgCollection()) {
                recErrorMsgCollectionRecErrorMsgToAttach = em.getReference(recErrorMsgCollectionRecErrorMsgToAttach.getClass(), recErrorMsgCollectionRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgCollection.add(recErrorMsgCollectionRecErrorMsgToAttach);
            }
            resCdFileMsg.setRecErrorMsgCollection(attachedRecErrorMsgCollection);
            Collection<TransactionLogs> attachedTransactionLogsCollection = new ArrayList<TransactionLogs>();
            for (TransactionLogs transactionLogsCollectionTransactionLogsToAttach : resCdFileMsg.getTransactionLogsCollection()) {
                transactionLogsCollectionTransactionLogsToAttach = em.getReference(transactionLogsCollectionTransactionLogsToAttach.getClass(), transactionLogsCollectionTransactionLogsToAttach.getLogID());
                attachedTransactionLogsCollection.add(transactionLogsCollectionTransactionLogsToAttach);
            }
            resCdFileMsg.setTransactionLogsCollection(attachedTransactionLogsCollection);
            em.persist(resCdFileMsg);
            if (recCdFileMsgRecCdFileId != null) {
                recCdFileMsgRecCdFileId.getResCdFileMsgCollection().add(resCdFileMsg);
                recCdFileMsgRecCdFileId = em.merge(recCdFileMsgRecCdFileId);
            }
            if (messageTypesMessageTypeId != null) {
                messageTypesMessageTypeId.getResCdFileMsgCollection().add(resCdFileMsg);
                messageTypesMessageTypeId = em.merge(messageTypesMessageTypeId);
            }
            for (RecErrorMsg recErrorMsgCollectionRecErrorMsg : resCdFileMsg.getRecErrorMsgCollection()) {
                ResCdFileMsg oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionRecErrorMsg = recErrorMsgCollectionRecErrorMsg.getResCdFileMsgResCdFileId();
                recErrorMsgCollectionRecErrorMsg.setResCdFileMsgResCdFileId(resCdFileMsg);
                recErrorMsgCollectionRecErrorMsg = em.merge(recErrorMsgCollectionRecErrorMsg);
                if (oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionRecErrorMsg != null) {
                    oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionRecErrorMsg.getRecErrorMsgCollection().remove(recErrorMsgCollectionRecErrorMsg);
                    oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionRecErrorMsg = em.merge(oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionRecErrorMsg);
                }
            }
            for (TransactionLogs transactionLogsCollectionTransactionLogs : resCdFileMsg.getTransactionLogsCollection()) {
                ResCdFileMsg oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionTransactionLogs = transactionLogsCollectionTransactionLogs.getResCdFileMsgResCdFileId();
                transactionLogsCollectionTransactionLogs.setResCdFileMsgResCdFileId(resCdFileMsg);
                transactionLogsCollectionTransactionLogs = em.merge(transactionLogsCollectionTransactionLogs);
                if (oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionTransactionLogs != null) {
                    oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionTransactionLogs.getTransactionLogsCollection().remove(transactionLogsCollectionTransactionLogs);
                    oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionTransactionLogs = em.merge(oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionTransactionLogs);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResCdFileMsg resCdFileMsg) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResCdFileMsg persistentResCdFileMsg = em.find(ResCdFileMsg.class, resCdFileMsg.getResCdFileId());
            RecCdFileMsg recCdFileMsgRecCdFileIdOld = persistentResCdFileMsg.getRecCdFileMsgRecCdFileId();
            RecCdFileMsg recCdFileMsgRecCdFileIdNew = resCdFileMsg.getRecCdFileMsgRecCdFileId();
            MessageTypes messageTypesMessageTypeIdOld = persistentResCdFileMsg.getMessageTypesMessageTypeId();
            MessageTypes messageTypesMessageTypeIdNew = resCdFileMsg.getMessageTypesMessageTypeId();
            Collection<RecErrorMsg> recErrorMsgCollectionOld = persistentResCdFileMsg.getRecErrorMsgCollection();
            Collection<RecErrorMsg> recErrorMsgCollectionNew = resCdFileMsg.getRecErrorMsgCollection();
            Collection<TransactionLogs> transactionLogsCollectionOld = persistentResCdFileMsg.getTransactionLogsCollection();
            Collection<TransactionLogs> transactionLogsCollectionNew = resCdFileMsg.getTransactionLogsCollection();
            List<String> illegalOrphanMessages = null;
            for (RecErrorMsg recErrorMsgCollectionOldRecErrorMsg : recErrorMsgCollectionOld) {
                if (!recErrorMsgCollectionNew.contains(recErrorMsgCollectionOldRecErrorMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecErrorMsg " + recErrorMsgCollectionOldRecErrorMsg + " since its resCdFileMsgResCdFileId field is not nullable.");
                }
            }
            for (TransactionLogs transactionLogsCollectionOldTransactionLogs : transactionLogsCollectionOld) {
                if (!transactionLogsCollectionNew.contains(transactionLogsCollectionOldTransactionLogs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionLogs " + transactionLogsCollectionOldTransactionLogs + " since its resCdFileMsgResCdFileId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recCdFileMsgRecCdFileIdNew != null) {
                recCdFileMsgRecCdFileIdNew = em.getReference(recCdFileMsgRecCdFileIdNew.getClass(), recCdFileMsgRecCdFileIdNew.getRECCDFileID());
                resCdFileMsg.setRecCdFileMsgRecCdFileId(recCdFileMsgRecCdFileIdNew);
            }
            if (messageTypesMessageTypeIdNew != null) {
                messageTypesMessageTypeIdNew = em.getReference(messageTypesMessageTypeIdNew.getClass(), messageTypesMessageTypeIdNew.getMessageTypeId());
                resCdFileMsg.setMessageTypesMessageTypeId(messageTypesMessageTypeIdNew);
            }
            Collection<RecErrorMsg> attachedRecErrorMsgCollectionNew = new ArrayList<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgCollectionNewRecErrorMsgToAttach : recErrorMsgCollectionNew) {
                recErrorMsgCollectionNewRecErrorMsgToAttach = em.getReference(recErrorMsgCollectionNewRecErrorMsgToAttach.getClass(), recErrorMsgCollectionNewRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgCollectionNew.add(recErrorMsgCollectionNewRecErrorMsgToAttach);
            }
            recErrorMsgCollectionNew = attachedRecErrorMsgCollectionNew;
            resCdFileMsg.setRecErrorMsgCollection(recErrorMsgCollectionNew);
            Collection<TransactionLogs> attachedTransactionLogsCollectionNew = new ArrayList<TransactionLogs>();
            for (TransactionLogs transactionLogsCollectionNewTransactionLogsToAttach : transactionLogsCollectionNew) {
                transactionLogsCollectionNewTransactionLogsToAttach = em.getReference(transactionLogsCollectionNewTransactionLogsToAttach.getClass(), transactionLogsCollectionNewTransactionLogsToAttach.getLogID());
                attachedTransactionLogsCollectionNew.add(transactionLogsCollectionNewTransactionLogsToAttach);
            }
            transactionLogsCollectionNew = attachedTransactionLogsCollectionNew;
            resCdFileMsg.setTransactionLogsCollection(transactionLogsCollectionNew);
            resCdFileMsg = em.merge(resCdFileMsg);
            if (recCdFileMsgRecCdFileIdOld != null && !recCdFileMsgRecCdFileIdOld.equals(recCdFileMsgRecCdFileIdNew)) {
                recCdFileMsgRecCdFileIdOld.getResCdFileMsgCollection().remove(resCdFileMsg);
                recCdFileMsgRecCdFileIdOld = em.merge(recCdFileMsgRecCdFileIdOld);
            }
            if (recCdFileMsgRecCdFileIdNew != null && !recCdFileMsgRecCdFileIdNew.equals(recCdFileMsgRecCdFileIdOld)) {
                recCdFileMsgRecCdFileIdNew.getResCdFileMsgCollection().add(resCdFileMsg);
                recCdFileMsgRecCdFileIdNew = em.merge(recCdFileMsgRecCdFileIdNew);
            }
            if (messageTypesMessageTypeIdOld != null && !messageTypesMessageTypeIdOld.equals(messageTypesMessageTypeIdNew)) {
                messageTypesMessageTypeIdOld.getResCdFileMsgCollection().remove(resCdFileMsg);
                messageTypesMessageTypeIdOld = em.merge(messageTypesMessageTypeIdOld);
            }
            if (messageTypesMessageTypeIdNew != null && !messageTypesMessageTypeIdNew.equals(messageTypesMessageTypeIdOld)) {
                messageTypesMessageTypeIdNew.getResCdFileMsgCollection().add(resCdFileMsg);
                messageTypesMessageTypeIdNew = em.merge(messageTypesMessageTypeIdNew);
            }
            for (RecErrorMsg recErrorMsgCollectionNewRecErrorMsg : recErrorMsgCollectionNew) {
                if (!recErrorMsgCollectionOld.contains(recErrorMsgCollectionNewRecErrorMsg)) {
                    ResCdFileMsg oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionNewRecErrorMsg = recErrorMsgCollectionNewRecErrorMsg.getResCdFileMsgResCdFileId();
                    recErrorMsgCollectionNewRecErrorMsg.setResCdFileMsgResCdFileId(resCdFileMsg);
                    recErrorMsgCollectionNewRecErrorMsg = em.merge(recErrorMsgCollectionNewRecErrorMsg);
                    if (oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionNewRecErrorMsg != null && !oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionNewRecErrorMsg.equals(resCdFileMsg)) {
                        oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionNewRecErrorMsg.getRecErrorMsgCollection().remove(recErrorMsgCollectionNewRecErrorMsg);
                        oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionNewRecErrorMsg = em.merge(oldResCdFileMsgResCdFileIdOfRecErrorMsgCollectionNewRecErrorMsg);
                    }
                }
            }
            for (TransactionLogs transactionLogsCollectionNewTransactionLogs : transactionLogsCollectionNew) {
                if (!transactionLogsCollectionOld.contains(transactionLogsCollectionNewTransactionLogs)) {
                    ResCdFileMsg oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionNewTransactionLogs = transactionLogsCollectionNewTransactionLogs.getResCdFileMsgResCdFileId();
                    transactionLogsCollectionNewTransactionLogs.setResCdFileMsgResCdFileId(resCdFileMsg);
                    transactionLogsCollectionNewTransactionLogs = em.merge(transactionLogsCollectionNewTransactionLogs);
                    if (oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionNewTransactionLogs != null && !oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionNewTransactionLogs.equals(resCdFileMsg)) {
                        oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionNewTransactionLogs.getTransactionLogsCollection().remove(transactionLogsCollectionNewTransactionLogs);
                        oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionNewTransactionLogs = em.merge(oldResCdFileMsgResCdFileIdOfTransactionLogsCollectionNewTransactionLogs);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = resCdFileMsg.getResCdFileId();
                if (findResCdFileMsg(id) == null) {
                    throw new NonexistentEntityException("The resCdFileMsg with id " + id + " no longer exists.");
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
            ResCdFileMsg resCdFileMsg;
            try {
                resCdFileMsg = em.getReference(ResCdFileMsg.class, id);
                resCdFileMsg.getResCdFileId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resCdFileMsg with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RecErrorMsg> recErrorMsgCollectionOrphanCheck = resCdFileMsg.getRecErrorMsgCollection();
            for (RecErrorMsg recErrorMsgCollectionOrphanCheckRecErrorMsg : recErrorMsgCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResCdFileMsg (" + resCdFileMsg + ") cannot be destroyed since the RecErrorMsg " + recErrorMsgCollectionOrphanCheckRecErrorMsg + " in its recErrorMsgCollection field has a non-nullable resCdFileMsgResCdFileId field.");
            }
            Collection<TransactionLogs> transactionLogsCollectionOrphanCheck = resCdFileMsg.getTransactionLogsCollection();
            for (TransactionLogs transactionLogsCollectionOrphanCheckTransactionLogs : transactionLogsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResCdFileMsg (" + resCdFileMsg + ") cannot be destroyed since the TransactionLogs " + transactionLogsCollectionOrphanCheckTransactionLogs + " in its transactionLogsCollection field has a non-nullable resCdFileMsgResCdFileId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RecCdFileMsg recCdFileMsgRecCdFileId = resCdFileMsg.getRecCdFileMsgRecCdFileId();
            if (recCdFileMsgRecCdFileId != null) {
                recCdFileMsgRecCdFileId.getResCdFileMsgCollection().remove(resCdFileMsg);
                recCdFileMsgRecCdFileId = em.merge(recCdFileMsgRecCdFileId);
            }
            MessageTypes messageTypesMessageTypeId = resCdFileMsg.getMessageTypesMessageTypeId();
            if (messageTypesMessageTypeId != null) {
                messageTypesMessageTypeId.getResCdFileMsgCollection().remove(resCdFileMsg);
                messageTypesMessageTypeId = em.merge(messageTypesMessageTypeId);
            }
            em.remove(resCdFileMsg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResCdFileMsg> findResCdFileMsgEntities() {
        return findResCdFileMsgEntities(true, -1, -1);
    }

    public List<ResCdFileMsg> findResCdFileMsgEntities(int maxResults, int firstResult) {
        return findResCdFileMsgEntities(false, maxResults, firstResult);
    }

    private List<ResCdFileMsg> findResCdFileMsgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResCdFileMsg.class));
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

    public ResCdFileMsg findResCdFileMsg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResCdFileMsg.class, id);
        } finally {
            em.close();
        }
    }

    public int getResCdFileMsgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResCdFileMsg> rt = cq.from(ResCdFileMsg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
