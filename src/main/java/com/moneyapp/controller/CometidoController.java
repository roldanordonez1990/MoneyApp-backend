package com.moneyapp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapp.jwtSeguridad.AutenticadorJWT;
import com.moneyapp.model.entities.Cometido;
import com.moneyapp.model.entities.Cuenta;
import com.moneyapp.model.entities.Establecimiento;
import com.moneyapp.model.repositories.CometidoRepository;

@CrossOrigin
@RestController
public class CometidoController {

	@Autowired
	CometidoRepository cometidoRep;

	// Método principal con el que obtengo todos los cometidos de un usuario. En la
	// request irá el id del usuario, que es enviado desde el cliente gracias el
	// HTTPINTERCEPTOR
	// Creo y envío DTO porque existen objetos dentro de objetos a los cuales debo
	// acceder de esta manera en lugar de devolver simples objetos que no me darían
	// todos los valores. DTO dento de DTO para trabajar mejor
	@GetMapping("cometidos/all")
	public DTO cometidosRealizados(HttpServletRequest request) {
		DTO dto = new DTO();
		//Asumo que va a fallar
		dto.put("result", "fail");
		try {
			//Obtengo el id del usuario a través del token del request del cliente
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			//Listado de cometidos (entidad) gracias al método del repositorio
			List<Cometido> cometido = this.cometidoRep.getTodosLosCometidos(idUsuAutenticado);
			//Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> cometidosDto = new ArrayList<DTO>();
			//Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO cometidos
			for (Cometido c : cometido) {
				cometidosDto.add(getCometidosDTO(c));
			}
			dto.put("cometidos", cometidosDto);
			dto.put("result", "ok");
		} catch (Exception e) {

		}
		// public List<Cometido>cometidosRealizados(HttpServletRequest request) {
		// int idUsuAutenticado =
		// AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
		// return this.cometidoRep.getTodosLosCometidos(idUsuAutenticado);
		return dto;
	}

	// Método cuya función es crear un dto con los datos completos del cometido. Que
	// son los que se enviarán en el método anterior al cliente
	private DTO getCometidosDTO(Cometido c) {

		DTO dto = new DTO();
		dto.put("idcometido", c.getIdcometido());
		dto.put("gasto", c.getGasto());
		dto.put("categoria", c.getCategoria());
		DTO lugar = getEstablecimientoIdAndNombreDTO(c.getEstablecimiento());
		dto.put("lugar", lugar);
		dto.put("fecha", c.getFecha());
		DTO cuenta = getCuentaUsuarioDTO(c.getCuenta());
		dto.put("id_cuenta", cuenta);
		dto.put("comision", c.getComision());

		return dto;

	}
	
	//Creo este DTO para proporcionar su contenido al anterior DTO
	private DTO getEstablecimientoIdAndNombreDTO(Establecimiento e) {

		DTO dto = new DTO();
		dto.put("idestablecimiento", e.getIdestablecimiento());
		dto.put("nombre", e.getNombre());

		return dto;

	}
	//Creo este DTO para proporcionar su contenido al anterior DTO
	private DTO getCuentaUsuarioDTO(Cuenta cu) {

		DTO dto = new DTO();
		dto.put("num_cuenta", cu.getNumCuenta());

		return dto;

	}

}
