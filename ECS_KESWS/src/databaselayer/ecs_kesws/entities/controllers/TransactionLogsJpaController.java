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
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
import databaselayer.ecs_kesws.entities.RecPaymentMsg;
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.LogTypes;
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
            ResCdFileMsg resCdFileMsgResCdFileId = transactionLogs.getResCdFileMsgResCdFileId();
            if (resCdFileMsgResCdFileId != null) {
                resCdFileMsgResCdFileId = em.getReference(resCdFileMsgResCdFileId.getClass(), resCdFileMsgResCdFileId.getResCdFileId());
                transactionLogs.setResCdFileMsgResCdFileId(resCdFileMsgResCdFileId);
            }
            RecPaymentMsg recPaymentMsgReceivedPaymentMsgId = transactionLogs.getRecPaymentMsgReceivedPaymentMsgId();
            if (recPaymentMsgReceivedPaymentMsgId != null) {
                recPaymentMsgReceivedPaymentMsgId = em.getReference(recPaymentMsgReceivedPaymentMsgId.getClass(), recPaymentMsgReceivedPaymentMsgId.getReceivedPaymentMsgId());
                transactionLogs.setRecPaymentMsgReceivedPaymentMsgId(recPaymentMsgReceivedPaymentMsgId);
            }
            ResPaymentMsg resPaymentMsgPayementMsgId = transactionLogs.getResPaymentMsgPayementMsgId();
            if (resPaymentMsgPayementMsgId != null) {
                resPaymentMsgPayementMsgId = em.getReference(resPaymentMsgPayementMsgId.getClass(), resPaymentMsgPayementMsgId.getPayementMsgId());
                transactionLogs.setResPaymentMsgPayementMsgId(resPaymentMsgPayementMsgId);
            }
            RecCdFileMsg recCdFileMsgRecCdFileId = transactionLogs.getRecCdFileMsgRecCdFileId();
            if (recCdFileMsgRecCdFileId != null) {
                recCdFileMsgRecCdFileId = em.getReference(recCdFileMsgRecCdFileId.getClass(), recCdFileMsgRecCdFileId.getRECCDFileID());
                transactionLogs.setRecCdFileMsgRecCdFileId(recCdFileMsgRecCdFileId);
            }
            LogTypes logTypesLogIdLevel = transactionLogs.getLogTypesLogIdLevel();
            if (logTypesLogIdLevel != null) {
                logTypesLogIdLevel = em.getReference(logTypesLogIdLevel.getClass(), logTypesLogIdLevel.getLogIdLevel());
                transactionLogs.setLogTypesLogIdLevel(logTypesLogIdLevel);
            }
            em.persist(transactionLogs);
            if (resCdFileMsgResCdFileId != null) {
                resCdFileMsgResCdFileId.getTransactionLogsCollection().add(transactionLogs);
                resCdFileMsgResCdFileId = em.merge(resCdFileMsgResCdFileId);
            }
            if (recPaymentMsgReceivedPaymentMsgId != null) {
                recPaymentMsgReceivedPaymentMsgId.getTransactionLogsCollection().add(transactionLogs);
                recPaymentMsgReceivedPaymentMsgId = em.merge(recPaymentMsgReceivedPaymentMsgId);
            }
            if (resPaymentMsgPayementMsgId != null) {
                resPaymentMsgPayementMsgId.getTransactionLogsCollection().add(transactionLogs);
                resPaymentMsgPayementMsgId = em.merge(resPaymentMsgPayementMsgId);
            }
            if (recCdFileMsgRecCdFileId != null) {
                recCdFileMsgRecCdFileId.getTransactionLogsCollection().add(transactionLogs);
                recCdFileMsgRecCdFileId = em.merge(recCdFileMsgRecCdFileId);
            }
            if (logTypesLogIdLevel != null) {
                logTypesLogIdLevel.getTransactionLogsCollection().add(transactionLogs);
                logTypesLogIdLevel = em.merge(logTypesLogIdLevel);
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
            TransactionLogs persistentTransactionLogs = em.find(TransactionLogs.class, transactionLogs.getLogID());
            ResCdFileMsg resCdFileMsgResCdFileIdOld = persistentTransactionLogs.getResCdFileMsgResCdFileId();
            ResCdFileMsg resCdFileMsgResCdFileIdNew = transactionLogs.getResCdFileMsgResCdFileId();
            RecPaymentMsg recPaymentMsgReceivedPaymentMsgIdOld = persistentTransactionLogs.getRecPaymentMsgReceivedPaymentMsgId();
            RecPaymentMsg recPaymentMsgReceivedPaymentMsgIdNew = transactionLogs.getRecPaymentMsgReceivedPaymentMsgId();
            ResPaymentMsg resPaymentMsgPayementMsgIdOld = persistentTransactionLogs.getResPaymentMsgPayementMsgId();
            ResPaymentMsg resPaymentMsgPayementMsgIdNew = transactionLogs.getResPaymentMsgPayementMsgId();
            RecCdFileMsg recCdFileMsgRecCdFileIdOld = persistentTransactionLogs.getRecCdFileMsgRecCdFileId();
            RecCdFileMsg recCdFileMsgRecCdFileIdNew = transactionLogs.getRecCdFileMsgRecCdFileId();
            LogTypes logTypesLogIdLevelOld = persistentTransactionLogs.getLogTypesLogIdLevel();
            LogTypes logTypesLogIdLevelNew = transactionLogs.getLogTypesLogIdLevel();
            if (resCdFileMsgResCdFileIdNew != null) {
                resCdFileMsgResCdFileIdNew = em.getReference(resCdFileMsgResCdFileIdNew.getClass(), resCdFileMsgResCdFileIdNew.getResCdFileId());
                transactionLogs.setResCdFileMsgResCdFileId(resCdFileMsgResCdFileIdNew);
            }
            if (recPaymentMsgReceivedPaymentMsgIdNew != null) {
                recPaymentMsgReceivedPaymentMsgIdNew = em.getReference(recPaymentMsgReceivedPaymentMsgIdNew.getClass(), recPaymentMsgReceivedPaymentMsgIdNew.getReceivedPaymentMsgId());
                transactionLogs.setRecPaymentMsgReceivedPaymentMsgId(recPaymentMsgReceivedPaymentMsgIdNew);
            }
            if (resPaymentMsgPayementMsgIdNew != null) {
                resPaymentMsgPayementMsgIdNew = em.getReference(resPaymentMsgPayementMsgIdNew.getClass(), resPaymentMsgPayementMsgIdNew.getPayementMsgId());
                transactionLogs.setResPaymentMsgPayementMsgId(resPaymentMsgPayementMsgIdNew);
            }
            if (recCdFileMsgRecCdFileIdNew != null) {
                recCdFileMsgRecCdFileIdNew = em.getReference(recCdFileMsgRecCdFileIdNew.getClass(), recCdFileMsgRecCdFileIdNew.getRECCDFileID());
                transactionLogs.setRecCdFileMsgRecCdFileId(recCdFileMsgRecCdFileIdNew);
            }
            if (logTypesLogIdLevelNew != null) {
                logTypesLogIdLevelNew = em.getReference(logTypesLogIdLevelNew.getClass(), logTypesLogIdLevelNew.getLogIdLevel());
                transactionLogs.setLogTypesLogIdLevel(logTypesLogIdLevelNew);
            }
            transactionLogs = em.merge(transactionLogs);
            if (resCdFileMsgResCdFileIdOld != null && !resCdFileMsgResCdFileIdOld.equals(resCdFileMsgResCdFileIdNew)) {
                resCdFileMsgResCdFileIdOld.getTransactionLogsCollection().remove(transactionLogs);
                resCdFileMsgResCdFileIdOld = em.merge(resCdFileMsgResCdFileIdOld);
            }
            if (resCdFileMsgResCdFileIdNew != null && !resCdFileMsgResCdFileIdNew.equals(resCdFileMsgResCdFileIdOld)) {
                resCdFileMsgResCdFileIdNew.getTransactionLogsCollection().add(transactionLogs);
                resCdFileMsgResCdFileIdNew = em.merge(resCdFileMsgResCdFileIdNew);
            }
            if (recPaymentMsgReceivedPaymentMsgIdOld != null && !recPaymentMsgReceivedPaymentMsgIdOld.equals(recPaymentMsgReceivedPaymentMsgIdNew)) {
                recPaymentMsgReceivedPaymentMsgIdOld.getTransactionLogsCollection().remove(transactionLogs);
                recPaymentMsgReceivedPaymentMsgIdOld = em.merge(recPaymentMsgReceivedPaymentMsgIdOld);
            }
            if (recPaymentMsgReceivedPaymentMsgIdNew != null && !recPaymentMsgReceivedPaymentMsgIdNew.equals(recPaymentMsgReceivedPaymentMsgIdOld)) {
                recPaymentMsgReceivedPaymentMsgIdNew.getTransactionLogsCollection().add(transactionLogs);
                recPaymentMsgReceivedPaymentMsgIdNew = em.merge(recPaymentMsgReceivedPaymentMsgIdNew);
            }
            if (resPaymentMsgPayementMsgIdOld != null && !resPaymentMsgPayementMsgIdOld.equals(resPaymentMsgPayementMsgIdNew)) {
                resPaymentMsgPayementMsgIdOld.getTransactionLogsCollection().remove(transactionLogs);
                resPaymentMsgPayementMsgIdOld = em.merge(resPaymentMsgPayementMsgIdOld);
            }
            if (resPaymentMsgPayementMsgIdNew != null && !resPaymentMsgPayementMsgIdNew.equals(resPaymentMsgPayementMsgIdOld)) {
                resPaymentMsgPayementMsgIdNew.getTransactionLogsCollection().add(transactionLogs);
                resPaymentMsgPayementMsgIdNew = em.merge(resPaymentMsgPayementMsgIdNew);
            }
            if (recCdFileMsgRecCdFileIdOld != null && !recCdFileMsgRecCdFileIdOld.equals(recCdFileMsgRecCdFileIdNew)) {
                recCdFileMsgRecCdFileIdOld.getTransactionLogsCollection().remove(transactionLogs);
                recCdFileMsgRecCdFileIdOld = em.merge(recCdFileMsgRecCdFileIdOld);
            }
            if (recCdFileMsgRecCdFileIdNew != null && !recCdFileMsgRecCdFileIdNew.equals(recCdFileMsgRecCdFileIdOld)) {
                recCdFileMsgRecCdFileIdNew.getTransactionLogsCollection().add(transactionLogs);
                recCdFileMsgRecCdFileIdNew = em.merge(recCdFileMsgRecCdFileIdNew);
            }
            if (logTypesLogIdLevelOld != null && !logTypesLogIdLevelOld.equals(logTypesLogIdLevelNew)) {
                logTypesLogIdLevelOld.getTransactionLogsCollection().remove(transactionLogs);
                logTypesLogIdLevelOld = em.merge(logTypesLogIdLevelOld);
            }
            if (logTypesLogIdLevelNew != null && !logTypesLogIdLevelNew.equals(logTypesLogIdLevelOld)) {
                logTypesLogIdLevelNew.getTransactionLogsCollection().add(transactionLogs);
                logTypesLogIdLevelNew = em.merge(logTypesLogIdLevelNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transactionLogs.getLogID();
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
                transactionLogs.getLogID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionLogs with id " + id + " no longer exists.", enfe);
            }
            ResCdFileMsg resCdFileMsgResCdFileId = transactionLogs.getResCdFileMsgResCdFileId();
            if (resCdFileMsgResCdFileId != null) {
                resCdFileMsgResCdFileId.getTransactionLogsCollection().remove(transactionLogs);
                resCdFileMsgResCdFileId = em.merge(resCdFileMsgResCdFileId);
            }
            RecPaymentMsg recPaymentMsgReceivedPaymentMsgId = transactionLogs.getRecPaymentMsgReceivedPaymentMsgId();
            if (recPaymentMsgReceivedPaymentMsgId != null) {
                recPaymentMsgReceivedPaymentMsgId.getTransactionLogsCollection().remove(transactionLogs);
                recPaymentMsgReceivedPaymentMsgId = em.merge(recPaymentMsgReceivedPaymentMsgId);
            }
            ResPaymentMsg resPaymentMsgPayementMsgId = transactionLogs.getResPaymentMsgPayementMsgId();
            if (resPaymentMsgPayementMsgId != null) {
                resPaymentMsgPayementMsgId.getTransactionLogsCollection().remove(transactionLogs);
                resPaymentMsgPayementMsgId = em.merge(resPaymentMsgPayementMsgId);
            }
            RecCdFileMsg recCdFileMsgRecCdFileId = transactionLogs.getRecCdFileMsgRecCdFileId();
            if (recCdFileMsgRecCdFileId != null) {
                recCdFileMsgRecCdFileId.getTransactionLogsCollection().remove(transactionLogs);
                recCdFileMsgRecCdFileId = em.merge(recCdFileMsgRecCdFileId);
            }
            LogTypes logTypesLogIdLevel = transactionLogs.getLogTypesLogIdLevel();
            if (logTypesLogIdLevel != null) {
                logTypesLogIdLevel.getTransactionLogsCollection().remove(transactionLogs);
                logTypesLogIdLevel = em.merge(logTypesLogIdLevel);
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
