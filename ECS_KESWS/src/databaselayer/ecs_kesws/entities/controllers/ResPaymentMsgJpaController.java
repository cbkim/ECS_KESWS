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
import databaselayer.ecs_kesws.entities.TransactionLogs;
import java.util.HashSet;
import java.util.Set;
import databaselayer.ecs_kesws.entities.RecPaymentMsg;
import databaselayer.ecs_kesws.entities.RecErrorMsg;
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
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
public class ResPaymentMsgJpaController implements Serializable {

    public ResPaymentMsgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResPaymentMsg resPaymentMsg) {
        if (resPaymentMsg.getTransactionLogses() == null) {
            resPaymentMsg.setTransactionLogses(new HashSet<TransactionLogs>());
        }
        if (resPaymentMsg.getRecPaymentMsgs() == null) {
            resPaymentMsg.setRecPaymentMsgs(new HashSet<RecPaymentMsg>());
        }
        if (resPaymentMsg.getRecErrorMsgs() == null) {
            resPaymentMsg.setRecErrorMsgs(new HashSet<RecErrorMsg>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecCdFileMsg recCdFileMsg = resPaymentMsg.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg = em.getReference(recCdFileMsg.getClass(), recCdFileMsg.getRecCdFileId());
                resPaymentMsg.setRecCdFileMsg(recCdFileMsg);
            }
            Set<TransactionLogs> attachedTransactionLogses = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesTransactionLogsToAttach : resPaymentMsg.getTransactionLogses()) {
                transactionLogsesTransactionLogsToAttach = em.getReference(transactionLogsesTransactionLogsToAttach.getClass(), transactionLogsesTransactionLogsToAttach.getLogId());
                attachedTransactionLogses.add(transactionLogsesTransactionLogsToAttach);
            }
            resPaymentMsg.setTransactionLogses(attachedTransactionLogses);
            Set<RecPaymentMsg> attachedRecPaymentMsgs = new HashSet<RecPaymentMsg>();
            for (RecPaymentMsg recPaymentMsgsRecPaymentMsgToAttach : resPaymentMsg.getRecPaymentMsgs()) {
                recPaymentMsgsRecPaymentMsgToAttach = em.getReference(recPaymentMsgsRecPaymentMsgToAttach.getClass(), recPaymentMsgsRecPaymentMsgToAttach.getReceivedPaymentMsgId());
                attachedRecPaymentMsgs.add(recPaymentMsgsRecPaymentMsgToAttach);
            }
            resPaymentMsg.setRecPaymentMsgs(attachedRecPaymentMsgs);
            Set<RecErrorMsg> attachedRecErrorMsgs = new HashSet<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgsRecErrorMsgToAttach : resPaymentMsg.getRecErrorMsgs()) {
                recErrorMsgsRecErrorMsgToAttach = em.getReference(recErrorMsgsRecErrorMsgToAttach.getClass(), recErrorMsgsRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgs.add(recErrorMsgsRecErrorMsgToAttach);
            }
            resPaymentMsg.setRecErrorMsgs(attachedRecErrorMsgs);
            em.persist(resPaymentMsg);
            if (recCdFileMsg != null) {
                recCdFileMsg.getResPaymentMsgs().add(resPaymentMsg);
                recCdFileMsg = em.merge(recCdFileMsg);
            }
            for (TransactionLogs transactionLogsesTransactionLogs : resPaymentMsg.getTransactionLogses()) {
                ResPaymentMsg oldResPaymentMsgOfTransactionLogsesTransactionLogs = transactionLogsesTransactionLogs.getResPaymentMsg();
                transactionLogsesTransactionLogs.setResPaymentMsg(resPaymentMsg);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
                if (oldResPaymentMsgOfTransactionLogsesTransactionLogs != null) {
                    oldResPaymentMsgOfTransactionLogsesTransactionLogs.getTransactionLogses().remove(transactionLogsesTransactionLogs);
                    oldResPaymentMsgOfTransactionLogsesTransactionLogs = em.merge(oldResPaymentMsgOfTransactionLogsesTransactionLogs);
                }
            }
            for (RecPaymentMsg recPaymentMsgsRecPaymentMsg : resPaymentMsg.getRecPaymentMsgs()) {
                ResPaymentMsg oldResPaymentMsgOfRecPaymentMsgsRecPaymentMsg = recPaymentMsgsRecPaymentMsg.getResPaymentMsg();
                recPaymentMsgsRecPaymentMsg.setResPaymentMsg(resPaymentMsg);
                recPaymentMsgsRecPaymentMsg = em.merge(recPaymentMsgsRecPaymentMsg);
                if (oldResPaymentMsgOfRecPaymentMsgsRecPaymentMsg != null) {
                    oldResPaymentMsgOfRecPaymentMsgsRecPaymentMsg.getRecPaymentMsgs().remove(recPaymentMsgsRecPaymentMsg);
                    oldResPaymentMsgOfRecPaymentMsgsRecPaymentMsg = em.merge(oldResPaymentMsgOfRecPaymentMsgsRecPaymentMsg);
                }
            }
            for (RecErrorMsg recErrorMsgsRecErrorMsg : resPaymentMsg.getRecErrorMsgs()) {
                ResPaymentMsg oldResPaymentMsgOfRecErrorMsgsRecErrorMsg = recErrorMsgsRecErrorMsg.getResPaymentMsg();
                recErrorMsgsRecErrorMsg.setResPaymentMsg(resPaymentMsg);
                recErrorMsgsRecErrorMsg = em.merge(recErrorMsgsRecErrorMsg);
                if (oldResPaymentMsgOfRecErrorMsgsRecErrorMsg != null) {
                    oldResPaymentMsgOfRecErrorMsgsRecErrorMsg.getRecErrorMsgs().remove(recErrorMsgsRecErrorMsg);
                    oldResPaymentMsgOfRecErrorMsgsRecErrorMsg = em.merge(oldResPaymentMsgOfRecErrorMsgsRecErrorMsg);
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
            RecCdFileMsg recCdFileMsgOld = persistentResPaymentMsg.getRecCdFileMsg();
            RecCdFileMsg recCdFileMsgNew = resPaymentMsg.getRecCdFileMsg();
            Set<TransactionLogs> transactionLogsesOld = persistentResPaymentMsg.getTransactionLogses();
            Set<TransactionLogs> transactionLogsesNew = resPaymentMsg.getTransactionLogses();
            Set<RecPaymentMsg> recPaymentMsgsOld = persistentResPaymentMsg.getRecPaymentMsgs();
            Set<RecPaymentMsg> recPaymentMsgsNew = resPaymentMsg.getRecPaymentMsgs();
            Set<RecErrorMsg> recErrorMsgsOld = persistentResPaymentMsg.getRecErrorMsgs();
            Set<RecErrorMsg> recErrorMsgsNew = resPaymentMsg.getRecErrorMsgs();
            List<String> illegalOrphanMessages = null;
            for (RecPaymentMsg recPaymentMsgsOldRecPaymentMsg : recPaymentMsgsOld) {
                if (!recPaymentMsgsNew.contains(recPaymentMsgsOldRecPaymentMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecPaymentMsg " + recPaymentMsgsOldRecPaymentMsg + " since its resPaymentMsg field is not nullable.");
                }
            }
            for (RecErrorMsg recErrorMsgsOldRecErrorMsg : recErrorMsgsOld) {
                if (!recErrorMsgsNew.contains(recErrorMsgsOldRecErrorMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecErrorMsg " + recErrorMsgsOldRecErrorMsg + " since its resPaymentMsg field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recCdFileMsgNew != null) {
                recCdFileMsgNew = em.getReference(recCdFileMsgNew.getClass(), recCdFileMsgNew.getRecCdFileId());
                resPaymentMsg.setRecCdFileMsg(recCdFileMsgNew);
            }
            Set<TransactionLogs> attachedTransactionLogsesNew = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesNewTransactionLogsToAttach : transactionLogsesNew) {
                transactionLogsesNewTransactionLogsToAttach = em.getReference(transactionLogsesNewTransactionLogsToAttach.getClass(), transactionLogsesNewTransactionLogsToAttach.getLogId());
                attachedTransactionLogsesNew.add(transactionLogsesNewTransactionLogsToAttach);
            }
            transactionLogsesNew = attachedTransactionLogsesNew;
            resPaymentMsg.setTransactionLogses(transactionLogsesNew);
            Set<RecPaymentMsg> attachedRecPaymentMsgsNew = new HashSet<RecPaymentMsg>();
            for (RecPaymentMsg recPaymentMsgsNewRecPaymentMsgToAttach : recPaymentMsgsNew) {
                recPaymentMsgsNewRecPaymentMsgToAttach = em.getReference(recPaymentMsgsNewRecPaymentMsgToAttach.getClass(), recPaymentMsgsNewRecPaymentMsgToAttach.getReceivedPaymentMsgId());
                attachedRecPaymentMsgsNew.add(recPaymentMsgsNewRecPaymentMsgToAttach);
            }
            recPaymentMsgsNew = attachedRecPaymentMsgsNew;
            resPaymentMsg.setRecPaymentMsgs(recPaymentMsgsNew);
            Set<RecErrorMsg> attachedRecErrorMsgsNew = new HashSet<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgsNewRecErrorMsgToAttach : recErrorMsgsNew) {
                recErrorMsgsNewRecErrorMsgToAttach = em.getReference(recErrorMsgsNewRecErrorMsgToAttach.getClass(), recErrorMsgsNewRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgsNew.add(recErrorMsgsNewRecErrorMsgToAttach);
            }
            recErrorMsgsNew = attachedRecErrorMsgsNew;
            resPaymentMsg.setRecErrorMsgs(recErrorMsgsNew);
            resPaymentMsg = em.merge(resPaymentMsg);
            if (recCdFileMsgOld != null && !recCdFileMsgOld.equals(recCdFileMsgNew)) {
                recCdFileMsgOld.getResPaymentMsgs().remove(resPaymentMsg);
                recCdFileMsgOld = em.merge(recCdFileMsgOld);
            }
            if (recCdFileMsgNew != null && !recCdFileMsgNew.equals(recCdFileMsgOld)) {
                recCdFileMsgNew.getResPaymentMsgs().add(resPaymentMsg);
                recCdFileMsgNew = em.merge(recCdFileMsgNew);
            }
            for (TransactionLogs transactionLogsesOldTransactionLogs : transactionLogsesOld) {
                if (!transactionLogsesNew.contains(transactionLogsesOldTransactionLogs)) {
                    transactionLogsesOldTransactionLogs.setResPaymentMsg(null);
                    transactionLogsesOldTransactionLogs = em.merge(transactionLogsesOldTransactionLogs);
                }
            }
            for (TransactionLogs transactionLogsesNewTransactionLogs : transactionLogsesNew) {
                if (!transactionLogsesOld.contains(transactionLogsesNewTransactionLogs)) {
                    ResPaymentMsg oldResPaymentMsgOfTransactionLogsesNewTransactionLogs = transactionLogsesNewTransactionLogs.getResPaymentMsg();
                    transactionLogsesNewTransactionLogs.setResPaymentMsg(resPaymentMsg);
                    transactionLogsesNewTransactionLogs = em.merge(transactionLogsesNewTransactionLogs);
                    if (oldResPaymentMsgOfTransactionLogsesNewTransactionLogs != null && !oldResPaymentMsgOfTransactionLogsesNewTransactionLogs.equals(resPaymentMsg)) {
                        oldResPaymentMsgOfTransactionLogsesNewTransactionLogs.getTransactionLogses().remove(transactionLogsesNewTransactionLogs);
                        oldResPaymentMsgOfTransactionLogsesNewTransactionLogs = em.merge(oldResPaymentMsgOfTransactionLogsesNewTransactionLogs);
                    }
                }
            }
            for (RecPaymentMsg recPaymentMsgsNewRecPaymentMsg : recPaymentMsgsNew) {
                if (!recPaymentMsgsOld.contains(recPaymentMsgsNewRecPaymentMsg)) {
                    ResPaymentMsg oldResPaymentMsgOfRecPaymentMsgsNewRecPaymentMsg = recPaymentMsgsNewRecPaymentMsg.getResPaymentMsg();
                    recPaymentMsgsNewRecPaymentMsg.setResPaymentMsg(resPaymentMsg);
                    recPaymentMsgsNewRecPaymentMsg = em.merge(recPaymentMsgsNewRecPaymentMsg);
                    if (oldResPaymentMsgOfRecPaymentMsgsNewRecPaymentMsg != null && !oldResPaymentMsgOfRecPaymentMsgsNewRecPaymentMsg.equals(resPaymentMsg)) {
                        oldResPaymentMsgOfRecPaymentMsgsNewRecPaymentMsg.getRecPaymentMsgs().remove(recPaymentMsgsNewRecPaymentMsg);
                        oldResPaymentMsgOfRecPaymentMsgsNewRecPaymentMsg = em.merge(oldResPaymentMsgOfRecPaymentMsgsNewRecPaymentMsg);
                    }
                }
            }
            for (RecErrorMsg recErrorMsgsNewRecErrorMsg : recErrorMsgsNew) {
                if (!recErrorMsgsOld.contains(recErrorMsgsNewRecErrorMsg)) {
                    ResPaymentMsg oldResPaymentMsgOfRecErrorMsgsNewRecErrorMsg = recErrorMsgsNewRecErrorMsg.getResPaymentMsg();
                    recErrorMsgsNewRecErrorMsg.setResPaymentMsg(resPaymentMsg);
                    recErrorMsgsNewRecErrorMsg = em.merge(recErrorMsgsNewRecErrorMsg);
                    if (oldResPaymentMsgOfRecErrorMsgsNewRecErrorMsg != null && !oldResPaymentMsgOfRecErrorMsgsNewRecErrorMsg.equals(resPaymentMsg)) {
                        oldResPaymentMsgOfRecErrorMsgsNewRecErrorMsg.getRecErrorMsgs().remove(recErrorMsgsNewRecErrorMsg);
                        oldResPaymentMsgOfRecErrorMsgsNewRecErrorMsg = em.merge(oldResPaymentMsgOfRecErrorMsgsNewRecErrorMsg);
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
            Set<RecPaymentMsg> recPaymentMsgsOrphanCheck = resPaymentMsg.getRecPaymentMsgs();
            for (RecPaymentMsg recPaymentMsgsOrphanCheckRecPaymentMsg : recPaymentMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResPaymentMsg (" + resPaymentMsg + ") cannot be destroyed since the RecPaymentMsg " + recPaymentMsgsOrphanCheckRecPaymentMsg + " in its recPaymentMsgs field has a non-nullable resPaymentMsg field.");
            }
            Set<RecErrorMsg> recErrorMsgsOrphanCheck = resPaymentMsg.getRecErrorMsgs();
            for (RecErrorMsg recErrorMsgsOrphanCheckRecErrorMsg : recErrorMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResPaymentMsg (" + resPaymentMsg + ") cannot be destroyed since the RecErrorMsg " + recErrorMsgsOrphanCheckRecErrorMsg + " in its recErrorMsgs field has a non-nullable resPaymentMsg field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RecCdFileMsg recCdFileMsg = resPaymentMsg.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg.getResPaymentMsgs().remove(resPaymentMsg);
                recCdFileMsg = em.merge(recCdFileMsg);
            }
            Set<TransactionLogs> transactionLogses = resPaymentMsg.getTransactionLogses();
            for (TransactionLogs transactionLogsesTransactionLogs : transactionLogses) {
                transactionLogsesTransactionLogs.setResPaymentMsg(null);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
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
