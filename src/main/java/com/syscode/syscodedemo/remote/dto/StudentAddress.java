package com.syscode.syscodedemo.remote.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class StudentAddress implements Serializable {

    Long id;

    @NotBlank
    @Size(max = 255)
    String address;

}