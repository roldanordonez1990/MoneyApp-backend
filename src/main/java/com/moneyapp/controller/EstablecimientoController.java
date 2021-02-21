package com.moneyapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapp.jwtSeguridad.AutenticadorJWT;
import com.moneyapp.model.entities.Cuenta;
import com.moneyapp.model.entities.Establecimiento;
import com.moneyapp.model.repositories.EstablecimientoRepository;
@CrossOrigin
@RestController
public class EstablecimientoController {

	@Autowired
	EstablecimientoRepository estaRep;

	@GetMapping("establecimiento/all")
	public Iterable<Establecimiento> getAllEstablecimientos() {
		return this.estaRep.findAll();
	}

	@GetMapping("establecimientosAngular/all")
	public DTO todasLasCuentas(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			List<Establecimiento> establecimientos = (List<Establecimiento>) this.estaRep.findAll();
			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> establecimientosDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos
			for (Establecimiento e : establecimientos) {
				establecimientosDTO.add(getNombreEstablecimiento(e));
			}
			dto.put("establecimiento", establecimientosDTO);
			dto.put("result", "ok");
		} catch (Exception e) {

		}

		return dto;
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private DTO getNombreEstablecimiento(Establecimiento e) {
		DTO dto = new DTO();
		dto.put("idestablecimiento", e.getIdestablecimiento());
		dto.put("nombre", e.getNombre());
		return dto;

	}

}
