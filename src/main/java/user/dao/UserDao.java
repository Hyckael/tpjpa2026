package user.dao;

import daoGeneric.AbstractJpaDao;
import jakarta.persistence.NoResultException;
import user.entity.User;

public class UserDao extends AbstractJpaDao< Long,User> {
    public UserDao() {
        this.setClazz(User.class);
    }

    public User findByEmail(String email){
        try{
            return entityManager.createQuery(
                    "SELECT u FROM User as u WHERE u.email = :email", User.class
            ).setParameter("email", email).getSingleResult();
        } catch(NoResultException e){return null;}
    }
}
