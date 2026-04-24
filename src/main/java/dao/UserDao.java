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
            return em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class
            ).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
