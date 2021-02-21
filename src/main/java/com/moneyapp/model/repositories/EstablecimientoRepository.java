package com.moneyapp.model.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.moneyapp.model.entities.Cometido;
import com.moneyapp.model.entities.Establecimiento;

public interface EstablecimientoRepository extends CrudRepository<Establecimiento, Integer>{

	@Query(value="SELECT * FROM establecimiento", nativeQuery = true)
	public List<Establecimiento>getTodosLosEstablecimientos();
	
	@Query(value="SELECT * FROM establecimiento where nombre=?", nativeQuery = true)
	public Establecimiento getEstablecimientoPorNombre(String nombreEstablecimiento);
}
