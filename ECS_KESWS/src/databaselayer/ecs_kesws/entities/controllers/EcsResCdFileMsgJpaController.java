/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import databaselayer.ecs_kesws.entities.EcsResCdFileMsg;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import databaselayer.ecs_kesws.entities.RecErrorMsg;
import databaselayer.ecs_kesws.entities.controllers.exceptions.IllegalOrphanException;
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
public class EcsResCdFileMsgJpaController implements Serializable {

    public EcsResCdFileMsgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EcsResCdFileMsg ecsResCdFileMsg) {
        if (ecsResCdFileMsg.getRecErrorMsgCollection() == null) {
            ecsResCdFileMsg.setRecErrorMsgCollection(new ArrayList<RecErrorMsg>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<RecErrorMsg> attachedRecErrorMsgCollection = new ArrayList<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgCollectionRecErrorMsgToAttach : ecsResCdFileMsg.getRecErrorMsgCollection()) {
                recErrorMsgCollectionRecErrorMsgToAttach = em.getReference(recErrorMsgCollectionRecErrorMsgToAttach.getClass(), recErrorMsgCollectionRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgCollection.add(recErrorMsgCollectionRecErrorMsgToAttach);
            }
            ecsResCdFileMsg.setRecErrorMsgCollection(attachedRecErrorMsgCollection);
            em.persist(ecsResCdFileMsg);
            for (RecErrorMsg recErrorMsgCollectionRecErrorMsg : ecsResCdFileMsg.getRecErrorMsgCollection()) {
                EcsResCdFileMsg oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionRecErrorMsg = recErrorMsgCollectionRecErrorMsg.getEcsResCdFileMsgEcsResCdFileMsgId();
                recErrorMsgCollectionRecErrorMsg.setEcsResCdFileMsgEcsResCdFileMsgId(ecsResCdFileMsg);
                recErrorMsgCollectionRecErrorMsg = em.merge(recErrorMsgCollectionRecErrorMsg);
                if (oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionRecErrorMsg != null) {
                    oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionRecErrorMsg.getRecErrorMsgCollection().remove(recErrorMsgCollectionRecErrorMsg);
                    oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionRecErrorMsg = em.merge(oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionRecErrorMsg);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EcsResCdFileMsg ecsResCdFileMsg) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EcsResCdFileMsg persistentEcsResCdFileMsg = em.find(EcsResCdFileMsg.class, ecsResCdFileMsg.getEcsResCdFileMsgId());
            Collection<RecErrorMsg> recErrorMsgCollectionOld = persistentEcsResCdFileMsg.getRecErrorMsgCollection();
            Collection<RecErrorMsg> recErrorMsgCollectionNew = ecsResCdFileMsg.getRecErrorMsgCollection();
            List<String> illegalOrphanMessages = null;
            for (RecErrorMsg recErrorMsgCollectionOldRecErrorMsg : recErrorMsgCollectionOld) {
                if (!recErrorMsgCollectionNew.contains(recErrorMsgCollectionOldRecErrorMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecErrorMsg " + recErrorMsgCollectionOldRecErrorMsg + " since its ecsResCdFileMsgEcsResCdFileMsgId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<RecErrorMsg> attachedRecErrorMsgCollectionNew = new ArrayList<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgCollectionNewRecErrorMsgToAttach : recErrorMsgCollectionNew) {
                recErrorMsgCollectionNewRecErrorMsgToAttach = em.getReference(recErrorMsgCollectionNewRecErrorMsgToAttach.getClass(), recErrorMsgCollectionNewRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgCollectionNew.add(recErrorMsgCollectionNewRecErrorMsgToAttach);
            }
            recErrorMsgCollectionNew = attachedRecErrorMsgCollectionNew;
            ecsResCdFileMsg.setRecErrorMsgCollection(recErrorMsgCollectionNew);
            ecsResCdFileMsg = em.merge(ecsResCdFileMsg);
            for (RecErrorMsg recErrorMsgCollectionNewRecErrorMsg : recErrorMsgCollectionNew) {
                if (!recErrorMsgCollectionOld.contains(recErrorMsgCollectionNewRecErrorMsg)) {
                    EcsResCdFileMsg oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionNewRecErrorMsg = recErrorMsgCollectionNewRecErrorMsg.getEcsResCdFileMsgEcsResCdFileMsgId();
                    recErrorMsgCollectionNewRecErrorMsg.setEcsResCdFileMsgEcsResCdFileMsgId(ecsResCdFileMsg);
                    recErrorMsgCollectionNewRecErrorMsg = em.merge(recErrorMsgCollectionNewRecErrorMsg);
                    if (oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionNewRecErrorMsg != null && !oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionNewRecErrorMsg.equals(ecsResCdFileMsg)) {
                        oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionNewRecErrorMsg.getRecErrorMsgCollection().remove(recErrorMsgCollectionNewRecErrorMsg);
                        oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionNewRecErrorMsg = em.merge(oldEcsResCdFileMsgEcsResCdFileMsgIdOfRecErrorMsgCollectionNewRecErrorMsg);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ecsResCdFileMsg.getEcsResCdFileMsgId();
                if (findEcsResCdFileMsg(id) == null) {
                    throw new NonexistentEntityException("The ecsResCdFileMsg with id " + id + " no longer exists.");
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
            EcsResCdFileMsg ecsResCdFileMsg;
            try {
                ecsResCdFileMsg = em.getReference(EcsResCdFileMsg.class, id);
                ecsResCdFileMsg.getEcsResCdFileMsgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ecsResCdFileMsg with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RecErrorMsg> recErrorMsgCollectionOrphanCheck = ecsResCdFileMsg.getRecErrorMsgCollection();
            for (RecErrorMsg recErrorMsgCollectionOrphanCheckRecErrorMsg : recErrorMsgCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EcsResCdFileMsg (" + ecsResCdFileMsg + ") cannot be destroyed since the RecErrorMsg " + recErrorMsgCollectionOrphanCheckRecErrorMsg + " in its recErrorMsgCollection field has a non-nullable ecsResCdFileMsgEcsResCdFileMsgId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ecsResCdFileMsg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EcsResCdFileMsg> findEcsResCdFileMsgEntities() {
        return findEcsResCdFileMsgEntities(true, -1, -1);
    }

    public List<EcsResCdFileMsg> findEcsResCdFileMsgEntities(int maxResults, int firstResult) {
        return findEcsResCdFileMsgEntities(false, maxResults, firstResult);
    }

    private List<EcsResCdFileMsg> findEcsResCdFileMsgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EcsResCdFileMsg.class));
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

    public EcsResCdFileMsg findEcsResCdFileMsg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EcsResCdFileMsg.class, id);
        } finally {
            em.close();
        }
    }

    public int getEcsResCdFileMsgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EcsResCdFileMsg> rt = cq.from(EcsResCdFileMsg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
