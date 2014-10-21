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
import databaselayer.ecs_kesws.entities.PricelistInternalProductcodeDocumentMap;
import java.util.ArrayList;
import java.util.Collection;
import databaselayer.ecs_kesws.entities.CdFileDetails;
import databaselayer.ecs_kesws.entities.EcsDocumentTypes;
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
public class EcsDocumentTypesJpaController implements Serializable {

    public EcsDocumentTypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EcsDocumentTypes ecsDocumentTypes) throws PreexistingEntityException, Exception {
        if (ecsDocumentTypes.getPricelistInternalProductcodeDocumentMapCollection() == null) {
            ecsDocumentTypes.setPricelistInternalProductcodeDocumentMapCollection(new ArrayList<PricelistInternalProductcodeDocumentMap>());
        }
        if (ecsDocumentTypes.getCdFileDetailsCollection() == null) {
            ecsDocumentTypes.setCdFileDetailsCollection(new ArrayList<CdFileDetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PricelistInternalProductcodeDocumentMap> attachedPricelistInternalProductcodeDocumentMapCollection = new ArrayList<PricelistInternalProductcodeDocumentMap>();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMapToAttach : ecsDocumentTypes.getPricelistInternalProductcodeDocumentMapCollection()) {
                pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMapToAttach = em.getReference(pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMapToAttach.getClass(), pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMapToAttach.getPricelistIPCMAPID());
                attachedPricelistInternalProductcodeDocumentMapCollection.add(pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMapToAttach);
            }
            ecsDocumentTypes.setPricelistInternalProductcodeDocumentMapCollection(attachedPricelistInternalProductcodeDocumentMapCollection);
            Collection<CdFileDetails> attachedCdFileDetailsCollection = new ArrayList<CdFileDetails>();
            for (CdFileDetails cdFileDetailsCollectionCdFileDetailsToAttach : ecsDocumentTypes.getCdFileDetailsCollection()) {
                cdFileDetailsCollectionCdFileDetailsToAttach = em.getReference(cdFileDetailsCollectionCdFileDetailsToAttach.getClass(), cdFileDetailsCollectionCdFileDetailsToAttach.getId());
                attachedCdFileDetailsCollection.add(cdFileDetailsCollectionCdFileDetailsToAttach);
            }
            ecsDocumentTypes.setCdFileDetailsCollection(attachedCdFileDetailsCollection);
            em.persist(ecsDocumentTypes);
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap : ecsDocumentTypes.getPricelistInternalProductcodeDocumentMapCollection()) {
                EcsDocumentTypes oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap.getDOCUMENTTYPESid();
                pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap.setDOCUMENTTYPESid(ecsDocumentTypes);
                pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap);
                if (oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap != null) {
                    oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMapCollection().remove(pricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap);
                    oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap = em.merge(oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionPricelistInternalProductcodeDocumentMap);
                }
            }
            for (CdFileDetails cdFileDetailsCollectionCdFileDetails : ecsDocumentTypes.getCdFileDetailsCollection()) {
                EcsDocumentTypes oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionCdFileDetails = cdFileDetailsCollectionCdFileDetails.getDOCUMENTTYPESidRef();
                cdFileDetailsCollectionCdFileDetails.setDOCUMENTTYPESidRef(ecsDocumentTypes);
                cdFileDetailsCollectionCdFileDetails = em.merge(cdFileDetailsCollectionCdFileDetails);
                if (oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionCdFileDetails != null) {
                    oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionCdFileDetails.getCdFileDetailsCollection().remove(cdFileDetailsCollectionCdFileDetails);
                    oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionCdFileDetails = em.merge(oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionCdFileDetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEcsDocumentTypes(ecsDocumentTypes.getId()) != null) {
                throw new PreexistingEntityException("EcsDocumentTypes " + ecsDocumentTypes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EcsDocumentTypes ecsDocumentTypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EcsDocumentTypes persistentEcsDocumentTypes = em.find(EcsDocumentTypes.class, ecsDocumentTypes.getId());
            Collection<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapCollectionOld = persistentEcsDocumentTypes.getPricelistInternalProductcodeDocumentMapCollection();
            Collection<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapCollectionNew = ecsDocumentTypes.getPricelistInternalProductcodeDocumentMapCollection();
            Collection<CdFileDetails> cdFileDetailsCollectionOld = persistentEcsDocumentTypes.getCdFileDetailsCollection();
            Collection<CdFileDetails> cdFileDetailsCollectionNew = ecsDocumentTypes.getCdFileDetailsCollection();
            List<String> illegalOrphanMessages = null;
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapCollectionOldPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapCollectionOld) {
                if (!pricelistInternalProductcodeDocumentMapCollectionNew.contains(pricelistInternalProductcodeDocumentMapCollectionOldPricelistInternalProductcodeDocumentMap)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapCollectionOldPricelistInternalProductcodeDocumentMap + " since its DOCUMENTTYPESid field is not nullable.");
                }
            }
            for (CdFileDetails cdFileDetailsCollectionOldCdFileDetails : cdFileDetailsCollectionOld) {
                if (!cdFileDetailsCollectionNew.contains(cdFileDetailsCollectionOldCdFileDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CdFileDetails " + cdFileDetailsCollectionOldCdFileDetails + " since its DOCUMENTTYPESidRef field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PricelistInternalProductcodeDocumentMap> attachedPricelistInternalProductcodeDocumentMapCollectionNew = new ArrayList<PricelistInternalProductcodeDocumentMap>();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMapToAttach : pricelistInternalProductcodeDocumentMapCollectionNew) {
                pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMapToAttach = em.getReference(pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMapToAttach.getClass(), pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMapToAttach.getPricelistIPCMAPID());
                attachedPricelistInternalProductcodeDocumentMapCollectionNew.add(pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMapToAttach);
            }
            pricelistInternalProductcodeDocumentMapCollectionNew = attachedPricelistInternalProductcodeDocumentMapCollectionNew;
            ecsDocumentTypes.setPricelistInternalProductcodeDocumentMapCollection(pricelistInternalProductcodeDocumentMapCollectionNew);
            Collection<CdFileDetails> attachedCdFileDetailsCollectionNew = new ArrayList<CdFileDetails>();
            for (CdFileDetails cdFileDetailsCollectionNewCdFileDetailsToAttach : cdFileDetailsCollectionNew) {
                cdFileDetailsCollectionNewCdFileDetailsToAttach = em.getReference(cdFileDetailsCollectionNewCdFileDetailsToAttach.getClass(), cdFileDetailsCollectionNewCdFileDetailsToAttach.getId());
                attachedCdFileDetailsCollectionNew.add(cdFileDetailsCollectionNewCdFileDetailsToAttach);
            }
            cdFileDetailsCollectionNew = attachedCdFileDetailsCollectionNew;
            ecsDocumentTypes.setCdFileDetailsCollection(cdFileDetailsCollectionNew);
            ecsDocumentTypes = em.merge(ecsDocumentTypes);
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapCollectionNew) {
                if (!pricelistInternalProductcodeDocumentMapCollectionOld.contains(pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap)) {
                    EcsDocumentTypes oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap.getDOCUMENTTYPESid();
                    pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap.setDOCUMENTTYPESid(ecsDocumentTypes);
                    pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap);
                    if (oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap != null && !oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap.equals(ecsDocumentTypes)) {
                        oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMapCollection().remove(pricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap);
                        oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap = em.merge(oldDOCUMENTTYPESidOfPricelistInternalProductcodeDocumentMapCollectionNewPricelistInternalProductcodeDocumentMap);
                    }
                }
            }
            for (CdFileDetails cdFileDetailsCollectionNewCdFileDetails : cdFileDetailsCollectionNew) {
                if (!cdFileDetailsCollectionOld.contains(cdFileDetailsCollectionNewCdFileDetails)) {
                    EcsDocumentTypes oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionNewCdFileDetails = cdFileDetailsCollectionNewCdFileDetails.getDOCUMENTTYPESidRef();
                    cdFileDetailsCollectionNewCdFileDetails.setDOCUMENTTYPESidRef(ecsDocumentTypes);
                    cdFileDetailsCollectionNewCdFileDetails = em.merge(cdFileDetailsCollectionNewCdFileDetails);
                    if (oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionNewCdFileDetails != null && !oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionNewCdFileDetails.equals(ecsDocumentTypes)) {
                        oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionNewCdFileDetails.getCdFileDetailsCollection().remove(cdFileDetailsCollectionNewCdFileDetails);
                        oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionNewCdFileDetails = em.merge(oldDOCUMENTTYPESidRefOfCdFileDetailsCollectionNewCdFileDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ecsDocumentTypes.getId();
                if (findEcsDocumentTypes(id) == null) {
                    throw new NonexistentEntityException("The ecsDocumentTypes with id " + id + " no longer exists.");
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
            EcsDocumentTypes ecsDocumentTypes;
            try {
                ecsDocumentTypes = em.getReference(EcsDocumentTypes.class, id);
                ecsDocumentTypes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ecsDocumentTypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapCollectionOrphanCheck = ecsDocumentTypes.getPricelistInternalProductcodeDocumentMapCollection();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapCollectionOrphanCheckPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EcsDocumentTypes (" + ecsDocumentTypes + ") cannot be destroyed since the PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapCollectionOrphanCheckPricelistInternalProductcodeDocumentMap + " in its pricelistInternalProductcodeDocumentMapCollection field has a non-nullable DOCUMENTTYPESid field.");
            }
            Collection<CdFileDetails> cdFileDetailsCollectionOrphanCheck = ecsDocumentTypes.getCdFileDetailsCollection();
            for (CdFileDetails cdFileDetailsCollectionOrphanCheckCdFileDetails : cdFileDetailsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EcsDocumentTypes (" + ecsDocumentTypes + ") cannot be destroyed since the CdFileDetails " + cdFileDetailsCollectionOrphanCheckCdFileDetails + " in its cdFileDetailsCollection field has a non-nullable DOCUMENTTYPESidRef field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ecsDocumentTypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EcsDocumentTypes> findEcsDocumentTypesEntities() {
        return findEcsDocumentTypesEntities(true, -1, -1);
    }

    public List<EcsDocumentTypes> findEcsDocumentTypesEntities(int maxResults, int firstResult) {
        return findEcsDocumentTypesEntities(false, maxResults, firstResult);
    }

    private List<EcsDocumentTypes> findEcsDocumentTypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EcsDocumentTypes.class));
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

    public EcsDocumentTypes findEcsDocumentTypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EcsDocumentTypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getEcsDocumentTypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EcsDocumentTypes> rt = cq.from(EcsDocumentTypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
