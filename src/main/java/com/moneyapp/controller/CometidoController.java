package com.moneyapp.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapp.jwtSeguridad.AutenticadorJWT;
import com.moneyapp.model.entities.Cometido;
import com.moneyapp.model.repositories.CometidoRepository;


@CrossOrigin
@RestController
public class CometidoController {
	
	@Autowired
	CometidoRepository cometidoRep;
	
	@GetMapping("cometidos/all")
	public List<Cometido>cometidosRealizados(HttpServletRequest request) {
		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
		return this.cometidoRep.getTodosLosCometidos(idUsuAutenticado);
	}

}
