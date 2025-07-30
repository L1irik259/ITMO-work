package org.example.forDataBase.service;

import java.util.List;
import java.util.Optional;

import org.example.forDataBase.Repository.UserRepository;
import org.example.forDataBase.modelForDB.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public void deleteUser(int login) {
        userRepository.deleteById(login);
    }
}
