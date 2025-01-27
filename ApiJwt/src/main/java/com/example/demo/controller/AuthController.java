package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.dto.AuthReqDto;
import com.example.demo.service.IUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired 
	private IUsuarioService iUsuarioService;
	
	@PostMapping("/log-in")
	ResponseEntity<?> logIn(@Valid @RequestBody AuthReqDto usuario){
		try {
			return new ResponseEntity<>(iUsuarioService.inicioSession(usuario) , HttpStatus.OK);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(e.getMessage() , HttpStatus.UNAUTHORIZED);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>(e.getMessage() , HttpStatus.UNAUTHORIZED);
		}	
	}
}
