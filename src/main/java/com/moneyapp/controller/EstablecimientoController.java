package com.moneyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapp.model.entities.Establecimiento;
import com.moneyapp.model.repositories.EstablecimientoRepository;

@RestController
public class EstablecimientoController {
	
	@Autowired
	EstablecimientoRepository estaRep;
	
	@GetMapping("establecimiento/all")
	public Iterable<Establecimiento> getAllEstablecimientos () {
		return this.estaRep.findAll();
	}

}
