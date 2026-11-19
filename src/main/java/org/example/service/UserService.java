package org.example.service;

import org.example.Model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private final Map<Integer, User> users;

    public UserService(){
        users = new HashMap<>();
    }

    public User getUser(Integer id){
        return users.get(id);
    }

    public void setUser(Integer id, User user){
        users.put(id, user);
    }

    public boolean findUser(Integer id){
        return users.containsKey(id);
    }
}
