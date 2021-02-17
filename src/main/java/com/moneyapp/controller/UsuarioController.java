package com.moneyapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapp.jwtSeguridad.AutenticadorJWT;
import com.moneyapp.model.entities.Usuario;
import com.moneyapp.model.repositories.UsuarioRepository;

@CrossOrigin
@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuRep;
	
	//Buscar todos los usuarios 
	@GetMapping("usuario/all")
	public Iterable<Usuario> encuentraUsuario() {

		return this.usuRep.findAll();
	}
	
	//Autenticar un usuario por logueo con DTO
	@PostMapping("usuario/autenticadoNormal")
	public DTO usuarioAutenticado(@RequestBody DatosUsuario datos) {

		DTO dto = new DTO();
		dto.put("usuario", usuRep.findByUsernameAndPassword(datos.username, datos.password));
		return dto;

	}
	
	//Método principal con el que obtengo un jwt del usuario logueado. Dentro del jwt obtenido va el id del usuario
	@PostMapping("usuario/autenticadoJWT")
	public DTO usuarioAutenticadoJWT(@RequestBody DatosUsuario datos) {

		DTO dto = new DTO();
		Usuario uAutenticado = (Usuario) usuRep.findByUsernameAndPassword(datos.username, datos.password);
		if (uAutenticado != null) {
			dto.put("jwt", AutenticadorJWT.codificaJWT(uAutenticado));
		}
		// Lo que devuelve es el jwt creado con un id del usuario en su interior
		return dto;

	}
}

/**
 * Clase interna que contiene los datos de autenticacion del usuario
 */
class DatosUsuario {
	String username;
	String password;

	/**
	 * Constructor
	 */
	public DatosUsuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
}
