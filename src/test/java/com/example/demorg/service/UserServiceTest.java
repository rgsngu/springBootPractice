package com.example.demorg.service;

import com.example.demorg.entity.User;
import com.example.demorg.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
//    @CsvSource({
//            "rahul",
//            "akshit",
//            "ram",
//            "shyam"
//    })
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveUserName(User user){
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @ValueSource( strings ={
            "1,1,2",
            "2,19,10",
            "3,3,9"
    })
    public void test(int a, int b,int expected){
        assertEquals(expected,a+b);

    }

}
