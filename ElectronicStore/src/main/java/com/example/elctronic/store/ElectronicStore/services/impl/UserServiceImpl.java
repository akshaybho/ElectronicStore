package com.example.elctronic.store.ElectronicStore.services.impl;

import com.example.elctronic.store.ElectronicStore.dtos.UserDto;
import com.example.elctronic.store.ElectronicStore.entities.User;
import com.example.elctronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.example.elctronic.store.ElectronicStore.repositories.UserRepository;
import com.example.elctronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        String userId = UUID.randomUUID().toString();

        //dto -> entity
        User user = dtoToEntity(userDto);
        user.setUserId(userId);
        User savedUser = userRepository.save(user);

        //entity -> dto
        UserDto newDto = entityToDto(savedUser);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found!!"));

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);

        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found !!"));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List <User> users = userRepository.findAll();
        List <UserDto> userDto = users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        return userDto;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with "+userId+" is not found!!"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User with "+email+" is not found!!"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List <User> users = userRepository.findByNameContaining(keyword);
        List <UserDto> userDto = users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        return userDto;
    }

    //entity -> dto
    public UserDto entityToDto(User savedUser)
    {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .gender(savedUser.getGender())
//                .about(savedUser.getAbout())
//                .imageName(savedUser.getImageName())
//                .build();
        return mapper.map(savedUser, UserDto.class);
    }

    //dto -> entity
    public User dtoToEntity(UserDto userDto)
    {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .gender(userDto.getGender())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName()).build();
       return mapper.map(userDto, User.class);
    }
}
