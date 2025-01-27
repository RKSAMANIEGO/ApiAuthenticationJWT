package com.example.demo.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioReqDto {

	@NotBlank(message="El nombre del usuario no puede estar vacio")
	@Size(min=3, message="EL usuario debe tener 3 caracteres como minimo" )
	private String username;
	
	@Email(message="Ingrese un email valido")
	@NotBlank(message="El email no puede estar vacio ni ser Nulo")
	private String email;
	
	@Size(min=8, message="La contraseña requiere minimo 8 caracteres")
	private String password;

	
	private String rol;
}
