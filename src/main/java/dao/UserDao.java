package dao;

import daoGeneric.AbstractJpaDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import entity.User;

public class UserDao extends AbstractJpaDao< Long,User> {
    public UserDao() {
        this.setClazz(User.class);
    }

    public User findByEmail(String email) {
        EntityManager em = getEm();
        try {
            User user = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class
            ).setParameter("email", email).getSingleResult();

            // Force le chargement du vrai sous-type en faisant un refresh
            if (user != null) {
                em.refresh(user);
                // Forcer l'initialisation du proxy
                user.getClass().getName();
            }
            return user;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
