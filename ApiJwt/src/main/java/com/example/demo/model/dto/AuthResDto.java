package com.example.demo.model.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResDto {

	private String username;
	private String message;
	private String token;
}
