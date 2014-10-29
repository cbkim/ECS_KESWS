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
import databaselayer.ecs_kesws.entities.CdFileDetails;
import databaselayer.ecs_kesws.entities.EcsDocumentTypes;
import java.util.HashSet;
import java.util.Set;
import databaselayer.ecs_kesws.entities.PricelistInternalProductcodeDocumentMap;
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
public class EcsDocumentTypesJpaController implements Serializable {

    public EcsDocumentTypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EcsDocumentTypes ecsDocumentTypes) throws PreexistingEntityException, Exception {
        if (ecsDocumentTypes.getCdFileDetailses() == null) {
            ecsDocumentTypes.setCdFileDetailses(new HashSet<CdFileDetails>());
        }
        if (ecsDocumentTypes.getPricelistInternalProductcodeDocumentMaps() == null) {
            ecsDocumentTypes.setPricelistInternalProductcodeDocumentMaps(new HashSet<PricelistInternalProductcodeDocumentMap>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<CdFileDetails> attachedCdFileDetailses = new HashSet<CdFileDetails>();
            for (CdFileDetails cdFileDetailsesCdFileDetailsToAttach : ecsDocumentTypes.getCdFileDetailses()) {
                cdFileDetailsesCdFileDetailsToAttach = em.getReference(cdFileDetailsesCdFileDetailsToAttach.getClass(), cdFileDetailsesCdFileDetailsToAttach.getId());
                attachedCdFileDetailses.add(cdFileDetailsesCdFileDetailsToAttach);
            }
            ecsDocumentTypes.setCdFileDetailses(attachedCdFileDetailses);
            Set<PricelistInternalProductcodeDocumentMap> attachedPricelistInternalProductcodeDocumentMaps = new HashSet<PricelistInternalProductcodeDocumentMap>();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach : ecsDocumentTypes.getPricelistInternalProductcodeDocumentMaps()) {
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach = em.getReference(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach.getClass(), pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach.getPricelistIpcMapId());
                attachedPricelistInternalProductcodeDocumentMaps.add(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach);
            }
            ecsDocumentTypes.setPricelistInternalProductcodeDocumentMaps(attachedPricelistInternalProductcodeDocumentMaps);
            em.persist(ecsDocumentTypes);
            for (CdFileDetails cdFileDetailsesCdFileDetails : ecsDocumentTypes.getCdFileDetailses()) {
                EcsDocumentTypes oldEcsDocumentTypesOfCdFileDetailsesCdFileDetails = cdFileDetailsesCdFileDetails.getEcsDocumentTypes();
                cdFileDetailsesCdFileDetails.setEcsDocumentTypes(ecsDocumentTypes);
                cdFileDetailsesCdFileDetails = em.merge(cdFileDetailsesCdFileDetails);
                if (oldEcsDocumentTypesOfCdFileDetailsesCdFileDetails != null) {
                    oldEcsDocumentTypesOfCdFileDetailsesCdFileDetails.getCdFileDetailses().remove(cdFileDetailsesCdFileDetails);
                    oldEcsDocumentTypesOfCdFileDetailsesCdFileDetails = em.merge(oldEcsDocumentTypesOfCdFileDetailsesCdFileDetails);
                }
            }
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap : ecsDocumentTypes.getPricelistInternalProductcodeDocumentMaps()) {
                EcsDocumentTypes oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.getEcsDocumentTypes();
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.setEcsDocumentTypes(ecsDocumentTypes);
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                if (oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap != null) {
                    oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                    oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = em.merge(oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
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
            Set<CdFileDetails> cdFileDetailsesOld = persistentEcsDocumentTypes.getCdFileDetailses();
            Set<CdFileDetails> cdFileDetailsesNew = ecsDocumentTypes.getCdFileDetailses();
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsOld = persistentEcsDocumentTypes.getPricelistInternalProductcodeDocumentMaps();
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsNew = ecsDocumentTypes.getPricelistInternalProductcodeDocumentMaps();
            List<String> illegalOrphanMessages = null;
            for (CdFileDetails cdFileDetailsesOldCdFileDetails : cdFileDetailsesOld) {
                if (!cdFileDetailsesNew.contains(cdFileDetailsesOldCdFileDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CdFileDetails " + cdFileDetailsesOldCdFileDetails + " since its ecsDocumentTypes field is not nullable.");
                }
            }
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsOld) {
                if (!pricelistInternalProductcodeDocumentMapsNew.contains(pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap + " since its ecsDocumentTypes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<CdFileDetails> attachedCdFileDetailsesNew = new HashSet<CdFileDetails>();
            for (CdFileDetails cdFileDetailsesNewCdFileDetailsToAttach : cdFileDetailsesNew) {
                cdFileDetailsesNewCdFileDetailsToAttach = em.getReference(cdFileDetailsesNewCdFileDetailsToAttach.getClass(), cdFileDetailsesNewCdFileDetailsToAttach.getId());
                attachedCdFileDetailsesNew.add(cdFileDetailsesNewCdFileDetailsToAttach);
            }
            cdFileDetailsesNew = attachedCdFileDetailsesNew;
            ecsDocumentTypes.setCdFileDetailses(cdFileDetailsesNew);
            Set<PricelistInternalProductcodeDocumentMap> attachedPricelistInternalProductcodeDocumentMapsNew = new HashSet<PricelistInternalProductcodeDocumentMap>();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach : pricelistInternalProductcodeDocumentMapsNew) {
                pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach = em.getReference(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach.getClass(), pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach.getPricelistIpcMapId());
                attachedPricelistInternalProductcodeDocumentMapsNew.add(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach);
            }
            pricelistInternalProductcodeDocumentMapsNew = attachedPricelistInternalProductcodeDocumentMapsNew;
            ecsDocumentTypes.setPricelistInternalProductcodeDocumentMaps(pricelistInternalProductcodeDocumentMapsNew);
            ecsDocumentTypes = em.merge(ecsDocumentTypes);
            for (CdFileDetails cdFileDetailsesNewCdFileDetails : cdFileDetailsesNew) {
                if (!cdFileDetailsesOld.contains(cdFileDetailsesNewCdFileDetails)) {
                    EcsDocumentTypes oldEcsDocumentTypesOfCdFileDetailsesNewCdFileDetails = cdFileDetailsesNewCdFileDetails.getEcsDocumentTypes();
                    cdFileDetailsesNewCdFileDetails.setEcsDocumentTypes(ecsDocumentTypes);
                    cdFileDetailsesNewCdFileDetails = em.merge(cdFileDetailsesNewCdFileDetails);
                    if (oldEcsDocumentTypesOfCdFileDetailsesNewCdFileDetails != null && !oldEcsDocumentTypesOfCdFileDetailsesNewCdFileDetails.equals(ecsDocumentTypes)) {
                        oldEcsDocumentTypesOfCdFileDetailsesNewCdFileDetails.getCdFileDetailses().remove(cdFileDetailsesNewCdFileDetails);
                        oldEcsDocumentTypesOfCdFileDetailsesNewCdFileDetails = em.merge(oldEcsDocumentTypesOfCdFileDetailsesNewCdFileDetails);
                    }
                }
            }
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsNew) {
                if (!pricelistInternalProductcodeDocumentMapsOld.contains(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap)) {
                    EcsDocumentTypes oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.getEcsDocumentTypes();
                    pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.setEcsDocumentTypes(ecsDocumentTypes);
                    pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                    if (oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap != null && !oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.equals(ecsDocumentTypes)) {
                        oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                        oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = em.merge(oldEcsDocumentTypesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = ecsDocumentTypes.getId();
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

    public void destroy(int id) throws IllegalOrphanException, NonexistentEntityException {
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
            Set<CdFileDetails> cdFileDetailsesOrphanCheck = ecsDocumentTypes.getCdFileDetailses();
            for (CdFileDetails cdFileDetailsesOrphanCheckCdFileDetails : cdFileDetailsesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EcsDocumentTypes (" + ecsDocumentTypes + ") cannot be destroyed since the CdFileDetails " + cdFileDetailsesOrphanCheckCdFileDetails + " in its cdFileDetailses field has a non-nullable ecsDocumentTypes field.");
            }
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsOrphanCheck = ecsDocumentTypes.getPricelistInternalProductcodeDocumentMaps();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsOrphanCheckPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EcsDocumentTypes (" + ecsDocumentTypes + ") cannot be destroyed since the PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapsOrphanCheckPricelistInternalProductcodeDocumentMap + " in its pricelistInternalProductcodeDocumentMaps field has a non-nullable ecsDocumentTypes field.");
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

    public EcsDocumentTypes findEcsDocumentTypes(int id) {
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
