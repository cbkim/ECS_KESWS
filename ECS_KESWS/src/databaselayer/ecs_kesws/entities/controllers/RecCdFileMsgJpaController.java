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
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import java.util.HashSet;
import java.util.Set;
import databaselayer.ecs_kesws.entities.CdFileDetails;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.TransactionLogs;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
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
public class RecCdFileMsgJpaController implements Serializable {

    public RecCdFileMsgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecCdFileMsg recCdFileMsg) {
        if (recCdFileMsg.getResPaymentMsgs() == null) {
            recCdFileMsg.setResPaymentMsgs(new HashSet<ResPaymentMsg>());
        }
        if (recCdFileMsg.getCdFileDetailses() == null) {
            recCdFileMsg.setCdFileDetailses(new HashSet<CdFileDetails>());
        }
        if (recCdFileMsg.getTransactionLogses() == null) {
            recCdFileMsg.setTransactionLogses(new HashSet<TransactionLogs>());
        }
        if (recCdFileMsg.getResCdFileMsgs() == null) {
            recCdFileMsg.setResCdFileMsgs(new HashSet<ResCdFileMsg>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MessageTypes messageTypes = recCdFileMsg.getMessageTypes();
            if (messageTypes != null) {
                messageTypes = em.getReference(messageTypes.getClass(), messageTypes.getMessageTypeId());
                recCdFileMsg.setMessageTypes(messageTypes);
            }
            Set<ResPaymentMsg> attachedResPaymentMsgs = new HashSet<ResPaymentMsg>();
            for (ResPaymentMsg resPaymentMsgsResPaymentMsgToAttach : recCdFileMsg.getResPaymentMsgs()) {
                resPaymentMsgsResPaymentMsgToAttach = em.getReference(resPaymentMsgsResPaymentMsgToAttach.getClass(), resPaymentMsgsResPaymentMsgToAttach.getPayementMsgId());
                attachedResPaymentMsgs.add(resPaymentMsgsResPaymentMsgToAttach);
            }
            recCdFileMsg.setResPaymentMsgs(attachedResPaymentMsgs);
            Set<CdFileDetails> attachedCdFileDetailses = new HashSet<CdFileDetails>();
            for (CdFileDetails cdFileDetailsesCdFileDetailsToAttach : recCdFileMsg.getCdFileDetailses()) {
                cdFileDetailsesCdFileDetailsToAttach = em.getReference(cdFileDetailsesCdFileDetailsToAttach.getClass(), cdFileDetailsesCdFileDetailsToAttach.getId());
                attachedCdFileDetailses.add(cdFileDetailsesCdFileDetailsToAttach);
            }
            recCdFileMsg.setCdFileDetailses(attachedCdFileDetailses);
            Set<TransactionLogs> attachedTransactionLogses = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesTransactionLogsToAttach : recCdFileMsg.getTransactionLogses()) {
                transactionLogsesTransactionLogsToAttach = em.getReference(transactionLogsesTransactionLogsToAttach.getClass(), transactionLogsesTransactionLogsToAttach.getLogId());
                attachedTransactionLogses.add(transactionLogsesTransactionLogsToAttach);
            }
            recCdFileMsg.setTransactionLogses(attachedTransactionLogses);
            Set<ResCdFileMsg> attachedResCdFileMsgs = new HashSet<ResCdFileMsg>();
            for (ResCdFileMsg resCdFileMsgsResCdFileMsgToAttach : recCdFileMsg.getResCdFileMsgs()) {
                resCdFileMsgsResCdFileMsgToAttach = em.getReference(resCdFileMsgsResCdFileMsgToAttach.getClass(), resCdFileMsgsResCdFileMsgToAttach.getResCdFileId());
                attachedResCdFileMsgs.add(resCdFileMsgsResCdFileMsgToAttach);
            }
            recCdFileMsg.setResCdFileMsgs(attachedResCdFileMsgs);
            em.persist(recCdFileMsg);
            if (messageTypes != null) {
                messageTypes.getRecCdFileMsgs().add(recCdFileMsg);
                messageTypes = em.merge(messageTypes);
            }
            for (ResPaymentMsg resPaymentMsgsResPaymentMsg : recCdFileMsg.getResPaymentMsgs()) {
                RecCdFileMsg oldRecCdFileMsgOfResPaymentMsgsResPaymentMsg = resPaymentMsgsResPaymentMsg.getRecCdFileMsg();
                resPaymentMsgsResPaymentMsg.setRecCdFileMsg(recCdFileMsg);
                resPaymentMsgsResPaymentMsg = em.merge(resPaymentMsgsResPaymentMsg);
                if (oldRecCdFileMsgOfResPaymentMsgsResPaymentMsg != null) {
                    oldRecCdFileMsgOfResPaymentMsgsResPaymentMsg.getResPaymentMsgs().remove(resPaymentMsgsResPaymentMsg);
                    oldRecCdFileMsgOfResPaymentMsgsResPaymentMsg = em.merge(oldRecCdFileMsgOfResPaymentMsgsResPaymentMsg);
                }
            }
            for (CdFileDetails cdFileDetailsesCdFileDetails : recCdFileMsg.getCdFileDetailses()) {
                RecCdFileMsg oldRecCdFileMsgOfCdFileDetailsesCdFileDetails = cdFileDetailsesCdFileDetails.getRecCdFileMsg();
                cdFileDetailsesCdFileDetails.setRecCdFileMsg(recCdFileMsg);
                cdFileDetailsesCdFileDetails = em.merge(cdFileDetailsesCdFileDetails);
                if (oldRecCdFileMsgOfCdFileDetailsesCdFileDetails != null) {
                    oldRecCdFileMsgOfCdFileDetailsesCdFileDetails.getCdFileDetailses().remove(cdFileDetailsesCdFileDetails);
                    oldRecCdFileMsgOfCdFileDetailsesCdFileDetails = em.merge(oldRecCdFileMsgOfCdFileDetailsesCdFileDetails);
                }
            }
            for (TransactionLogs transactionLogsesTransactionLogs : recCdFileMsg.getTransactionLogses()) {
                RecCdFileMsg oldRecCdFileMsgOfTransactionLogsesTransactionLogs = transactionLogsesTransactionLogs.getRecCdFileMsg();
                transactionLogsesTransactionLogs.setRecCdFileMsg(recCdFileMsg);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
                if (oldRecCdFileMsgOfTransactionLogsesTransactionLogs != null) {
                    oldRecCdFileMsgOfTransactionLogsesTransactionLogs.getTransactionLogses().remove(transactionLogsesTransactionLogs);
                    oldRecCdFileMsgOfTransactionLogsesTransactionLogs = em.merge(oldRecCdFileMsgOfTransactionLogsesTransactionLogs);
                }
            }
            for (ResCdFileMsg resCdFileMsgsResCdFileMsg : recCdFileMsg.getResCdFileMsgs()) {
                RecCdFileMsg oldRecCdFileMsgOfResCdFileMsgsResCdFileMsg = resCdFileMsgsResCdFileMsg.getRecCdFileMsg();
                resCdFileMsgsResCdFileMsg.setRecCdFileMsg(recCdFileMsg);
                resCdFileMsgsResCdFileMsg = em.merge(resCdFileMsgsResCdFileMsg);
                if (oldRecCdFileMsgOfResCdFileMsgsResCdFileMsg != null) {
                    oldRecCdFileMsgOfResCdFileMsgsResCdFileMsg.getResCdFileMsgs().remove(resCdFileMsgsResCdFileMsg);
                    oldRecCdFileMsgOfResCdFileMsgsResCdFileMsg = em.merge(oldRecCdFileMsgOfResCdFileMsgsResCdFileMsg);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecCdFileMsg recCdFileMsg) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecCdFileMsg persistentRecCdFileMsg = em.find(RecCdFileMsg.class, recCdFileMsg.getRecCdFileId());
            MessageTypes messageTypesOld = persistentRecCdFileMsg.getMessageTypes();
            MessageTypes messageTypesNew = recCdFileMsg.getMessageTypes();
            Set<ResPaymentMsg> resPaymentMsgsOld = persistentRecCdFileMsg.getResPaymentMsgs();
            Set<ResPaymentMsg> resPaymentMsgsNew = recCdFileMsg.getResPaymentMsgs();
            Set<CdFileDetails> cdFileDetailsesOld = persistentRecCdFileMsg.getCdFileDetailses();
            Set<CdFileDetails> cdFileDetailsesNew = recCdFileMsg.getCdFileDetailses();
            Set<TransactionLogs> transactionLogsesOld = persistentRecCdFileMsg.getTransactionLogses();
            Set<TransactionLogs> transactionLogsesNew = recCdFileMsg.getTransactionLogses();
            Set<ResCdFileMsg> resCdFileMsgsOld = persistentRecCdFileMsg.getResCdFileMsgs();
            Set<ResCdFileMsg> resCdFileMsgsNew = recCdFileMsg.getResCdFileMsgs();
            List<String> illegalOrphanMessages = null;
            for (ResPaymentMsg resPaymentMsgsOldResPaymentMsg : resPaymentMsgsOld) {
                if (!resPaymentMsgsNew.contains(resPaymentMsgsOldResPaymentMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ResPaymentMsg " + resPaymentMsgsOldResPaymentMsg + " since its recCdFileMsg field is not nullable.");
                }
            }
            for (CdFileDetails cdFileDetailsesOldCdFileDetails : cdFileDetailsesOld) {
                if (!cdFileDetailsesNew.contains(cdFileDetailsesOldCdFileDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CdFileDetails " + cdFileDetailsesOldCdFileDetails + " since its recCdFileMsg field is not nullable.");
                }
            }
            for (ResCdFileMsg resCdFileMsgsOldResCdFileMsg : resCdFileMsgsOld) {
                if (!resCdFileMsgsNew.contains(resCdFileMsgsOldResCdFileMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ResCdFileMsg " + resCdFileMsgsOldResCdFileMsg + " since its recCdFileMsg field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (messageTypesNew != null) {
                messageTypesNew = em.getReference(messageTypesNew.getClass(), messageTypesNew.getMessageTypeId());
                recCdFileMsg.setMessageTypes(messageTypesNew);
            }
            Set<ResPaymentMsg> attachedResPaymentMsgsNew = new HashSet<ResPaymentMsg>();
            for (ResPaymentMsg resPaymentMsgsNewResPaymentMsgToAttach : resPaymentMsgsNew) {
                resPaymentMsgsNewResPaymentMsgToAttach = em.getReference(resPaymentMsgsNewResPaymentMsgToAttach.getClass(), resPaymentMsgsNewResPaymentMsgToAttach.getPayementMsgId());
                attachedResPaymentMsgsNew.add(resPaymentMsgsNewResPaymentMsgToAttach);
            }
            resPaymentMsgsNew = attachedResPaymentMsgsNew;
            recCdFileMsg.setResPaymentMsgs(resPaymentMsgsNew);
            Set<CdFileDetails> attachedCdFileDetailsesNew = new HashSet<CdFileDetails>();
            for (CdFileDetails cdFileDetailsesNewCdFileDetailsToAttach : cdFileDetailsesNew) {
                cdFileDetailsesNewCdFileDetailsToAttach = em.getReference(cdFileDetailsesNewCdFileDetailsToAttach.getClass(), cdFileDetailsesNewCdFileDetailsToAttach.getId());
                attachedCdFileDetailsesNew.add(cdFileDetailsesNewCdFileDetailsToAttach);
            }
            cdFileDetailsesNew = attachedCdFileDetailsesNew;
            recCdFileMsg.setCdFileDetailses(cdFileDetailsesNew);
            Set<TransactionLogs> attachedTransactionLogsesNew = new HashSet<TransactionLogs>();
            for (TransactionLogs transactionLogsesNewTransactionLogsToAttach : transactionLogsesNew) {
                transactionLogsesNewTransactionLogsToAttach = em.getReference(transactionLogsesNewTransactionLogsToAttach.getClass(), transactionLogsesNewTransactionLogsToAttach.getLogId());
                attachedTransactionLogsesNew.add(transactionLogsesNewTransactionLogsToAttach);
            }
            transactionLogsesNew = attachedTransactionLogsesNew;
            recCdFileMsg.setTransactionLogses(transactionLogsesNew);
            Set<ResCdFileMsg> attachedResCdFileMsgsNew = new HashSet<ResCdFileMsg>();
            for (ResCdFileMsg resCdFileMsgsNewResCdFileMsgToAttach : resCdFileMsgsNew) {
                resCdFileMsgsNewResCdFileMsgToAttach = em.getReference(resCdFileMsgsNewResCdFileMsgToAttach.getClass(), resCdFileMsgsNewResCdFileMsgToAttach.getResCdFileId());
                attachedResCdFileMsgsNew.add(resCdFileMsgsNewResCdFileMsgToAttach);
            }
            resCdFileMsgsNew = attachedResCdFileMsgsNew;
            recCdFileMsg.setResCdFileMsgs(resCdFileMsgsNew);
            recCdFileMsg = em.merge(recCdFileMsg);
            if (messageTypesOld != null && !messageTypesOld.equals(messageTypesNew)) {
                messageTypesOld.getRecCdFileMsgs().remove(recCdFileMsg);
                messageTypesOld = em.merge(messageTypesOld);
            }
            if (messageTypesNew != null && !messageTypesNew.equals(messageTypesOld)) {
                messageTypesNew.getRecCdFileMsgs().add(recCdFileMsg);
                messageTypesNew = em.merge(messageTypesNew);
            }
            for (ResPaymentMsg resPaymentMsgsNewResPaymentMsg : resPaymentMsgsNew) {
                if (!resPaymentMsgsOld.contains(resPaymentMsgsNewResPaymentMsg)) {
                    RecCdFileMsg oldRecCdFileMsgOfResPaymentMsgsNewResPaymentMsg = resPaymentMsgsNewResPaymentMsg.getRecCdFileMsg();
                    resPaymentMsgsNewResPaymentMsg.setRecCdFileMsg(recCdFileMsg);
                    resPaymentMsgsNewResPaymentMsg = em.merge(resPaymentMsgsNewResPaymentMsg);
                    if (oldRecCdFileMsgOfResPaymentMsgsNewResPaymentMsg != null && !oldRecCdFileMsgOfResPaymentMsgsNewResPaymentMsg.equals(recCdFileMsg)) {
                        oldRecCdFileMsgOfResPaymentMsgsNewResPaymentMsg.getResPaymentMsgs().remove(resPaymentMsgsNewResPaymentMsg);
                        oldRecCdFileMsgOfResPaymentMsgsNewResPaymentMsg = em.merge(oldRecCdFileMsgOfResPaymentMsgsNewResPaymentMsg);
                    }
                }
            }
            for (CdFileDetails cdFileDetailsesNewCdFileDetails : cdFileDetailsesNew) {
                if (!cdFileDetailsesOld.contains(cdFileDetailsesNewCdFileDetails)) {
                    RecCdFileMsg oldRecCdFileMsgOfCdFileDetailsesNewCdFileDetails = cdFileDetailsesNewCdFileDetails.getRecCdFileMsg();
                    cdFileDetailsesNewCdFileDetails.setRecCdFileMsg(recCdFileMsg);
                    cdFileDetailsesNewCdFileDetails = em.merge(cdFileDetailsesNewCdFileDetails);
                    if (oldRecCdFileMsgOfCdFileDetailsesNewCdFileDetails != null && !oldRecCdFileMsgOfCdFileDetailsesNewCdFileDetails.equals(recCdFileMsg)) {
                        oldRecCdFileMsgOfCdFileDetailsesNewCdFileDetails.getCdFileDetailses().remove(cdFileDetailsesNewCdFileDetails);
                        oldRecCdFileMsgOfCdFileDetailsesNewCdFileDetails = em.merge(oldRecCdFileMsgOfCdFileDetailsesNewCdFileDetails);
                    }
                }
            }
            for (TransactionLogs transactionLogsesOldTransactionLogs : transactionLogsesOld) {
                if (!transactionLogsesNew.contains(transactionLogsesOldTransactionLogs)) {
                    transactionLogsesOldTransactionLogs.setRecCdFileMsg(null);
                    transactionLogsesOldTransactionLogs = em.merge(transactionLogsesOldTransactionLogs);
                }
            }
            for (TransactionLogs transactionLogsesNewTransactionLogs : transactionLogsesNew) {
                if (!transactionLogsesOld.contains(transactionLogsesNewTransactionLogs)) {
                    RecCdFileMsg oldRecCdFileMsgOfTransactionLogsesNewTransactionLogs = transactionLogsesNewTransactionLogs.getRecCdFileMsg();
                    transactionLogsesNewTransactionLogs.setRecCdFileMsg(recCdFileMsg);
                    transactionLogsesNewTransactionLogs = em.merge(transactionLogsesNewTransactionLogs);
                    if (oldRecCdFileMsgOfTransactionLogsesNewTransactionLogs != null && !oldRecCdFileMsgOfTransactionLogsesNewTransactionLogs.equals(recCdFileMsg)) {
                        oldRecCdFileMsgOfTransactionLogsesNewTransactionLogs.getTransactionLogses().remove(transactionLogsesNewTransactionLogs);
                        oldRecCdFileMsgOfTransactionLogsesNewTransactionLogs = em.merge(oldRecCdFileMsgOfTransactionLogsesNewTransactionLogs);
                    }
                }
            }
            for (ResCdFileMsg resCdFileMsgsNewResCdFileMsg : resCdFileMsgsNew) {
                if (!resCdFileMsgsOld.contains(resCdFileMsgsNewResCdFileMsg)) {
                    RecCdFileMsg oldRecCdFileMsgOfResCdFileMsgsNewResCdFileMsg = resCdFileMsgsNewResCdFileMsg.getRecCdFileMsg();
                    resCdFileMsgsNewResCdFileMsg.setRecCdFileMsg(recCdFileMsg);
                    resCdFileMsgsNewResCdFileMsg = em.merge(resCdFileMsgsNewResCdFileMsg);
                    if (oldRecCdFileMsgOfResCdFileMsgsNewResCdFileMsg != null && !oldRecCdFileMsgOfResCdFileMsgsNewResCdFileMsg.equals(recCdFileMsg)) {
                        oldRecCdFileMsgOfResCdFileMsgsNewResCdFileMsg.getResCdFileMsgs().remove(resCdFileMsgsNewResCdFileMsg);
                        oldRecCdFileMsgOfResCdFileMsgsNewResCdFileMsg = em.merge(oldRecCdFileMsgOfResCdFileMsgsNewResCdFileMsg);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recCdFileMsg.getRecCdFileId();
                if (findRecCdFileMsg(id) == null) {
                    throw new NonexistentEntityException("The recCdFileMsg with id " + id + " no longer exists.");
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
            RecCdFileMsg recCdFileMsg;
            try {
                recCdFileMsg = em.getReference(RecCdFileMsg.class, id);
                recCdFileMsg.getRecCdFileId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recCdFileMsg with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<ResPaymentMsg> resPaymentMsgsOrphanCheck = recCdFileMsg.getResPaymentMsgs();
            for (ResPaymentMsg resPaymentMsgsOrphanCheckResPaymentMsg : resPaymentMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RecCdFileMsg (" + recCdFileMsg + ") cannot be destroyed since the ResPaymentMsg " + resPaymentMsgsOrphanCheckResPaymentMsg + " in its resPaymentMsgs field has a non-nullable recCdFileMsg field.");
            }
            Set<CdFileDetails> cdFileDetailsesOrphanCheck = recCdFileMsg.getCdFileDetailses();
            for (CdFileDetails cdFileDetailsesOrphanCheckCdFileDetails : cdFileDetailsesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RecCdFileMsg (" + recCdFileMsg + ") cannot be destroyed since the CdFileDetails " + cdFileDetailsesOrphanCheckCdFileDetails + " in its cdFileDetailses field has a non-nullable recCdFileMsg field.");
            }
            Set<ResCdFileMsg> resCdFileMsgsOrphanCheck = recCdFileMsg.getResCdFileMsgs();
            for (ResCdFileMsg resCdFileMsgsOrphanCheckResCdFileMsg : resCdFileMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RecCdFileMsg (" + recCdFileMsg + ") cannot be destroyed since the ResCdFileMsg " + resCdFileMsgsOrphanCheckResCdFileMsg + " in its resCdFileMsgs field has a non-nullable recCdFileMsg field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            MessageTypes messageTypes = recCdFileMsg.getMessageTypes();
            if (messageTypes != null) {
                messageTypes.getRecCdFileMsgs().remove(recCdFileMsg);
                messageTypes = em.merge(messageTypes);
            }
            Set<TransactionLogs> transactionLogses = recCdFileMsg.getTransactionLogses();
            for (TransactionLogs transactionLogsesTransactionLogs : transactionLogses) {
                transactionLogsesTransactionLogs.setRecCdFileMsg(null);
                transactionLogsesTransactionLogs = em.merge(transactionLogsesTransactionLogs);
            }
            em.remove(recCdFileMsg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecCdFileMsg> findRecCdFileMsgEntities() {
        return findRecCdFileMsgEntities(true, -1, -1);
    }

    public List<RecCdFileMsg> findRecCdFileMsgEntities(int maxResults, int firstResult) {
        return findRecCdFileMsgEntities(false, maxResults, firstResult);
    }

    private List<RecCdFileMsg> findRecCdFileMsgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecCdFileMsg.class));
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

    public RecCdFileMsg findRecCdFileMsg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecCdFileMsg.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecCdFileMsgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecCdFileMsg> rt = cq.from(RecCdFileMsg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
