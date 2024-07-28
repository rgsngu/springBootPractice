package com.example.demorg.service;

import com.example.demorg.entity.User;
import com.example.demorg.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUserName(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(User.builder().userName("ram").password("scwcewc").roles(new ArrayList<>()).build()));
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername("ram");
            Assertions.assertNotNull(userDetails);
        }
    }
}
