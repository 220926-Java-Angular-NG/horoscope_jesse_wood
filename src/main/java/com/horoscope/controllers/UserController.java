package com.horoscope.controllers;

import com.horoscope.models.User;
import com.horoscope.services.UserService;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public UserController() {
        this(new UserService());
    }

    public Handler login = context -> {
        if (CurrentUser.currentUser == null) {
            User loginAttempt = context.bodyAsClass(User.class);
            User accountFound = service.getUserByEmail(loginAttempt.getEmail());
            if (accountFound != null && accountFound.getPassword().equals(loginAttempt.getPassword())) {
                CurrentUser.currentUser = accountFound;
                context.json(accountFound).status(200);
            } else {
                context.result("Invalid Login Credentials.").status(404);
            }
        } else {
            context.result("Already logged in.").status(400);
        }
    };

    public Handler logout = context -> {
        if (CurrentUser.currentUser != null) {
            CurrentUser.currentUser = null;
            context.result("Successfully logged out.").status(200);
        } else {
            context.result("Already logged out.").status(400);
        }
    };

    public Handler signup = context -> {
        if (CurrentUser.currentUser == null) {
            User newUser = context.bodyAsClass(User.class);
            if (service.getUserByEmail(newUser.getEmail()) == null) {
                int id = service.createUser(newUser);
                if (id > 0) {
                    newUser = service.getUserByID(id);
                    CurrentUser.currentUser = newUser;
                    context.json(newUser).status(200);
                } else {
                    context.result("Invalid credentials.").status(400);
                }
            } else {
                context.result("This user already exists.").status(400);
            }
        } else {
            context.result("Please logout first.").status(409);
        }
    };

    public Handler updateMood = context -> {
        if (CurrentUser.currentUser != null) {
            User user = context.bodyAsClass(User.class);
            if (user.getId() != CurrentUser.currentUser.getId())
                user.setId(CurrentUser.currentUser.getId());
            if (!user.getFirstName().equals(CurrentUser.currentUser.getFirstName()))
                user.setFirstName(CurrentUser.currentUser.getFirstName());
            if (!user.getLastName().equals(CurrentUser.currentUser.getLastName()))
                user.setLastName(CurrentUser.currentUser.getLastName());
            if (!user.getPassword().equals(CurrentUser.currentUser.getPassword()))
                user.setPassword(CurrentUser.currentUser.getPassword());
            if (!user.getZodiac().equals(CurrentUser.currentUser.getZodiac()))
                user.setZodiac(CurrentUser.currentUser.getZodiac());

            if (service.updateUser(user)) {
                context.json(user).status(201);
            } else {
                context.json(user).status(200);
            }
        } else {
            context.result("Please login.").status(400);
        }
    };
}
