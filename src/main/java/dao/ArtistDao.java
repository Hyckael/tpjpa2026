package dao;

import daoGeneric.AbstractJpaDao;
import dto.ArtistDto;
import entity.Artist;
import entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
    public Artist saveAndLinkToEvent(ArtistDto dto) {
        EntityManager em = getEm();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Artist artist = new Artist();
            artist.setName(dto.getName());
            artist.setGenre(dto.getGenre());
            artist.setBiography(dto.getBiography());
            artist.setCountry(dto.getCountry());

            em.persist(artist);

            // Lier à l'event si fourni
            if (dto.getEventId() != null) {
                Event event = em.find(Event.class, dto.getEventId());
                if (event != null) {
                    event.getArtists().add(artist);
                }
            }

            tx.commit();
            return artist;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
