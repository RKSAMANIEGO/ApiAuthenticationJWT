package com.example.demo.model;
import com.example.demo.model.Enum.EnumRol;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;


@Entity
@Table(name="usuario")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50 , nullable = false , unique=true)
	private String username;
	
	@Column(length = 90 , nullable = false , unique=true)
	private String email;
	
	@Column(length = 90 , nullable = false )
	private String password;
	
	@Enumerated(EnumType.STRING)
	private EnumRol rol;
	
	@Default
	private Boolean isEnabled=true;
}
