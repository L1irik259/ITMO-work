package org.example.forDataBase.modelForDB;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "Name")
    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserData> userDatas = new ArrayList<>();

    public User() { }

    public User(String userName) {
        this.userName = userName;
    }

    public int getUserId() { return this.userId; }
    
    public String getUserName() { return this.userName; }
    public void setUserName(String name) { this.userName = name; }

    public List<UserData> getUserDatas() { return this.userDatas; }
    public void setUserDatas(List<UserData> userDatas) { this.userDatas = userDatas; }
}
