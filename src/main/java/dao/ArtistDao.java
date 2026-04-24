package dao;

import daoGeneric.AbstractJpaDao;
import entity.Artist;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ArtistDao extends AbstractJpaDao<Long, Artist> {
    public ArtistDao() {this.setClazz(Artist.class);
    }
    public List<Artist> findByCountry(String country){
        EntityManager em = getEm();
        try {
            return em.createQuery(
                    "SELECT a FROM Artist as a WHERE a.country = :country", Artist.class
            ).setParameter("country", country).getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
