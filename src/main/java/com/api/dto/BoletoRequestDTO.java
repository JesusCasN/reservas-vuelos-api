package com.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class BoletoRequestDTO implements Serializable {

    @NotBlank(message = "El campo pdfUrl no puede estar vacío")
    private String pdfUrl;

}
