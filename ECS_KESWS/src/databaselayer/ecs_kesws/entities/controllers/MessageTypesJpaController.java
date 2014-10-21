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
import java.util.ArrayList;
import java.util.Collection;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.controllers.exceptions.IllegalOrphanException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.PreexistingEntityException;
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
        if (messageTypes.getResCdFileMsgCollection() == null) {
            messageTypes.setResCdFileMsgCollection(new ArrayList<ResCdFileMsg>());
        }
        if (messageTypes.getRecCdFileMsgCollection() == null) {
            messageTypes.setRecCdFileMsgCollection(new ArrayList<RecCdFileMsg>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ResCdFileMsg> attachedResCdFileMsgCollection = new ArrayList<ResCdFileMsg>();
            for (ResCdFileMsg resCdFileMsgCollectionResCdFileMsgToAttach : messageTypes.getResCdFileMsgCollection()) {
                resCdFileMsgCollectionResCdFileMsgToAttach = em.getReference(resCdFileMsgCollectionResCdFileMsgToAttach.getClass(), resCdFileMsgCollectionResCdFileMsgToAttach.getResCdFileId());
                attachedResCdFileMsgCollection.add(resCdFileMsgCollectionResCdFileMsgToAttach);
            }
            messageTypes.setResCdFileMsgCollection(attachedResCdFileMsgCollection);
            Collection<RecCdFileMsg> attachedRecCdFileMsgCollection = new ArrayList<RecCdFileMsg>();
            for (RecCdFileMsg recCdFileMsgCollectionRecCdFileMsgToAttach : messageTypes.getRecCdFileMsgCollection()) {
                recCdFileMsgCollectionRecCdFileMsgToAttach = em.getReference(recCdFileMsgCollectionRecCdFileMsgToAttach.getClass(), recCdFileMsgCollectionRecCdFileMsgToAttach.getRECCDFileID());
                attachedRecCdFileMsgCollection.add(recCdFileMsgCollectionRecCdFileMsgToAttach);
            }
            messageTypes.setRecCdFileMsgCollection(attachedRecCdFileMsgCollection);
            em.persist(messageTypes);
            for (ResCdFileMsg resCdFileMsgCollectionResCdFileMsg : messageTypes.getResCdFileMsgCollection()) {
                MessageTypes oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionResCdFileMsg = resCdFileMsgCollectionResCdFileMsg.getMessageTypesMessageTypeId();
                resCdFileMsgCollectionResCdFileMsg.setMessageTypesMessageTypeId(messageTypes);
                resCdFileMsgCollectionResCdFileMsg = em.merge(resCdFileMsgCollectionResCdFileMsg);
                if (oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionResCdFileMsg != null) {
                    oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionResCdFileMsg.getResCdFileMsgCollection().remove(resCdFileMsgCollectionResCdFileMsg);
                    oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionResCdFileMsg = em.merge(oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionResCdFileMsg);
                }
            }
            for (RecCdFileMsg recCdFileMsgCollectionRecCdFileMsg : messageTypes.getRecCdFileMsgCollection()) {
                MessageTypes oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionRecCdFileMsg = recCdFileMsgCollectionRecCdFileMsg.getMessageTypesMessageTypeId();
                recCdFileMsgCollectionRecCdFileMsg.setMessageTypesMessageTypeId(messageTypes);
                recCdFileMsgCollectionRecCdFileMsg = em.merge(recCdFileMsgCollectionRecCdFileMsg);
                if (oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionRecCdFileMsg != null) {
                    oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionRecCdFileMsg.getRecCdFileMsgCollection().remove(recCdFileMsgCollectionRecCdFileMsg);
                    oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionRecCdFileMsg = em.merge(oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionRecCdFileMsg);
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
            Collection<ResCdFileMsg> resCdFileMsgCollectionOld = persistentMessageTypes.getResCdFileMsgCollection();
            Collection<ResCdFileMsg> resCdFileMsgCollectionNew = messageTypes.getResCdFileMsgCollection();
            Collection<RecCdFileMsg> recCdFileMsgCollectionOld = persistentMessageTypes.getRecCdFileMsgCollection();
            Collection<RecCdFileMsg> recCdFileMsgCollectionNew = messageTypes.getRecCdFileMsgCollection();
            List<String> illegalOrphanMessages = null;
            for (ResCdFileMsg resCdFileMsgCollectionOldResCdFileMsg : resCdFileMsgCollectionOld) {
                if (!resCdFileMsgCollectionNew.contains(resCdFileMsgCollectionOldResCdFileMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ResCdFileMsg " + resCdFileMsgCollectionOldResCdFileMsg + " since its messageTypesMessageTypeId field is not nullable.");
                }
            }
            for (RecCdFileMsg recCdFileMsgCollectionOldRecCdFileMsg : recCdFileMsgCollectionOld) {
                if (!recCdFileMsgCollectionNew.contains(recCdFileMsgCollectionOldRecCdFileMsg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecCdFileMsg " + recCdFileMsgCollectionOldRecCdFileMsg + " since its messageTypesMessageTypeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ResCdFileMsg> attachedResCdFileMsgCollectionNew = new ArrayList<ResCdFileMsg>();
            for (ResCdFileMsg resCdFileMsgCollectionNewResCdFileMsgToAttach : resCdFileMsgCollectionNew) {
                resCdFileMsgCollectionNewResCdFileMsgToAttach = em.getReference(resCdFileMsgCollectionNewResCdFileMsgToAttach.getClass(), resCdFileMsgCollectionNewResCdFileMsgToAttach.getResCdFileId());
                attachedResCdFileMsgCollectionNew.add(resCdFileMsgCollectionNewResCdFileMsgToAttach);
            }
            resCdFileMsgCollectionNew = attachedResCdFileMsgCollectionNew;
            messageTypes.setResCdFileMsgCollection(resCdFileMsgCollectionNew);
            Collection<RecCdFileMsg> attachedRecCdFileMsgCollectionNew = new ArrayList<RecCdFileMsg>();
            for (RecCdFileMsg recCdFileMsgCollectionNewRecCdFileMsgToAttach : recCdFileMsgCollectionNew) {
                recCdFileMsgCollectionNewRecCdFileMsgToAttach = em.getReference(recCdFileMsgCollectionNewRecCdFileMsgToAttach.getClass(), recCdFileMsgCollectionNewRecCdFileMsgToAttach.getRECCDFileID());
                attachedRecCdFileMsgCollectionNew.add(recCdFileMsgCollectionNewRecCdFileMsgToAttach);
            }
            recCdFileMsgCollectionNew = attachedRecCdFileMsgCollectionNew;
            messageTypes.setRecCdFileMsgCollection(recCdFileMsgCollectionNew);
            messageTypes = em.merge(messageTypes);
            for (ResCdFileMsg resCdFileMsgCollectionNewResCdFileMsg : resCdFileMsgCollectionNew) {
                if (!resCdFileMsgCollectionOld.contains(resCdFileMsgCollectionNewResCdFileMsg)) {
                    MessageTypes oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionNewResCdFileMsg = resCdFileMsgCollectionNewResCdFileMsg.getMessageTypesMessageTypeId();
                    resCdFileMsgCollectionNewResCdFileMsg.setMessageTypesMessageTypeId(messageTypes);
                    resCdFileMsgCollectionNewResCdFileMsg = em.merge(resCdFileMsgCollectionNewResCdFileMsg);
                    if (oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionNewResCdFileMsg != null && !oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionNewResCdFileMsg.equals(messageTypes)) {
                        oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionNewResCdFileMsg.getResCdFileMsgCollection().remove(resCdFileMsgCollectionNewResCdFileMsg);
                        oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionNewResCdFileMsg = em.merge(oldMessageTypesMessageTypeIdOfResCdFileMsgCollectionNewResCdFileMsg);
                    }
                }
            }
            for (RecCdFileMsg recCdFileMsgCollectionNewRecCdFileMsg : recCdFileMsgCollectionNew) {
                if (!recCdFileMsgCollectionOld.contains(recCdFileMsgCollectionNewRecCdFileMsg)) {
                    MessageTypes oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionNewRecCdFileMsg = recCdFileMsgCollectionNewRecCdFileMsg.getMessageTypesMessageTypeId();
                    recCdFileMsgCollectionNewRecCdFileMsg.setMessageTypesMessageTypeId(messageTypes);
                    recCdFileMsgCollectionNewRecCdFileMsg = em.merge(recCdFileMsgCollectionNewRecCdFileMsg);
                    if (oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionNewRecCdFileMsg != null && !oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionNewRecCdFileMsg.equals(messageTypes)) {
                        oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionNewRecCdFileMsg.getRecCdFileMsgCollection().remove(recCdFileMsgCollectionNewRecCdFileMsg);
                        oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionNewRecCdFileMsg = em.merge(oldMessageTypesMessageTypeIdOfRecCdFileMsgCollectionNewRecCdFileMsg);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = messageTypes.getMessageTypeId();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            Collection<ResCdFileMsg> resCdFileMsgCollectionOrphanCheck = messageTypes.getResCdFileMsgCollection();
            for (ResCdFileMsg resCdFileMsgCollectionOrphanCheckResCdFileMsg : resCdFileMsgCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MessageTypes (" + messageTypes + ") cannot be destroyed since the ResCdFileMsg " + resCdFileMsgCollectionOrphanCheckResCdFileMsg + " in its resCdFileMsgCollection field has a non-nullable messageTypesMessageTypeId field.");
            }
            Collection<RecCdFileMsg> recCdFileMsgCollectionOrphanCheck = messageTypes.getRecCdFileMsgCollection();
            for (RecCdFileMsg recCdFileMsgCollectionOrphanCheckRecCdFileMsg : recCdFileMsgCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MessageTypes (" + messageTypes + ") cannot be destroyed since the RecCdFileMsg " + recCdFileMsgCollectionOrphanCheckRecCdFileMsg + " in its recCdFileMsgCollection field has a non-nullable messageTypesMessageTypeId field.");
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

    public MessageTypes findMessageTypes(Integer id) {
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
