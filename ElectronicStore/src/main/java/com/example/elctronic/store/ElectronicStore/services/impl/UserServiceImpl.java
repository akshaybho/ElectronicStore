package com.example.elctronic.store.ElectronicStore.services.impl;

import com.example.elctronic.store.ElectronicStore.dtos.PageableResponse;
import com.example.elctronic.store.ElectronicStore.dtos.UserDto;
import com.example.elctronic.store.ElectronicStore.entities.User;
import com.example.elctronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.example.elctronic.store.ElectronicStore.helper.Helper;
import com.example.elctronic.store.ElectronicStore.repositories.UserRepository;
import com.example.elctronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

        String fullPath = imagePath + user.getImageName();

        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }
        catch(FileNotFoundException ex)
        {
            logger.info("User image not found in folder");
            ex.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        return response;
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
        List <UserDto> userDto = users.stream().map(this::entityToDto).collect(Collectors.toList());
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
