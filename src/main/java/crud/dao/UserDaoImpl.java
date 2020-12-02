package crud.dao;

import org.springframework.stereotype.Repository;

import crud.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(int userId) {
        entityManager.remove(
                entityManager.find(User.class, userId)
        );
    }

    @Override
    public User getUserById(int userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public List<User> getUserList() {
        return entityManager.createQuery("select u from User u")
                .getResultList();
    }
}
