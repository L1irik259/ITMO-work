package org.example.forDataBase.Repository;

import java.util.List;

import org.example.forDataBase.modelForDB.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    List<User> findByuserName(String userName);
}
