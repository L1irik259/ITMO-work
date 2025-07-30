package org.example.forDataBase.service;

import java.util.List;
import java.util.Optional;

import org.example.forDataBase.modelForDB.UserData;
import org.example.forDataBase.Repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {
    private UserDataRepository userDataRepository; 

    @Autowired
    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    Optional<UserData> findById(int id) {
        return userDataRepository.findById(id);
    }

    public void saveUserData(UserData userData) {
        userDataRepository.save(userData);
    }

    public void deleteUserData(int id) {
        userDataRepository.deleteById(id);
    }

    public List<UserData> findByAll() {
        return userDataRepository.findAll();
    }
}
