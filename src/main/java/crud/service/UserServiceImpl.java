package crud.service;

import crud.dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import crud.dao.UserDao;
import crud.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    @Override
    public void delete(int userId) {
        userDao.delete(userId);
    }

    @Transactional
    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    @Transactional
    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

}
