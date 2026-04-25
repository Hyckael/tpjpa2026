package daoGeneric;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.EntityManagerHelper;

public abstract class AbstractJpaDao<K, T extends Serializable> implements IGenericDao<K, T> {

    private Class<T> clazz;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public EntityManager getEm() {
        return EntityManagerHelper.getEntityManagerFactory().createEntityManager();
    }

    public T findOne(K id) {
        EntityManager em = getEm();
        try {
            return em.find(clazz, id);
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = getEm();
        try {
            return em.createQuery("select e from " + clazz.getName() + " as e", clazz)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(T entity) {
        EntityManager em = getEm();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public T update(T entity) {
        EntityManager em = getEm();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T res = em.merge(entity);
            tx.commit();
            return res;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(T entity) {
        EntityManager em = getEm();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteById(K entityId) {
        delete(findOne(entityId));
    }
}