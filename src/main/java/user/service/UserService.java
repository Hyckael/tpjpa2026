package user.service;

import dao.AbstractJpaDao;
import user.entity.Admin;
import user.entity.Client;
import user.entity.Organizer;
import user.entity.User;
import user.dao.UserDao;

import java.util.*;

public class UserService {
    UserDao userDao = new UserDao();
    public User createUser(String type, String name, String email, String password,String address,String phone) {
        User user;
        switch (type) {
            case "client":
                user = new Client();
                break;
            case "organizer":
                user = new Organizer();
                break;
            case "admin":
                user = new Admin();
                break;
            default:
                throw new IllegalArgumentException("Type inconnu");
        }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAdresse(address);
        user.setPhone(phone);
        user.setCreatedAt(new Date());

        userDao.save(user);
        return user;
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }
}
