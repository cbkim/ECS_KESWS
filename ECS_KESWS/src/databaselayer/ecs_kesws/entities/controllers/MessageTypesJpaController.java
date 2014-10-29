/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import databaselayer.ecs_kesws.entities.MessageTypes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
import java.util.HashSet;
import java.util.Set;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.controllers.exceptions.IllegalOrphanException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kim
 */
public class MessageTypesJpaController implements Serializable {

    public MessageTypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MessageTypes messageTypes) throws PreexistingEntityException, Exception {
        if (messageTypes.getResCdFileMsgs() == null) {
            messageTypes.setResCdFileMsgs(new HashSet<ResCdFileMsg>());
        }
        if (messageTypes.getRecCdFileMsgs() == null) {
            messageTypes.setRecCdFileMsgs(new HashSet<RecCdFileMsg>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<ResCdFileMsg> attachedResCdFileMsgs = new HashSet<ResCdFileMsg>();
            for (ResCdFileMsg resCdFileMsgsResCdFileMsgToAttach : messageTypes.getResCdFileMsgs()) {
                resCdFileMsgsResCdFileMsgToAttach = em.getReference(resCdFileMsgsResCdFileMsgToAttach.getClass(), resCdFileMsgsResCdFileMsgToAttach.getResCdFileId());
                attachedResCdFileMsgs.add(resCdFileMsgsResCdFileMsgToAttach);
            }
            messageTypes.setResCdFileMsgs(attachedResCdFileMsgs);
            Set<RecCdFileMsg> attachedRecCdFileMsgs = new HashSet<RecCdFileMsg>();
            for (RecCdFileMsg recCdFileMsgsRecCdFileMsgToAttach : messageTypes.getRecCdFileMsgs()) {
                recCdFileMsgsRecCdFileMsgToAttach = em.getReference(recCdFileMsgsRecCdFileMsgToAttach.getClass(), recCdFileMsgsRecCdFileMsgToAttach.getRecCdFileId());
                attachedRecCdFileMsgs.add(recCdFileMsgsRecCdFileMsgToAttach);
            }
            messageTypes.setRecCdFileMsgs(attachedRecCdFileMsgs);
            em.persist(messageTypes);
            for (ResCdFileMsg resCdFileMsgsResCdFileMsg : messageTypes.getResCdFileMsgs()) {
                MessageTypes oldMessageTypesOfResCdFileMsgsResCdFileMsg = resCdFileMsgsResCdFileMsg.getMessageTypes();
                resCdFileMsgsResCdFileMsg.setMessageTypes(messageTypes);
                resCdFileMsgsResCdFileMsg = em.merge(resCdFileMsgsResCdFileMsg);
                if (oldMessageTypesOfResCdFileMsgsResCdFileMsg != null) {
                    oldMessageTypesOfResCdFileMsgsResCdFileMsg.getResCdFileMsgs().remove(resCdFileMsgsResCdFileMsg);
                    oldMessageTypesOfResCdFileMsgsResCdFileMsg = em.merge(oldMessageTypesOfResCdFileMsgsResCdFileMsg);
                }
            }
            for (RecCdFileMsg recCdFileMsgsRecCdFileMsg : messageTypes.getRecCdFileMsgs()) {
                MessageTypes oldMessageTypesOfRecCdFileMsgsRecCdFileMsg = recCdFileMsgsRecCdFileMsg.getMessageTypes();
                recCdFileMsgsRecCdFileMsg.setMessageTypes(messageTypes);
                recCdFileMsgsRecCdFileMsg = em.merge(recCdFileMsgsRecCdFileMsg);
                if (oldMessageTypesOfRecCdFileMsgsRecCdFileMsg != null) {
                    oldMessageTypesOfRecCdFileMsgsRecCdFileMsg.getRecCdFileMsgs().remove(recCdFileMsgsRecCdFileMsg);
                    oldMessageTypesOfRecCdFileMsgsRecCdFileMsg = em.merge(oldMessageTypesOfRecCdFileMsgsRecCdFileMsg);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMessageTypes(messageTypes.getMessageTypeId()) != null) {
                throw new PreexistingEntityException("MessageTypes " + messageTypes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MessageTypes messageTypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MessageTypes persistentMessageTypes = em.find(MessageTypes.class, messageTypes.getMessageTypeId());
            Set<ResCdFileMsg> resCdFileMsgsOld = persistentMessageTypes.getResCdFileMsgs();
            Set<ResCdFileMsg> resCdFileMsgsNew = messageTypes.getResCdFileMsgs();
            Set<RecCdFileMsg> recCdFileMsgsOld = persistentMessageTypes.getRecCdFileMsgs();
            Set<RecCdFileMsg> recCdFileMsgsNew = messageTypes.getRecCdFileMsgs();
            List<String> illegalOrphanMessages = null;
            for (ResCdFileMsg resCdFileMsgsOldResCdFileMsg : resCdFileMsgsOld) {
                if (!resCdFileMsgsNew.contains(resCdFileMsgsOldResCdFileMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ResCdFileMsg " + resCdFileMsgsOldResCdFileMsg + " since its messageTypes field is not nullable.");
                }
            }
            for (RecCdFileMsg recCdFileMsgsOldRecCdFileMsg : recCdFileMsgsOld) {
                if (!recCdFileMsgsNew.contains(recCdFileMsgsOldRecCdFileMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecCdFileMsg " + recCdFileMsgsOldRecCdFileMsg + " since its messageTypes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<ResCdFileMsg> attachedResCdFileMsgsNew = new HashSet<ResCdFileMsg>();
            for (ResCdFileMsg resCdFileMsgsNewResCdFileMsgToAttach : resCdFileMsgsNew) {
                resCdFileMsgsNewResCdFileMsgToAttach = em.getReference(resCdFileMsgsNewResCdFileMsgToAttach.getClass(), resCdFileMsgsNewResCdFileMsgToAttach.getResCdFileId());
                attachedResCdFileMsgsNew.add(resCdFileMsgsNewResCdFileMsgToAttach);
            }
            resCdFileMsgsNew = attachedResCdFileMsgsNew;
            messageTypes.setResCdFileMsgs(resCdFileMsgsNew);
            Set<RecCdFileMsg> attachedRecCdFileMsgsNew = new HashSet<RecCdFileMsg>();
            for (RecCdFileMsg recCdFileMsgsNewRecCdFileMsgToAttach : recCdFileMsgsNew) {
                recCdFileMsgsNewRecCdFileMsgToAttach = em.getReference(recCdFileMsgsNewRecCdFileMsgToAttach.getClass(), recCdFileMsgsNewRecCdFileMsgToAttach.getRecCdFileId());
                attachedRecCdFileMsgsNew.add(recCdFileMsgsNewRecCdFileMsgToAttach);
            }
            recCdFileMsgsNew = attachedRecCdFileMsgsNew;
            messageTypes.setRecCdFileMsgs(recCdFileMsgsNew);
            messageTypes = em.merge(messageTypes);
            for (ResCdFileMsg resCdFileMsgsNewResCdFileMsg : resCdFileMsgsNew) {
                if (!resCdFileMsgsOld.contains(resCdFileMsgsNewResCdFileMsg)) {
                    MessageTypes oldMessageTypesOfResCdFileMsgsNewResCdFileMsg = resCdFileMsgsNewResCdFileMsg.getMessageTypes();
                    resCdFileMsgsNewResCdFileMsg.setMessageTypes(messageTypes);
                    resCdFileMsgsNewResCdFileMsg = em.merge(resCdFileMsgsNewResCdFileMsg);
                    if (oldMessageTypesOfResCdFileMsgsNewResCdFileMsg != null && !oldMessageTypesOfResCdFileMsgsNewResCdFileMsg.equals(messageTypes)) {
                        oldMessageTypesOfResCdFileMsgsNewResCdFileMsg.getResCdFileMsgs().remove(resCdFileMsgsNewResCdFileMsg);
                        oldMessageTypesOfResCdFileMsgsNewResCdFileMsg = em.merge(oldMessageTypesOfResCdFileMsgsNewResCdFileMsg);
                    }
                }
            }
            for (RecCdFileMsg recCdFileMsgsNewRecCdFileMsg : recCdFileMsgsNew) {
                if (!recCdFileMsgsOld.contains(recCdFileMsgsNewRecCdFileMsg)) {
                    MessageTypes oldMessageTypesOfRecCdFileMsgsNewRecCdFileMsg = recCdFileMsgsNewRecCdFileMsg.getMessageTypes();
                    recCdFileMsgsNewRecCdFileMsg.setMessageTypes(messageTypes);
                    recCdFileMsgsNewRecCdFileMsg = em.merge(recCdFileMsgsNewRecCdFileMsg);
                    if (oldMessageTypesOfRecCdFileMsgsNewRecCdFileMsg != null && !oldMessageTypesOfRecCdFileMsgsNewRecCdFileMsg.equals(messageTypes)) {
                        oldMessageTypesOfRecCdFileMsgsNewRecCdFileMsg.getRecCdFileMsgs().remove(recCdFileMsgsNewRecCdFileMsg);
                        oldMessageTypesOfRecCdFileMsgsNewRecCdFileMsg = em.merge(oldMessageTypesOfRecCdFileMsgsNewRecCdFileMsg);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = messageTypes.getMessageTypeId();
                if (findMessageTypes(id) == null) {
                    throw new NonexistentEntityException("The messageTypes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MessageTypes messageTypes;
            try {
                messageTypes = em.getReference(MessageTypes.class, id);
                messageTypes.getMessageTypeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The messageTypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<ResCdFileMsg> resCdFileMsgsOrphanCheck = messageTypes.getResCdFileMsgs();
            for (ResCdFileMsg resCdFileMsgsOrphanCheckResCdFileMsg : resCdFileMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MessageTypes (" + messageTypes + ") cannot be destroyed since the ResCdFileMsg " + resCdFileMsgsOrphanCheckResCdFileMsg + " in its resCdFileMsgs field has a non-nullable messageTypes field.");
            }
            Set<RecCdFileMsg> recCdFileMsgsOrphanCheck = messageTypes.getRecCdFileMsgs();
            for (RecCdFileMsg recCdFileMsgsOrphanCheckRecCdFileMsg : recCdFileMsgsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MessageTypes (" + messageTypes + ") cannot be destroyed since the RecCdFileMsg " + recCdFileMsgsOrphanCheckRecCdFileMsg + " in its recCdFileMsgs field has a non-nullable messageTypes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(messageTypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MessageTypes> findMessageTypesEntities() {
        return findMessageTypesEntities(true, -1, -1);
    }

    public List<MessageTypes> findMessageTypesEntities(int maxResults, int firstResult) {
        return findMessageTypesEntities(false, maxResults, firstResult);
    }

    private List<MessageTypes> findMessageTypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MessageTypes.class));
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

    public MessageTypes findMessageTypes(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MessageTypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageTypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MessageTypes> rt = cq.from(MessageTypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
