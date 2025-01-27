package com.example.demo.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.demo.model.CUsuario;
import com.example.demo.model.Enum.EnumRol;
import com.example.demo.model.dto.UsuarioReqDto;
import com.example.demo.model.dto.UsuarioResDto;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	// CONVERT USUARIO REQUEST A USUARIO
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="isEnabled", ignore=true )
	@Mapping(target="rol", expression="java( convertToEnum(usuario.getRol()) )")
	CUsuario dtoReqToUsuario(UsuarioReqDto usuario);
	
	
	default EnumRol convertToEnum (String rol) {
		return EnumRol.valueOf(rol);
		
	}
	
	// CONVERT DE USUARIO A DTO RESPONSE
	
	@Mapping(target="rol", expression="java( convertToString( usuario.getRol() )  )" )
	UsuarioResDto usuarioToDtores(CUsuario usuario);
	
	default String convertToString(EnumRol rol) {
		 return rol.name();
	}
}
