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
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
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
        if (ecsResCdFileMsg.getRecErrorMsgs() == null) {
            ecsResCdFileMsg.setRecErrorMsgs(new HashSet<RecErrorMsg>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<RecErrorMsg> attachedRecErrorMsgs = new HashSet<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgsRecErrorMsgToAttach : ecsResCdFileMsg.getRecErrorMsgs()) {
                recErrorMsgsRecErrorMsgToAttach = em.getReference(recErrorMsgsRecErrorMsgToAttach.getClass(), recErrorMsgsRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgs.add(recErrorMsgsRecErrorMsgToAttach);
            }
            ecsResCdFileMsg.setRecErrorMsgs(attachedRecErrorMsgs);
            em.persist(ecsResCdFileMsg);
            for (RecErrorMsg recErrorMsgsRecErrorMsg : ecsResCdFileMsg.getRecErrorMsgs()) {
                EcsResCdFileMsg oldEcsResCdFileMsgOfRecErrorMsgsRecErrorMsg = recErrorMsgsRecErrorMsg.getEcsResCdFileMsg();
                recErrorMsgsRecErrorMsg.setEcsResCdFileMsg(ecsResCdFileMsg);
                recErrorMsgsRecErrorMsg = em.merge(recErrorMsgsRecErrorMsg);
                if (oldEcsResCdFileMsgOfRecErrorMsgsRecErrorMsg != null) {
                    oldEcsResCdFileMsgOfRecErrorMsgsRecErrorMsg.getRecErrorMsgs().remove(recErrorMsgsRecErrorMsg);
                    oldEcsResCdFileMsgOfRecErrorMsgsRecErrorMsg = em.merge(oldEcsResCdFileMsgOfRecErrorMsgsRecErrorMsg);
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
            Set<RecErrorMsg> recErrorMsgsOld = persistentEcsResCdFileMsg.getRecErrorMsgs();
            Set<RecErrorMsg> recErrorMsgsNew = ecsResCdFileMsg.getRecErrorMsgs();
            List<String> illegalOrphanMessages = null;
            for (RecErrorMsg recErrorMsgsOldRecErrorMsg : recErrorMsgsOld) {
                if (!recErrorMsgsNew.contains(recErrorMsgsOldRecErrorMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecErrorMsg " + recErrorMsgsOldRecErrorMsg + " since its ecsResCdFileMsg field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<RecErrorMsg> attachedRecErrorMsgsNew = new HashSet<RecErrorMsg>();
            for (RecErrorMsg recErrorMsgsNewRecErrorMsgToAttach : recErrorMsgsNew) {
                recErrorMsgsNewRecErrorMsgToAttach = em.getReference(recErrorMsgsNewRecErrorMsgToAttach.getClass(), recErrorMsgsNewRecErrorMsgToAttach.getRecErrorMsgId());
                attachedRecErrorMsgsNew.add(recErrorMsgsNewRecErrorMsgToAttach);
            }
            recErrorMsgsNew = attachedRecErrorMsgsNew;
            ecsResCdFileMsg.setRecErrorMsgs(recErrorMsgsNew);
            ecsResCdFileMsg = em.merge(ecsResCdFileMsg);
            for (RecErrorMsg recErrorMsgsNewRecErrorMsg : recErrorMsgsNew) {
                if (!recErrorMsgsOld.contains(recErrorMsgsNewRecErrorMsg)) {
                    EcsResCdFileMsg oldEcsResCdFileMsgOfRecErrorMsgsNewRecErrorMsg = recErrorMsgsNewRecErrorMsg.getEcsResCdFileMsg();
                    recErrorMsgsNewRecErrorMsg.setEcsResCdFileMsg(ecsResCdFileMsg);
                    recErrorMsgsNewRecErrorMsg = em.merge(recErrorMsgsNewRecErrorMsg);
                    if (oldEcsResCdFileMsgOfRecErrorMsgsNewRecErrorMsg != null && !oldEcsResCdFileMsgOfRecErrorMsgsNewRecErrorMsg.equals(ecsResCdFileMsg)) {
                        oldEcsResCdFileMsgOfRecErrorMsgsNewRecErrorMsg.getRecErrorMsgs().remove(recErrorMsgsNewRecErrorMsg);
                        oldEcsResCdFileMsgOfRecErrorMsgsNewRecErrorMsg = em.merge(oldEcsResCdFileMsgOfRecErrorMsgsNewRecErrorMsg);
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
            Set<RecErrorMsg> recErrorMsgsOrphanCheck = ecsResCdFileMsg.getRecErrorMsgs();
            for (RecErrorMsg recErrorMsgsOrphanCheckRecErrorMsg : recErrorMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EcsResCdFileMsg (" + ecsResCdFileMsg + ") cannot be destroyed since the RecErrorMsg " + recErrorMsgsOrphanCheckRecErrorMsg + " in its recErrorMsgs field has a non-nullable ecsResCdFileMsg field.");
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
