package com.moneyapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapp.jwtSeguridad.AutenticadorJWT;
import com.moneyapp.model.entities.Cuenta;
import com.moneyapp.model.repositories.CuentaRepository;

@CrossOrigin
@RestController
public class CuentasController {

	@Autowired
	CuentaRepository cuentaRep;

	@GetMapping("cuentasUsuario/all")
	public DTO todasLasCuentasDeUsuario(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			List<Cuenta> cuentas = this.cuentaRep.getTodasLasCuentasPorUsuario(idUsuAutenticado);
			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> cuentasDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos
			for (Cuenta c : cuentas) {
				cuentasDTO.add(getCuentaAndSaldo(c));
			}
			dto.put("cuentas", cuentasDTO);
			dto.put("result", "ok");
		} catch (Exception e) {

		}

		return dto;
	}
	/**
	 * 
	 */
	@GetMapping("cuentas/all")
	public DTO todasLasCuentas(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			List<Cuenta> cuentas = (List<Cuenta>) this.cuentaRep.findAll();
			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> cuentasDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos
			for (Cuenta c : cuentas) {
				cuentasDTO.add(getCuentaAndSaldo(c));
			}
			dto.put("todasLasCuentas", cuentasDTO);
			dto.put("result", "ok");
		} catch (Exception e) {

		}

		return dto;
	}
	/**
	 * 
	 */
	@GetMapping("cuentas/allPorNombre")
	public DTO todasLasCuentasPorNombre(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			List<Cuenta> cuentas = (List<Cuenta>) this.cuentaRep.getCuentaPorNombre(idUsuAutenticado);
			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> cuentasPorNombreDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos
			for (Cuenta c : cuentas) {
				cuentasPorNombreDTO.add(getCuentaAndSaldoPorNombre(c));
			}
			dto.put("todasLasCuentasPorNombre", cuentasPorNombreDTO);
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
	private DTO getCuentaAndSaldo(Cuenta c) {
		DTO dto = new DTO();
		dto.put("num_cuenta", c.getNumCuenta());
		dto.put("saldo", c.getSaldo());
		return dto;

	}
	
	private DTO getCuentaAndSaldoPorNombre(Cuenta c) {
		DTO dto = new DTO();
		dto.put("num_cuenta", c.getNumCuenta());
		dto.put("saldo", c.getSaldo());
		dto.put("username", c.getUsuario().getUsername());
		return dto;

	}
	
	/**
	 * 
	 */
	
	@PutMapping("/ingreso/nuevo")
	public DTO nuevoIngresoEnCuenta(@RequestBody DatosIngreso datosIngresos, HttpServletRequest request) {
		
		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			Cuenta cuentaAingresar = this.cuentaRep.getCuentaPorNumero(datosIngresos.num_cuenta);
			Cuenta cuentaQueIngresa = this.cuentaRep.getCuentaUsuario(idUsuAutenticado);
			
			//float saldoCuentaAingresar = cuentaAingresar.getSaldo();
			cuentaAingresar.setSaldo(cuentaAingresar.getSaldo() + datosIngresos.saldo);
			//float saldoCuentaQueIngresa = cuentaQueIngresa.getSaldo();
			cuentaQueIngresa.setSaldo(cuentaQueIngresa.getSaldo() - datosIngresos.saldo);
			this.cuentaRep.save(cuentaAingresar);
			this.cuentaRep.save(cuentaQueIngresa);
			dto.put("result", "ok");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dto;
		
	}
	
}

/**
 * Clase interna que contiene los datos de los ingresos para sacarlos por el
 * cliente
 */
class DatosIngreso {
	float saldo;
	int num_cuenta;

	/**
	 * Constructor
	 */
	public DatosIngreso(float saldo, int num_cuenta) {
		super();
		this.saldo = saldo;
		this.num_cuenta = num_cuenta;

	}
}

