package com.example.demo.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.CUsuario;
import com.example.demo.repository.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final IUsuarioRepository iUsuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		CUsuario usuarioEncontrado = iUsuarioRepository.findByEmail(username).orElseThrow( () -> new UsernameNotFoundException("User '"+ username+"' Not Found"));
		
		List<SimpleGrantedAuthority> authority= new ArrayList<>();
		
		authority.add(new SimpleGrantedAuthority("ROLE_"+usuarioEncontrado.getRol().name()));
		
		return new User(usuarioEncontrado.getEmail(), usuarioEncontrado.getPassword(), true, true, true, true, authority);
	}

}
