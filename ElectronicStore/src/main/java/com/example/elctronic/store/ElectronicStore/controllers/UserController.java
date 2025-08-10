package com.example.elctronic.store.ElectronicStore.controllers;

import com.example.elctronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.example.elctronic.store.ElectronicStore.dtos.UserDto;
import com.example.elctronic.store.ElectronicStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
    {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable ("userId") String userId,
                                              @RequestBody UserDto userDto)
    {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
    {
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> allUsers = userService.getAllUser();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId)
    {
        UserDto singleUser = userService.getUserById(userId);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        UserDto singleUser = userService.getUserByEmail(email);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }
    //search user
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords)
    {
       List <UserDto> searchUserDetail = userService.searchUser(keywords);
        return new ResponseEntity<>(searchUserDetail, HttpStatus.OK);
    }
}
