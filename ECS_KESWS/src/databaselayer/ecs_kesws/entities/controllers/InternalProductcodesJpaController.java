/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import databaselayer.ecs_kesws.entities.InternalProductcodes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class InternalProductcodesJpaController implements Serializable {

    public InternalProductcodesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InternalProductcodes internalProductcodes) {
        if (internalProductcodes.getPricelistInternalProductcodeDocumentMaps() == null) {
            internalProductcodes.setPricelistInternalProductcodeDocumentMaps(new HashSet<PricelistInternalProductcodeDocumentMap>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<PricelistInternalProductcodeDocumentMap> attachedPricelistInternalProductcodeDocumentMaps = new HashSet<PricelistInternalProductcodeDocumentMap>();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach : internalProductcodes.getPricelistInternalProductcodeDocumentMaps()) {
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach = em.getReference(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach.getClass(), pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach.getPricelistIpcMapId());
                attachedPricelistInternalProductcodeDocumentMaps.add(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach);
            }
            internalProductcodes.setPricelistInternalProductcodeDocumentMaps(attachedPricelistInternalProductcodeDocumentMaps);
            em.persist(internalProductcodes);
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap : internalProductcodes.getPricelistInternalProductcodeDocumentMaps()) {
                InternalProductcodes oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.getInternalProductcodes();
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.setInternalProductcodes(internalProductcodes);
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                if (oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap != null) {
                    oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                    oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = em.merge(oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InternalProductcodes internalProductcodes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InternalProductcodes persistentInternalProductcodes = em.find(InternalProductcodes.class, internalProductcodes.getIpcId());
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsOld = persistentInternalProductcodes.getPricelistInternalProductcodeDocumentMaps();
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsNew = internalProductcodes.getPricelistInternalProductcodeDocumentMaps();
            List<String> illegalOrphanMessages = null;
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsOld) {
                if (!pricelistInternalProductcodeDocumentMapsNew.contains(pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap + " since its internalProductcodes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<PricelistInternalProductcodeDocumentMap> attachedPricelistInternalProductcodeDocumentMapsNew = new HashSet<PricelistInternalProductcodeDocumentMap>();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach : pricelistInternalProductcodeDocumentMapsNew) {
                pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach = em.getReference(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach.getClass(), pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach.getPricelistIpcMapId());
                attachedPricelistInternalProductcodeDocumentMapsNew.add(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMapToAttach);
            }
            pricelistInternalProductcodeDocumentMapsNew = attachedPricelistInternalProductcodeDocumentMapsNew;
            internalProductcodes.setPricelistInternalProductcodeDocumentMaps(pricelistInternalProductcodeDocumentMapsNew);
            internalProductcodes = em.merge(internalProductcodes);
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsNew) {
                if (!pricelistInternalProductcodeDocumentMapsOld.contains(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap)) {
                    InternalProductcodes oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.getInternalProductcodes();
                    pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.setInternalProductcodes(internalProductcodes);
                    pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                    if (oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap != null && !oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.equals(internalProductcodes)) {
                        oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                        oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = em.merge(oldInternalProductcodesOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = internalProductcodes.getIpcId();
                if (findInternalProductcodes(id) == null) {
                    throw new NonexistentEntityException("The internalProductcodes with id " + id + " no longer exists.");
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
            InternalProductcodes internalProductcodes;
            try {
                internalProductcodes = em.getReference(InternalProductcodes.class, id);
                internalProductcodes.getIpcId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The internalProductcodes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsOrphanCheck = internalProductcodes.getPricelistInternalProductcodeDocumentMaps();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsOrphanCheckPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InternalProductcodes (" + internalProductcodes + ") cannot be destroyed since the PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapsOrphanCheckPricelistInternalProductcodeDocumentMap + " in its pricelistInternalProductcodeDocumentMaps field has a non-nullable internalProductcodes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(internalProductcodes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InternalProductcodes> findInternalProductcodesEntities() {
        return findInternalProductcodesEntities(true, -1, -1);
    }

    public List<InternalProductcodes> findInternalProductcodesEntities(int maxResults, int firstResult) {
        return findInternalProductcodesEntities(false, maxResults, firstResult);
    }

    private List<InternalProductcodes> findInternalProductcodesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InternalProductcodes.class));
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

    public InternalProductcodes findInternalProductcodes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InternalProductcodes.class, id);
        } finally {
            em.close();
        }
    }

    public int getInternalProductcodesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InternalProductcodes> rt = cq.from(InternalProductcodes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
