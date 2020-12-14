package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    UserController userController;
    private UserRepository userRepository=mock(UserRepository.class);
    private CartRepository cartRepository=mock(CartRepository.class);
    private BCryptPasswordEncoder encoder=mock(BCryptPasswordEncoder.class);
    @Before
    public void setup(){
        userController = new UserController();
        TestUtils.injectsObjects(userController, "userRepository", userRepository);
        TestUtils.injectsObjects(userController, "userRepository", cartRepository);
        TestUtils.injectsObjects(userController, "userRepository", encoder);
    }

    @Test
    public void create_user_happy_path() throws Exception{
        when(encoder.encode("testPassword")).thenReturn("this is Hashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmedPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0,user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("this is Hashed", user.getPassword());

    }
}