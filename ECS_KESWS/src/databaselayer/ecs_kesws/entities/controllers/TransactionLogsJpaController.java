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
import databaselayer.ecs_kesws.entities.LogTypes;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.RecPaymentMsg;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import databaselayer.ecs_kesws.entities.TransactionLogs;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kim
 */
public class TransactionLogsJpaController implements Serializable {

    public TransactionLogsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionLogs transactionLogs) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LogTypes logTypes = transactionLogs.getLogTypes();
            if (logTypes != null) {
                logTypes = em.getReference(logTypes.getClass(), logTypes.getLogIdLevel());
                transactionLogs.setLogTypes(logTypes);
            }
            RecCdFileMsg recCdFileMsg = transactionLogs.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg = em.getReference(recCdFileMsg.getClass(), recCdFileMsg.getRecCdFileId());
                transactionLogs.setRecCdFileMsg(recCdFileMsg);
            }
            RecPaymentMsg recPaymentMsg = transactionLogs.getRecPaymentMsg();
            if (recPaymentMsg != null) {
                recPaymentMsg = em.getReference(recPaymentMsg.getClass(), recPaymentMsg.getReceivedPaymentMsgId());
                transactionLogs.setRecPaymentMsg(recPaymentMsg);
            }
            ResCdFileMsg resCdFileMsg = transactionLogs.getResCdFileMsg();
            if (resCdFileMsg != null) {
                resCdFileMsg = em.getReference(resCdFileMsg.getClass(), resCdFileMsg.getResCdFileId());
                transactionLogs.setResCdFileMsg(resCdFileMsg);
            }
            ResPaymentMsg resPaymentMsg = transactionLogs.getResPaymentMsg();
            if (resPaymentMsg != null) {
                resPaymentMsg = em.getReference(resPaymentMsg.getClass(), resPaymentMsg.getPayementMsgId());
                transactionLogs.setResPaymentMsg(resPaymentMsg);
            }
            em.persist(transactionLogs);
            if (logTypes != null) {
                logTypes.getTransactionLogses().add(transactionLogs);
                logTypes = em.merge(logTypes);
            }
            if (recCdFileMsg != null) {
                recCdFileMsg.getTransactionLogses().add(transactionLogs);
                recCdFileMsg = em.merge(recCdFileMsg);
            }
            if (recPaymentMsg != null) {
                recPaymentMsg.getTransactionLogses().add(transactionLogs);
                recPaymentMsg = em.merge(recPaymentMsg);
            }
            if (resCdFileMsg != null) {
                resCdFileMsg.getTransactionLogses().add(transactionLogs);
                resCdFileMsg = em.merge(resCdFileMsg);
            }
            if (resPaymentMsg != null) {
                resPaymentMsg.getTransactionLogses().add(transactionLogs);
                resPaymentMsg = em.merge(resPaymentMsg);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionLogs transactionLogs) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransactionLogs persistentTransactionLogs = em.find(TransactionLogs.class, transactionLogs.getLogId());
            LogTypes logTypesOld = persistentTransactionLogs.getLogTypes();
            LogTypes logTypesNew = transactionLogs.getLogTypes();
            RecCdFileMsg recCdFileMsgOld = persistentTransactionLogs.getRecCdFileMsg();
            RecCdFileMsg recCdFileMsgNew = transactionLogs.getRecCdFileMsg();
            RecPaymentMsg recPaymentMsgOld = persistentTransactionLogs.getRecPaymentMsg();
            RecPaymentMsg recPaymentMsgNew = transactionLogs.getRecPaymentMsg();
            ResCdFileMsg resCdFileMsgOld = persistentTransactionLogs.getResCdFileMsg();
            ResCdFileMsg resCdFileMsgNew = transactionLogs.getResCdFileMsg();
            ResPaymentMsg resPaymentMsgOld = persistentTransactionLogs.getResPaymentMsg();
            ResPaymentMsg resPaymentMsgNew = transactionLogs.getResPaymentMsg();
            if (logTypesNew != null) {
                logTypesNew = em.getReference(logTypesNew.getClass(), logTypesNew.getLogIdLevel());
                transactionLogs.setLogTypes(logTypesNew);
            }
            if (recCdFileMsgNew != null) {
                recCdFileMsgNew = em.getReference(recCdFileMsgNew.getClass(), recCdFileMsgNew.getRecCdFileId());
                transactionLogs.setRecCdFileMsg(recCdFileMsgNew);
            }
            if (recPaymentMsgNew != null) {
                recPaymentMsgNew = em.getReference(recPaymentMsgNew.getClass(), recPaymentMsgNew.getReceivedPaymentMsgId());
                transactionLogs.setRecPaymentMsg(recPaymentMsgNew);
            }
            if (resCdFileMsgNew != null) {
                resCdFileMsgNew = em.getReference(resCdFileMsgNew.getClass(), resCdFileMsgNew.getResCdFileId());
                transactionLogs.setResCdFileMsg(resCdFileMsgNew);
            }
            if (resPaymentMsgNew != null) {
                resPaymentMsgNew = em.getReference(resPaymentMsgNew.getClass(), resPaymentMsgNew.getPayementMsgId());
                transactionLogs.setResPaymentMsg(resPaymentMsgNew);
            }
            transactionLogs = em.merge(transactionLogs);
            if (logTypesOld != null && !logTypesOld.equals(logTypesNew)) {
                logTypesOld.getTransactionLogses().remove(transactionLogs);
                logTypesOld = em.merge(logTypesOld);
            }
            if (logTypesNew != null && !logTypesNew.equals(logTypesOld)) {
                logTypesNew.getTransactionLogses().add(transactionLogs);
                logTypesNew = em.merge(logTypesNew);
            }
            if (recCdFileMsgOld != null && !recCdFileMsgOld.equals(recCdFileMsgNew)) {
                recCdFileMsgOld.getTransactionLogses().remove(transactionLogs);
                recCdFileMsgOld = em.merge(recCdFileMsgOld);
            }
            if (recCdFileMsgNew != null && !recCdFileMsgNew.equals(recCdFileMsgOld)) {
                recCdFileMsgNew.getTransactionLogses().add(transactionLogs);
                recCdFileMsgNew = em.merge(recCdFileMsgNew);
            }
            if (recPaymentMsgOld != null && !recPaymentMsgOld.equals(recPaymentMsgNew)) {
                recPaymentMsgOld.getTransactionLogses().remove(transactionLogs);
                recPaymentMsgOld = em.merge(recPaymentMsgOld);
            }
            if (recPaymentMsgNew != null && !recPaymentMsgNew.equals(recPaymentMsgOld)) {
                recPaymentMsgNew.getTransactionLogses().add(transactionLogs);
                recPaymentMsgNew = em.merge(recPaymentMsgNew);
            }
            if (resCdFileMsgOld != null && !resCdFileMsgOld.equals(resCdFileMsgNew)) {
                resCdFileMsgOld.getTransactionLogses().remove(transactionLogs);
                resCdFileMsgOld = em.merge(resCdFileMsgOld);
            }
            if (resCdFileMsgNew != null && !resCdFileMsgNew.equals(resCdFileMsgOld)) {
                resCdFileMsgNew.getTransactionLogses().add(transactionLogs);
                resCdFileMsgNew = em.merge(resCdFileMsgNew);
            }
            if (resPaymentMsgOld != null && !resPaymentMsgOld.equals(resPaymentMsgNew)) {
                resPaymentMsgOld.getTransactionLogses().remove(transactionLogs);
                resPaymentMsgOld = em.merge(resPaymentMsgOld);
            }
            if (resPaymentMsgNew != null && !resPaymentMsgNew.equals(resPaymentMsgOld)) {
                resPaymentMsgNew.getTransactionLogses().add(transactionLogs);
                resPaymentMsgNew = em.merge(resPaymentMsgNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transactionLogs.getLogId();
                if (findTransactionLogs(id) == null) {
                    throw new NonexistentEntityException("The transactionLogs with id " + id + " no longer exists.");
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
            TransactionLogs transactionLogs;
            try {
                transactionLogs = em.getReference(TransactionLogs.class, id);
                transactionLogs.getLogId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionLogs with id " + id + " no longer exists.", enfe);
            }
            LogTypes logTypes = transactionLogs.getLogTypes();
            if (logTypes != null) {
                logTypes.getTransactionLogses().remove(transactionLogs);
                logTypes = em.merge(logTypes);
            }
            RecCdFileMsg recCdFileMsg = transactionLogs.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg.getTransactionLogses().remove(transactionLogs);
                recCdFileMsg = em.merge(recCdFileMsg);
            }
            RecPaymentMsg recPaymentMsg = transactionLogs.getRecPaymentMsg();
            if (recPaymentMsg != null) {
                recPaymentMsg.getTransactionLogses().remove(transactionLogs);
                recPaymentMsg = em.merge(recPaymentMsg);
            }
            ResCdFileMsg resCdFileMsg = transactionLogs.getResCdFileMsg();
            if (resCdFileMsg != null) {
                resCdFileMsg.getTransactionLogses().remove(transactionLogs);
                resCdFileMsg = em.merge(resCdFileMsg);
            }
            ResPaymentMsg resPaymentMsg = transactionLogs.getResPaymentMsg();
            if (resPaymentMsg != null) {
                resPaymentMsg.getTransactionLogses().remove(transactionLogs);
                resPaymentMsg = em.merge(resPaymentMsg);
            }
            em.remove(transactionLogs);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TransactionLogs> findTransactionLogsEntities() {
        return findTransactionLogsEntities(true, -1, -1);
    }

    public List<TransactionLogs> findTransactionLogsEntities(int maxResults, int firstResult) {
        return findTransactionLogsEntities(false, maxResults, firstResult);
    }

    private List<TransactionLogs> findTransactionLogsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionLogs.class));
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

    public TransactionLogs findTransactionLogs(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionLogs.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionLogsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionLogs> rt = cq.from(TransactionLogs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
