package com.example.contactList.service;

import com.example.contactList.config.EncriptionConfig;
import com.example.contactList.domain.User;
import com.example.contactList.domain.enums.Role;
import com.example.contactList.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    private final EncriptionConfig encriptionConfig;

    @Autowired
    public UserService(EncriptionConfig encriptionConfig, UserRepo userRepo) {
        this.encriptionConfig = encriptionConfig;
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails = userRepo.findByUsername(s);
        return userRepo.findByUsername(s);
    }

    public boolean addUser(User user) {

        User userFromDb;

        if(user.getUsername() != null && !StringUtils.isEmpty(user.getUsername()) && user.getPassword() != null && !StringUtils.isEmpty(user.getPassword()))
            userFromDb = userRepo.findByUsername(user.getUsername());
        else return false;

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(encriptionConfig.getPasswordEncoder().encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }


}
