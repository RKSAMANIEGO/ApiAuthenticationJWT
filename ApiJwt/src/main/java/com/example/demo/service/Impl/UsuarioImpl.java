package com.example.demo.service.Impl;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Jwt.Utils;
import com.example.demo.model.CUsuario;
import com.example.demo.model.Enum.EnumRol;
import com.example.demo.model.dto.AuthReqDto;
import com.example.demo.model.dto.AuthResDto;
import com.example.demo.model.dto.UsuarioReqDto;
import com.example.demo.model.dto.UsuarioResDto;
import com.example.demo.model.mapper.UsuarioMapper;
import com.example.demo.repository.IUsuarioRepository;
import com.example.demo.service.IUsuarioService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioImpl implements IUsuarioService{

	private final IUsuarioRepository iUsuarioRepository;
	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder pass;
	private final AuthenticationManager authenticationManager;
	private final Utils utils;
	
	@Override
	public List<UsuarioResDto> listar() {
		List<CUsuario> usuarios= iUsuarioRepository.findAll();
		
		return usuarios.stream().map(usuario -> usuarioMapper.usuarioToDtores(usuario)).toList();
	
	}

	@Override
	public UsuarioResDto guardarUsuario(UsuarioReqDto usuario) {
		
		if( !iUsuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
		
		usuario.setRol(usuario.getRol().toUpperCase());
		usuario.setPassword(pass.encode(usuario.getPassword()));
		
		CUsuario usuarioSave= iUsuarioRepository.save(usuarioMapper.dtoReqToUsuario(usuario));
		return usuarioMapper.usuarioToDtores(usuarioSave);
		
		
		}
	
	
		
		throw new IllegalArgumentException("Email '"+usuario.getEmail()+"' ya Existe");
	}

	@Override
	public UsuarioResDto actualizarUsuario(Integer id, UsuarioReqDto usuario) {		
		CUsuario usuarioEncontrado=iUsuarioRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Usuario con ID '"+ id +"' no Existe..."));
		
		usuarioEncontrado.setUsername(usuario.getUsername()!=null ? usuario.getUsername() : usuarioEncontrado.getUsername());
		usuarioEncontrado.setEmail(usuario.getEmail()!=null ? usuario.getEmail() : usuarioEncontrado.getEmail());
		usuarioEncontrado.setPassword(usuario.getPassword()!=null ? usuario.getPassword() : usuarioEncontrado.getPassword());
		
		if(usuario.getRol()!=null) {
			usuarioEncontrado.setRol(EnumRol.valueOf(usuario.getRol()) );
		}
		
	 	CUsuario UsuarioSave = iUsuarioRepository.save(usuarioEncontrado);
	 	
	 	return usuarioMapper.usuarioToDtores(UsuarioSave);
	}

	@Override
	public String eliminarUsuario(Integer id) {
		
		CUsuario usuarioEncontrado=iUsuarioRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Usuario no Existe..."));
		
		usuarioEncontrado.setIsEnabled(false);
		iUsuarioRepository.save(usuarioEncontrado);
		
		return "Usuario '"+usuarioEncontrado.getUsername()+"' Eliminado" ;
	}

	@Override
	public AuthResDto inicioSession(AuthReqDto usuario) {
		
		CUsuario usuarioEncontrado=iUsuarioRepository.findByEmail(usuario.getEmail()).orElseThrow( ()-> new EntityNotFoundException("Usuario no Existe..."));
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword()));
			
			String token= utils.createToken(usuario.getEmail());
			AuthResDto dtoRes= new AuthResDto(usuarioEncontrado.getUsername(), "Bienvenido "+usuarioEncontrado.getUsername(), token );
			return dtoRes;
			

		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Credenciales Incorrectas "+e.getMessage() );
		}

	}
	

}
