package com.syscode.syscodedemo.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class StudentCreateDto implements Serializable {

    @NotBlank
    String name;

    @Email
    @NotBlank
    @Size(max = 100)
    String email;

}
