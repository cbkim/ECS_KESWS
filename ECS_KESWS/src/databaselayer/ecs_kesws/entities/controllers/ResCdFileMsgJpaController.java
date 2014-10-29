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
import databaselayer.ecs_kesws.entities.MessageTypes;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.RecErrorMsg;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
import java.util.HashSet;
import java.util.Set;
import databaselayer.ecs_kesws.entities.TransactionLogs;
import databaselayer.ecs_kesws.entities.controllers.exceptions.IllegalOrphanException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
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
        if (resCdFileMsg.getRecErrorMsgs() == null) {
            resCdFileMsg.setRecErrorMsgs(new HashSet<RecErrorMsg>());
        }
        if (resCdFileMsg.getTransactionLogses() == null) {
            resCdFileMsg.setTransactionLogses(new HashSet<TransactionLogs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MessageTypes messageTypes = resCdFileMsg.getMessageTypes();
            if (messageTypes != null) {
                messageTypes = em.getReference(messageTypes.getClass(), messageTypes.getMessageTypeId());
                resCdFileMsg.setMessageTypes(messageTypes);
            }
            RecCdFileMsg recCdFileMsg = resCdFileMsg.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg = em.getReference(recCdFileMsg.getClass(), recCdFileMsg.getRecCdFileId());
                resCdFileMsg.setRecCdFileMsg(recCdFileMsg);
            }
            Set<RecErrorMsg> attachedRecErrorMsgs = new HashSet<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgsRecErrorMsgToAttach : resCdFileMsg.getRecErrorMsgs()) {
                recErrorMsgsRecErrorMsgToAttach = em.getReference(recErrorMsgsRecErrorMsgToAttach.getClass(), recErrorMsgsRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgs.add(recErrorMsgsRecErrorMsgToAttach);
            }
            resCdFileMsg.setRecErrorMsgs(attachedRecErrorMsgs);
            Set<TransactionLogs> attachedTransactionLogses = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesTransactionLogsToAttach : resCdFileMsg.getTransactionLogses()) {
                transactionLogsesTransactionLogsToAttach = em.getReference(transactionLogsesTransactionLogsToAttach.getClass(), transactionLogsesTransactionLogsToAttach.getLogId());
                attachedTransactionLogses.add(transactionLogsesTransactionLogsToAttach);
            }
            resCdFileMsg.setTransactionLogses(attachedTransactionLogses);
            em.persist(resCdFileMsg);
            if (messageTypes != null) {
                messageTypes.getResCdFileMsgs().add(resCdFileMsg);
                messageTypes = em.merge(messageTypes);
            }
            if (recCdFileMsg != null) {
                recCdFileMsg.getResCdFileMsgs().add(resCdFileMsg);
                recCdFileMsg = em.merge(recCdFileMsg);
            }
            for (RecErrorMsg recErrorMsgsRecErrorMsg : resCdFileMsg.getRecErrorMsgs()) {
                ResCdFileMsg oldResCdFileMsgOfRecErrorMsgsRecErrorMsg = recErrorMsgsRecErrorMsg.getResCdFileMsg();
                recErrorMsgsRecErrorMsg.setResCdFileMsg(resCdFileMsg);
                recErrorMsgsRecErrorMsg = em.merge(recErrorMsgsRecErrorMsg);
                if (oldResCdFileMsgOfRecErrorMsgsRecErrorMsg != null) {
                    oldResCdFileMsgOfRecErrorMsgsRecErrorMsg.getRecErrorMsgs().remove(recErrorMsgsRecErrorMsg);
                    oldResCdFileMsgOfRecErrorMsgsRecErrorMsg = em.merge(oldResCdFileMsgOfRecErrorMsgsRecErrorMsg);
                }
            }
            for (TransactionLogs transactionLogsesTransactionLogs : resCdFileMsg.getTransactionLogses()) {
                ResCdFileMsg oldResCdFileMsgOfTransactionLogsesTransactionLogs = transactionLogsesTransactionLogs.getResCdFileMsg();
                transactionLogsesTransactionLogs.setResCdFileMsg(resCdFileMsg);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
                if (oldResCdFileMsgOfTransactionLogsesTransactionLogs != null) {
                    oldResCdFileMsgOfTransactionLogsesTransactionLogs.getTransactionLogses().remove(transactionLogsesTransactionLogs);
                    oldResCdFileMsgOfTransactionLogsesTransactionLogs = em.merge(oldResCdFileMsgOfTransactionLogsesTransactionLogs);
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
            MessageTypes messageTypesOld = persistentResCdFileMsg.getMessageTypes();
            MessageTypes messageTypesNew = resCdFileMsg.getMessageTypes();
            RecCdFileMsg recCdFileMsgOld = persistentResCdFileMsg.getRecCdFileMsg();
            RecCdFileMsg recCdFileMsgNew = resCdFileMsg.getRecCdFileMsg();
            Set<RecErrorMsg> recErrorMsgsOld = persistentResCdFileMsg.getRecErrorMsgs();
            Set<RecErrorMsg> recErrorMsgsNew = resCdFileMsg.getRecErrorMsgs();
            Set<TransactionLogs> transactionLogsesOld = persistentResCdFileMsg.getTransactionLogses();
            Set<TransactionLogs> transactionLogsesNew = resCdFileMsg.getTransactionLogses();
            List<String> illegalOrphanMessages = null;
            for (RecErrorMsg recErrorMsgsOldRecErrorMsg : recErrorMsgsOld) {
                if (!recErrorMsgsNew.contains(recErrorMsgsOldRecErrorMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecErrorMsg " + recErrorMsgsOldRecErrorMsg + " since its resCdFileMsg field is not nullable.");
                }
            }
            for (TransactionLogs transactionLogsesOldTransactionLogs : transactionLogsesOld) {
                if (!transactionLogsesNew.contains(transactionLogsesOldTransactionLogs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionLogs " + transactionLogsesOldTransactionLogs + " since its resCdFileMsg field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (messageTypesNew != null) {
                messageTypesNew = em.getReference(messageTypesNew.getClass(), messageTypesNew.getMessageTypeId());
                resCdFileMsg.setMessageTypes(messageTypesNew);
            }
            if (recCdFileMsgNew != null) {
                recCdFileMsgNew = em.getReference(recCdFileMsgNew.getClass(), recCdFileMsgNew.getRecCdFileId());
                resCdFileMsg.setRecCdFileMsg(recCdFileMsgNew);
            }
            Set<RecErrorMsg> attachedRecErrorMsgsNew = new HashSet<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgsNewRecErrorMsgToAttach : recErrorMsgsNew) {
                recErrorMsgsNewRecErrorMsgToAttach = em.getReference(recErrorMsgsNewRecErrorMsgToAttach.getClass(), recErrorMsgsNewRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgsNew.add(recErrorMsgsNewRecErrorMsgToAttach);
            }
            recErrorMsgsNew = attachedRecErrorMsgsNew;
            resCdFileMsg.setRecErrorMsgs(recErrorMsgsNew);
            Set<TransactionLogs> attachedTransactionLogsesNew = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesNewTransactionLogsToAttach : transactionLogsesNew) {
                transactionLogsesNewTransactionLogsToAttach = em.getReference(transactionLogsesNewTransactionLogsToAttach.getClass(), transactionLogsesNewTransactionLogsToAttach.getLogId());
                attachedTransactionLogsesNew.add(transactionLogsesNewTransactionLogsToAttach);
            }
            transactionLogsesNew = attachedTransactionLogsesNew;
            resCdFileMsg.setTransactionLogses(transactionLogsesNew);
            resCdFileMsg = em.merge(resCdFileMsg);
            if (messageTypesOld != null && !messageTypesOld.equals(messageTypesNew)) {
                messageTypesOld.getResCdFileMsgs().remove(resCdFileMsg);
                messageTypesOld = em.merge(messageTypesOld);
            }
            if (messageTypesNew != null && !messageTypesNew.equals(messageTypesOld)) {
                messageTypesNew.getResCdFileMsgs().add(resCdFileMsg);
                messageTypesNew = em.merge(messageTypesNew);
            }
            if (recCdFileMsgOld != null && !recCdFileMsgOld.equals(recCdFileMsgNew)) {
                recCdFileMsgOld.getResCdFileMsgs().remove(resCdFileMsg);
                recCdFileMsgOld = em.merge(recCdFileMsgOld);
            }
            if (recCdFileMsgNew != null && !recCdFileMsgNew.equals(recCdFileMsgOld)) {
                recCdFileMsgNew.getResCdFileMsgs().add(resCdFileMsg);
                recCdFileMsgNew = em.merge(recCdFileMsgNew);
            }
            for (RecErrorMsg recErrorMsgsNewRecErrorMsg : recErrorMsgsNew) {
                if (!recErrorMsgsOld.contains(recErrorMsgsNewRecErrorMsg)) {
                    ResCdFileMsg oldResCdFileMsgOfRecErrorMsgsNewRecErrorMsg = recErrorMsgsNewRecErrorMsg.getResCdFileMsg();
                    recErrorMsgsNewRecErrorMsg.setResCdFileMsg(resCdFileMsg);
                    recErrorMsgsNewRecErrorMsg = em.merge(recErrorMsgsNewRecErrorMsg);
                    if (oldResCdFileMsgOfRecErrorMsgsNewRecErrorMsg != null && !oldResCdFileMsgOfRecErrorMsgsNewRecErrorMsg.equals(resCdFileMsg)) {
                        oldResCdFileMsgOfRecErrorMsgsNewRecErrorMsg.getRecErrorMsgs().remove(recErrorMsgsNewRecErrorMsg);
                        oldResCdFileMsgOfRecErrorMsgsNewRecErrorMsg = em.merge(oldResCdFileMsgOfRecErrorMsgsNewRecErrorMsg);
                    }
                }
            }
            for (TransactionLogs transactionLogsesNewTransactionLogs : transactionLogsesNew) {
                if (!transactionLogsesOld.contains(transactionLogsesNewTransactionLogs)) {
                    ResCdFileMsg oldResCdFileMsgOfTransactionLogsesNewTransactionLogs = transactionLogsesNewTransactionLogs.getResCdFileMsg();
                    transactionLogsesNewTransactionLogs.setResCdFileMsg(resCdFileMsg);
                    transactionLogsesNewTransactionLogs = em.merge(transactionLogsesNewTransactionLogs);
                    if (oldResCdFileMsgOfTransactionLogsesNewTransactionLogs != null && !oldResCdFileMsgOfTransactionLogsesNewTransactionLogs.equals(resCdFileMsg)) {
                        oldResCdFileMsgOfTransactionLogsesNewTransactionLogs.getTransactionLogses().remove(transactionLogsesNewTransactionLogs);
                        oldResCdFileMsgOfTransactionLogsesNewTransactionLogs = em.merge(oldResCdFileMsgOfTransactionLogsesNewTransactionLogs);
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
            Set<RecErrorMsg> recErrorMsgsOrphanCheck = resCdFileMsg.getRecErrorMsgs();
            for (RecErrorMsg recErrorMsgsOrphanCheckRecErrorMsg : recErrorMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResCdFileMsg (" + resCdFileMsg + ") cannot be destroyed since the RecErrorMsg " + recErrorMsgsOrphanCheckRecErrorMsg + " in its recErrorMsgs field has a non-nullable resCdFileMsg field.");
            }
            Set<TransactionLogs> transactionLogsesOrphanCheck = resCdFileMsg.getTransactionLogses();
            for (TransactionLogs transactionLogsesOrphanCheckTransactionLogs : transactionLogsesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResCdFileMsg (" + resCdFileMsg + ") cannot be destroyed since the TransactionLogs " + transactionLogsesOrphanCheckTransactionLogs + " in its transactionLogses field has a non-nullable resCdFileMsg field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            MessageTypes messageTypes = resCdFileMsg.getMessageTypes();
            if (messageTypes != null) {
                messageTypes.getResCdFileMsgs().remove(resCdFileMsg);
                messageTypes = em.merge(messageTypes);
            }
            RecCdFileMsg recCdFileMsg = resCdFileMsg.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg.getResCdFileMsgs().remove(resCdFileMsg);
                recCdFileMsg = em.merge(recCdFileMsg);
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
