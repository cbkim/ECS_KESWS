/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import databaselayer.ecs_kesws.entities.CdFileDetails;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import databaselayer.ecs_kesws.entities.EcsDocumentTypes;
import databaselayer.ecs_kesws.entities.PricelistInternalProductcodeDocumentMap;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.controllers.exceptions.NonexistentEntityException;
import databaselayer.ecs_kesws.entities.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kim
 */
public class CdFileDetailsJpaController implements Serializable {

    public CdFileDetailsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CdFileDetails cdFileDetails) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EcsDocumentTypes ecsDocumentTypes = cdFileDetails.getEcsDocumentTypes();
            if (ecsDocumentTypes != null) {
                ecsDocumentTypes = em.getReference(ecsDocumentTypes.getClass(), ecsDocumentTypes.getId());
                cdFileDetails.setEcsDocumentTypes(ecsDocumentTypes);
            }
            PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMap = cdFileDetails.getPricelistInternalProductcodeDocumentMap();
            if (pricelistInternalProductcodeDocumentMap != null) {
                pricelistInternalProductcodeDocumentMap = em.getReference(pricelistInternalProductcodeDocumentMap.getClass(), pricelistInternalProductcodeDocumentMap.getPricelistIpcMapId());
                cdFileDetails.setPricelistInternalProductcodeDocumentMap(pricelistInternalProductcodeDocumentMap);
            }
            RecCdFileMsg recCdFileMsg = cdFileDetails.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg = em.getReference(recCdFileMsg.getClass(), recCdFileMsg.getRecCdFileId());
                cdFileDetails.setRecCdFileMsg(recCdFileMsg);
            }
            em.persist(cdFileDetails);
            if (ecsDocumentTypes != null) {
                ecsDocumentTypes.getCdFileDetailses().add(cdFileDetails);
                ecsDocumentTypes = em.merge(ecsDocumentTypes);
            }
            if (pricelistInternalProductcodeDocumentMap != null) {
                pricelistInternalProductcodeDocumentMap.getCdFileDetailses().add(cdFileDetails);
                pricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMap);
            }
            if (recCdFileMsg != null) {
                recCdFileMsg.getCdFileDetailses().add(cdFileDetails);
                recCdFileMsg = em.merge(recCdFileMsg);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCdFileDetails(cdFileDetails.getId()) != null) {
                throw new PreexistingEntityException("CdFileDetails " + cdFileDetails + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CdFileDetails cdFileDetails) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CdFileDetails persistentCdFileDetails = em.find(CdFileDetails.class, cdFileDetails.getId());
            EcsDocumentTypes ecsDocumentTypesOld = persistentCdFileDetails.getEcsDocumentTypes();
            EcsDocumentTypes ecsDocumentTypesNew = cdFileDetails.getEcsDocumentTypes();
            PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapOld = persistentCdFileDetails.getPricelistInternalProductcodeDocumentMap();
            PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapNew = cdFileDetails.getPricelistInternalProductcodeDocumentMap();
            RecCdFileMsg recCdFileMsgOld = persistentCdFileDetails.getRecCdFileMsg();
            RecCdFileMsg recCdFileMsgNew = cdFileDetails.getRecCdFileMsg();
            if (ecsDocumentTypesNew != null) {
                ecsDocumentTypesNew = em.getReference(ecsDocumentTypesNew.getClass(), ecsDocumentTypesNew.getId());
                cdFileDetails.setEcsDocumentTypes(ecsDocumentTypesNew);
            }
            if (pricelistInternalProductcodeDocumentMapNew != null) {
                pricelistInternalProductcodeDocumentMapNew = em.getReference(pricelistInternalProductcodeDocumentMapNew.getClass(), pricelistInternalProductcodeDocumentMapNew.getPricelistIpcMapId());
                cdFileDetails.setPricelistInternalProductcodeDocumentMap(pricelistInternalProductcodeDocumentMapNew);
            }
            if (recCdFileMsgNew != null) {
                recCdFileMsgNew = em.getReference(recCdFileMsgNew.getClass(), recCdFileMsgNew.getRecCdFileId());
                cdFileDetails.setRecCdFileMsg(recCdFileMsgNew);
            }
            cdFileDetails = em.merge(cdFileDetails);
            if (ecsDocumentTypesOld != null && !ecsDocumentTypesOld.equals(ecsDocumentTypesNew)) {
                ecsDocumentTypesOld.getCdFileDetailses().remove(cdFileDetails);
                ecsDocumentTypesOld = em.merge(ecsDocumentTypesOld);
            }
            if (ecsDocumentTypesNew != null && !ecsDocumentTypesNew.equals(ecsDocumentTypesOld)) {
                ecsDocumentTypesNew.getCdFileDetailses().add(cdFileDetails);
                ecsDocumentTypesNew = em.merge(ecsDocumentTypesNew);
            }
            if (pricelistInternalProductcodeDocumentMapOld != null && !pricelistInternalProductcodeDocumentMapOld.equals(pricelistInternalProductcodeDocumentMapNew)) {
                pricelistInternalProductcodeDocumentMapOld.getCdFileDetailses().remove(cdFileDetails);
                pricelistInternalProductcodeDocumentMapOld = em.merge(pricelistInternalProductcodeDocumentMapOld);
            }
            if (pricelistInternalProductcodeDocumentMapNew != null && !pricelistInternalProductcodeDocumentMapNew.equals(pricelistInternalProductcodeDocumentMapOld)) {
                pricelistInternalProductcodeDocumentMapNew.getCdFileDetailses().add(cdFileDetails);
                pricelistInternalProductcodeDocumentMapNew = em.merge(pricelistInternalProductcodeDocumentMapNew);
            }
            if (recCdFileMsgOld != null && !recCdFileMsgOld.equals(recCdFileMsgNew)) {
                recCdFileMsgOld.getCdFileDetailses().remove(cdFileDetails);
                recCdFileMsgOld = em.merge(recCdFileMsgOld);
            }
            if (recCdFileMsgNew != null && !recCdFileMsgNew.equals(recCdFileMsgOld)) {
                recCdFileMsgNew.getCdFileDetailses().add(cdFileDetails);
                recCdFileMsgNew = em.merge(recCdFileMsgNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = cdFileDetails.getId();
                if (findCdFileDetails(id) == null) {
                    throw new NonexistentEntityException("The cdFileDetails with id " + id + " no longer exists.");
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
            CdFileDetails cdFileDetails;
            try {
                cdFileDetails = em.getReference(CdFileDetails.class, id);
                cdFileDetails.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cdFileDetails with id " + id + " no longer exists.", enfe);
            }
            EcsDocumentTypes ecsDocumentTypes = cdFileDetails.getEcsDocumentTypes();
            if (ecsDocumentTypes != null) {
                ecsDocumentTypes.getCdFileDetailses().remove(cdFileDetails);
                ecsDocumentTypes = em.merge(ecsDocumentTypes);
            }
            PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMap = cdFileDetails.getPricelistInternalProductcodeDocumentMap();
            if (pricelistInternalProductcodeDocumentMap != null) {
                pricelistInternalProductcodeDocumentMap.getCdFileDetailses().remove(cdFileDetails);
                pricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMap);
            }
            RecCdFileMsg recCdFileMsg = cdFileDetails.getRecCdFileMsg();
            if (recCdFileMsg != null) {
                recCdFileMsg.getCdFileDetailses().remove(cdFileDetails);
                recCdFileMsg = em.merge(recCdFileMsg);
            }
            em.remove(cdFileDetails);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CdFileDetails> findCdFileDetailsEntities() {
        return findCdFileDetailsEntities(true, -1, -1);
    }

    public List<CdFileDetails> findCdFileDetailsEntities(int maxResults, int firstResult) {
        return findCdFileDetailsEntities(false, maxResults, firstResult);
    }

    private List<CdFileDetails> findCdFileDetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CdFileDetails.class));
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

    public CdFileDetails findCdFileDetails(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CdFileDetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getCdFileDetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CdFileDetails> rt = cq.from(CdFileDetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
