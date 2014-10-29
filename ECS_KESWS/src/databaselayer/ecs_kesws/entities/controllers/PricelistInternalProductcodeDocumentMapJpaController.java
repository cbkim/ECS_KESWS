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
import databaselayer.ecs_kesws.entities.EcsDocumentTypes;
import databaselayer.ecs_kesws.entities.InternalProductcodes;
import databaselayer.ecs_kesws.entities.Pricelist;
import databaselayer.ecs_kesws.entities.CdFileDetails;
import databaselayer.ecs_kesws.entities.PricelistInternalProductcodeDocumentMap;
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
public class PricelistInternalProductcodeDocumentMapJpaController implements Serializable {

    public PricelistInternalProductcodeDocumentMapJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMap) {
        if (pricelistInternalProductcodeDocumentMap.getCdFileDetailses() == null) {
            pricelistInternalProductcodeDocumentMap.setCdFileDetailses(new HashSet<CdFileDetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EcsDocumentTypes ecsDocumentTypes = pricelistInternalProductcodeDocumentMap.getEcsDocumentTypes();
            if (ecsDocumentTypes != null) {
                ecsDocumentTypes = em.getReference(ecsDocumentTypes.getClass(), ecsDocumentTypes.getId());
                pricelistInternalProductcodeDocumentMap.setEcsDocumentTypes(ecsDocumentTypes);
            }
            InternalProductcodes internalProductcodes = pricelistInternalProductcodeDocumentMap.getInternalProductcodes();
            if (internalProductcodes != null) {
                internalProductcodes = em.getReference(internalProductcodes.getClass(), internalProductcodes.getIpcId());
                pricelistInternalProductcodeDocumentMap.setInternalProductcodes(internalProductcodes);
            }
            Pricelist pricelist = pricelistInternalProductcodeDocumentMap.getPricelist();
            if (pricelist != null) {
                pricelist = em.getReference(pricelist.getClass(), pricelist.getPriceId());
                pricelistInternalProductcodeDocumentMap.setPricelist(pricelist);
            }
            Set<CdFileDetails> attachedCdFileDetailses = new HashSet<CdFileDetails>();
            for (CdFileDetails cdFileDetailsesCdFileDetailsToAttach : pricelistInternalProductcodeDocumentMap.getCdFileDetailses()) {
                cdFileDetailsesCdFileDetailsToAttach = em.getReference(cdFileDetailsesCdFileDetailsToAttach.getClass(), cdFileDetailsesCdFileDetailsToAttach.getId());
                attachedCdFileDetailses.add(cdFileDetailsesCdFileDetailsToAttach);
            }
            pricelistInternalProductcodeDocumentMap.setCdFileDetailses(attachedCdFileDetailses);
            em.persist(pricelistInternalProductcodeDocumentMap);
            if (ecsDocumentTypes != null) {
                ecsDocumentTypes.getPricelistInternalProductcodeDocumentMaps().add(pricelistInternalProductcodeDocumentMap);
                ecsDocumentTypes = em.merge(ecsDocumentTypes);
            }
            if (internalProductcodes != null) {
                internalProductcodes.getPricelistInternalProductcodeDocumentMaps().add(pricelistInternalProductcodeDocumentMap);
                internalProductcodes = em.merge(internalProductcodes);
            }
            if (pricelist != null) {
                pricelist.getPricelistInternalProductcodeDocumentMaps().add(pricelistInternalProductcodeDocumentMap);
                pricelist = em.merge(pricelist);
            }
            for (CdFileDetails cdFileDetailsesCdFileDetails : pricelistInternalProductcodeDocumentMap.getCdFileDetailses()) {
                PricelistInternalProductcodeDocumentMap oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesCdFileDetails = cdFileDetailsesCdFileDetails.getPricelistInternalProductcodeDocumentMap();
                cdFileDetailsesCdFileDetails.setPricelistInternalProductcodeDocumentMap(pricelistInternalProductcodeDocumentMap);
                cdFileDetailsesCdFileDetails = em.merge(cdFileDetailsesCdFileDetails);
                if (oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesCdFileDetails != null) {
                    oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesCdFileDetails.getCdFileDetailses().remove(cdFileDetailsesCdFileDetails);
                    oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesCdFileDetails = em.merge(oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesCdFileDetails);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMap) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PricelistInternalProductcodeDocumentMap persistentPricelistInternalProductcodeDocumentMap = em.find(PricelistInternalProductcodeDocumentMap.class, pricelistInternalProductcodeDocumentMap.getPricelistIpcMapId());
            EcsDocumentTypes ecsDocumentTypesOld = persistentPricelistInternalProductcodeDocumentMap.getEcsDocumentTypes();
            EcsDocumentTypes ecsDocumentTypesNew = pricelistInternalProductcodeDocumentMap.getEcsDocumentTypes();
            InternalProductcodes internalProductcodesOld = persistentPricelistInternalProductcodeDocumentMap.getInternalProductcodes();
            InternalProductcodes internalProductcodesNew = pricelistInternalProductcodeDocumentMap.getInternalProductcodes();
            Pricelist pricelistOld = persistentPricelistInternalProductcodeDocumentMap.getPricelist();
            Pricelist pricelistNew = pricelistInternalProductcodeDocumentMap.getPricelist();
            Set<CdFileDetails> cdFileDetailsesOld = persistentPricelistInternalProductcodeDocumentMap.getCdFileDetailses();
            Set<CdFileDetails> cdFileDetailsesNew = pricelistInternalProductcodeDocumentMap.getCdFileDetailses();
            List<String> illegalOrphanMessages = null;
            for (CdFileDetails cdFileDetailsesOldCdFileDetails : cdFileDetailsesOld) {
                if (!cdFileDetailsesNew.contains(cdFileDetailsesOldCdFileDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CdFileDetails " + cdFileDetailsesOldCdFileDetails + " since its pricelistInternalProductcodeDocumentMap field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ecsDocumentTypesNew != null) {
                ecsDocumentTypesNew = em.getReference(ecsDocumentTypesNew.getClass(), ecsDocumentTypesNew.getId());
                pricelistInternalProductcodeDocumentMap.setEcsDocumentTypes(ecsDocumentTypesNew);
            }
            if (internalProductcodesNew != null) {
                internalProductcodesNew = em.getReference(internalProductcodesNew.getClass(), internalProductcodesNew.getIpcId());
                pricelistInternalProductcodeDocumentMap.setInternalProductcodes(internalProductcodesNew);
            }
            if (pricelistNew != null) {
                pricelistNew = em.getReference(pricelistNew.getClass(), pricelistNew.getPriceId());
                pricelistInternalProductcodeDocumentMap.setPricelist(pricelistNew);
            }
            Set<CdFileDetails> attachedCdFileDetailsesNew = new HashSet<CdFileDetails>();
            for (CdFileDetails cdFileDetailsesNewCdFileDetailsToAttach : cdFileDetailsesNew) {
                cdFileDetailsesNewCdFileDetailsToAttach = em.getReference(cdFileDetailsesNewCdFileDetailsToAttach.getClass(), cdFileDetailsesNewCdFileDetailsToAttach.getId());
                attachedCdFileDetailsesNew.add(cdFileDetailsesNewCdFileDetailsToAttach);
            }
            cdFileDetailsesNew = attachedCdFileDetailsesNew;
            pricelistInternalProductcodeDocumentMap.setCdFileDetailses(cdFileDetailsesNew);
            pricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMap);
            if (ecsDocumentTypesOld != null && !ecsDocumentTypesOld.equals(ecsDocumentTypesNew)) {
                ecsDocumentTypesOld.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMap);
                ecsDocumentTypesOld = em.merge(ecsDocumentTypesOld);
            }
            if (ecsDocumentTypesNew != null && !ecsDocumentTypesNew.equals(ecsDocumentTypesOld)) {
                ecsDocumentTypesNew.getPricelistInternalProductcodeDocumentMaps().add(pricelistInternalProductcodeDocumentMap);
                ecsDocumentTypesNew = em.merge(ecsDocumentTypesNew);
            }
            if (internalProductcodesOld != null && !internalProductcodesOld.equals(internalProductcodesNew)) {
                internalProductcodesOld.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMap);
                internalProductcodesOld = em.merge(internalProductcodesOld);
            }
            if (internalProductcodesNew != null && !internalProductcodesNew.equals(internalProductcodesOld)) {
                internalProductcodesNew.getPricelistInternalProductcodeDocumentMaps().add(pricelistInternalProductcodeDocumentMap);
                internalProductcodesNew = em.merge(internalProductcodesNew);
            }
            if (pricelistOld != null && !pricelistOld.equals(pricelistNew)) {
                pricelistOld.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMap);
                pricelistOld = em.merge(pricelistOld);
            }
            if (pricelistNew != null && !pricelistNew.equals(pricelistOld)) {
                pricelistNew.getPricelistInternalProductcodeDocumentMaps().add(pricelistInternalProductcodeDocumentMap);
                pricelistNew = em.merge(pricelistNew);
            }
            for (CdFileDetails cdFileDetailsesNewCdFileDetails : cdFileDetailsesNew) {
                if (!cdFileDetailsesOld.contains(cdFileDetailsesNewCdFileDetails)) {
                    PricelistInternalProductcodeDocumentMap oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesNewCdFileDetails = cdFileDetailsesNewCdFileDetails.getPricelistInternalProductcodeDocumentMap();
                    cdFileDetailsesNewCdFileDetails.setPricelistInternalProductcodeDocumentMap(pricelistInternalProductcodeDocumentMap);
                    cdFileDetailsesNewCdFileDetails = em.merge(cdFileDetailsesNewCdFileDetails);
                    if (oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesNewCdFileDetails != null && !oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesNewCdFileDetails.equals(pricelistInternalProductcodeDocumentMap)) {
                        oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesNewCdFileDetails.getCdFileDetailses().remove(cdFileDetailsesNewCdFileDetails);
                        oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesNewCdFileDetails = em.merge(oldPricelistInternalProductcodeDocumentMapOfCdFileDetailsesNewCdFileDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pricelistInternalProductcodeDocumentMap.getPricelistIpcMapId();
                if (findPricelistInternalProductcodeDocumentMap(id) == null) {
                    throw new NonexistentEntityException("The pricelistInternalProductcodeDocumentMap with id " + id + " no longer exists.");
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
            PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMap;
            try {
                pricelistInternalProductcodeDocumentMap = em.getReference(PricelistInternalProductcodeDocumentMap.class, id);
                pricelistInternalProductcodeDocumentMap.getPricelistIpcMapId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pricelistInternalProductcodeDocumentMap with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<CdFileDetails> cdFileDetailsesOrphanCheck = pricelistInternalProductcodeDocumentMap.getCdFileDetailses();
            for (CdFileDetails cdFileDetailsesOrphanCheckCdFileDetails : cdFileDetailsesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PricelistInternalProductcodeDocumentMap (" + pricelistInternalProductcodeDocumentMap + ") cannot be destroyed since the CdFileDetails " + cdFileDetailsesOrphanCheckCdFileDetails + " in its cdFileDetailses field has a non-nullable pricelistInternalProductcodeDocumentMap field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EcsDocumentTypes ecsDocumentTypes = pricelistInternalProductcodeDocumentMap.getEcsDocumentTypes();
            if (ecsDocumentTypes != null) {
                ecsDocumentTypes.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMap);
                ecsDocumentTypes = em.merge(ecsDocumentTypes);
            }
            InternalProductcodes internalProductcodes = pricelistInternalProductcodeDocumentMap.getInternalProductcodes();
            if (internalProductcodes != null) {
                internalProductcodes.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMap);
                internalProductcodes = em.merge(internalProductcodes);
            }
            Pricelist pricelist = pricelistInternalProductcodeDocumentMap.getPricelist();
            if (pricelist != null) {
                pricelist.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMap);
                pricelist = em.merge(pricelist);
            }
            em.remove(pricelistInternalProductcodeDocumentMap);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PricelistInternalProductcodeDocumentMap> findPricelistInternalProductcodeDocumentMapEntities() {
        return findPricelistInternalProductcodeDocumentMapEntities(true, -1, -1);
    }

    public List<PricelistInternalProductcodeDocumentMap> findPricelistInternalProductcodeDocumentMapEntities(int maxResults, int firstResult) {
        return findPricelistInternalProductcodeDocumentMapEntities(false, maxResults, firstResult);
    }

    private List<PricelistInternalProductcodeDocumentMap> findPricelistInternalProductcodeDocumentMapEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PricelistInternalProductcodeDocumentMap.class));
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

    public PricelistInternalProductcodeDocumentMap findPricelistInternalProductcodeDocumentMap(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PricelistInternalProductcodeDocumentMap.class, id);
        } finally {
            em.close();
        }
    }

    public int getPricelistInternalProductcodeDocumentMapCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PricelistInternalProductcodeDocumentMap> rt = cq.from(PricelistInternalProductcodeDocumentMap.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
