package com.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponseDTO implements Serializable {
    private String token;
}