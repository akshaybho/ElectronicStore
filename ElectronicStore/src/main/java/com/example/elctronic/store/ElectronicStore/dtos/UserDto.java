package com.example.elctronic.store.ElectronicStore.dtos;

import com.example.elctronic.store.ElectronicStore.validator.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3, max = 70, message = "Invalid Name!!")
    private String name;

    @Email(message = "Invalid User email")
    private String email;

    @NotBlank(message = "Password is required!!")
    private String password;

    private String gender;

    private String about;

    private String imageName;
}
