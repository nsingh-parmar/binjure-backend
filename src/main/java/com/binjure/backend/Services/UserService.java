package com.binjure.backend.Services;

import com.binjure.backend.Models.UserModel;
import com.binjure.backend.Models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }
    
    public String getNextUserID() {
        final String prefix = "usr";
        List<UserModel> allUsers = getUsers();
        if(allUsers.isEmpty()){
            return prefix + 1;
        } else {
            ArrayList<Integer> userIds = new ArrayList<>();
            for (UserModel u : allUsers) {
                userIds.add(Integer.parseInt(u.getId().split(prefix)[1]));
            }
            int nextID = Collections.max(userIds) + 1;
            return prefix + nextID;
        }
    }
    
    public UserModel createUser(UserModel user) {
        String nextID = getNextUserID();
        user.setId(nextID);
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.insert(user);
    }


    public UserModel getUser(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(id));
        return mongoTemplate.findOne(query, UserModel.class);
    }

    public UserModel getUserByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, UserModel.class);
    }

    public boolean deleteUser(String id) {
        UserModel preDeletion = getUser(id);
        try {
            String userId = preDeletion.getId();
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(id));
            UserModel deleted = mongoTemplate.findAndRemove(query, UserModel.class);
            try {
                UserModel verify = getUser(deleted.getId());
                return true;
            } catch (NullPointerException nex) {
                return false;
            }
        } catch (NullPointerException nex) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        UserModel userMatch = userRepository.findByEmail(emailAddress);
        String userEmail = userMatch.getEmail();
        String passWord = userMatch.getPassword();
        return new User(userEmail, passWord, new ArrayList<>());
    }
}