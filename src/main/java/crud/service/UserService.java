package crud.service;

import crud.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void update(User user);
    void delete(User user);
    User getUserById(int userId);
    List<User> getUserList();
}
