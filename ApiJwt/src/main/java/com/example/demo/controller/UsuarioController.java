package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.dto.*;
import com.example.demo.service.IUsuarioService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping
	ResponseEntity<List<UsuarioResDto>> listarUsuarios(){
		return new ResponseEntity<List<UsuarioResDto>>(usuarioService.listar(), HttpStatus.OK);
	}
	
	@PostMapping("/guardarUsuario")
	ResponseEntity<?> guardarUsuario(@RequestBody UsuarioReqDto usuario){
		try {
			return new ResponseEntity<>(usuarioService.guardarUsuario(usuario),HttpStatus.CREATED);} 
		catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);}	
	}
	
	@PutMapping("/actualizar/{id}")
	ResponseEntity<?> actualizarUsuario(@PathVariable(name="id") Integer cod, @RequestBody UsuarioReqDto usuario ){
		try {
			return new ResponseEntity<>(usuarioService.actualizarUsuario(cod,usuario),HttpStatus.OK);} 
		catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);}
	}
	
	@DeleteMapping("/eliminar/{id}")
	ResponseEntity<String> eliminarUsuario(@PathVariable(name="id")Integer cod ){
		try {
			return new ResponseEntity<>(usuarioService.eliminarUsuario(cod),HttpStatus.OK);} 
		catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);}
	}
	
}
