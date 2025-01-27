package com.example.demo.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthReqDto {

	@Email (message = "Ingrese un Email valido")
	@NotBlank(message = "El email no puede ser nulo ni vacio")
	private String email;
	
	@NotBlank(message="La contraseña no puede estar vacio")
	@Size(min=8, message="La contraseña debe de tener 8 Caracteres como minimo")
	private String password;
	
}
