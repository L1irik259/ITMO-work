package org.example.forDataBase.Repository;

import java.util.List;

import org.example.forDataBase.modelForDB.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    List<UserData> findByDataTotalSquare(double userDataTotalSquare);
}
