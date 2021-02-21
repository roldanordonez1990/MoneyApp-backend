package com.moneyapp.model.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.moneyapp.model.entities.Cometido;

public interface CometidoRepository extends CrudRepository<Cometido, Integer>{
	
	
	@Query(value="SELECT c.* FROM cometido as c where c.id_user=? order by c.fecha desc", nativeQuery = true)
	public List<Cometido>getTodosLosCometidos(int idUsuario);
	

}
