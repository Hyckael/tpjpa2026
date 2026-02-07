package dao;

import auth.User;

public class UserDao extends AbstractJpaDao{
    public UserDao() {
        setClazz(User.class);
    }

    public User findByEmail(String email){
        return entityManager.createQuery(
                "SELECT u FROM User as u WHERE u.email = :email", User.class
        ).setParameter("email", email).getSingleResult();
    }
}
