package com.example.demorg.service;

import com.example.demorg.entity.User;
import com.example.demorg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if(user.isPresent()) {
            User usrDetail=user.get();
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .builder()
                    .username(usrDetail.getUserName())
                    .password(usrDetail.getPassword())
                    .roles(usrDetail.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found with UserName " +username);
    }
}
