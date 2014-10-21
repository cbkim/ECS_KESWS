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
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
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
            EcsResCdFileMsg ecsResCdFileMsgEcsResCdFileMsgId = recErrorMsg.getEcsResCdFileMsgEcsResCdFileMsgId();
            if (ecsResCdFileMsgEcsResCdFileMsgId != null) {
                ecsResCdFileMsgEcsResCdFileMsgId = em.getReference(ecsResCdFileMsgEcsResCdFileMsgId.getClass(), ecsResCdFileMsgEcsResCdFileMsgId.getEcsResCdFileMsgId());
                recErrorMsg.setEcsResCdFileMsgEcsResCdFileMsgId(ecsResCdFileMsgEcsResCdFileMsgId);
            }
            ResPaymentMsg resPaymentMsgPayementMsgId = recErrorMsg.getResPaymentMsgPayementMsgId();
            if (resPaymentMsgPayementMsgId != null) {
                resPaymentMsgPayementMsgId = em.getReference(resPaymentMsgPayementMsgId.getClass(), resPaymentMsgPayementMsgId.getPayementMsgId());
                recErrorMsg.setResPaymentMsgPayementMsgId(resPaymentMsgPayementMsgId);
            }
            ResCdFileMsg resCdFileMsgResCdFileId = recErrorMsg.getResCdFileMsgResCdFileId();
            if (resCdFileMsgResCdFileId != null) {
                resCdFileMsgResCdFileId = em.getReference(resCdFileMsgResCdFileId.getClass(), resCdFileMsgResCdFileId.getResCdFileId());
                recErrorMsg.setResCdFileMsgResCdFileId(resCdFileMsgResCdFileId);
            }
            em.persist(recErrorMsg);
            if (ecsResCdFileMsgEcsResCdFileMsgId != null) {
                ecsResCdFileMsgEcsResCdFileMsgId.getRecErrorMsgCollection().add(recErrorMsg);
                ecsResCdFileMsgEcsResCdFileMsgId = em.merge(ecsResCdFileMsgEcsResCdFileMsgId);
            }
            if (resPaymentMsgPayementMsgId != null) {
                resPaymentMsgPayementMsgId.getRecErrorMsgCollection().add(recErrorMsg);
                resPaymentMsgPayementMsgId = em.merge(resPaymentMsgPayementMsgId);
            }
            if (resCdFileMsgResCdFileId != null) {
                resCdFileMsgResCdFileId.getRecErrorMsgCollection().add(recErrorMsg);
                resCdFileMsgResCdFileId = em.merge(resCdFileMsgResCdFileId);
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
            EcsResCdFileMsg ecsResCdFileMsgEcsResCdFileMsgIdOld = persistentRecErrorMsg.getEcsResCdFileMsgEcsResCdFileMsgId();
            EcsResCdFileMsg ecsResCdFileMsgEcsResCdFileMsgIdNew = recErrorMsg.getEcsResCdFileMsgEcsResCdFileMsgId();
            ResPaymentMsg resPaymentMsgPayementMsgIdOld = persistentRecErrorMsg.getResPaymentMsgPayementMsgId();
            ResPaymentMsg resPaymentMsgPayementMsgIdNew = recErrorMsg.getResPaymentMsgPayementMsgId();
            ResCdFileMsg resCdFileMsgResCdFileIdOld = persistentRecErrorMsg.getResCdFileMsgResCdFileId();
            ResCdFileMsg resCdFileMsgResCdFileIdNew = recErrorMsg.getResCdFileMsgResCdFileId();
            if (ecsResCdFileMsgEcsResCdFileMsgIdNew != null) {
                ecsResCdFileMsgEcsResCdFileMsgIdNew = em.getReference(ecsResCdFileMsgEcsResCdFileMsgIdNew.getClass(), ecsResCdFileMsgEcsResCdFileMsgIdNew.getEcsResCdFileMsgId());
                recErrorMsg.setEcsResCdFileMsgEcsResCdFileMsgId(ecsResCdFileMsgEcsResCdFileMsgIdNew);
            }
            if (resPaymentMsgPayementMsgIdNew != null) {
                resPaymentMsgPayementMsgIdNew = em.getReference(resPaymentMsgPayementMsgIdNew.getClass(), resPaymentMsgPayementMsgIdNew.getPayementMsgId());
                recErrorMsg.setResPaymentMsgPayementMsgId(resPaymentMsgPayementMsgIdNew);
            }
            if (resCdFileMsgResCdFileIdNew != null) {
                resCdFileMsgResCdFileIdNew = em.getReference(resCdFileMsgResCdFileIdNew.getClass(), resCdFileMsgResCdFileIdNew.getResCdFileId());
                recErrorMsg.setResCdFileMsgResCdFileId(resCdFileMsgResCdFileIdNew);
            }
            recErrorMsg = em.merge(recErrorMsg);
            if (ecsResCdFileMsgEcsResCdFileMsgIdOld != null && !ecsResCdFileMsgEcsResCdFileMsgIdOld.equals(ecsResCdFileMsgEcsResCdFileMsgIdNew)) {
                ecsResCdFileMsgEcsResCdFileMsgIdOld.getRecErrorMsgCollection().remove(recErrorMsg);
                ecsResCdFileMsgEcsResCdFileMsgIdOld = em.merge(ecsResCdFileMsgEcsResCdFileMsgIdOld);
            }
            if (ecsResCdFileMsgEcsResCdFileMsgIdNew != null && !ecsResCdFileMsgEcsResCdFileMsgIdNew.equals(ecsResCdFileMsgEcsResCdFileMsgIdOld)) {
                ecsResCdFileMsgEcsResCdFileMsgIdNew.getRecErrorMsgCollection().add(recErrorMsg);
                ecsResCdFileMsgEcsResCdFileMsgIdNew = em.merge(ecsResCdFileMsgEcsResCdFileMsgIdNew);
            }
            if (resPaymentMsgPayementMsgIdOld != null && !resPaymentMsgPayementMsgIdOld.equals(resPaymentMsgPayementMsgIdNew)) {
                resPaymentMsgPayementMsgIdOld.getRecErrorMsgCollection().remove(recErrorMsg);
                resPaymentMsgPayementMsgIdOld = em.merge(resPaymentMsgPayementMsgIdOld);
            }
            if (resPaymentMsgPayementMsgIdNew != null && !resPaymentMsgPayementMsgIdNew.equals(resPaymentMsgPayementMsgIdOld)) {
                resPaymentMsgPayementMsgIdNew.getRecErrorMsgCollection().add(recErrorMsg);
                resPaymentMsgPayementMsgIdNew = em.merge(resPaymentMsgPayementMsgIdNew);
            }
            if (resCdFileMsgResCdFileIdOld != null && !resCdFileMsgResCdFileIdOld.equals(resCdFileMsgResCdFileIdNew)) {
                resCdFileMsgResCdFileIdOld.getRecErrorMsgCollection().remove(recErrorMsg);
                resCdFileMsgResCdFileIdOld = em.merge(resCdFileMsgResCdFileIdOld);
            }
            if (resCdFileMsgResCdFileIdNew != null && !resCdFileMsgResCdFileIdNew.equals(resCdFileMsgResCdFileIdOld)) {
                resCdFileMsgResCdFileIdNew.getRecErrorMsgCollection().add(recErrorMsg);
                resCdFileMsgResCdFileIdNew = em.merge(resCdFileMsgResCdFileIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recErrorMsg.getRecErrorMsgId();
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            EcsResCdFileMsg ecsResCdFileMsgEcsResCdFileMsgId = recErrorMsg.getEcsResCdFileMsgEcsResCdFileMsgId();
            if (ecsResCdFileMsgEcsResCdFileMsgId != null) {
                ecsResCdFileMsgEcsResCdFileMsgId.getRecErrorMsgCollection().remove(recErrorMsg);
                ecsResCdFileMsgEcsResCdFileMsgId = em.merge(ecsResCdFileMsgEcsResCdFileMsgId);
            }
            ResPaymentMsg resPaymentMsgPayementMsgId = recErrorMsg.getResPaymentMsgPayementMsgId();
            if (resPaymentMsgPayementMsgId != null) {
                resPaymentMsgPayementMsgId.getRecErrorMsgCollection().remove(recErrorMsg);
                resPaymentMsgPayementMsgId = em.merge(resPaymentMsgPayementMsgId);
            }
            ResCdFileMsg resCdFileMsgResCdFileId = recErrorMsg.getResCdFileMsgResCdFileId();
            if (resCdFileMsgResCdFileId != null) {
                resCdFileMsgResCdFileId.getRecErrorMsgCollection().remove(recErrorMsg);
                resCdFileMsgResCdFileId = em.merge(resCdFileMsgResCdFileId);
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

    public RecErrorMsg findRecErrorMsg(Integer id) {
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
