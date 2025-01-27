package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.CUsuario;
import com.example.demo.model.Enum.EnumRol;
import com.example.demo.repository.IUsuarioRepository;

@SpringBootApplication
public class ApiJwtApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ApiJwtApplication.class, args);
	}

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private PasswordEncoder pass;
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("nahomi"+ pass.encode("nahomi123"));
		System.out.println("javier"+pass.encode("javier123"));
		
		if(iUsuarioRepository.count()==0) {
			CUsuario uAdmin=CUsuario.builder()
					.username("enrike rod keler")
					.email("erksg.10.26@gmail.com")
					.password(pass.encode("enrike123"))
					.rol(EnumRol.ADMINISTRADOR)
					.build();
			
			CUsuario uUser=CUsuario.builder()
					.username("angie jessica")
					.email("angie@gmail.com")
					.password(pass.encode("angie123"))
					.rol(EnumRol.USUARIO)
					.build();
			
			
			iUsuarioRepository.saveAll(List.of(uAdmin,uUser));
		}else {
			System.out.println("La Tabla Usuario ya tiene Registros.");
		}
		
	}

}
