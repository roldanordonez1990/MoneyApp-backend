package com.moneyapp.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moneyapp.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	public Usuario findByUsername(String username);
    public Usuario findByUsernameAndPassword(String username,String password);
    
}
 