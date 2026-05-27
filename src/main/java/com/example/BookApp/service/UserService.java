package com.example.BookApp.service;

import com.example.BookApp.model.UserEntity;
import com.example.BookApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =  userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        return
                User
                        .withUsername(user.getName())
                        .password(user.getPassword())
                        .roles(user.getRole().replace("ROLE_",""))
                        .build();
    }
}
