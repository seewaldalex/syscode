package com.syscode.syscodedemo.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class StudentUpdateDto implements Serializable {

    @NotBlank
    String name;

    @Email
    @NotBlank
    @Size(max = 100)
    String email;

}
