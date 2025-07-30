package org.example;

import org.example.forDataBase.modelForDB.User;
import org.example.forDataBase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        User user = new User();
        user.setUserName("Kirill");

        userService.saveUser(user);

        System.out.println("Юзер успешно сохранён в базу!");
    }
}
