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
import databaselayer.ecs_kesws.entities.EcsResCdFileMsg;
import databaselayer.ecs_kesws.entities.RecErrorMsg;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kim
 */
public class RecErrorMsgJpaController implements Serializable {

    public RecErrorMsgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecErrorMsg recErrorMsg) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EcsResCdFileMsg ecsResCdFileMsg = recErrorMsg.getEcsResCdFileMsg();
            if (ecsResCdFileMsg != null) {
                ecsResCdFileMsg = em.getReference(ecsResCdFileMsg.getClass(), ecsResCdFileMsg.getEcsResCdFileMsgId());
                recErrorMsg.setEcsResCdFileMsg(ecsResCdFileMsg);
            }
            ResCdFileMsg resCdFileMsg = recErrorMsg.getResCdFileMsg();
            if (resCdFileMsg != null) {
                resCdFileMsg = em.getReference(resCdFileMsg.getClass(), resCdFileMsg.getResCdFileId());
                recErrorMsg.setResCdFileMsg(resCdFileMsg);
            }
            ResPaymentMsg resPaymentMsg = recErrorMsg.getResPaymentMsg();
            if (resPaymentMsg != null) {
                resPaymentMsg = em.getReference(resPaymentMsg.getClass(), resPaymentMsg.getPayementMsgId());
                recErrorMsg.setResPaymentMsg(resPaymentMsg);
            }
            em.persist(recErrorMsg);
            if (ecsResCdFileMsg != null) {
                ecsResCdFileMsg.getRecErrorMsgs().add(recErrorMsg);
                ecsResCdFileMsg = em.merge(ecsResCdFileMsg);
            }
            if (resCdFileMsg != null) {
                resCdFileMsg.getRecErrorMsgs().add(recErrorMsg);
                resCdFileMsg = em.merge(resCdFileMsg);
            }
            if (resPaymentMsg != null) {
                resPaymentMsg.getRecErrorMsgs().add(recErrorMsg);
                resPaymentMsg = em.merge(resPaymentMsg);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecErrorMsg(recErrorMsg.getRecErrorMsgId()) != null) {
                throw new PreexistingEntityException("RecErrorMsg " + recErrorMsg + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecErrorMsg recErrorMsg) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecErrorMsg persistentRecErrorMsg = em.find(RecErrorMsg.class, recErrorMsg.getRecErrorMsgId());
            EcsResCdFileMsg ecsResCdFileMsgOld = persistentRecErrorMsg.getEcsResCdFileMsg();
            EcsResCdFileMsg ecsResCdFileMsgNew = recErrorMsg.getEcsResCdFileMsg();
            ResCdFileMsg resCdFileMsgOld = persistentRecErrorMsg.getResCdFileMsg();
            ResCdFileMsg resCdFileMsgNew = recErrorMsg.getResCdFileMsg();
            ResPaymentMsg resPaymentMsgOld = persistentRecErrorMsg.getResPaymentMsg();
            ResPaymentMsg resPaymentMsgNew = recErrorMsg.getResPaymentMsg();
            if (ecsResCdFileMsgNew != null) {
                ecsResCdFileMsgNew = em.getReference(ecsResCdFileMsgNew.getClass(), ecsResCdFileMsgNew.getEcsResCdFileMsgId());
                recErrorMsg.setEcsResCdFileMsg(ecsResCdFileMsgNew);
            }
            if (resCdFileMsgNew != null) {
                resCdFileMsgNew = em.getReference(resCdFileMsgNew.getClass(), resCdFileMsgNew.getResCdFileId());
                recErrorMsg.setResCdFileMsg(resCdFileMsgNew);
            }
            if (resPaymentMsgNew != null) {
                resPaymentMsgNew = em.getReference(resPaymentMsgNew.getClass(), resPaymentMsgNew.getPayementMsgId());
                recErrorMsg.setResPaymentMsg(resPaymentMsgNew);
            }
            recErrorMsg = em.merge(recErrorMsg);
            if (ecsResCdFileMsgOld != null && !ecsResCdFileMsgOld.equals(ecsResCdFileMsgNew)) {
                ecsResCdFileMsgOld.getRecErrorMsgs().remove(recErrorMsg);
                ecsResCdFileMsgOld = em.merge(ecsResCdFileMsgOld);
            }
            if (ecsResCdFileMsgNew != null && !ecsResCdFileMsgNew.equals(ecsResCdFileMsgOld)) {
                ecsResCdFileMsgNew.getRecErrorMsgs().add(recErrorMsg);
                ecsResCdFileMsgNew = em.merge(ecsResCdFileMsgNew);
            }
            if (resCdFileMsgOld != null && !resCdFileMsgOld.equals(resCdFileMsgNew)) {
                resCdFileMsgOld.getRecErrorMsgs().remove(recErrorMsg);
                resCdFileMsgOld = em.merge(resCdFileMsgOld);
            }
            if (resCdFileMsgNew != null && !resCdFileMsgNew.equals(resCdFileMsgOld)) {
                resCdFileMsgNew.getRecErrorMsgs().add(recErrorMsg);
                resCdFileMsgNew = em.merge(resCdFileMsgNew);
            }
            if (resPaymentMsgOld != null && !resPaymentMsgOld.equals(resPaymentMsgNew)) {
                resPaymentMsgOld.getRecErrorMsgs().remove(recErrorMsg);
                resPaymentMsgOld = em.merge(resPaymentMsgOld);
            }
            if (resPaymentMsgNew != null && !resPaymentMsgNew.equals(resPaymentMsgOld)) {
                resPaymentMsgNew.getRecErrorMsgs().add(recErrorMsg);
                resPaymentMsgNew = em.merge(resPaymentMsgNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = recErrorMsg.getRecErrorMsgId();
                if (findRecErrorMsg(id) == null) {
                    throw new NonexistentEntityException("The recErrorMsg with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecErrorMsg recErrorMsg;
            try {
                recErrorMsg = em.getReference(RecErrorMsg.class, id);
                recErrorMsg.getRecErrorMsgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recErrorMsg with id " + id + " no longer exists.", enfe);
            }
            EcsResCdFileMsg ecsResCdFileMsg = recErrorMsg.getEcsResCdFileMsg();
            if (ecsResCdFileMsg != null) {
                ecsResCdFileMsg.getRecErrorMsgs().remove(recErrorMsg);
                ecsResCdFileMsg = em.merge(ecsResCdFileMsg);
            }
            ResCdFileMsg resCdFileMsg = recErrorMsg.getResCdFileMsg();
            if (resCdFileMsg != null) {
                resCdFileMsg.getRecErrorMsgs().remove(recErrorMsg);
                resCdFileMsg = em.merge(resCdFileMsg);
            }
            ResPaymentMsg resPaymentMsg = recErrorMsg.getResPaymentMsg();
            if (resPaymentMsg != null) {
                resPaymentMsg.getRecErrorMsgs().remove(recErrorMsg);
                resPaymentMsg = em.merge(resPaymentMsg);
            }
            em.remove(recErrorMsg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecErrorMsg> findRecErrorMsgEntities() {
        return findRecErrorMsgEntities(true, -1, -1);
    }

    public List<RecErrorMsg> findRecErrorMsgEntities(int maxResults, int firstResult) {
        return findRecErrorMsgEntities(false, maxResults, firstResult);
    }

    private List<RecErrorMsg> findRecErrorMsgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecErrorMsg.class));
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

    public RecErrorMsg findRecErrorMsg(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecErrorMsg.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecErrorMsgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecErrorMsg> rt = cq.from(RecErrorMsg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
