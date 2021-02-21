package com.moneyapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	/**
	 * 
	 */
	
	@PostMapping("/usuario/nuevoRegistro")
	public DTO nuevoUsuarioRegistrado(@RequestBody DatosUsuarioNuevoRegistro datosNuevo) {
		
		DTO dto = new DTO();
		
		dto.put("result", "fail");
		try {
			Usuario u = new Usuario();
			
			u.setUsername(datosNuevo.username);
			u.setEmail(datosNuevo.email);
			u.setPassword(datosNuevo.password);
			this.usuRep.save(u);
			dto.put("result", "ok");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dto;
		
	}
	
	/**
	 * 
	 * @param datosNuevo
	 * @return
	 */
	
	@PutMapping("/usuario/updateDatos")
	public DTO updateDatosUsurio(@RequestBody DatosUsuarioNuevoRegistro datosNuevo, HttpServletRequest request) {
		
		DTO dto = new DTO();
		
		dto.put("result", "fail");
		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			Usuario u = this.usuRep.findById(idUsuAutenticado).get();
			u.setUsername(datosNuevo.username);
			u.setEmail(datosNuevo.email);
			u.setPassword(datosNuevo.password);
			this.usuRep.save(u);
			dto.put("result", "ok");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dto;
		
	}
	/**
	 * 
	 * @param foto
	 * @param request
	 * @return
	 */
	
	@GetMapping("/usuario/autenticadoImagen")
	public DTO getUsuarioAutenticado (boolean foto, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo el usuario autenticado, por su JWT

		// Intento localizar un usuario a partir de su id
		Usuario usuAutenticado = usuRep.findById(idUsuAutenticado).get();
		if (usuAutenticado != null) {
			dto.put("idusuario", usuAutenticado.getIdusuario());
			dto.put("username", usuAutenticado.getUsername());
			dto.put("password", usuAutenticado.getPassword());
			dto.put("email", usuAutenticado.getEmail());
			dto.put("foto", foto? usuAutenticado.getFoto() : "");
		}

		// Finalmente devuelvo el JWT creado, puede estar vacío si la autenticación no ha funcionado
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

/**
 * Clase interna que contiene los datos de autenticacion del usuario
 */
class DatosUsuarioNuevoRegistro {
	String username;
	String email;
	String password;

	/**
	 * Constructor
	 */
	public DatosUsuarioNuevoRegistro(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
