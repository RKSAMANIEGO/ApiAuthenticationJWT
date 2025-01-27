package com.example.demo.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResDto {
	
	private Integer id;
	private String username;
	private String email;
	private String password;
	private String rol;
}
