package com.example.elctronic.store.ElectronicStore.dtos;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Min(value = 3, message = "title must be of minimum 3 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String coverImage;
}
