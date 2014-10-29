/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselayer.ecs_kesws.entities.controllers;

import databaselayer.ecs_kesws.entities.Pricelist;
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
public class PricelistJpaController implements Serializable {

    public PricelistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pricelist pricelist) {
        if (pricelist.getPricelistInternalProductcodeDocumentMaps() == null) {
            pricelist.setPricelistInternalProductcodeDocumentMaps(new HashSet<PricelistInternalProductcodeDocumentMap>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<PricelistInternalProductcodeDocumentMap> attachedPricelistInternalProductcodeDocumentMaps = new HashSet<PricelistInternalProductcodeDocumentMap>();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach : pricelist.getPricelistInternalProductcodeDocumentMaps()) {
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach = em.getReference(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach.getClass(), pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach.getPricelistIpcMapId());
                attachedPricelistInternalProductcodeDocumentMaps.add(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMapToAttach);
            }
            pricelist.setPricelistInternalProductcodeDocumentMaps(attachedPricelistInternalProductcodeDocumentMaps);
            em.persist(pricelist);
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap : pricelist.getPricelistInternalProductcodeDocumentMaps()) {
                Pricelist oldPricelistOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.getPricelist();
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.setPricelist(pricelist);
                pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                if (oldPricelistOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap != null) {
                    oldPricelistOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                    oldPricelistOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap = em.merge(oldPricelistOfPricelistInternalProductcodeDocumentMapsPricelistInternalProductcodeDocumentMap);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pricelist pricelist) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pricelist persistentPricelist = em.find(Pricelist.class, pricelist.getPriceId());
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsOld = persistentPricelist.getPricelistInternalProductcodeDocumentMaps();
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsNew = pricelist.getPricelistInternalProductcodeDocumentMaps();
            List<String> illegalOrphanMessages = null;
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsOld) {
                if (!pricelistInternalProductcodeDocumentMapsNew.contains(pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapsOldPricelistInternalProductcodeDocumentMap + " since its pricelist field is not nullable.");
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
            pricelist.setPricelistInternalProductcodeDocumentMaps(pricelistInternalProductcodeDocumentMapsNew);
            pricelist = em.merge(pricelist);
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsNew) {
                if (!pricelistInternalProductcodeDocumentMapsOld.contains(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap)) {
                    Pricelist oldPricelistOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.getPricelist();
                    pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.setPricelist(pricelist);
                    pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = em.merge(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                    if (oldPricelistOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap != null && !oldPricelistOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.equals(pricelist)) {
                        oldPricelistOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap.getPricelistInternalProductcodeDocumentMaps().remove(pricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                        oldPricelistOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap = em.merge(oldPricelistOfPricelistInternalProductcodeDocumentMapsNewPricelistInternalProductcodeDocumentMap);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pricelist.getPriceId();
                if (findPricelist(id) == null) {
                    throw new NonexistentEntityException("The pricelist with id " + id + " no longer exists.");
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
            Pricelist pricelist;
            try {
                pricelist = em.getReference(Pricelist.class, id);
                pricelist.getPriceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pricelist with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<PricelistInternalProductcodeDocumentMap> pricelistInternalProductcodeDocumentMapsOrphanCheck = pricelist.getPricelistInternalProductcodeDocumentMaps();
            for (PricelistInternalProductcodeDocumentMap pricelistInternalProductcodeDocumentMapsOrphanCheckPricelistInternalProductcodeDocumentMap : pricelistInternalProductcodeDocumentMapsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pricelist (" + pricelist + ") cannot be destroyed since the PricelistInternalProductcodeDocumentMap " + pricelistInternalProductcodeDocumentMapsOrphanCheckPricelistInternalProductcodeDocumentMap + " in its pricelistInternalProductcodeDocumentMaps field has a non-nullable pricelist field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pricelist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pricelist> findPricelistEntities() {
        return findPricelistEntities(true, -1, -1);
    }

    public List<Pricelist> findPricelistEntities(int maxResults, int firstResult) {
        return findPricelistEntities(false, maxResults, firstResult);
    }

    private List<Pricelist> findPricelistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pricelist.class));
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

    public Pricelist findPricelist(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pricelist.class, id);
        } finally {
            em.close();
        }
    }

    public int getPricelistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pricelist> rt = cq.from(Pricelist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
