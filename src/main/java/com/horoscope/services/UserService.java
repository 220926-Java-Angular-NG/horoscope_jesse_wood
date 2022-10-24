package com.horoscope.services;

import com.horoscope.models.User;
import com.horoscope.repos.UserRepo;

import java.util.List;

public class UserService {
    private final UserRepo repo;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public UserService() {
        this(new UserRepo());
    }

    public int createUser(User user) {
        return repo.create(user);
    }

    public List<User> getAllUsers() {
        return repo.getAll();
    }

    public User getUserByID(int id) {
        return repo.getByID(id);
    }

    public User getUserByEmail(String email) { return repo.getByEmail(email); }

    public boolean updateUser(User user) {
        return user.equals(repo.update(user));
    }

    public boolean deleteUser(User user) {
        return repo.delete(user);
    }

    public boolean deleteUserByID(int id) { return repo.deleteByID(id); }

}
