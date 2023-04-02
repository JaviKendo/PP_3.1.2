package academy.kata.springboot.springboot.dao;

import academy.kata.springboot.springboot.model.User;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public List<User> getUsersCount(int count) {
        if (count >= 5) {
            return getAllUsers();
        }

        return getAllUsers().subList(0, count);
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User updatedUser) {
        if (getUserById(updatedUser.getId()) != null) {
            entityManager.merge(updatedUser);
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = getUserById(id);

        if (user != null) {
            entityManager.remove(user);
        }
    }

}