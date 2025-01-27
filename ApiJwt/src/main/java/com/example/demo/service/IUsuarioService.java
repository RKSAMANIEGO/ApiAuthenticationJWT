package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.AuthReqDto;
import com.example.demo.model.dto.AuthResDto;
import com.example.demo.model.dto.UsuarioReqDto;
import com.example.demo.model.dto.UsuarioResDto;

public interface IUsuarioService {

	AuthResDto inicioSession(AuthReqDto usuario);
	List<UsuarioResDto> listar();
	UsuarioResDto guardarUsuario(UsuarioReqDto usuario);
	UsuarioResDto actualizarUsuario(Integer id, UsuarioReqDto usuario);
	String eliminarUsuario(Integer id);
	
}
